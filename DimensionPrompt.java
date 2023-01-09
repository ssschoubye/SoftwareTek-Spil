import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class DimensionPrompt {


    static String backImage1 = "Images/Background/green1.png";
    static String backImage2 = "Images/Background/green2.png";

    static String whiteImage = "Images/whitePieceRing.png";
    static String blackImage = "Images/blackPieceRing.png";


    private static int x;
    private static int y;

    private static int size=8;
    /*
    Parent root = null;
        try {
        root = FXMLLoader.load(Objects.requireNonNull(DimensionPrompt.class.getResource("dimension.fxml")));
    } catch (IOException e) {
        throw new RuntimeException(e);
    }*/

    static Scene scene;

    static {
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(DimensionPrompt.class.getResource("dimension.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static int[] start1() {
        Stage stage = new Stage();
        start(stage);
        x = size;
        y = size;
        int[] dim = {x, y};
        System.out.println(dim);
        return dim;

    }

    @FXML
    public static void start(Stage dimStage) {


        String appIcon = "Images/reversiIcon.png";
        Image icon = new Image(appIcon);

        dimStage.getIcons().add(icon);
        dimStage.setTitle("Reversi");
        dimStage.initStyle(StageStyle.UNDECORATED);


        fillPreviewPane(size);



        dimStage.setScene(scene);
        dimStage.showAndWait();

    }

    @FXML
    static
    Pane previewPane;
    @FXML
    static
    Label previewPaneLabel;

    private static void fillPreviewPane(int size) {
        previewPane = (Pane)scene.lookup("#previewPane");
        previewPaneLabel = (Label)scene.lookup("#previewPaneLabel");
        previewPaneLabel.setText(size+"x"+size);
        Image whitePieceImage = new Image(whiteImage);
        Image blackPieceImage = new Image(blackImage);



        Image backGround1 = new Image(backImage1);
        Image backGround2 = new Image(backImage2);

        GridPane previewGrid = new GridPane();




        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                ImageView back;

                if ((i + j) % 2 == 1) {
                    back = new ImageView(backGround1);
                } else {
                    back = new ImageView(backGround2);
                }

                previewGrid.add(back, i, j);
                back.setMouseTransparent(true);
                back.setPreserveRatio(true);
                back.fitWidthProperty().bind(Bindings.divide(previewPane.widthProperty(), size));
                back.fitHeightProperty().bind(Bindings.divide(previewPane.heightProperty(), size));


                if ((i == size/2 && j == size/2-1)||(i == size/2 && j == size/2)){
                    ImageView whitePiece = new ImageView(whitePieceImage);
                    previewGrid.add(whitePiece,i,j);
                    whitePiece.setPreserveRatio(true);
                    whitePiece.setMouseTransparent(true);
                    whitePiece.fitWidthProperty().bind(Bindings.divide(previewPane.widthProperty(), size));
                    whitePiece.fitHeightProperty().bind(Bindings.divide(previewPane.heightProperty(), size));


                } else if ((i == size/2-1 && j == size/2-1)||(i == size/2-1 && j == size/2)){
                    ImageView blackPiece = new ImageView(blackPieceImage);
                    previewGrid.add(blackPiece,i,j);
                    blackPiece.setPreserveRatio(true);
                    blackPiece.setMouseTransparent(true);
                    blackPiece.fitWidthProperty().bind(Bindings.divide(previewPane.widthProperty(), size));
                    blackPiece.fitHeightProperty().bind(Bindings.divide(previewPane.heightProperty(), size));
                }




            }
        }




        previewPane.getChildren().add(previewGrid);

    }

    //////////////////////////////////////////////////////////////
    ///                          Skins                         ///
    //////////////////////////////////////////////////////////////



    //------------------Background skins-------------------------
    public void woodChoice(){
        backImage1 = "Images/Background/wood1.png";
        backImage2 = "Images/Background/wood2.png";
        fillPreviewPane(size);
    }
    public void blueChoice(){
        backImage1 = "Images/Background/blue1.png";
        backImage2 = "Images/Background/blue2.png";
        fillPreviewPane(size);
    }
    public void brownChoice(){
        backImage1 = "Images/Background/brown1.png";
        backImage2 = "Images/Background/brown2.png";
        fillPreviewPane(size);

    }
    public void bubbleGumChoice(){
        backImage1 = "Images/Background/bubblegum1.png";
        backImage2 = "Images/Background/bubblegum2.png";
        fillPreviewPane(size);

    }
    public void glassChoice(){
        backImage1 = "Images/Background/glass1.png";
        backImage2 = "Images/Background/glass2.png";
        fillPreviewPane(size);    }
    public void greenChoice(){
        backImage1 = "Images/Background/green1.png";
        backImage2 = "Images/Background/green2.png";
        fillPreviewPane(size);

    }

    //---------------------white skins-------------------------



    //---------------------black skins-------------------------
    






    //////////////////////////////////////////////////////////////
    ///                    Title bar layout                    ///
    //////////////////////////////////////////////////////////////
    @FXML
    HBox titlebar;

    private double windowX = 0;
    private double windowY = 0;

    public static void setSize(int size) {
        DimensionPrompt.size = size;
    }

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


}
