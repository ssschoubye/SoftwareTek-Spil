import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

import java.io.IOException;



public class Menu extends Application {



    public static boolean loadGame = false;


    public static void main(String[]args){
        launch(args);
    }


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
        Stage stage = (Stage) button1.getScene().getWindow();
        stage.close();
        DimensionPrompt.start1();
    }


    @FXML
    Button gameLoad;

    //PlayAlone game;
    @FXML
    private void loadGame(){
        DimensionPrompt.gamemode = 1;
        Stage stage = (Stage) gameLoad.getScene().getWindow();
        stage.close();
        PlayAlone game = new PlayAlone();

        loadGame=true;
        GameLoader dimLoad = new GameLoader();

        game.gameStart(dimLoad.dimensionLoad, dimLoad.dimensionLoad);
    }

    @FXML
    Button button3;

    @FXML
    private void options(){
        System.out.println("Options");
    }






    //////////////////////////////////////////////////////////////
    ///                    Title bar layout                    ///
    ///               by Emil Wille Andersen s194501           ///
    ///     Code for creating the custom title bar layout      ///
    //////////////////////////////////////////////////////////////

    //Setting the HBox in FXML to be the titlebar
    @FXML
    HBox titlebar;

    //Initializing an x- and y-value for dragging the window around
    private double windowX = 0;
    private double windowY = 0;

    //This, as well as the next method is for dragging a window
    //The idea for these methods comes from the internet
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

    //Method for closing the application
    @FXML
    private void onExitButtonClick(){
        Platform.exit();
    }

    //setting the button for minimizing the application
    @FXML
    Button minimize;
    //Setting the method for the button
    @FXML
    public void OnMinimizeButtonClick(){
        Stage stage = (Stage)minimize.getScene().getWindow();
        stage.setIconified(true);
    }

}

