import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.util.Arrays;
import java.util.Objects;

/////////////////////////////////////////////////////////////////
///                      Online Server side                   ///
///               by Søren Sehested Schoubye s224756          ///
/// Controls the game regarding the server side of online play///
/////////////////////////////////////////////////////////////////

//This class is almost identical to the PlayOnlineHost class, the only difference is that this class is the client and the other is the server.
//The comments in this class are therefore very similar to the comments in the PlayOnlineHost class.
//THis class furthermore is based on the code from PlayAlone class.
//Relevant changes have been commented. Otherwise information about gameplay functions be found in the PlayAlone class.
public class PlayOnlineHost extends Application implements Serializable {

    static int width;
    static int height;
    static int turn = (int) (Math.random() * 2) + 1;

    static int firstStartingPlayer = 1;

    static int gameNumber = 1;

    static int turnCounter = 1;
    static String whiteImage = "Images/WhitePieces/whitePiece1.png";
    static String blackImage = "Images/BlackPieces/blackPiece1.png";
    String markerImage = "Images/markerDark.png";
    static String backImage1;
    static String backImage2;
    String placeSoundFile = "Sounds/placeSound1.mp3";

    String appIcon = "Images/reversiIcon.png";


    static Scene scene;

    static {
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(PlayAlone.class.getResource("playAlone.fxml"))));
        } catch (IOException ignored) {

        }
    }


    public void gameStart() {


        DimensionPrompt dimPrompt = new DimensionPrompt();
        width = DimensionPrompt.dim.x;
        height = DimensionPrompt.dim.x;
        whiteImage = dimPrompt.whiteImage;
        blackImage = dimPrompt.blackImage;
        backImage1 = dimPrompt.back1;
        backImage2 = dimPrompt.back2;
        Stage stage = new Stage();
        start(stage);
    }
    Server server; //The server object is created here.
    static Board game;
    static GridPane board;
    @Override
    public void start(Stage primaryStage) {
        AudioClip placeSound = new AudioClip(getClass().getResource(placeSoundFile).toExternalForm());
        Label showTurn = (Label)scene.lookup("#showTurn");
        System.out.println("Starting game");
        try{
            server = new Server(new ServerSocket(8080));
        }catch(IOException e){
            e.printStackTrace();
        }
        System.out.println("Server started");
        game = new Board(width, height);
        game.initialize();
        turn = game.startingPlayer(gameNumber, firstStartingPlayer);
        showTurn.setText(turnColor(turn) + "'s turn");


        Label whiteScore = (Label)scene.lookup("#whiteScore");
        whiteScore.setText("x"+game.getScore()[0]);

        Label blackScore = (Label)scene.lookup("#blackScore");
        blackScore.setText("x"+game.getScore()[1]);


        board = new GridPane();
        
        Button saveGame = (Button) scene.lookup("#saveGame");

        saveGame.setVisible(false);

        server.recieveArray();

        Button[][] cells = new Button[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Button();
                cells[i][j].getStylesheets().add(getClass().getResource("boardButtons.css").toExternalForm());
                board.add(cells[i][j], i, j);

                final int ii = i;
                final int jj = j;

                updateGridPane(game, board);


                // Create an event handler for "on action"
                cells[i][j].setOnAction(event -> {
                    if (game.placePiece(ii, jj, turn)) {
                        placeSound.play();
                        turnCounter++;
                        //Switches player turn
                        if (turnCounter == 4) {
                            turn = Board.turnSwitch(turn);
                            showTurn.setText(turnColor(turn) + "'s turn");
                        }

                        if (turnCounter > 2) {
                            turn = Board.turnSwitch(turn);
                            showTurn.setText(turnColor(turn) + "'s turn");


                            //Checks for legal spots
                            if (!game.legalSpots(turn)) {
                                if (!game.legalSpots(Board.turnSwitch(turn))) {

                                    updateGridPane(game, board);

                                    delay(500,() ->{
                                        WinPage win = new WinPage();
                                        turnCounter = 1;
                                        gameNumber++;

                                        try {
                                            primaryStage.close();
                                            win.winStart(game);
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    });
                                    //Save value for ending game
                                } else {
                                    turn = Board.turnSwitch(turn);
                                    showTurn.setText(turnColor(turn) + "'s turn");
                                }

                            }
                        }

                        whiteScore.setText("x"+game.getScore()[0]);
                        blackScore.setText("x"+game.getScore()[1]);
                        updateGridPane(game, board);
                        System.out.println(Arrays.deepToString(game.map));
                        server.sendArray(game.map);
                    }

                });
                Pane gamePane = (Pane) scene.lookup("#gamePane");
                cells[i][j].prefHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), width));
                cells[i][j].prefWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));

            }
            board.setAlignment(Pos.CENTER);

        }

        Image icon = new Image(appIcon);
        primaryStage.setTitle("Reversi Advanced");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();


    }


    //Borrowed from the internet
    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }





    private void updateGridPane(Board game, GridPane board) {
        Pane gamePane = (Pane) scene.lookup("#gamePane");
        board.getChildren().removeIf(node -> node instanceof ImageView);
        gamePane.getChildren().remove(board);
        Image whitePieceImage = new Image(whiteImage);
        Image blackPieceImage = new Image(blackImage);
        Image markingImage = new Image(markerImage);

        Image backGround1 = new Image(backImage1);
        Image backGround2 = new Image(backImage2);

        Pane whiteScoreSkin = (Pane) scene.lookup("#whiteScoreSkin");
        ImageView whitePieceScoreImage = new ImageView(whitePieceImage);
        whiteScoreSkin.getChildren().add(whitePieceScoreImage);
        whitePieceScoreImage.setPreserveRatio(true);
        whitePieceScoreImage.fitWidthProperty().bind(whiteScoreSkin.widthProperty());

        Pane blackScoreSkin = (Pane) scene.lookup("#blackScoreSkin");
        ImageView blackPieceScoreImage = new ImageView(blackPieceImage);
        blackScoreSkin.getChildren().add(blackPieceScoreImage);
        blackPieceScoreImage.setPreserveRatio(true);
        blackPieceScoreImage.fitWidthProperty().bind(blackScoreSkin.widthProperty());


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                ImageView back;

                if ((x + y) % 2 == 1) {
                    back = new ImageView(backGround1);
                } else {
                    back = new ImageView(backGround2);
                }


                board.add(back, x, y);
                back.setMouseTransparent(true);
                back.setPreserveRatio(true);
                back.fitWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));
                back.fitHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), width));


                if (game.map[x][y] == 1) {
                    ImageView whitePiece = new ImageView(whitePieceImage);
                    board.add(whitePiece, x, y);
                    whitePiece.setPreserveRatio(true);
                    whitePiece.setMouseTransparent(true);
                    whitePiece.fitWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));
                    whitePiece.fitHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), width));
                } else if (game.map[x][y] == 2) {
                    ImageView blackPiece = new ImageView(blackPieceImage);
                    board.add(blackPiece, x, y);
                    blackPiece.setPreserveRatio(true);
                    blackPiece.setMouseTransparent(true);
                    blackPiece.fitWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));
                    blackPiece.fitHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), width));
                } else if (game.map[x][y] == 3 || game.map[x][y] == 4) {
                    ImageView marker = new ImageView(markingImage);
                    board.add(marker, x, y);
                    marker.setPreserveRatio(true);
                    marker.setMouseTransparent(true);
                    marker.fitWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));
                    marker.fitHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), width));
                }
            }
        }

        gamePane.getChildren().add(board);
    }

    public String turnColor(int turn) {
        if (turn == 1) {
            return "White";
        } else if (turn == 2) {
            return "Black";
        } else return null;
    }


    //////////////////////////////////////////////////////////////
    ///                    Title bar layout                    ///
    ///              Same code as for the Menu.java            ///
    ///       Further explanations of code can be sen there    ///
    //////////////////////////////////////////////////////////////
    @FXML
    HBox titlebar;

    private double windowX = 0;
    private double windowY = 0;

    @FXML
    private void titleBarDragged(MouseEvent event) {
        Stage stage = (Stage) titlebar.getScene().getWindow();
        stage.setY(event.getScreenY() - windowY);
        stage.setX(event.getScreenX() - windowX);
    }

    @FXML
    private void titleBarPressed(MouseEvent event) {
        windowX = event.getSceneX();
        windowY = event.getSceneY();
    }

    @FXML
    private void onExitButtonClick() {
        Platform.exit();
    }

    @FXML
    Button minimize;

    @FXML
    public void OnMinimizeButtonClick() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public static void setMap(int[][] map) {//Takes the input map from the client and sets it to the map in the game.
        Platform.runLater(new Runnable() {
            @Override
            public void run() {//This is needed to run the code on the JavaFX thread.
                game.map = map; //Sets the map in the game to the map from the client.
                whiteImage = DimensionPrompt.whiteImage; //Sets the images to the images from the client.
                blackImage = DimensionPrompt.blackImage;
                backImage1 = DimensionPrompt.backImage1;
                backImage2 = DimensionPrompt.backImage2;

                PlayOnlineHost playOnlineHost = new PlayOnlineHost();
                playOnlineHost.updateGridPane(game, board); //Calls the updateGridPane method to update the board.
            }
        });
    }

}

