import static java.lang.Math.min;

public class Board {


    public int x_axis;
    public int y_axis;

    int[][] map;


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
        map[x_axis / 2 - 1][y_axis / 2 - 1] = 1;
        map[x_axis / 2][y_axis / 2 - 1] = 2;
        map[x_axis / 2][y_axis / 2] = 1;
        map[x_axis / 2 - 1][y_axis / 2] = 2;
    }

    /*
    public boolean legalSpots(int playerTurn) {
        boolean anyLegalSpots = false;
        for (int x = 0; x < x_axis; x++) {
            for (int y = 0; y < y_axis; y++) {
                boolean legalSpot = false;
                if (map[x][y] == 3) {
                    map[x][y] = 0;
                }
                if (map[x][y] == 0) {

                    //Compare up left
                    if ((x > 0 && y > 0) && map[x - 1][y - 1] == 3 - playerTurn) {
                        int checks = min(x - 1, y - 1);
                        for (int l = 1; l <= checks; l++) {
                            if (map[x - 1 - l][y - 1 - l] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                legalSpot = true;
                                break;

                            } else if (map[x - 1 - l][y - 1 - l] != 3 - playerTurn) {
                                break;
                            }
                        }
                    }
                    if (legalSpot) continue;
                    //Compare up
                    if ((y > 0) && map[x][y - 1] == 3 - playerTurn) {
                        int checks = y - 1;
                        for (int l = 1; l <= checks; l++) {
                            if (map[x][y - 1 - l] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                legalSpot = true;
                                break;
                            } else if (map[x][y - 1 - l] != 3 - playerTurn) {
                                break;
                            }
                        }
                    }

                    if (legalSpot) continue;

                    //Compare up right
                    if ((x < x_axis - 1 && y > 0) && map[x + 1][y - 1] == 3 - playerTurn) {
                        int checks = min(x_axis - x - 2, y - 1);
                        for (int l = 1; l <= checks; l++) {
                            if (map[x + 1 + l][y - 1 - l] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                legalSpot = true;
                                break;
                            } else if (map[x + 1 + l][y - 1 - l] != 3 - playerTurn) {
                                break;
                            }

                        }
                    }

                    if (legalSpot) continue;
                    //Compare left
                    if (x > 0 && map[x - 1][y] == 3 - playerTurn) {
                        int checks = x - 1;
                        for (int l = 1; l <= checks; l++) {
                            if (map[x - 1 - l][y] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                legalSpot = true;
                                break;
                            } else if (map[x - 1 - l][y] != 3 - playerTurn) {
                                break;
                            }
                        }
                    }

                    if (legalSpot) continue;
                    //Compare right
                    if (x < x_axis - 1 && map[x + 1][y] == 3 - playerTurn) {
                        int checks = x_axis - x - 2;
                        for (int l = 1; l <= checks; l++) {
                            if (map[x + 1 + l][y] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                legalSpot = true;
                                break;
                            } else if (map[x + 1 + l][y] != 3 - playerTurn) {
                                break;
                            }
                        }
                    }

                    if (legalSpot) continue;
                    //Compare down left
                    if ((x > 0 && y < y_axis - 1) && map[x - 1][y + 1] == 3 - playerTurn) {
                        int checks = min(x - 1, y_axis - y - 2);
                        for (int l = 1; l <= checks; l++) {
                            if (map[x - 1 - l][y + 1 + l] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                legalSpot = true;
                                break;
                            } else if (map[x - 1 - l][y + 1 + l] != 3 - playerTurn) {
                                break;
                            }
                        }

                    }

                    if (legalSpot) continue;
                    //Compare down
                    if (y < y_axis - 1 && map[x][y + 1] == 3 - playerTurn) {
                        int checks = y_axis - y - 2;
                        for (int l = 0; l <= checks; l++) {
                            if (map[x][y + 1 + l] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                legalSpot = true;
                                break;
                            } else if (map[x][y + 1 + l] != 3 - playerTurn) {
                                break;
                            }
                        }
                    }

                    if (legalSpot) continue;
                    //Compare down left
                    if ((x < x_axis - 1 && y < y_axis - 1) && map[x + 1][y + 1] == 3 - playerTurn) {
                        int checks = min(x_axis - x - 2, y_axis - y - 2);
                        for (int l = 1; l <= checks; l++) {
                            if (map[x + 1 + l][y + 1 + l] == playerTurn) {
                                map[x][y] = 3;
                                anyLegalSpots = true;
                                break;
                            } else if (map[x + 1 + l][y + 1 + l] != 3 - playerTurn) {
                                break;
                            }
                        }
                    }


                }
            }
        }
        return anyLegalSpots;
    }
    */

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
                        if (dx == 0 && dy == 0) {
                            continue;
                        }
                        if (!isOnBoard(x + dx, y + dy)) {
                            continue;
                        }
                        if (map[x + dx][y + dy] != 3 - playerTurn) {
                            continue;
                        }

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

        if (playerTurn != 1 && playerTurn != 2) {
            System.out.println("Value has to be 1 or 2");
            return false;
        }
        if (x < 0 || x >= x_axis || y < 0 || y >= y_axis || map[x][y] != 3) {
            System.out.println("Not possible placement");
            return false;
        }

        map[x][y] = playerTurn;

        //Checking which pieces to flip
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                if (!isOnBoard(x + dx, y + dy)) {
                    continue;
                }
                if (map[x + dx][y + dy] != 3 - playerTurn) {
                    continue;
                }
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
                if (flipDirection){
                    while (l > 0) {
                        l--;
                        map[x + dx * l][y + dy * l] = playerTurn;

                    }
                }



            }
        }


        return true;
    }

    //Set a value for a given spot (ONLY FOR TESTING)
    public void setTestValue(int x, int y, int value) {
        if (map[x][y] != 3) {
            System.out.println("Not possible placement");
        } else {
            map[x][y] = value;
        }

    }

    public int[] getScore(){
        int[] score = {0,0};
        for (int x = 0; x<x_axis;x++){
            for (int y = 0; y<y_axis;y++){
                if (map[x][y]==1){
                    score[0]++;
                } else if (map[x][y]==2){
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
        for (int x=0;x<x_axis;x++){
            System.out.print(x+" ");
        }
        System.out.print("\n   _");
        for (int x=0;x<x_axis;x++){
            System.out.print(" _");
        }
        System.out.println();
        for (int i = 0; i < y_axis; i++) {
            System.out.print(i+" | ");
            for (int j = 0; j < x_axis; j++) {
                if (map[j][i]==0){
                    System.out.print("- ");
                } else if (map[j][i]==3) {
                    System.out.print("* ");
                } else {
                    System.out.print(map[j][i] + " ");
                }
            }
            System.out.println();
        }


        return null;
    }


}
