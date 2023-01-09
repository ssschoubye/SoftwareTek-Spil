import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DimensionPrompt {

    private static int x;
    private static int y;
    public static int[] start1()  {
            Stage stage = new Stage();
            start(stage);
            x = getDimx();
            y = getDimy();
            int[] dim = {x, y};
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
        String appIcon = "Images/reversiIcon.png";
        Image icon = new Image(appIcon);
        dimStage.getIcons().add(icon);
        dimStage.setTitle("Reversi");
        dimStage.setScene(scene);
        dimStage.showAndWait();
        textfield1 = (TextField) scene.lookup("#textfield1");
        textfield2 = (TextField) scene.lookup("#textfield2");

    }

    @FXML
    private static TextField textfield1;


    public static int getDimx() {
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
    private static TextField textfield3;

    @FXML
    public static String getPlayer1() {
        String text = textfield3.getText();
        return text;
    }

    @FXML
    private static TextField textfield4;

    @FXML
    public static String getPlayer2(){
        String text = textfield4.getText();
        return text;
    }



    @FXML

    private Button button1;

    @FXML
    private void closeApp(){
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();

    }

}
