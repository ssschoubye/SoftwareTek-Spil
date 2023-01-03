import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class DimensionPrompt {

    public static int[] start1() throws IOException {
        Stage stage = new Stage();
        start(stage);

        String axis = "2";
        int x = getDimx();
        int y = getDimy();
        int[] dim = {x, y};
        stage.close();
        return dim;


    }
    @FXML
    public static void start(Stage dimStage) throws IOException {
        Parent root = FXMLLoader.load(DimensionPrompt.class.getResource("dimension.fxml"));
        Scene scene = new Scene(root);
        dimStage.setScene(scene);
        dimStage.show();
    }

    @FXML
    private static TextField textfield1;


    public static int getDimx() {
        String text = textfield1.getText();
        try {
            int x = Integer.parseInt(text);
            return x;

        } catch (NumberFormatException e) {
            // Handle the exception
        }
        return 8;
    }
    @FXML
    private static TextField textfield2;

    @FXML
    public static int getDimy() {
        String text = textfield2.getText();
        try {
            int y = Integer.parseInt(text);
            return y;

        } catch (NumberFormatException e) {
            // Handle the exception
        }
        return 8;
    }

    @FXML

    private Button button1;

    @FXML
    private void closeApp(){
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();
    }

}
