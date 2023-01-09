import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class DimensionPrompt {


    String backImage1 = "Images/backgroundSkins/chess1.png";
    String backImage2 = "Images/backgroundSkins/chess2.png";


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
        dimStage.initStyle(StageStyle.UNDECORATED);
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

    @FXML
    static
    GridPane previewPane;
    @FXML
    Label previewPaneLabel;

    private void fillPreviewPane(int size){

    }


    //////////////////////////////////////////////////////////////
    ///                    Title bar layout                    ///
    //////////////////////////////////////////////////////////////
    @FXML
    HBox titlebar;

    private double windowX = 0;
    private double windowY = 0;

    @FXML
    private void titleBarDragged(MouseEvent event){
        Stage stage = (Stage) titlebar.getScene().getWindow();
        stage.setY(event.getScreenY()-windowY);
        stage.setX(event.getScreenX()-windowX);

    }
    @FXML
    private void titleBarPressed(MouseEvent event){
        windowX = event.getSceneX();
        windowY = event.getSceneY();

    }


    @FXML
    private void onExitButtonClick(){
        Platform.exit();
    }
    @FXML
    Button minimize;
    @FXML
    public void OnMinimizeButtonClick(){
        Stage stage = (Stage)minimize.getScene().getWindow();
        stage.setIconified(true);
    }











}
