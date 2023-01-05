import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.media.Media;


public class Visualizer extends Application {

    static int width;
    static int height;
    static int turn = (int)(Math.random() * 2) + 1;
    static int turnCounter = 1;
    String whiteImage = "Images/whitePiece.png";
    String blackImage = "Images/blackPiece.png";
    String markerImage = "Images/markerDark.png";
    String backImage1 = "Images/backgroundSkins/chess1.png";
    String backImage2 = "Images/backgroundSkins/chess2.png";
    AudioClip buzzer = new AudioClip(getClass().getResource("/Sounds/sound1.mp3").toExternalForm());



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


        // Create GridPane, which will function as the playing board
        GridPane board = new GridPane();

        // Create 2D array of buttons, which functions as the individual cells on the playing board
        Button[][] cells = new Button[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Button();

                cells[i][j].getStylesheets().add(getClass().getResource("application.css").toExternalForm());

                board.add(cells[i][j], i, j);

                final int ii = i;
                final int jj = j;


                updateGridpane(primaryStage, game, board, blackImage, whiteImage, markerImage);



                // Tilføj en event handler for "on action"
                cells[i][j].setOnAction(event -> {
                    if (game.placePiece(ii,jj,turn)){
                        turnCounter++;
                        buzzer.play();


                        //Switches player turn
                        turn = Board.turnSwitch(turn);

                        //Checks for legal spots after first 4 turns
                        if (turnCounter>4){
                            if (!game.legalSpots(turn)) {
                                if(!game.legalSpots(Board.turnSwitch(turn))){
                                    System.out.println("No more possible moves \n    game over");




                                    //Save value for ending game
                                } else{
                                    System.out.println("\n" + turn + " has no possible moves");
                                    turn = Board.turnSwitch(turn);

                                    //no move possible for current player
                                }

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


        //board.maxHeight(10);


        VBox vbox = new VBox();
        Button button1 = new Button("button1");
        Button button2 = new Button("button2");
        Button button3 = new Button("button3");
        Text text = new Text("'s turn");


        vbox.getChildren().addAll(button1,button2,button3);
        vbox.setSpacing(50);
        vbox.setPadding(new Insets(50,50,50,50));
        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(board,text);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(vbox,vbox2);
        hbox.setSpacing(100);

        Scene scene = new Scene(hbox, 600, 600);

        //
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


}