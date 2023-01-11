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
                if(map[x][y]== 4){
                    anyLegalSpots= true;
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

    public static int weigthedScore(Board board) {
        int score = 0;
        int whiteCorners = 1;
        int blackCorners = 1;
        int whiteMobility = 1;
        int blackMobility = 1;
        int whiteStability = 1;
        int blackStability = 1;
        int[][] staticWeights;
        int[] dx = {1,1,0,-1,-1,-1,0,1};
        int[] dy = {0,1,1,1,0,-1,-1,-1};


        if(board.x_axis==4){
            staticWeights = new int[][]{{4, -3, -3, 4},
                    {-3, 1, 1, -3},
                    {-3, 1, 1, -3},
                    {4, -3, -3, 4}};
        }

        if(board.x_axis==8){
            staticWeights = getInts();
        }
        if(board.x_axis==12){
            staticWeights = new int[][]{{4,-3,2,2,2,2,2,2,2,2,-3,4},
                                    {-3,-4,-1,-1,-1,-1,-1,-1,-1,-1,-4,-3},
                                    {2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,2},
                                    {2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,2},
                                    {2,-1,-1,-1,1,0,0,1,-1,-1,-1,2},
                                    {2,-1,-1,-1,0,1,1,0,-1,-1,-1,2},
                                    {2,-1,-1,-1,0,1,1,0,-1,-1,-1,2},
                                    {2,-1,-1,-1,1,0,0,1,-1,-1,-1,2},
                                    {2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,2},
                                    {2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,2},
                                    {-3,-4,-1,-1,-1,-1,-1,-1,-1,-1,-4,-3},
                                    {4,-3,2,2,2,2,2,2,2,2,-3,4}};
        }else{
            staticWeights = getInts();
        }

            //Runs through all squares on the playing board
            for (int x = 0; x < board.x_axis; x++) {
                for (int y = 0; y < board.y_axis; y++) {



                    // Check if the current square is a corner
                    if ((x == 0 || x == board.x_axis - 1) && (y == 0 || y == board.y_axis - 1)) {
                        if (board.map[x][y] == 1) {
                            whiteCorners += 30;
                        }else if(board.map[x][y] == 2){
                            blackCorners += 30;
                    }
                    }

                    // Check if the placed piece is stable, and if it grants the opponent additional possible moves
                    for (int i = 0; i < dx.length; i++) {
                        int xx = x + dx[i];
                        int yy = y + dy[i];
                        if (xx >= 0 && xx < dx.length && yy >= 0 && yy < dy.length && board.map[xx][yy] == 0) {
                            if (board.map[x][y] == 1) {
                                blackMobility += 5; //blackMobility+
                            }if (isStable(board, x, y, 1)) {
                                whiteStability += 25;
                            }

                            else if (board.map[x][y] == 2) {
                                whiteMobility += 5; //whiteMobility+
                            } if (isStable(board, x, y, 2)) {
                                blackStability += 25;
                            }
                        }
                    }

                    // Add points for each piece on the board
                    if (board.map[x][y] == 1) {
                        score += 25*staticWeights[x][y]*whiteCorners*whiteMobility*whiteStability;
                    }else if(board.map[x][y] == 2){
                        score -= 25*staticWeights[x][y]*blackCorners*blackMobility*blackStability;
                    }
                }
            }
            return score;
    }

    private static int[][] getInts() {
        int[][] staticWeights;
        staticWeights = new int[][]{{4,-3,2,2,2,2,-3,4},
                {-3,-4,-1,-1,-1,-1,-4,-3},
                {2,-1,1,0,0,1,-1,2},
                {2,-1,0,1,1,0,-1,2},
                {2,-1,0,1,1,0,-1,2},
                {2,-1,1,0,0,1,-1,2},
                {-3,-4,-1,-1,-1,-1,-4,-3},
                {4,-3,2,2,2,2,-3,4}};
        return staticWeights;
    }

    private static boolean isStable(Board board, int x, int y,int piece) {

        int end1 = 10;
        int end2 = 10;

        int dx = 0;
        int dy = 0;

        int l = 1;

        //Direction 1
        dx = -1;
        dy = -1;
        while(board.isOnBoard(x+l*dx,y+l*dy) && board.map[x+l*dx][y+l*dy] == piece){

            l++;
        } if(board.isOnBoard(x+l*dx,y+l*dy)){
            end1=board.map[x+l*dx][y+l*dy];

            dx = 1;
            dy = 1;
            l=1;
            while(board.isOnBoard(x+l*dx,y+l*dy) && board.map[x+l*dx][y+l*dy] == piece){
                l++;
            }  if(board.isOnBoard(x+l*dx,y+l*dy)) {
                end2 = board.map[x + l * dx][y + l * dy];
            } if (end2 != 10 && end1 != end2){
                return false;
            }
        }


        //Direction 2
        l=1;
        end1=10;
        end2=10;
        dx = 0;
        dy = -1;
        while(board.isOnBoard(x+l*dx,y+l*dy) && board.map[x+l*dx][y+l*dy] == piece){

            l++;
        } if(board.isOnBoard(x+l*dx,y+l*dy)){
            end1=board.map[x+l*dx][y+l*dy];

            dx = 0;
            dy = 1;
            l=1;
            while(board.isOnBoard(x+l*dx,y+l*dy) && board.map[x+l*dx][y+l*dy] == piece){
                l++;
            }  if(board.isOnBoard(x+l*dx,y+l*dy)) {
                end2 = board.map[x + l * dx][y + l * dy];
            } if (end2 != 10 && end1 != end2){
                return false;
            }
        }
        //Direction 3
        l=1;
        end1=10;
        end2=10;
        dx = 1;
        dy = -1;
        while(board.isOnBoard(x+l*dx,y+l*dy) && board.map[x+l*dx][y+l*dy] == piece){

            l++;
        } if(board.isOnBoard(x+l*dx,y+l*dy)){
            end1=board.map[x+l*dx][y+l*dy];

            dx = -1;
            dy = 1;
            l=1;
            while(board.isOnBoard(x+l*dx,y+l*dy) && board.map[x+l*dx][y+l*dy] == piece){
                l++;
            }  if(board.isOnBoard(x+l*dx,y+l*dy)) {
                end2 = board.map[x + l * dx][y + l * dy];
            } if (end2 != 10 && end1 != end2){
                return false;
            }
        }
        //Direction 4
        l=1;
        end1=10;
        end2=10;
        dx = 1;
        dy = 0;
        while(board.isOnBoard(x+l*dx,y+l*dy) && board.map[x+l*dx][y+l*dy] == piece){

            l++;
        } if(board.isOnBoard(x+l*dx,y+l*dy)){
            end1=board.map[x+l*dx][y+l*dy];

            dx = -1;
            dy = 0;
            l=1;
            while(board.isOnBoard(x+l*dx,y+l*dy) && board.map[x+l*dx][y+l*dy] == piece){
                l++;
            }  if(board.isOnBoard(x+l*dx,y+l*dy)) {
                end2 = board.map[x + l * dx][y + l * dy];
            } if (end2 != 10 && end1 != end2){
                return false;
            }
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
            } else if (gameNumber % 2 == 1) {
                return firstStartingPlayer;
            }
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

}




