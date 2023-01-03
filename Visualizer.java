import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Visualizer {
    private static int width = 12;
    private static int height = 12;


    
    public void start(Stage primaryStage) {
        GridPane board = new GridPane();
        Button[][] cells = new Button[width][height];

        for(int i = 0; i < height; ++i) {
            for(int j = 0; j < width; ++j) {
                cells[i][j] = new Button();
                board.add(cells[i][j], i, j);
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

        Scene scene = new Scene(board, 600.0, 600.0);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
