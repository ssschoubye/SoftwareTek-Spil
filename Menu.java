import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.io.IOException;



public class Menu extends Application {

private int [] dim;

    public static void Menu(String[]args){
        launch(args);
    }

    @FXML
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("menuvis.fxml"));
        Scene scene = new Scene(root);
        String appIcon = "Images/reversiIcon.png";
        Image icon = new Image(appIcon);
        primaryStage.setTitle("Reversi Advanced");
        primaryStage.getIcons().add(icon);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    Button button1;
    @FXML
    private void startGame()  {
        System.out.println("Start Game");
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();
        dim = DimensionPrompt.start1();
        setDim(dim);
        Visualizer game = new Visualizer();
        if (dim[0] == 0 || dim[1] == 0){
            dim[0] = 8;
            dim[1] = 8;
            System.out.println(dim[0]);
            game.gameStart(dim[0],dim[1]);
        }else if(dim[0] > 0 || dim[1] > 0){
            System.out.println(dim[0] + " " + dim[1]);
            game.gameStart(dim[0],dim[1]);
        }

    }
    public void setDim(int[] dim){
        this.dim = dim;
    }
    public int[] getDim(){
        int[] dim1 = dim;
        return dim1;
    }

    @FXML
    Button button2;
    @FXML
    private void loadGame(){
        System.out.println("Load Game");
    }
    @FXML
    Button button3;
    @FXML
    private void options(){
        System.out.println("Options");
    }

    //////////////////////////////////////////////////////////////
    ///                    Size of window                      ///
    //////////////////////////////////////////////////////////////
    Rectangle2D screenBounds = Screen.getPrimary().getBounds();

    @FXML
    int windowHeight = (int)screenBounds.getHeight()/2;
    @FXML
    double windowWidth = screenBounds.getHeight()*3/4;





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

