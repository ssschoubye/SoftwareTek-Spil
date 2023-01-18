public class MiniMaxAlphaBetaAI {


    private static Board board;
    private static int maxDepth;
    //static int exploredChildren = 0;
    private static boolean has4 = true;

    public MiniMaxAlphaBetaAI(Board board, int maxDepth) {

        MiniMaxAlphaBetaAI.board = board;
        MiniMaxAlphaBetaAI.maxDepth = maxDepth;
    }


    public static void AIMakeMove(int playerTurn) {

        int bestX = 0;
        int bestY = 0;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if(has4){
            has4=false;
            for (int x = 0; x < board.x_axis && !has4; x++) {
                for (int y = 0; y < board.y_axis&& !has4; y++) {
                    if (board.map[x][y] == 4) {
                        has4 = true;
                    }
                }
            }
        }

        if(!has4){
            board.legalSpots(playerTurn);
        }

        for (int x = 0; x < board.x_axis; x++) {
            for (int y = 0; y < board.y_axis; y++) {
                if (board.map[x][y] != 3 && board.map[x][y] != 4) continue;
                Board copy = board.copy();
                copy.legalSpots(playerTurn);
                copy.placePiece(x, y, playerTurn);
                if(playerTurn==1){
                    int value = maxValue(copy, playerTurn,1, alpha, beta);
                    if (value < beta) {
                        beta = value;
                        bestX = x;
                        bestY = y;
                    }
                }else if(playerTurn==2){
                    int value = minValue(copy, playerTurn,1, alpha, beta);
                    if (value > alpha) {
                        alpha = value;
                        bestX = x;
                        bestY = y;
                    }

                }
            }
        }

        board.placePiece(bestX, bestY, playerTurn);
        //System.out.println(exploredChildren);
    }


    private static int maxValue(Board board, int playerTurn, int depth, int alpha, int beta) {
        //exploredChildren++;
        if (depth == maxDepth || !board.legalSpots(playerTurn)) {
            //System.out.println("" + depth + " lag nede i træet er den højeste score for " + Visualizer.turnColor(playerTurn) +", hvis " + Visualizer.turnColor(Board.turnSwitch(playerTurn)) + " spiller perfekt, =" +Board.evaluateScore(board,playerTurn));
            return boardHeuristic(board);

        }
        int bestScore = Integer.MIN_VALUE;
        Board copy = board.copy();
        copy.legalSpots(playerTurn);

        for (int x = 0; x < copy.x_axis; x++) {
            for (int y = 0; y < copy.y_axis; y++) {
                if (copy.map[x][y] != 3 && copy.map[x][y] != 4) continue;
                copy.placePiece(x, y, playerTurn);
                bestScore = Math.max(bestScore, minValue(copy, Board.turnSwitch(playerTurn),depth + 1, alpha, beta));
                alpha = Math.max(alpha, bestScore);
                if (alpha >= beta) {
                    //System.out.println("Beta cutoff performed");
                    //System.out.println("Alpha is = " + alpha);
                    break; //beta cutoff
                }
            }
        }
        return bestScore;
    }

    private static int minValue(Board board, int playerTurn, int depth, int alpha, int beta) {
        if (depth == maxDepth || !board.legalSpots(playerTurn)) {
            //System.out.println("" + depth + " lag nede i træet er den laveste score for " + Visualizer.turnColor(playerTurn) +", hvis " + Visualizer.turnColor(Board.turnSwitch(playerTurn)) + " spiller perfekt, =" +Board.evaluateScore(board,playerTurn));
            return boardHeuristic(board);
        }
        int bestScore = Integer.MAX_VALUE;
        Board copy = board.copy();
        copy.legalSpots(playerTurn);

        for (int x = 0; x < copy.x_axis; x++) {
            for (int y = 0; y < copy.y_axis; y++) {
                if (copy.map[x][y] != 3 && copy.map[x][y] != 4) continue;
                copy.placePiece(x, y, playerTurn);
                bestScore = Math.min(bestScore, maxValue(copy, Board.turnSwitch(playerTurn),depth + 1, alpha, beta)); //max og max??
                beta = Math.min(beta, bestScore);
                if (alpha >= beta) {
                    //System.out.println("Alpha cutoff performed");
                    //System.out.println("Beta is = "+beta);
                    break; //alpha cutoff
                }
            }
        }
        return bestScore;
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







}