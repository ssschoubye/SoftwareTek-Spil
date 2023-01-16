import java.util.Scanner;

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
        int turnCounter=1;
        Scanner size = new Scanner(System.in);
        System.out.print("Enter the size of the x-axis: ");
        int xSize = size.nextInt();
        System.out.print("Enter the size of the y-axis: ");
        int ySize = size.nextInt();

        Board game = new Board(xSize, ySize);

        game.initialize();
        //Game starts with 1 (White) having the first turn
        int turn = 1;
        System.out.println("\n____________________________________");
        while (true) {
            if (turnCounter>4){
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


                System.out.println("\n" + turn + "'s turn to place a piece");

            } else if (turnCounter==3) {
                turn = turnSwitch(turn);
            }game.printOut();


            Scanner coord = new Scanner(System.in);

            int x = 0;
            int y = 0;

            do {
                System.out.print("Enter the first coordinate: ");
                x = coord.nextInt();
                System.out.print("Enter the second coordinate: ");
                y = coord.nextInt();

            } while (!game.placePiece(x, y, turn));

            System.out.println("\n____________________________________");
            turnCounter++;
        }
        System.out.println("\n____________________________________");
        int[] score = game.getScore();
        game.printOut();

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