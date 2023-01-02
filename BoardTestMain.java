

public class BoardTestMain {
    public static void main(String[] args) {

        Board game = new Board(8,8);

        game.iniGame();
        game.setTestValue(3,5,1);


        game.legalSpots(2);
        game.toString();
        System.out.println("------------------");
        game.setTestValue(4,5,2);

        game.legalSpots(1);

        game.toString();


    }



}
