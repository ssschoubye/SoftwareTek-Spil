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
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class PlayAlone extends Application {

    static int width;
    static int height;
    static int turn = (int) (Math.random() * 2) + 1;

    static int firstStartingPlayer = (int) (Math.random() * 2) + 1;

    static int gameNumber = 1;

    static int turnCounter = 1;
    String whiteImage;
    String blackImage;
    String markerImage = "Images/markerDark.png";
    String backImage1;
    String backImage2;
    String placeSoundFile = "Sounds/placeSound1.mp3";

    String appIcon = "Images/reversiIcon.png";



    static Scene scene;

    static {
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(PlayAlone.class.getResource("playAlone.fxml"))));
        } catch (IOException e) {
            System.out.println("Could not load FXML-file");

        }
    }


    public void gameStart(int inwidth, int inheight) {
        DimensionPrompt dimPrompt = new DimensionPrompt();
        width = inwidth;
        height = inheight;
        whiteImage = dimPrompt.whiteImage;
        blackImage = dimPrompt.blackImage;
        backImage1 = dimPrompt.back1;
        backImage2 = dimPrompt.back2;
        Stage stage = new Stage();
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) {
        AudioClip placeSound = new AudioClip(getClass().getResource(placeSoundFile).toExternalForm());
        Label showTurn = (Label)scene.lookup("#showTurn");

        Board game = new Board(width, height);
        game.initialize();
        turn = game.startingPlayer(gameNumber, firstStartingPlayer);
        showTurn.setText(turnColor(turn) + "'s turn");


        Label whiteScore = (Label)scene.lookup("#whiteScore");
        whiteScore.setText("x"+game.getScore()[0]);

        Label blackScore = (Label)scene.lookup("#blackScore");
        blackScore.setText("x"+game.getScore()[1]);

        turnCounter=1;
        GridPane board = new GridPane();


        Button[][] cells = new Button[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Button();
                cells[i][j].getStylesheets().add(getClass().getResource("boardButtons.css").toExternalForm());
                board.add(cells[i][j], i, j);

                final int ii = i;
                final int jj = j;

                updateGridpane(game, board, whiteImage, blackImage, markerImage);


                cells[i][j].setOnAction(event -> {
                    if (game.placePiece(ii, jj, turn)) {
                        placeSound.play();

                        turnCounter++;

                        if (turnCounter == 3) {
                            turn = Board.turnSwitch(turn);
                            showTurn.setText(turnColor(turn) + "'s turn");
                        }

                        if (turnCounter > 4) {
                            turn = Board.turnSwitch(turn);
                            showTurn.setText(turnColor(turn) + "'s turn");


                            if (!game.legalSpots(turn)) {
                                if (!game.legalSpots(Board.turnSwitch(turn))) {
                                    System.out.println("No more possible moves \n    game over");

                                    updateGridpane(game, board, whiteImage, blackImage, markerImage);

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


                                } else {

                                    System.out.println("\n" + turn + " has no possible moves");
                                    turn = Board.turnSwitch(turn);
                                    showTurn.setText(turnColor(turn) + "'s turn");
                                    //no move possible for current player
                                }

                            }
                        }

                        whiteScore.setText("x"+game.getScore()[0]);
                        blackScore.setText("x"+game.getScore()[1]);
                        updateGridpane(game, board, whiteImage, blackImage, markerImage);


                    }

                });
                Pane gamePane = (Pane) scene.lookup("#gamePane");
                cells[i][j].prefHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), width));
                cells[i][j].prefWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));

            }
            board.setAlignment(Pos.CENTER);

        }

        Image icon = new Image(appIcon);
        primaryStage.setTitle("Reversi");
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





    private void updateGridpane(Board game, GridPane board, String whiteImage, String blackImage, String markerImage) {
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
    //////////////////////////////////////////////////////////////
    ///                 Back to Menu button                    ///
    //////////////////////////////////////////////////////////////
    @FXML
    private Button backMenu;

    @FXML
    public void backToMenu() throws IOException {
        Stage stage = (Stage) backMenu.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        Menu menu = new Menu();
        menu.start(primaryStage);

    }

}

