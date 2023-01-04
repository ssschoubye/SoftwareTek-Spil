import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;


public class Visualizer extends Application {

    static int width = 8;
    static int height = 8;

    static int turn = 1;


    ArrayList<Point> moveList = new ArrayList();


    public static void main(String[] args) {launch(args);}

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

                updateGridpane(primaryStage, game, board);


                // Tilføj en event handler for "on action"
                cells[i][j].setOnAction(event -> {
                    if (game.placePiece(ii,jj,turn)){
                        //Switches player turn
                        turn = Board.turnSwitch(turn);

                        //Checks for legal spots
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
                        updateGridpane(primaryStage, game, board);



                    }

                });

                if ((i + j) % 2 == 0) {
                    cells[i][j].setStyle("-fx-background-color: BURLYWOOD; -fx-border-color: TAN");

                } else {
                    cells[i][j].setStyle("-fx-background-color: BLANCHEDALMOND; -fx-border-color: TAN");
                }
                cells[i][j].prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                cells[i][j].prefWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));

            }
            board.setAlignment(Pos.CENTER);
        }


        Scene scene = new Scene(board, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private void updateGridpane(Stage primaryStage, Board game, GridPane board) {

        board.getChildren().removeIf(node -> node instanceof ImageView);

        for (int x =0 ; x<width;x++){
            for(int y = 0; y<height;y++){

                if(game.map[x][y]==1){
                    ImageView whitePiece = new ImageView("whitePiece.png");
                    board.add(whitePiece, x, y);
                    whitePiece.setMouseTransparent(true);
                    whitePiece.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                    whitePiece.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                } else if (game.map[x][y]==2) {
                    ImageView blackPiece = new ImageView("blackPiece.png");
                    board.add(blackPiece, x, y);
                    blackPiece.setMouseTransparent(true);
                    blackPiece.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                    blackPiece.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                } else if (game.map[x][y]==3){
                    ImageView marker = new ImageView("marker.png");
                    board.add(marker, x, y);
                    marker.setMouseTransparent(true);
                    marker.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                    marker.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                }
            }
        }
    }


}