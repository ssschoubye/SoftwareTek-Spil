public class Board {


    public int x_axis;
    public int y_axis;

    int[][] map;


    public Board(int x_axis, int y_axis) {
        this.y_axis = y_axis;
        this.x_axis = x_axis;
        this.map = new int[x_axis][y_axis];
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
                if (map[x][y] == 4) {
                    anyLegalSpots = true;
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

        if (playerTurn != 1 && playerTurn != 2) {
            System.out.println("Value has to be 1 or 2");
            return false;
        }
        if (x < 0 || x >= x_axis || y < 0 || y >= y_axis || (map[x][y] != 3 && map[x][y] != 4)) {
            System.out.println("Not possible placement");
            return false;
        }
        map[x][y] = playerTurn;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (checkXDxYDy(x, y, playerTurn, dx, dy)) continue;
                flipCapturedPieces(x, y, dx, dy, playerTurn);
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

    public static int boardHeuristic(Board board){

        //Parameters
        int whiteCoins = 0;
        int blackCoins = 0;
        int whiteStability = 0;
        int blackStability = 0;
        int whiteMobility;
        int blackMobility;
        int whiteCorners = 0;
        int blackCorners = 0;

        //Parameter weights
        int coinsWeight = 25;
        int cornerWeight = 500;
        int stabilityWeight = 30;
        int mobilityWeight = 5;

        //Scores
        int coinScore = 0;
        int mobilityScore = 0;
        int stabilityScore = 0;
        int cornerScore = 0;
        int score;

        //Runs through all cells on board and counts parameters
        for(int x=0; x<board.x_axis; x++){
            for(int y=0; y<board.y_axis; y++){
                if(board.map[x][y]==1){
                    whiteCoins++;
                    if(isStable(board, x, y, 1)){
                        whiteStability++;
                    }
                    if((x == 0 || x == board.x_axis - 1) && (y == 0 || y == board.y_axis - 1)){
                        whiteCorners++;
                    }
                }

                if(board.map[x][y]==2){
                    blackCoins++;
                    if(isStable(board, x, y, 2)){
                        blackStability++;
                    }
                    if((x == 0 || x == board.x_axis - 1) && (y == 0 || y == board.y_axis - 1)){
                        blackCorners++;
                    }
                }
            }
        }

        whiteMobility = getMobility(board,1);
        blackMobility = getMobility(board,2);


        if(whiteCoins+blackCoins != 0) {
            coinScore = 100 * (whiteCoins - blackCoins) / (whiteCoins + blackCoins);
        }

        if(whiteMobility+blackMobility != 0) {
            mobilityScore = 100 * (whiteMobility - blackMobility) / (whiteMobility + blackMobility);
        }

        if(whiteCorners+blackCorners != 0) {
            cornerScore = 100 * (whiteCorners - blackCorners) / (whiteCorners + blackCorners);
        }

        if(whiteStability+blackStability != 0) {
            stabilityScore = 100 * (whiteStability - blackStability) / (whiteStability + blackStability);
        }


        score = coinsWeight * coinScore + mobilityWeight * mobilityScore + cornerWeight * cornerScore + stabilityWeight * stabilityScore;

        return score;
    }


    //The term mobility refers to the amount of possible moves a player has. The method below returns this number for a given player
    private static int getMobility(Board board, int playerTurn){
        int mobility = 0;
        Board copy = board.copy();
        copy.legalSpots(playerTurn);
        for(int i=0; i<copy.x_axis; i++){
            for(int j=0; j<copy.y_axis; j++){
                if(copy.map[i][j]==3){
                    mobility++;
                }
            }
        }
    return mobility;
    }


    //The method below determines whether a piece can be flipped by the opponent immediately after placement
    private static boolean isStable(Board board, int x, int y, int piece) {

        if(!stableDirection(board,x,y,-1,-1,piece)) return false;
        if(!stableDirection(board,x,y,0,-1,piece)) return false;
        if(!stableDirection(board,x,y,-1,0,piece)) return false;
        return stableDirection(board, x, y, 1, -1, piece);

    }

    public static boolean stableDirection(Board board,int x,int y, int dx, int dy, int piece){
        int l = 1;
        int end1;
        int end2 = 10;
        while (board.isOnBoard(x + l * dx, y + l * dy) && board.map[x + l * dx][y + l * dy] == piece) {

            l++;
        }
        if (board.isOnBoard(x + l * dx, y + l * dy)) {
            end1 = board.map[x + l * dx][y + l * dy];

            dx *= -1;
            dy *= -1;
            l = 1;
            while (board.isOnBoard(x + l * dx, y + l * dy) && board.map[x + l * dx][y + l * dy] == piece) {
                l++;
            }
            if (board.isOnBoard(x + l * dx, y + l * dy)) {
                end2 = board.map[x + l * dx][y + l * dy];
            }
            return end2 == 10 || end1 == end2;
        }
        return true;
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
            System.out.print("_");
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
            } else {
                return firstStartingPlayer;
            }
        }
        return 0;
    }

    public int[] getDim() {
        return new int[]{x_axis, y_axis};

    }

    public Board copy() {
        Board copy = new Board(x_axis, y_axis);
        for (int x = 0; x < x_axis; x++) {
            if (y_axis >= 0) System.arraycopy(map[x], 0, copy.map[x], 0, y_axis);
        }
        return copy;
    }

}




