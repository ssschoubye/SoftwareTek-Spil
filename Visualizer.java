import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.text.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.awt.*;
import java.io.IOException;


public class Visualizer extends Application {

    static int width;
    static int height;
    static int turn = 1;
    String whiteImage = "Images/whitePiece.png";
    String blackImage = "Images/blackPiece.png";
    String markerImage = "Images/marker.png";
    String backImage1 = "Images/backgroundSkins/chess1.png";
    String backImage2 = "Images/backgroundSkins/chess2.png";



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
        game.legalSpots(1);




        // Create GridPane, which will function as the playing board
        GridPane board = new GridPane();

        // Create 2D array of buttons, which functions as the individual cells on the playing board
        Button[][] cells = new Button[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Button();

                board.add(cells[i][j], i, j);

                final int ii = i;
                final int jj = j;

                updateGridpane(primaryStage, game, board, blackImage, whiteImage, markerImage);



                // Tilføj en event handler for "on action"
                cells[i][j].setOnAction(event -> {
                    if (game.placePiece(ii,jj,turn)){
                        //Switches player turn
                        turn = Board.turnSwitch(turn);

                        //Checks for legal spots
                        if (!game.legalSpots(turn)) {

                            if(!game.legalSpots(Board.turnSwitch(turn))){
                                System.out.println("No more possible moves \n    game over");
                                WinPage win = new WinPage();
                                //Save value for ending game
                                try {
                                    win.winStart(game);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } else{
                                System.out.println("\n" + turn + " has no possible moves");
                                turn = Board.turnSwitch(turn);

                                //no move possible for current player
                            }

                        }
                        updateGridpane(primaryStage, game, board, blackImage, whiteImage, markerImage);




                    }

                });

                cells[i][j].prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                cells[i][j].prefWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));

            }
            board.setAlignment(Pos.CENTER);
        }

        HBox surface = hbox(vbox(currentplayer(),toText("p1: 20 points"),toText("p2: 18 points"),toText("4"),new Button("restart"),new Button("settings")),board);
        Scene scene = new Scene(surface, 800, 800);
        board.setPadding(new Insets(10,10,10,10));
        primaryStage.setMinWidth(250);
        primaryStage.setScene(scene);
        //primaryStage.setResizable(false);
        primaryStage.show();


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
                } else if (game.map[x][y] == 3) {
                    ImageView marker = new ImageView(markingImage);
                    board.add(marker, x, y);
                    marker.setMouseTransparent(true);
                    marker.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                    marker.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                }
            }
        }
    }
    public Text playertext (String s) {
        Text text = new Text("s");

        return text;
    }

    public HBox hbox (VBox vbox, GridPane board) {
        HBox hbox = new HBox();
        hbox.getChildren().addAll(vbox,board);
        return hbox;
    }

    public VBox vbox (Text text1, Text text2, Text text3, Text text4, Button button, Button settings) {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(text1,text2,text3,text4,button,settings);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(-20,0,20,20));
        vbox.setSpacing(40.0);
        button.setMinWidth(60);
        settings.setMinWidth(60);

        return vbox;
    }

    public Text toText(String string) {
        Text text = new Text(string);
        font(text,"Comic Sans MS",20);
        return text;
    }

    public void font(Text text,String font, int size) {
        text.setFont(new Font(font,size));
    }

   public Text currentplayer() {
       String s = "";
           if (turn == 1) {
               s = "player 1";
           }
           if (turn == 2) {
               s = "player 2";
           }


           Text t = new Text(s);

       return t;
   }
}