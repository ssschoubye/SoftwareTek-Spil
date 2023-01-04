import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;


public class Visualizer extends Application {

    static int width = 8;
    static int height = 8;

    static int turn = 1;

    String whiteImage = "Images/whitePiece.png";
    String blackImage = "Images/blackPiece.png";
    String markerImage = "Images/marker.png";
    String backImage1 = "Images/backgroundSkins/chess1.png";
    String backImage2 = "Images/backgroundSkins/chess2.png";


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        Board game = new Board(width, height);
        game.initialize();
        game.legalSpots(1);


        // Create GridPane, which will function as the playing board
        GridPane board = new GridPane();


        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

        // Create 2D array of buttons, which functions as the individual cells on the playing board
        Button[][] cells = new Button[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Button();

                board.add(cells[i][j], i, j);
                cells[i][j].minWidth(-1);



                final int ii = i;
                final int jj = j;

                updateGridpane(primaryStage, game, board, blackImage, whiteImage, markerImage);


                // Tilføj en event handler for "on action"
                cells[i][j].setOnAction(event -> {
                    if (game.placePiece(ii, jj, turn)) {
                        //Switches player turn
                        turn = Board.turnSwitch(turn);

                        //Checks for legal spots
                        if (!game.legalSpots(turn)) {
                            if (!game.legalSpots(Board.turnSwitch(turn))) {
                                System.out.println("No more possible moves \n    game over");
                                //Save value for ending game
                            } else {
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


        Scene scene = new Scene(board, 600, 600);
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


}