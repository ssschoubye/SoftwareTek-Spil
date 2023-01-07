import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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
    String skinBackground = "Images/skinBackground.png";


    AudioClip buzzer = new AudioClip(getClass().getResource("/Sounds/sound1.mp3").toExternalForm());

    Label text = new Label(turn+"'s turn");

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
                        text.setText(turn+"'s turn");

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
        ///////////////////////////////////////////////////////////////////////////////////////
        ///                            Skin choices on left                                 ///
        ///////////////////////////////////////////////////////////////////////////////////////


        GridPane whiteSkins = new GridPane();

        whiteSkins.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Button whiteSkin1 = new Button();
        whiteSkins.add(whiteSkin1, 0, 0);

        whiteSkin1.prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
        whiteSkin1.prefWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));

        whiteSkin1.setOnAction(actionEvent -> {
            whiteImage = "Images/whitePiece.png";
            updateGridpane(primaryStage, game, board, blackImage, whiteImage, markerImage);
        });

        Button whiteSkin2 = new Button();
        whiteSkins.add(whiteSkin2, 1, 0);

        whiteSkin2.prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
        whiteSkin2.prefWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));

        whiteSkin2.setOnAction(actionEvent -> {
            whiteImage = "Images/whitePiece2.png";
            updateGridpane(primaryStage, game, board, blackImage, whiteImage, markerImage);
        });

        Button whiteSkin3 = new Button();
        whiteSkins.add(whiteSkin3, 2, 0);

        whiteSkin3.prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
        whiteSkin3.prefWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));

        whiteSkin3.setOnAction(actionEvent -> {
            whiteImage = "Images/whitePiece3.png";
            updateGridpane(primaryStage, game, board, blackImage, whiteImage, markerImage);
        });

        Image backGround1 = new Image(backImage1);
        Image backGround2 = new Image(backImage2);

        for (int i = 0; i<3;i++){
            ImageView back;

            if ( i%2==1){
                back = new ImageView(backGround1);
            } else  {
                back = new ImageView(backGround2);
            }
            whiteSkins.add(back, i, 0);
            back.setMouseTransparent(true);
            back.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
            back.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));

        }

        ImageView whiteSkin1Image = new ImageView("Images/whitePiece.png");
        whiteSkins.add(whiteSkin1Image, 0, 0);
        whiteSkin1Image.setMouseTransparent(true);
        whiteSkin1Image.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
        whiteSkin1Image.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));

        ImageView whiteSkin2Image = new ImageView("Images/whitePiece2.png");
        whiteSkins.add(whiteSkin2Image, 1, 0);
        whiteSkin2Image.setMouseTransparent(true);
        whiteSkin2Image.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
        whiteSkin2Image.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));

        ImageView whiteSkin3Image = new ImageView("Images/whitePiece3.png");
        whiteSkins.add(whiteSkin3Image, 2, 0);
        whiteSkin3Image.setMouseTransparent(true);
        whiteSkin3Image.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
        whiteSkin3Image.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));





        GridPane blackSkins = new GridPane();
        blackSkins.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Button blackSkin1 = new Button();
        Button blackSkin2 = new Button();
        Button blackSkin3 = new Button();

        blackSkins.add(blackSkin1,0,0);
        blackSkins.add(blackSkin2,0,1);
        blackSkins.add(blackSkin3,0,2);


        VBox vbox = new VBox();

        vbox.getChildren().addAll(whiteSkins,blackSkins);
        vbox.setSpacing(50);
        vbox.setPadding(new Insets(50,50,50,50));


        VBox vbox2 = new VBox();
        vbox2.getChildren().addAll(board,text);
        vbox2.setAlignment(Pos.CENTER);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(vbox2,vbox);
        hbox.setSpacing(100);

        Scene scene = new Scene(hbox, 600, 600);
        scene.getStylesheets().add("defaultStyle.css");


        primaryStage.setMinWidth(250);
        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(true);
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