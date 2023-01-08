public class ReversiAI {


        private static Board board;
        private static int maxDepth;

        public ReversiAI(Board board, int maxDepth){

            this.board = board;
        }


        public static void AIMakeMove(int playerTurn){

            int bestX = 0;
            int bestY = 0;
            int alpha = -64;
            int beta = 64;
            int score = 0;
            for (int x = 0; x < board.x_axis; x++) {
                for (int y = 0; y < board.y_axis; y++) {
                    if (board.map[x][y] != 3 && board.map[x][y] != 4) continue;
                    Board copy = board.copy();
                    copy.placePiece(x, y, playerTurn);
                    int value = minValue(copy, playerTurn, 1, alpha, beta);
                    if (value > alpha) {
                        alpha = value;
                        bestX = x;
                        bestY = y;
                    }
                }
            }

            // Place a piece on the best spot
            board.placePiece(bestX, bestY, playerTurn);
        }

    private static int maxValue(Board board, int playerTurn, int depth, int alpha, int beta) {
        if (depth == maxDepth || !board.legalSpots(playerTurn)) {
            return Board.getScoreDif(board);
        }
        int value = Integer.MIN_VALUE;
        for (int x = 0; x < board.x_axis; x++) {
            for (int y = 0; y < board.y_axis; y++) {
                if (board.map[x][y] != 3 && board.map[x][y] != 4) continue;
                Board copy = board.copy();
                copy.placePiece(x, y, playerTurn);
                value = Math.max(value, minValue(copy, 3 - playerTurn, depth + 1, alpha, beta));
                if (value >= beta) {
                    return value;
                }
                alpha = Math.max(alpha, value);
            }
        }
        return value;
    }

    private static int minValue(Board board, int playerTurn, int depth, int alpha, int beta) {
        if (depth == maxDepth || !board.legalSpots(playerTurn)) {
            return Board.getScoreDif(board);
        }
        int value = Integer.MAX_VALUE;
        for (int x = 0; x < board.x_axis; x++) {
            for (int y = 0; y < board.y_axis; y++) {
                if (board.map[x][y] != 3 && board.map[x][y] != 4) continue;
                Board copy = board.copy();
                copy.placePiece(x, y, playerTurn);
                value = Math.min(value, maxValue(copy, 3 - playerTurn, depth + 1, alpha, beta));
                if (value <= alpha) {
                    return value;
                }
                beta = Math.min(beta, value);
            }
        }
        return value;
    }




}
