import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class WinPage {
    //////////////////////////////////////////////////////////////
    ///                      End of Game page                  ///
    ///               by Søren Sehested Schoubye s224756       ///
    ///     Creates the end of game page and redirects player  ///
    //////////////////////////////////////////////////////////////
    private int[] dim; //The dimensions of the board are stored in this array.

    public void winStart(Board game) throws IOException {
        dim = game.getDim(); //gets the dimensions of the board from the game object.

        int[] score = game.getScore(); //gets the score of the game from the game object.
        System.out.println(score[0] + " " + score[1]);
        if (score[0] > score[1]) { //Determines which player won the game.
            Stage stage = new Stage();
            start(stage, dim); //Calls the start method to launch the stage and set the scene.
            String winner = "White won with "+score[0]+" pieces on the board!";
            String loser  = "Black lost with only "+score[1]+" pieces." ;
            setLoser(loser); //sets the loser label to the correct text.
            setWinner(winner); //sets the winner label to the correct text.
        }else if (score[0] < score[1]) {
            Stage stage = new Stage();
            start(stage, dim);
            String winner = "Black won with "+score[1]+" pieces on the board!";
            String loser  = "White lost with only "+score[0]+" pieces.";
            setWinner(winner);
            setLoser(loser);
        }else{
            Stage stage = new Stage();
            start(stage, dim);
            String winner = "Aww, looks like the game ended in a draw!\nBoth players had "+score[0]+" points.";
            String loser  = "";
            setWinner(winner);
            setLoser(loser);
        }

    }
//The start method is used to create the stage and set the scene.
    @FXML
    public void start(Stage primaryStage, int[] dim) throws IOException {
        int[] dim1 = dim; //Loads the dimensions of the board into an array.
        Parent root = FXMLLoader.load(getClass().getResource("Winpage.fxml")); //How the stage precisely is made have been explained in the menu class.
        Scene scene = new Scene(root);
        String appIcon = "Images/reversiIcon.png";
        Image icon = new Image(appIcon);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Reversi Advanced");

        restart = (Button) scene.lookup("#restart");
        restart.setUserData(dim1);
        label1 = (Label) scene.lookup("#label1");//These lines of code are used to get the labels and buttons from the fxml file.
        label2 = (Label) scene.lookup("#label2");//Thereby the labels can be filled with the correct text before the stage is shown.
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    //The following two methods inserts the correct text into the labels.
    //They are linked to the fxml file with the @FXML annotation.
    @FXML
    Label label1;
    @FXML
    public void setWinner(String winner) {
        label1.setText(winner); //Sets the text of the label to the winner text.
    }
    @FXML
    Label label2;

    @FXML
    public void setLoser(String loser) {
        label2.setText(loser);
    }
//The following method is used to close the stage.
    @FXML
    Button restart;

    @FXML
    public void restart() throws IOException {
        int[] dim = (int[]) restart.getUserData(); //Gets the dimensions of the board from the button.
        //How the stage is closed have been explained in the menu class.
        Stage stage = (Stage) restart.getScene().getWindow();
        stage.close();

        if (DimensionPrompt.gamemode==1){ //If the game was a singleplayer game, the game is restarted.
            PlayAlone playAlone = new PlayAlone();//The game is restarted by creating a new PlayAlone object.
            playAlone.gameStart(dim[0], dim[1]);
        }else if (DimensionPrompt.gamemode==2){
            PlayAI playAI = new PlayAI();
            playAI.gameStart(dim[0], dim[1]);
        }
    }


    //////////////////////////////////////////////////////////////
    ///                    Title bar layout                    ///
    ///              Same code as for the Menu.java            ///
    ///       Further explanations of code can be sen there    ///
    //////////////////////////////////////////////////////////////
    @FXML
    HBox titlebar;

    private double windowX = 0;
    private double windowY = 0;

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

    //////////////////////////////////////////////////////////////
    ///                 Back to Menu button                    ///
    //////////////////////////////////////////////////////////////

    @FXML
    public void backToMenu() throws IOException { //Functionality of the back to menu is described in the DimensionsPrompt class.
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        Menu menu = new Menu();
        menu.start(primaryStage);

    }

}





