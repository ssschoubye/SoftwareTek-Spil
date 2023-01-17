public class Board {


    public int x_axis;
    public int y_axis;

    int[][] map;

    static int gamemode;


    public Board(int x_axis, int y_axis) {
        this.y_axis = y_axis;
        this.x_axis = x_axis;
        this.map = new int[x_axis][y_axis];
    }

    public Board(int size) {
        this.y_axis = size;
        this.x_axis = size;
        map = new int[size][size];
    }

    public Board() {
        //Constructor der bliver benyttet i Server.
        int[][] map;
    }

    public void initialize() {
        map[x_axis / 2 - 1][y_axis / 2 - 1] = 4;
        map[x_axis / 2][y_axis / 2 - 1] = 4;
        map[x_axis / 2][y_axis / 2] = 4;
        map[x_axis / 2 - 1][y_axis / 2] = 4;

    }

    public boolean legalSpots(int playerTurn) {
        boolean anyLegalSpots = false;
        for (int x = 0; x < x_axis; x++) {
            for (int y = 0; y < y_axis; y++) {
                if (map[x][y] == 3) {
                    map[x][y] = 0;
                }
                if (map[x][y] != 0) continue;
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (checkXDxYDy(x, y, playerTurn, dx, dy)) continue;

                        int l = 0;

                        while (true) {
                            l++;
                            if (!isOnBoard(x + dx * l, y + dy * l)) {
                                break;
                            }
                            if (map[x + dx * l][y + dy * l] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                break;
                            } else if (map[x + dx * l][y + dy * l] != 3 - playerTurn) {
                                break;
                            }
                        }
                    }
                }
            }
        }
        return anyLegalSpots;
    }

    public boolean isOnBoard(int x, int y) {
        return x >= 0 && y >= 0 && x < x_axis && y < y_axis;
    }

    public void clearLegalSpots() {
        for (int x = 0; x < x_axis; x++) {
            for (int y = 0; y < y_axis; y++) {
                if (map[x][y] == 3) {
                    map[x][y] = 0;
                }
            }
        }
    }

    public boolean placePiece(int x, int y, int playerTurn) {
        // Place the piece on the board
        if (!isOnBoard(x,y) || (map[x][y] != 3 && map[x][y] != 4)) {
            System.out.println("Not possible placement");
            return false;
        }
        map[x][y] = playerTurn;

        // Check for captured pieces in each direction and allow the player to manually flip them
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (checkXDxYDy(x, y, playerTurn, dx, dy)) continue;
                flipCapturedPieces(x, y, dx, dy, playerTurn);
                //manualFlip(playerTurn);
            }
        }
        return true;
    }

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

    public int[] getScore() {
        int[] score = {0, 0};
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

    //print the whole board using toString (ONLY FOR TESTING)
    public String printOut() {
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


        return null;
    }

    public static int turnSwitch(int currentTurn) {
        return currentTurn % 2 + 1;
    }

    public void flipCapturedPieces(int x, int y, int dx, int dy, int playerTurn) {
        // Check if there are any captured pieces in the specified direction
        boolean flipDirection = false;
        int l = 0;
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
        if (flipDirection) {
            // Flip the captured pieces
            while (l > 0) {
                l--;
                map[x + dx * l][y + dy * l] = playerTurn;
            }
        }
    }

    public int startingPlayer(int gameNumber, int firstStartingPlayer) {
        if (gameNumber % 2 == 0) {
            return turnSwitch(firstStartingPlayer);
        } else if (gameNumber % 2 == 1) {
            return firstStartingPlayer;
        }
        return 0;
    }

    public int[] getDim() {
        int[] dim = {x_axis, y_axis};
        return dim;

    }

    public Board copy() {
        Board copy = new Board(x_axis, y_axis);
        for (int x = 0; x < x_axis; x++) {
            for (int y = 0; y < y_axis; y++) {
                copy.map[x][y] = map[x][y];
            }
        }
        return copy;
    }


    public static int weigthedScore(Board board) {
        int score = 0;


        //Runs through all squares on the playing board
        for (int x = 0; x < board.x_axis; x++) {
            for (int y = 0; y < board.y_axis; y++) {

                // Add points for each piece on the board +1
                if (board.map[x][y] == 1) {
                    score++;
                } else if (board.map[x][y] == 2) {
                    score--;
                }

                // Check if the current square is a corner +1000
                if ((x == 0 || x == board.x_axis - 1) && (y == 0 || y == board.y_axis - 1)) {
                    if (board.map[x][y] == 1) {
                        score += 1000;
                    } else if (board.map[x][y] == 2) {
                        score -= 1000;
                    }
                }

                // Check if the current square is adjacent to a corner -10
                if ((x == 0 || x == board.x_axis - 1 || y == 0 || y == board.y_axis - 1) &&
                        (x == 1 || x == board.x_axis - 2 || y == 1 || y == board.y_axis - 2)) {
                    if (board.map[x][y] == 1) {
                        score -= 10;
                    } else if (board.map[x][y] == 2) {
                        score += 10;
                    }
                }

                // Check if the current square is on the wall but not adjacent to a corner +10
                if ((x == 0 || x == board.x_axis - 1 || y == 0 || y == board.y_axis - 1) &&
                        ((x == 0 || x == board.x_axis - 1 || y == 0 || y == board.y_axis - 1) &&
                                (x == 1 || x == board.x_axis - 2 || y == 1 || y == board.y_axis - 2))) {
                    if (board.map[x][y] == 1) {
                        score += 10;
                    } else if (board.map[x][y] == 2) {
                        score -= 10;
                    }
                }
            }
        }
        return score;
    }


    public int[][] getArray() {
        return map;
    }

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



