import java.util.Scanner;

public class BoardTestMain {

    public static int turnSwitch(int currentTurn){
        if (currentTurn==1){
            return 2;
        } else if (currentTurn==2){
            return 1;
        } else {
            System.out.println("Wrong current turn");
            return 0;
        }
    }

    public static void main(String[] args) {
        /*
        Board testGame = new Board(8,8);

        testGame.initialize();
        testGame.legalSpots(2);
        testGame.toString();

        System.out.println("\n____________________________________");

        testGame.placePiece(3,2,2);
        testGame.legalSpots(1);
        testGame.toString();

        System.out.println("\n____________________________________");

        testGame.placePiece(2,4,1);
        testGame.legalSpots(2);
        testGame.toString();

        System.out.println("\n____________________________________");

        testGame.placePiece(2,4,1);
        testGame.legalSpots(2);
        testGame.toString();

        System.out.println("\n____________________________________");
        System.out.println("Test over");
        System.out.println("Now starting game");
        System.out.println("\n____________________________________");
        */

        Scanner size = new Scanner(System.in);
        System.out.print("Enter the size of the x-axis: ");
        int xSize = size.nextInt();
        System.out.print("Enter the size of the y-axis: ");
        int ySize = size.nextInt();

        Board game = new Board(xSize,ySize);

        game.initialize();
        //Game starts with 1 (White) having the first turn
        int turn=2;
        System.out.println("\n____________________________________");
        while (true){
            turn = turnSwitch(turn);

            if(!game.legalSpots(turnSwitch(turn)) && !game.legalSpots(turn)){
                System.out.println("No more possible moves \n    game over");
                break;
            }
            if(!game.legalSpots(turn)){
                System.out.println("\n"+turn+" has no possible moves");
                continue;
            }
            System.out.println();
            game.toString();

            System.out.println("\n"+turn+"'s turn to place a piece");

            Scanner coord = new Scanner(System.in);

            int x=0;
            int y=0;

            while (true){
                System.out.print("Enter the first coordinate: ");
                x = coord.nextInt();
                System.out.print("Enter the second coordinate: ");
                y = coord.nextInt();
                if (game.placePiece(x,y,turn)){
                    break;
                }

            }
            System.out.println("\n____________________________________");

        }
        System.out.println("\n____________________________________");
        int[] score = game.getScore();
        game.toString();

        if (score[0]>score[1]){
            System.out.println("1 won the game with " + score[0] + " pieces on the board.");
            System.out.println("2 only had " + score[1] + " pieces.");
        } else if (score[0]<score[1]){
            System.out.println("2 won the game with " + score[1] + " pieces on the board.");
            System.out.println("1 only had " + score[0] + " pieces.");
        } else if (score[0]==score[1]){
            System.out.println("The game was a draw. Both players had" + score[0] + " pieces on the board.");

        } else {
            System.out.println("ERROR");
        }



    }



}
