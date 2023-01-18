
//Creating an object Board, which is used for representing a game of reversi
public class Board {


    //Every game will have a certain size consisting of an x- and y-axis
    public int x_axis;
    public int y_axis;

    //Every game will have a 2d int array symbolizing the game state
    int[][] map;


    //An object for initializing the object
    public Board(int x_axis, int y_axis) {
        this.y_axis = y_axis;
        this.x_axis = x_axis;
        this.map = new int[x_axis][y_axis];
    }

    //Constructor for the server
    public Board() {
        int[][] map;
    }

    //Method for initializing the game board itself
    //This is done by setting all four middle fields to 4 to indicate where the first four moves can be made
    public void initialize() {
        map[x_axis / 2 - 1][y_axis / 2 - 1] = 4;
        map[x_axis / 2][y_axis / 2 - 1] = 4;
        map[x_axis / 2][y_axis / 2] = 4;
        map[x_axis / 2 - 1][y_axis / 2] = 4;

    }

    //Legal spots shows where a given player can make a move and returns whether of not the player actually has an available move
    public boolean legalSpots(int playerTurn) {
        //Firsts sets that there is no legal spots (As the method is yet to disprove this)
        boolean anyLegalSpots = false;

        //Nested for loop going through all fields in the map of the game
        for (int x = 0; x < x_axis; x++) {
            for (int y = 0; y < y_axis; y++) {

                //Any previous indications of a possible player move is reverted to 0
                if (map[x][y] == 3) {
                    map[x][y] = 0;
                }

                //If the field in the map isn't empty, move on to the next field
                if (map[x][y] != 0) continue;

                //nested for loop looking at the 8 fields around the actual field
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {

                        //checks if not supposed to look through a direction
                        //(Does so if the piece looked at is opposite color of the current player)
                        if (checkXDxYDy(x, y, playerTurn, dx, dy)) continue;

                        //initializes an int for moving further down a direction
                        int l = 0;

                        //While loop checking further down a direction
                        while (true) {
                            l++;
                            //If the coordinate set is not on the game board, the loop breaks
                            if (!isOnBoard(x + dx * l, y + dy * l)) {
                                break;
                            }
                            //If a piece of the color of the current player is found, the initial field is a possible move
                            //In this case, the method also states that there is in fact at least one legal move for the player
                            if (map[x + dx * l][y + dy * l] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                break;

                            //Otherwise if it sees something that is not the color of the opposite player, it breaks
                            } else if (map[x + dx * l][y + dy * l] != 3 - playerTurn) {
                                break;
                            }
                        }
                    }
                }
            }
        }

