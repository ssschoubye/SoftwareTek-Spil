import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class WinPage {


    private int[] dim;

    public void winStart(Board game) throws IOException {
        dim = game.getDim();

        int[] score = game.getScore();
        score[0] = 1;
        score[1] = 1;
        System.out.println(score[0] + " " + score[1]);
        if (score[0] > score[1]) {
            Stage stage = new Stage();
            start(stage, dim);
            String winner = "Player 1";
            String loser  = "Player 2";
            setLoser(loser);
            setWinner(winner);
        }else if (score[0] < score[1]) {
            Stage stage = new Stage();
            start(stage, dim);
            String winner = "Player 2";
            String loser  = "Player 1";
            setWinner(winner);
            setLoser(loser);
        }else{
            Stage stage = new Stage();
            draw(stage);
        }

    }

    @FXML
    public void start(Stage primaryStage, int[] dim) throws IOException {
        int[] dim1 = dim;
        Parent root = FXMLLoader.load(getClass().getResource("Winpage.fxml"));
        Scene scene = new Scene(root);

        restart = (Button) scene.lookup("#restart");
        restart.setUserData(dim1);
        label1 = (Label) scene.lookup("#label1");
        label2 = (Label) scene.lookup("#label2");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    @FXML
    public void draw(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Draw.fxml"));
        Scene scene = new Scene(root);
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

}
