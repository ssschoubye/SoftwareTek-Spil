//The main class for the AI, containing a constructor and methods for performing all
// necessary calculations for making a move.
// The entire class and all containing methods are made by Frederik Svane, s224766
public class MiniMaxAlphaBetaAI {


    private static Board board;
    private static int maxDepth;
    public static boolean has4 = true;

    //Constructor for initializing an AI to play against, with a given board and max search depth
    public MiniMaxAlphaBetaAI(Board board, int maxDepth) {

        MiniMaxAlphaBetaAI.board = board;
        MiniMaxAlphaBetaAI.maxDepth = maxDepth;
    }

    //The method below contains a condition for the start of the game, such that the AI does not
    // place a piece on an illegal square during the start of the game, the minimax search with alpha-beta pruning, where the order of
    // method-calls depends on whether the AI is playing as black or as white, and a placePiece call to place a piece of the determined square.
    public static void AIMakeMove(int playerTurn) {

        int bestX = 0;
        int bestY = 0;
        //The value alpha is initialized to the lowest possible integer value, and the opposite for beta.
        // Alpha is used to compare moves for the maximizing player, while beta is used to compare moves for the minimizing player.
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        if (has4) {
            has4 = false;
            for (int x = 0; x < board.x_axis && !has4; x++) {
                for (int y = 0; y < board.y_axis && !has4; y++) {
                    if (board.map[x][y] == 4) {
                        has4 = true;

                    }
                }
            }
        }

        if (!has4) {
            board.legalSpots(playerTurn);
        }

        for (int x = 0; x < board.x_axis; x++) {
            for (int y = 0; y < board.y_axis; y++) {
                if (board.map[x][y] != 3 && board.map[x][y] != 4) continue;
                Board copy = board.copy();
                if (playerTurn == 1) {
                    int value = maxValue(copy, playerTurn, 1, alpha, beta);
                    if (value < beta) {
                        beta = value;
                        bestX = x;
                        bestY = y;
                    }
                } else if (playerTurn == 2) {
                    int value = minValue(copy, playerTurn, 1, alpha, beta);
                    if (value > alpha) {
                        alpha = value;
                        bestX = x;
                        bestY = y;
                    }

                }
            }
        }

        board.placePiece(bestX, bestY, playerTurn);
    }

    //Calculates the best possible move for the maximizing player, by placing a piece on the board, and then
    //calculating the best possible move for the minimizing player with depth+1. The opposite is then done in the minValue method.
    // By calling minValue and maxValue alternatingly with depth+1, the bottom of the search tree will be reached before returning the board score.
    // The AI uses this information to choose which path to take down the search tree, by analysing what the highest score the AI can
    //achieve at the lowest depth of the search tree, assuming the minimizing always player plays perfectly with regard to the AI's own idea
    //of the board heuristics. The same is done when the AI is playing as the minimizing player, but in an opposite manner.
    private static int maxValue(Board board, int playerTurn, int depth, int alpha, int beta) {
        if (depth == maxDepth || !board.legalSpots(playerTurn)) {
            return boardHeuristic(board);

        }
        int bestScore = Integer.MIN_VALUE;
        Board copy = board.copy();
        copy.legalSpots(playerTurn);

        for (int x = 0; x < copy.x_axis; x++) {
            for (int y = 0; y < copy.y_axis; y++) {
                if (copy.map[x][y] != 3 && copy.map[x][y] != 4) continue;
                copy.placePiece(x, y, playerTurn);
                bestScore = Math.max(bestScore, minValue(copy, Board.turnSwitch(playerTurn), depth + 1, alpha, beta));
                alpha = Math.max(alpha, bestScore);
                if (alpha >= beta) {
                    break; //Beta cutoff. If the score of the node is lower, than what can be achieved by making another move, the node will be pruned,
                    //meaning that all of its children will not be explored.
                }
            }
        }
        return bestScore;
    }


    private static int minValue(Board board, int playerTurn, int depth, int alpha, int beta) {
        if (depth == maxDepth || !board.legalSpots(playerTurn)) {
            return boardHeuristic(board);
        }
        int bestScore = Integer.MAX_VALUE;
        Board copy = board.copy();
        copy.legalSpots(playerTurn);

        for (int x = 0; x < copy.x_axis; x++) {
            for (int y = 0; y < copy.y_axis; y++) {
                if (copy.map[x][y] != 3 && copy.map[x][y] != 4) continue;
                copy.placePiece(x, y, playerTurn);
                bestScore = Math.min(bestScore, maxValue(copy, Board.turnSwitch(playerTurn), depth + 1, alpha, beta)); //max og max??
                beta = Math.min(beta, bestScore);
                if (alpha >= beta) {
                    break; //Alpha cutoff. Same idea as the beta cutoff, but opposite.
                }
            }
        }
        return bestScore;
    }