        //At the end, the method returns whether the player has a possible move
        return anyLegalSpots;
    }

    //A simple method which determines whether a coordinate set is within the game board
    public boolean isOnBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < x_axis && y < y_axis;
    }

    /*
    //A function to clear possible spots (Used for when the AI or another online player has their turn)
    //Ended up not using it for the AI, as the game doesn't show the state in between the players move and the AI's moves
    public void clearLegalSpots() {
        for (int x = 0; x < x_axis; x++) {
            for (int y = 0; y < y_axis; y++) {
                if (map[x][y] == 3) {
                    map[x][y] = 0;
                }
            }
        }
    }*/


    //A method for trying to place a piece. Does so if possible and returns true, otherwise returns false
    public boolean placePiece(int x, int y, int playerTurn) {
        //Checks if coordinate set is not a possible move
        if (!isOnBoard(x,y) || (map[x][y] != 3 && map[x][y] != 4)) {
            return false;
        }

        //sets the piece
        map[x][y] = playerTurn;

        //Checks for which pieces to turn over and does so automatically
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (checkXDxYDy(x, y, playerTurn, dx, dy)) continue;
                flipCapturedPieces(x, y, dx, dy, playerTurn);
            }
        }
        //returns true if the piece could in fact be placed
        return true;
    }

    //A method for checking whether or not to look further down a direction either when placing a piece or when checking if there are any legal spots
    private boolean checkXDxYDy(int x, int y, int playerTurn, int dx, int dy) {
        if (dx == 0 && dy == 0) {
            return true;
        }
        if (!isOnBoard(x + dx, y + dy)) {
            return true;
        }
        return map[x + dx][y + dy] != 3 - playerTurn;
    }

    //Set a value for a given spot (ONLY FOR TESTING)
    public void setTestValue(int x, int y, int value) {
        if (map[x][y] != 3) {
            System.out.println("Not possible placement");
        } else {
            map[x][y] = value;
        }

    }

    //Method for returning an array with length 2, containing the current score for white and black
    public int[] getScore() {
        //Initialises the array
        int[] score = {0, 0};

        //Goes through the entire map and increments the two scores when finding a piece with that color.
        for (int x = 0; x < x_axis; x++) {
            for (int y = 0; y < y_axis; y++) {
                if (map[x][y] == 1) {
                    score[0]++;
                } else if (map[x][y] == 2) {
                    score[1]++;
                }
            }
        }
        return score;
    }

    //print the whole board (ONLY FOR TESTING)
    //Simply goes through the board and prints it out in the terminal
    //Does so with added coordinates on the x- and y-axis
    public void printOut() {
        System.out.print("    ");
        for (int x = 0; x < x_axis; x++) {
            System.out.print(x + "  ");
        }
        System.out.print("\n   _");
        for (int x = 0; x < x_axis; x++) {
            System.out.print("___");
        }
        System.out.println();
        for (int i = 0; i < y_axis; i++) {
            System.out.print(i + " | ");
            for (int j = 0; j < x_axis; j++) {
                if (map[j][i] == 0) {
                    System.out.print("-  ");
                } else if (map[j][i] == 3) {
                    System.out.print("*  ");
                } else {
                    System.out.print(map[j][i] + "  ");
                }
            }
            System.out.println();
        }
    }

    //Takes the current turn and returns the opposite turn
    public static int turnSwitch(int currentTurn) {
        return currentTurn % 2 + 1;
    }

    //Method for flipping pieces when placing one
    public void flipCapturedPieces(int x, int y, int dx, int dy, int playerTurn) {

        boolean flipDirection = false;

        int l = 0;
        //Goes through a direction from where the piece was placed and determines whether to flip this direction
        while (true) {
            l++;
            if (!isOnBoard(x + dx * l, y + dy * l)) {
                break;
            }
            if (map[x + dx * l][y + dy * l] == playerTurn) {
                flipDirection = true;
                break;
            } else if (map[x + dx * l][y + dy * l] != 3 - playerTurn) {
                break;
            }
        }
        //If the direction in the previous loop was determined to be flipped, this while loop does so
        if (flipDirection) {

            while (l > 0) {
                l--;
                map[x + dx * l][y + dy * l] = playerTurn;
            }
        }
    }

    //Method determines who is to start a given game by taken the number of games and who started the first game
    //The method is not used for the first game.
    public int startingPlayer(int gameNumber, int firstStartingPlayer) {
        if (gameNumber % 2 == 0) {
            return turnSwitch(firstStartingPlayer);
        } else if (gameNumber % 2 == 1) {
            return firstStartingPlayer;
        }
        return 0;
    }


    public int[] getDim() { //Returns the dimensions of the board
        int[] dim = {x_axis, y_axis};
        return dim;

    }

    //Creates a copy of the game board, by using the arraycopy method from the Java.lang.System library
    public Board copy() {
        Board copy = new Board(x_axis, y_axis);
        for (int x = 0; x < x_axis; x++) {
            if (y_axis >= 0) System.arraycopy(map[x], 0, copy.map[x], 0, y_axis);
        }
        return copy;
    }

    public int[][] getArray() {
        return map;
    } //Returns the map array

    //A toString method for the object
    //Used for creating the save file for a given game
    @Override
    public String toString() {
        String boardstring = "";
        for (int i = 0; i < y_axis; i++) {
            for (int j = 0; j < x_axis; j++) {
                boardstring += (map[j][i]);
            }
        }
        return boardstring;
    }
}



