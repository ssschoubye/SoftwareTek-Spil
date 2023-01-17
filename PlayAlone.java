import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
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

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class PlayAlone extends Application {

    static int width;
    static int height;
    static int turn = (int) (Math.random() * 2) + 1;

    static int firstStartingPlayer = (int) (Math.random() * 2) + 1;

    static int gameNumber = 1;

    static int turnCounter = 1;

    String whiteImage = "";
    String blackImage = "";
    String markerImage = "Images/markerDark.png";
    String backImage1 = "Images/Background/green1.png";
    String backImage2 = "Images/Background/green2.png";
    String placeSoundFile = "Sounds/placeSound1.mp3";


    String appIcon = "Images/reversiIcon.png";

    static Board boardFile = new Board();


    Scene scene;


    public void gameStart(int inwidth, int inheight) {

        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(PlayAlone.class.getResource("playAlone.fxml"))));
        } catch (IOException e) {
            System.out.println("Could not load FXML-file");

        }

        DimensionPrompt dimPrompt = new DimensionPrompt();
        width = inwidth;
        height = inheight;
        whiteImage = DimensionPrompt.whiteImage;
        blackImage = DimensionPrompt.blackImage;
        backImage1 = dimPrompt.back1;
        backImage2 = dimPrompt.back2;
        Stage stage = new Stage();
        start(stage);
    }

    @Override
    public void start(Stage primaryStage) {
        AudioClip placeSound = new AudioClip(getClass().getResource(placeSoundFile).toExternalForm());
        Label showTurn = (Label) scene.lookup("#showTurn");

        Board game = new Board(width, height);
        game.initialize();
        turn = game.startingPlayer(gameNumber, firstStartingPlayer);
        showTurn.setText(turnColor(turn) + "'s turn");
        turnCounter = 1;


        if (Menu.loadGame) {
            GameLoader gameloader = new GameLoader();
            turn = gameloader.playerLoad;
            whiteImage = gameloader.whiteLoad;
            DimensionPrompt.whiteImage = whiteImage;

            blackImage = gameloader.blackLoad;
            DimensionPrompt.blackImage = blackImage;

            backImage1 = gameloader.board1Load;
            DimensionPrompt.backImage1 = backImage1;

            backImage2 = gameloader.board2Load;
            DimensionPrompt.backImage2 = backImage2;

            game.map = gameloader.gameMapLoad;

            turnCounter = gameloader.turnCount;

            Menu.loadGame = false;
        }


        Label whiteScore = (Label) scene.lookup("#whiteScore");
        whiteScore.setText("x" + game.getScore()[0]);

        Label blackScore = (Label) scene.lookup("#blackScore");
        blackScore.setText("x" + game.getScore()[1]);


        GridPane board = new GridPane();


        Button[][] cells = new Button[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Button();
                cells[i][j].getStylesheets().add(getClass().getResource("boardButtons.css").toExternalForm());
                board.add(cells[i][j], i, j);

                final int ii = i;
                final int jj = j;

                updateGridPane(game, board);


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

                                    updateGridPane(game, board);
                                    new Thread(() -> {
                                        try {
                                            Thread.sleep(800);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }
                                        WinPage win = new WinPage();
                                        turnCounter = 1;
                                        gameNumber++;

                                        Platform.runLater(() -> {
                                            primaryStage.close();
                                            try {
                                                win.winStart(game);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }

                                        });

                                    }).start();

                                } else {

                                    System.out.println("\n" + turn + " has no possible moves");
                                    turn = Board.turnSwitch(turn);
                                    showTurn.setText(turnColor(turn) + "'s turn");
                                    //no move possible for current player
                                }

                            }
                        }

                        whiteScore.setText("x" + game.getScore()[0]);
                        blackScore.setText("x" + game.getScore()[1]);
                        updateGridPane(game, board);


                    }
                });
                Pane gamePane = (Pane) scene.lookup("#gamePane");
                cells[i][j].prefHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), height));
                cells[i][j].prefWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), height));

            }
            board.setAlignment(Pos.CENTER);

        }

        Image icon = new Image(appIcon);
        boardFile = game;
        primaryStage.setTitle("Reversi");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        System.out.println("retur til start");


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
                back.fitHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), height));


                if (game.map[x][y] == 1) {
                    ImageView whitePiece = new ImageView(whitePieceImage);
                    board.add(whitePiece, x, y);
                    whitePiece.setPreserveRatio(true);
                    whitePiece.setMouseTransparent(true);
                    whitePiece.fitWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));
                    whitePiece.fitHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), height));
                } else if (game.map[x][y] == 2) {
                    ImageView blackPiece = new ImageView(blackPieceImage);
                    board.add(blackPiece, x, y);
                    blackPiece.setPreserveRatio(true);
                    blackPiece.setMouseTransparent(true);
                    blackPiece.fitWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));
                    blackPiece.fitHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), height));
                } else if (game.map[x][y] == 3 || game.map[x][y] == 4) {
                    ImageView marker = new ImageView(markingImage);
                    board.add(marker, x, y);
                    marker.setPreserveRatio(true);
                    marker.setMouseTransparent(true);
                    marker.fitWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));
                    marker.fitHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), height));
                }
            }
        }

        gamePane.getChildren().add(board);
        boardFile = game;
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


    public void OnMinimizeButtonClick() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
    //////////////////////////////////////////////////////////////
    ///                 Back to Menu button                    ///
    //////////////////////////////////////////////////////////////

    @FXML
    public void backToMenu() throws IOException {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        Menu menu = new Menu();
        menu.start(primaryStage);

    }

    //////////////////////////////////////////////////////////////
    ///                       Save game                        ///
    //////////////////////////////////////////////////////////////

    @FXML
    Button saveGame;

    @FXML
    public void filesaver(ActionEvent event) throws IOException {
        String saveFile = "Boardsave.txt";
        DimensionPrompt dimPrompt = new DimensionPrompt();

        try {
            FileWriter writer = new FileWriter(saveFile);
            writer.write("" + turn);
            writer.write("\n");
            writer.write("" + height);
            writer.write("\n");
            writer.write(dimPrompt.white);
            writer.write("\n");
            writer.write(dimPrompt.black);
            writer.write("\n");
            writer.write(dimPrompt.back1);
            writer.write("\n");
            writer.write(dimPrompt.back2);
            writer.write("\n");
            writer.write(boardFile.toString());
            writer.write("\n");
            writer.write("" + turnCounter);
            writer.close();
            System.out.println("Game saved");
            //gameLoader();
        } catch (Exception e) {
            System.out.println("Error occured");
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        Menu menu = new Menu();
        menu.start(primaryStage);


    }


}


