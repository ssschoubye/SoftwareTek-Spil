import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.print.PrinterGraphics;
import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;


public class Visualizer extends Application {

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

    String appIcon = "Images/reversiIcon.png";


    static Button button = new Button("restart");

    static HBox hbox = new HBox();


    static Scene scene;

    static {
        try {
            hbox = new HBox(button, FXMLLoader.load(Objects.requireNonNull(Visualizer.class.getResource("game.fxml"))));
        } catch (IOException e) {
            System.out.println("Could not load FXML-file");

        }
    }

    static {
        scene = new Scene(hbox);
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

        Label showTurn = (Label) scene.lookup("#showTurn");


        Board game = new Board(width, height);
        game.initialize();
        turn = game.startingPlayer(gameNumber, firstStartingPlayer);
        showTurn.setText(turnColor(turn) + "'s turn");

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


                // Create an event handler for "on action"
                cells[i][j].setOnAction(event -> {
                    if (game.placePiece(ii, jj, turn)) {
                        turnCounter++;
                        //Switches player turn
                        if (turnCounter == 3) {
                            turn = Board.turnSwitch(turn);
                            showTurn.setText(turnColor(turn) + "'s turn");
                        }

                        if (turnCounter > 4) {
                            turn = Board.turnSwitch(turn);
                            showTurn.setText(turnColor(turn) + "'s turn");


                            //Checks for legal spots
                            if (!game.legalSpots(turn)) {
                                if (!game.legalSpots(Board.turnSwitch(turn))) {
                                    System.out.println("No more possible moves \n    game over");
                                    //Save value for ending game
                                    WinPage win = new WinPage();
                                    turnCounter = 1;
                                    gameNumber++;
                                    try {
                                        primaryStage.close();
                                        win.winStart(game);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                } else {

                                    System.out.println("\n" + turn + " has no possible moves");
                                    turn = Board.turnSwitch(turn);
                                    showTurn.setText(turnColor(turn) + "'s turn");
                                    //no move possible for current player
                                }

                            }
                        }

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
        gameSaver(game);


        System.out.println(1);








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

  /*  public void printBoard() {
         String print = "";
        int[][] boardText = new int [0][];

        for (int i = 0; i <= width + 1; i++) {
            for (int j = 0; j <= width + 1; j++) {
                int x = boardText[i][j];
                print += x;
            }
        }
        String finalPrint = print;
        button.setOnAction(actionEvent -> {
                System.out.println(finalPrint);
            });

    } */

   /* public void printing(int x_axis, int y_axis) {
        Board board = new Board();
        int[][] map = board.map;


        for (int i = 0; i < y_axis; i++) {
            for (int j = 0; j < x_axis; j++) {
                if (map[j][i] == 0) {
                    System.out.print("-  ");
                } else if (map[j][i] == 3) {
                    System.out.print("*  ");
                } else {
                    System.out.print(map[j][i] + "  ");
                }
            }
            System.out.println();
        }

    } */

        public void gameSaver (Board board) {
        String saveFile = "Boardsave.txt";

        button.setOnAction(actionEvent -> {

            try {

                FileWriter writer = new FileWriter(saveFile);

                writer.write("" + turn);
                writer.write("\n");
                writer.write("" + height);
                writer.write("\n");
                writer.write(whiteImage);
                writer.write("\n");
                writer.write(blackImage);
                writer.write("\n");
                writer.write(backImage1);
                writer.write("\n");
                writer.write(backImage2);
                writer.write("\n");
                writer.write(board.boardtransfer());
                writer.close();
                System.out.println("Game saved");

                gameLoader();


            } catch (Exception e) {
                System.out.println("Error occured");
                throw new RuntimeException(e);
            }
        });
        }
        public void gameLoader () {
            try (BufferedReader lineReader = new BufferedReader(new FileReader("Boardsave.txt"))) {
                String line = "";

                String player = "";
                String dimension = "";
                String white = "";
                String black = "";
                String board1 = "";
                String board2 = "";
                String gridString = "";
                int [][] grid = new int[width][width];


                int lineCount = 0;
                while ((line = lineReader.readLine()) != null && lineCount < 7) {
                    if (lineCount == 0) {
                        player = line;
                    } else if (lineCount == 1) {
                        dimension = line;
                    } else if (lineCount == 2) {
                        white = line;
                    } else if (lineCount == 3) {
                        black = line;
                    } else if (lineCount == 4) {
                        board1 = line;
                    } else if (lineCount == 5) {
                        board2 = line;

                    } else if (lineCount == 6) {

                        gridString = line;

                    } lineCount++;
                }
                System.out.println(player);
                System.out.println(dimension);
                System.out.println(white);
                System.out.println(black);
                System.out.println(board1);
                System.out.println(board2);
                    for (int i = 0; i < width; i++) {
                        for (int j=0; j<width; j++) {
                            System.out.print(grid[j][i] + " ");
                           }
                        System.out.println();
                    }


            } catch (IOException e){
                e.printStackTrace();
            }


        }

    }
