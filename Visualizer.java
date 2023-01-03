import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.awt.*;


public class Visualizer extends Application {

    int width = 8;
    int height = 8;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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

                // Tilføj en event handler for "on action"
                cells[i][j].setOnAction(event -> {
                    // Opret en ny ImageView og tilføj den til brættet
                    ImageView imageView = new ImageView("7575white.png");
                    board.add(imageView, ii, jj);

                    // Skaler billedet med brættet
                    imageView.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                    imageView.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
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


    }