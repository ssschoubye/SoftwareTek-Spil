public class BoardTestMain {

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

    public static void main(String[] args) {

        int xSize = 8;
        int ySize = 8;
        Board game = new Board(xSize, ySize);
        MiniMaxAlphaBetaAI AI = new MiniMaxAlphaBetaAI(game, 3);
        RndBot bot = new RndBot(game);
        game.initialize();

        //Game starts with 1 (White) having the first turn
        int turn = 1;
        while (true) {
            turn = turnSwitch(turn);

            if (!game.legalSpots(turn)) {
                if(!game.legalSpots(turnSwitch(turn))){
                    System.out.println("No more possible moves \n    game over");
                    break;
                } else{
                    System.out.println("\n" + turn + " has no possible moves");
                    continue;
                }

            }
            System.out.println();
            game.toString();

            System.out.println("\n" + turn + "'s turn to place a piece");
            if(turn==1) {
                MiniMaxAlphaBetaAI.AIMakeMove(turn);
            }else if(turn==2){
                RndBot.rndBotMakeMove(turn);
            }


            System.out.println("\n____________________________________");

        }
        System.out.println("\n____________________________________");
        int[] score = game.getScore();
        game.toString();

        if (score[0] > score[1]) {
            System.out.println("1 won the game with " + score[0] + " pieces on the board.");
            System.out.println("2 only had " + score[1] + " pieces.");
        } else if (score[0] < score[1]) {
            System.out.println("2 won the game with " + score[1] + " pieces on the board.");
            System.out.println("1 only had " + score[0] + " pieces.");
        } else if (score[0] == score[1]) {
            System.out.println("The game was a draw. Both players had" + score[0] + " pieces on the board.");

        } else {
            System.out.println("ERROR");
        }


    }


}