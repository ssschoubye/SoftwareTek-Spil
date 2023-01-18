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

    //instantiates the width and height for the game
    static int width;
    static int height;

    //initializes starting player for the first game
    static int turn = (int) (Math.random() * 2) + 1;
    static int firstStartingPlayer = (int) (Math.random() * 2) + 1;

    //Initializes the number of games played
    static int gameNumber = 1;

    //initializes the turncounter for a game
    static int turnCounter = 1;

    //Sets paths to different images used for the visualization
    String whiteImage = "";
    String blackImage = "";
    String markerImage = "Images/markerDark.png";
    String backImage1 = "Images/Background/green1.png";
    String backImage2 = "Images/Background/green2.png";
    String appIcon = "Images/reversiIcon.png";

    //Sets path to the mp3 which plays when placing a piece
    String placeSoundFile = "Sounds/placeSound1.mp3";

    //Initializes BoardFile for saving the game at a later time
    static Board boardFile = new Board();

    //Instantiates the scene for the game
    Scene scene;


    public void gameStart(int inwidth, int inheight) {

        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(PlayAlone.class.getResource("playAlone.fxml"))));
        } catch (IOException ignored) {

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

        //Loads in the sound to be used
        AudioClip placeSound = new AudioClip(getClass().getResource(placeSoundFile).toExternalForm());

        //Finds the label which shows whose turn it is
        Label showTurn = (Label) scene.lookup("#showTurn");

        //Created a new game with the Board object
        Board game = new Board(width, height);

        //Sets four middle squares to 4
        game.initialize();

        //Determines who is to start the game
        turn = game.startingPlayer(gameNumber, firstStartingPlayer);

        //Updates the label for showing turn
        showTurn.setText(turnColor(turn) + "'s turn");

        //Sets turn counter to 1
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

        //Finds label for white's score and sets the score
        Label whiteScore = (Label) scene.lookup("#whiteScore");
        whiteScore.setText("x" + game.getScore()[0]);

        //Finds label for black's score and sets the score
        Label blackScore = (Label) scene.lookup("#blackScore");
        blackScore.setText("x" + game.getScore()[1]);

        //Initializes a gridpane to add buttons and graphic to
        GridPane board = new GridPane();

        //Creates a 2D array of buttons
        Button[][] cells = new Button[width][height];

        //Nested for loop which goes through the array and sets the buttons and sets their actions
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                //Creates a new button at the given coordinate set
                cells[i][j] = new Button();

                //Sets the style of the buttons by using a stylesheet
                cells[i][j].getStylesheets().add(getClass().getResource("boardButtons.css").toExternalForm());

                //Adds the button to its respective position in the gridPane
                board.add(cells[i][j], i, j);

                //Creates new integers for the button for using when parsing its coordinate set
                final int ii = i;
                final int jj = j;

                //A method for updating what is seen when playing the game
                updateGridPane(game, board);

                //Setting the action for the button
                cells[i][j].setOnAction(event -> {

                    //When clicking a field, first the game checks if that field is a possible move for the player
                    //Places the piece in case this is possible
                    if (game.placePiece(ii, jj, turn)) {

                        //Plays the place sound
                        placeSound.play();

                        //Adds to the turn counter
                        turnCounter++;

                        //Switches turns
                        if (turnCounter == 3) {
                            turn = Board.turnSwitch(turn);
                            showTurn.setText(turnColor(turn) + "'s turn");
                        }

                        //After the turn count reaches 4 or more, the turn will switch every round and new legal spots will be found
                        if (turnCounter > 4) {
                            turn = Board.turnSwitch(turn);
                            showTurn.setText(turnColor(turn) + "'s turn");

                            //Checks if the new player has a move
                            if (!game.legalSpots(turn)) {

                                //Checks if the old player has a move, given that the new player doesn't
                                if (!game.legalSpots(Board.turnSwitch(turn))) {
                                    //None of the players have a move and the game is now over

                                    //Updates the final view of the board
                                    updateGridPane(game, board);

                                    //Creates a new thread in order to let the FX application update its view
                                    //And then have a delay before continuing to the restart page
                                    new Thread(() -> {
                                        try {
                                            //Sets the delay timer to 800 milliseconds
                                            Thread.sleep(800);
                                        } catch (InterruptedException e) {
                                            throw new RuntimeException(e);
                                        }

                                        WinPage win = new WinPage();
                                        //Sets turncounter back to 1
                                        turnCounter = 1;

                                        //Increments game number
                                        gameNumber++;

                                        //Tells the program to start the restart page when other tasks are finished
                                        Platform.runLater(() -> {
                                            primaryStage.close();
                                            try {
                                                win.winStart(game);
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }

                                        });

                                    }).start();

                                //For the case where the new player can't make a move but the old player can
                                } else {
                                    turn = Board.turnSwitch(turn);
                                    showTurn.setText(turnColor(turn) + "'s turn");
                                }

                            }
                        }

                        //Updates the score for both players
                        whiteScore.setText("x" + game.getScore()[0]);
                        blackScore.setText("x" + game.getScore()[1]);

                        //Updates the visuals
                        updateGridPane(game, board);


                    }
                });

                //Locates the pane on the playing stage
                Pane gamePane = (Pane) scene.lookup("#gamePane");

                //Sets the button to be the appropriate width and height
                //This is done by dividing the height and width of the gamePane with the width and height of the game
                cells[i][j].prefHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), height));
                cells[i][j].prefWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), height));

            }
            //Sets the alignment for objects on the board to be centered
            board.setAlignment(Pos.CENTER);

        }

        //Sets the Icon for the application
        //Usually this will both set the icon in the title bar and on the task bar of the computer
        //However as a new custom title bar was made, this method only sets the icon for the task bar
        Image icon = new Image(appIcon);
        primaryStage.getIcons().add(icon);
        //Sets the title for the stage. Same as for the icon, this is only for when hovering over the game in the task bar
        primaryStage.setTitle("Reversi Advanced");

        //Updates the boardfile to match the game, in case the player wants to save it
        boardFile = game;

        //sets the scene for the stage
        primaryStage.setScene(scene);

        //Removes the default title bar
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();


    }


    //Method for updating the visuals to match that of the game
    private void updateGridPane(Board game, GridPane board) {

        //Finds the pane on the scene, for which the game board is added to
        Pane gamePane = (Pane) scene.lookup("#gamePane");

        //Removes all current images from the gridpane
        //This is done to not have multiple pictures on top of each other
        board.getChildren().removeIf(node -> node instanceof ImageView);

        //Removing current gridpane from the pane
        //Again, this is done to avoid having multiple boards on top of the same pane
        gamePane.getChildren().remove(board);

        //Creates the images for the different graphics of the board to add them to the gridpane as an imageview at
        //a later point
        Image whitePieceImage = new Image(whiteImage);
        Image blackPieceImage = new Image(blackImage);
        Image markingImage = new Image(markerImage);
        Image backGround1 = new Image(backImage1);
        Image backGround2 = new Image(backImage2);

        //Updates the image used for the black and white pieces for the graphic that shows their scores
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


        //Iterates through the game
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                //The following block of codes alternates between the two background images and adds them to the gridPane
                //The alternating between them is what achieves the checkered look
                ImageView back;
                if ((x + y) % 2 == 1) {
                    back = new ImageView(backGround1);
                } else {
                    back = new ImageView(backGround2);
                }
                board.add(back, x, y);
                back.setMouseTransparent(true);
                back.setPreserveRatio(true);
                //Sets the back images to be of the appropriate size
                back.fitWidthProperty().bind(Bindings.divide(gamePane.widthProperty(), width));
                back.fitHeightProperty().bind(Bindings.divide(gamePane.heightProperty(), height));

                //The following three if statements adds their respective graphic in the same way that the background images
                //where added
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
        //Adds the updated board to the gamePane again
        gamePane.getChildren().add(board);
    }

    //Method for returning the respective color string for the two playerturns, 1 and 2
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        Menu menu = new Menu();
        menu.start(primaryStage);


    }


}