    //The method below return the board score, which is an evaluation of what player is leading in the game.
    //Many factors need to be considered when composing a score for the board.
    //Mainly the amount of pieces of each color,
    //how vulnerable they are to being flipped by the opponent,
    //how many possible moves each player has,
    //and how many corners each player has, since the corner is the most valuable square.
    //Each of these parameters are then multiplied with an arbitrary weight.
    //The score is then calculated, where white advantage entails a positive score, and black advantage entails negative score.
    public static int boardHeuristic(Board board) {

        //Parameters
        int whitePieces = 0;
        int blackPieces = 0;
        int whiteStability = 0;
        int blackStability = 0;
        int whiteMobility;
        int blackMobility;
        int whiteCorners = 0;
        int blackCorners = 0;

        //Parameter weights
        int piecesWeight = 25;
        int cornerWeight = 500;
        int stabilityWeight = 30;
        int mobilityWeight = 5;

        //Scores
        int pieceScore = 0;
        int mobilityScore = 0;
        int stabilityScore = 0;
        int cornerScore = 0;
        int score;

        //Runs through all cells on board and counts parameters
        for (int x = 0; x < board.x_axis; x++) {
            for (int y = 0; y < board.y_axis; y++) {
                if (board.map[x][y] == 1) {
                    whitePieces++;
                    if (isStable(board, x, y, 1)) {
                        whiteStability++;
                    }
                    if ((x == 0 || x == board.x_axis - 1) && (y == 0 || y == board.y_axis - 1)) {
                        whiteCorners++;
                    }
                }

                if (board.map[x][y] == 2) {
                    blackPieces++;
                    if (isStable(board, x, y, 2)) {
                        blackStability++;
                    }
                    if ((x == 0 || x == board.x_axis - 1) && (y == 0 || y == board.y_axis - 1)) {
                        blackCorners++;
                    }
                }
            }
        }

        whiteMobility = getMobility(board, 1);
        blackMobility = getMobility(board, 2);

        if (whitePieces + blackPieces != 0) {
            pieceScore = 100 * (whitePieces - blackPieces) / (whitePieces + blackPieces);
        }

        if (whiteMobility + blackMobility != 0) {
            mobilityScore = 100 * (whiteMobility - blackMobility) / (whiteMobility + blackMobility);
        }

        if (whiteCorners + blackCorners != 0) {
            cornerScore = 100 * (whiteCorners - blackCorners) / (whiteCorners + blackCorners);
        }

        if (whiteStability + blackStability != 0) {
            stabilityScore = 100 * (whiteStability - blackStability) / (whiteStability + blackStability);
        }


        score = piecesWeight * pieceScore + mobilityWeight * mobilityScore + cornerWeight * cornerScore + stabilityWeight * stabilityScore;

        return score;
    }


    //The term mobility refers to the amount of possible moves a player has. The method below returns this number for a given player
    private static int getMobility(Board board, int playerTurn) {
        int mobility = 0;
        Board copy = board.copy();
        copy.legalSpots(playerTurn);
        for (int i = 0; i < copy.x_axis; i++) {
            for (int j = 0; j < copy.y_axis; j++) {
                if (copy.map[i][j] == 3) {
                    mobility++;
                }
            }
        }
        return mobility;
    }

    //Checks if a piece on a square with the coordinates x and y, is stable, meaning if it can be flipped by the opponent in the immediate next turn.
    //This is done by checking all 4 directions around the coin, and checking if there is an empty space on one end of the direction, and an opponent
    //piece on the opposite side of the direction.
    private static boolean isStable(Board board, int x, int y, int piece) {

        if (!stableDirection(board, x, y, -1, -1, piece)) return false;
        if (!stableDirection(board, x, y, 0, -1, piece)) return false;
        if (!stableDirection(board, x, y, -1, 0, piece)) return false;
        return stableDirection(board, x, y, 1, -1, piece);

    }

    public static boolean stableDirection(Board board, int x, int y, int dx, int dy, int piece) {
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