import java.util.Scanner;

//Class for running the game in the terminal
//Class is made only for testing of the game logic
//The class has not been updated with the feature where the two players start out with placing the first four pieces
//two by two
public class BoardTestMain {

    public static void main(String[] args){

        //Setting the size of the game and initializing it
        Scanner size = new Scanner(System.in);
        System.out.print("Enter the size of the x-axis: ");
        int xSize = size.nextInt();
        System.out.print("Enter the size of the y-axis: ");
        int ySize = size.nextInt();
        Board game = new Board(xSize, ySize);

        game.initialize();

        //Game starts with 1 (White) having the first turn but is set to 2 here as th while loop running the game
        //starts with switching turn
        int turn = 2;
        int turncounter =1;

        while (true) {
            turn = turnSwitch(turn);

            //Checking if there are any legal spots for the player and the next and acts accordingly
            if (turncounter>4){
                if (!game.legalSpots(turn)) {
                    if(!game.legalSpots(turnSwitch(turn))){
                        System.out.println("No more possible moves \n    game over");
                        break;
                    } else{
                        System.out.println("\n" + turn + " has no possible moves");
                        continue;
                    }

                }
            } turncounter++;

            //Prints out game state
            System.out.println();
            game.printOut();

            //prints whose turn it is
            System.out.println("\n" + turn + "'s turn to place a piece");

            //Scans an input, first for the x coordinate and then the y-coordinate
            //Tries again if the given coordinate set is not a legal move
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

        }
        System.out.println("\n____________________________________");

        //After the game ends, the winner is found and printed
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

}
