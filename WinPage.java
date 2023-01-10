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


    private int[] dim;

    public void winStart(Board game) throws IOException {
        dim = game.getDim();

        int[] score = game.getScore();
        System.out.println(score[0] + " " + score[1]);
        if (score[0] > score[1]) {
            Stage stage = new Stage();
            start(stage, dim);
            String winner = "White won with "+score[0]+" pieces on the board!";
            String loser  = "Black lost with only "+score[1]+" pieces." ;
            setLoser(loser);
            setWinner(winner);
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

    @FXML
    public void start(Stage primaryStage, int[] dim) throws IOException {
        int[] dim1 = dim;
        Parent root = FXMLLoader.load(getClass().getResource("Winpage.fxml"));
        Scene scene = new Scene(root);
        String appIcon = "Images/reversiIcon.png";
        Image icon = new Image(appIcon);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Reversi");

        restart = (Button) scene.lookup("#restart");
        restart.setUserData(dim1);
        label1 = (Label) scene.lookup("#label1");
        label2 = (Label) scene.lookup("#label2");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    @FXML
    public void draw(Stage primaryStage, int[] dim) throws IOException {
        int[] dim1 = dim;
        Parent root = FXMLLoader.load(getClass().getResource("Draw.fxml"));

        Scene scene = new Scene(root);
        String appIcon = "Images/reversiIcon.png";
        Image icon = new Image(appIcon);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Reversi");
        restart = (Button) scene.lookup("#restart");
        restart.setUserData(dim1);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    Label label1;

    @FXML
    public void setWinner(String winner) {
        label1.setText(winner);
    }

    @FXML
    Label label2;

    @FXML
    public void setLoser(String loser) {
        label2.setText(loser);
    }

    @FXML
    Button restart;

    @FXML
    public void restart() throws IOException {
        int[] dim =(int[]) restart.getUserData();
        Stage stage = (Stage) restart.getScene().getWindow();
        stage.close();

        Visualizer visualizer = new Visualizer();
        visualizer.gameStart(dim[0], dim[1]);
    }


    //////////////////////////////////////////////////////////////
    ///                    Title bar layout                    ///
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



}





