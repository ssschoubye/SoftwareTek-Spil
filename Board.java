import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class Board {


    public int x_axis;
    public int y_axis;

    public int[][] map;


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


    public boolean placePiece(int x, int y, int playerTurn) {
        // Place the piece on the board
        if (playerTurn != 1 && playerTurn != 2) {
            System.out.println("Value has to be 1 or 2");
            return false;
        }
        if (x < 0 || x >= x_axis || y < 0 || y >= y_axis || (map[x][y] != 3 && map[x][y] != 4)) {
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
    @Override
    public String toString() {
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
        if (currentTurn == 1) {
            return 2;
        } else if (currentTurn == 2) {
            return 1;
        } else {
            System.out.println("Wrong current turn");
            return 0;
        }
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

        if (gameNumber == 1) {
            return (int) (Math.random() * 2) + 1;
        } else if (gameNumber > 1) {
            if (gameNumber % 2 == 0) {
                return turnSwitch(firstStartingPlayer);
            } else if (gameNumber % 2 == 1) {
                return firstStartingPlayer;
            }
        }
        return 0;
    }

    /*
   public void manualFlip(int playerTurn) {
        toString();
        // Create a scanner to read input from the command line
        Scanner scanner = new Scanner(System.in);
        // Continue prompting the player for coordinates until all captured pieces have been flipped
        while (true) {
            // Prompt the player for the coordinates of a captured piece

            System.out.print("Player " + playerTurn + ", enter the first coordinate of a captured piece to flip it: ");
            int x = scanner.nextInt();
            System.out.print("Player " + playerTurn + ", enter the second coordinate of a captured piece to flip it: ");
            int y = scanner.nextInt();
            // Flip the piece if it is a captured piece
            if (map[x][y] == 4) {
                map[x][y] = playerTurn;
            } else {
                System.out.println("Not a captured piece. Try again.");
            }
            // Check if there are any more captured pieces on the board
            boolean moreCapturedPieces = false;
            for (int i = 0; i < x_axis; i++) {
                for (int j = 0; j < y_axis; j++) {
                    if (map[i][j] == 4) {
                        moreCapturedPieces = true;
                        break;
                    }
                }
            }
            if (!moreCapturedPieces) {
                // If there are no more captured pieces, exit the loop
                break;
            }
        }
    }

     */
    public int[] getDim() {
        int[] dim = {x_axis, y_axis};
        return dim;

    }

    public String boardtransfer() {
        String boardstring = "";
        for (int i = 0; i < y_axis; i++) {
            for (int j = 0; j < x_axis; j++) {
                boardstring += (map[j][i]);
            }
        }
        return boardstring;
    }
}





