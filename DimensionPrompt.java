import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DimensionPrompt {
    private static int x;
    private static int y;
    public static int[] start1()  {
        int i = 0;
            System.out.println("Test1");
            Stage stage = new Stage();
            start(stage);
            System.out.println("Test2");
            x = getDimx();
            System.out.println("Test3");
            y = getDimy();
            System.out.println("Test4");
            int[] dim = {x, y};
            System.out.println(dim);
            System.out.println("Test5");
            System.out.println(dim);
            return dim;

    }
    @FXML
    public static void start(Stage dimStage)  {

        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(DimensionPrompt.class.getResource("dimension.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        dimStage.setScene(scene);
        dimStage.showAndWait();
        textfield1 = (TextField) scene.lookup("#textfield1");
        textfield2 = (TextField) scene.lookup("#textfield2");

    }

    @FXML
    private static TextField textfield1;


    public static int getDimx() {
        System.out.println("Test5");
        String text = textfield1.getText();
        while (x == 0) {
            try {
                int x = Integer.parseInt(text);
                return x;

            } catch (NumberFormatException e) {
                System.out.println("Not a number");
                x = 0;
                return 0;
            }
        }
return x;
    }
    @FXML
    private static TextField textfield2;

    @FXML
    public static int getDimy() {
        System.out.println("Test6");
        String text = textfield2.getText();
        try {
            int y = Integer.parseInt(text);
            return y;

        } catch (NumberFormatException e) {
            System.out.println("Not a number");
            return 0;
        }

    }

    @FXML

    private Button button1;

    @FXML
    private void closeApp(){
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();

    }

}
