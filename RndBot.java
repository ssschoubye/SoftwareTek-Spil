public class RndBot {

    private static Board board;

    public RndBot(Board board){
        this.board = board;
    }

    public static void rndBotMakeMove(int playerTurn){

        int x = 0;
        int y = 0;

        do{
            x = (int) (Math.random()* board.x_axis);
            y = (int) (Math.random()* board.y_axis);
        }while(board.map[x][y] != 3 && board.map[x][y] != 4);

        board.placePiece(x,y,playerTurn);
    }

}
