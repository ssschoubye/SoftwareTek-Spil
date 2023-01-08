import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class Visualizer extends Application {

    static int width;
    static int height;
    static int turn = (int)(Math.random()*2)+1;

    static int firstStartingPlayer = (int)(Math.random()*2)+1;

    static int gameNumber = 1;

    static int turnCounter =1;
    String whiteImage = "Images/whitePiece.png";
    String blackImage = "Images/blackPiece.png";
    String markerImage = "Images/marker.png";
    String backImage1 = "Images/backgroundSkins/chess1.png";
    String backImage2 = "Images/backgroundSkins/chess2.png";

    String appIcon = "Images/reversiIcon.png";
    Label showTurn = new Label(turnColor(turn)+"'s turn");



    public void gameStart(int inwidth, int inheight){
        width = inwidth;
        height = inheight;
        Stage stage = new Stage();
        start(stage);
    }



    @Override
    public void start(Stage primaryStage) {

        Board game = new Board(width,height);
        game.initialize();
        turn = game.startingPlayer(gameNumber,firstStartingPlayer);
        showTurn.setText(turnColor(turn)+" is starting");
        ReversiAI klogAI = new ReversiAI(game, 10);



        GridPane board = new GridPane();


        Button[][] cells = new Button[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Button();
                cells[i][j].getStylesheets().add(getClass().getResource("boardButtons.css").toExternalForm());
                board.add(cells[i][j], i, j);

                final int ii = i;
                final int jj = j;

                updateGridpane(primaryStage, game, board,whiteImage, blackImage, markerImage);


                cells[i][j].setOnAction(event -> {
                    if (game.placePiece(ii,jj,turn)){
                        turnCounter++;

                        if(turnCounter==3){
                            showTurn.setText(turnColor(turn)+"'s turn");
                            turn = Board.turnSwitch(turn);

                            updateGridpane(primaryStage, game, board,whiteImage, blackImage, markerImage);

                            ReversiAI.AIMakeMove(turn);
                            turnCounter++;

                            ReversiAI.AIMakeMove(turn);
                            turn = Board.turnSwitch(turn);
                            game.legalSpots(turn);
                        }
                        if (turnCounter>4){
                            turn = Board.turnSwitch(turn);


                            if (!game.legalSpots(turn)) {
                                if(!game.legalSpots(Board.turnSwitch(turn))){
                                    System.out.println("No more possible moves \n    game over");

                                    WinPage win = new WinPage();
                                    turnCounter = 1;
                                    gameNumber++;
                                    try{
                                        primaryStage.close();
                                        win.winStart(game);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                } else{
                                    System.out.println("\n" + turn + " has no possible moves");
                                    turn = Board.turnSwitch(turn);
                                    game.legalSpots(turn);




                                }

                            }else {

                                while (true){


                                    ReversiAI.AIMakeMove(turn);
                                    if(!game.legalSpots(Board.turnSwitch(turn))){
                                        if(!game.legalSpots(turn)){
                                            System.out.println("No more possible moves \n    game over");
                                            WinPage win = new WinPage();
                                            turnCounter = 1;
                                            gameNumber++;
                                            try{
                                                primaryStage.close();
                                                win.winStart(game);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        } else continue;
                                    }
                                    turn = Board.turnSwitch(turn);

                                    break;

                                }


                            }

                        }
                        showTurn.setText(turnColor(turn)+"'s turn");

                        updateGridpane(primaryStage, game, board, whiteImage, blackImage, markerImage);




                    }

                });

                cells[i][j].prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                cells[i][j].prefWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));

            }
            board.setAlignment(Pos.CENTER);
        }

        Image icon = new Image(appIcon);
        showTurn.setFont(Font.font("Comic Sans", 24));
        VBox vbox = new VBox();
        primaryStage.setTitle("Reversi");
        vbox.getChildren().addAll(board,showTurn);
        vbox.setAlignment(Pos.CENTER);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(vbox, screenBounds.getHeight()/2, screenBounds.getHeight()/2);
        board.setPadding(new Insets(10,10,10,10));
        primaryStage.setMinWidth(250);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        primaryStage.show();




    }

    private void timeDelay() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
    }


    private void updateGridpane(Stage primaryStage, Board game, GridPane board, String whiteImage, String blackImage, String markerImage) {

        board.getChildren().removeIf(node -> node instanceof ImageView);
        Image whitePieceImage = new Image(whiteImage);
        Image blackPieceImage = new Image(blackImage);
        Image markingImage = new Image(markerImage);

        Image backGround1 = new Image(backImage1);
        Image backGround2 = new Image(backImage2);


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                ImageView back;

                if ( (x+y)%2==1){
                    back = new ImageView(backGround1);
                } else  {
                    back = new ImageView(backGround2);
                }


                board.add(back, x, y);
                back.setMouseTransparent(true);
                back.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                back.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));


                if (game.map[x][y] == 1) {
                    ImageView whitePiece = new ImageView(whitePieceImage);
                    board.add(whitePiece, x, y);
                    whitePiece.setMouseTransparent(true);
                    whitePiece.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                    whitePiece.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                } else if (game.map[x][y] == 2) {
                    ImageView blackPiece = new ImageView(blackPieceImage);
                    board.add(blackPiece, x, y);
                    blackPiece.setMouseTransparent(true);
                    blackPiece.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                    blackPiece.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                } else if (game.map[x][y] == 3 || game.map[x][y] == 4) {
                    ImageView marker = new ImageView(markingImage);
                    board.add(marker, x, y);
                    marker.setMouseTransparent(true);
                    marker.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                    marker.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                }
            }
        }
    }

    public static String turnColor(int turn){
        if(turn==1){
            return "White";
        } else if(turn==2){
            return "Black";
        }else return null;
    }

}