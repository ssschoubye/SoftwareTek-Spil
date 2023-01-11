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
            return Board.weigthedScore(board);

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
            return Board.weigthedScore(board);
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

}
