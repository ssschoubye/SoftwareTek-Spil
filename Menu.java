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

    //////////////////////////////////////////////////////////////
    ///               Menu Stage setup and logic               ///
    ///               by Søren Sehested Schoubye s224756       ///
    ///     Code for creating the stage containing the menu    ///
    //////////////////////////////////////////////////////////////

    public static boolean loadGame = false;


    public static void main(String[]args){
        //The main method is used to launch the application. Its only purpose is to launch the following method.
        launch(args);
    }

//The start method is used to create the stage and set the scene.
    public void start(Stage primaryStage) throws IOException {
        //First we make a connection to the FXML file, which contains the layout of the stage.
        //The FXML file is created using SceneBuilder.
        Parent root = FXMLLoader.load(getClass().getResource("menuvis.fxml"));
        Scene scene = new Scene(root);
        String appIcon = "Images/reversiIcon.png"; //The icon of the application is set here.
        Image icon = new Image(appIcon); //Creating an image object from the icon file.
        primaryStage.setTitle("Reversi Advanced"); //The title of the application is set here.
        primaryStage.getIcons().add(icon);  //The icon is added to the stage.
        primaryStage.initStyle(StageStyle.UNDECORATED); //The stage is set to be undecorated, meaning that it has no borders.
        primaryStage.setScene(scene); //The scene is added to the stage.
        primaryStage.show(); //The stage is shown. Meaning the window appears on the screen.

    }

    @FXML //The FXML annotation is used to connect the following code to the FXML file.
    Button button1; //This corresponds to a button named button1 in the FXML file.
    //The following method starts the game.
    @FXML
    private void startGame()  { //In the FXML file this method is connected to the button1 button.
        //This happens through the onAction attribute of the button. Meaning when the button is pressed, this method is called.
        Stage stage = (Stage) button1.getScene().getWindow(); //This specifies the stage that the button is in.
        stage.close(); //Closes the stage that the button is acociated with. Meaning the menu window will close.
        DimensionPrompt.start1(); //This will call the start1 method in the DimensionPrompt class.
        // Resulting in the dimension prompt window appearing.
    }

    //The following method is used to load a game.
    //The usage of the button and closing the scene is the same as in the startGame method.
    @FXML
    Button gameLoad;

    //PlayAlone game;
    @FXML
    private void loadGame(){
        DimensionPrompt.gamemode = 1; //The gamemode is set to 1, meaning that the game will be played alone.
        //For now the load game function is only available in the play alone mode.
        Stage stage = (Stage) gameLoad.getScene().getWindow();
        stage.close();
        PlayAlone game = new PlayAlone(); //A new PlayAlone object is created.

        //The following code is explained in the PlayAlone class.
        loadGame=true; //
        GameLoader dimLoad = new GameLoader();

        game.gameStart(dimLoad.dimensionLoad, dimLoad.dimensionLoad);
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

