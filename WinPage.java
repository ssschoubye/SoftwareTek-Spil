import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class WinPage {



    public void winStart(Board game) throws IOException {
        Stage stage = new Stage();
        start(stage);
        int[] score = game.getScore();
        System.out.println(score[0] + " " + score[1]);
        if (score[0] > score[1]) {
            String winner = "Player 1";
            String loser  = "Player 2";
            setLoser(loser);
            setWinner(winner);
        }else if (score[0] < score[1]) {
            String winner = "Player 2";
            String loser  = "Player 1";
            setWinner(winner);
            setLoser(loser);
        }else{
            setWinner(null);
            setLoser(null);
        }

    }

    @FXML
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Winpage.fxml"));
        Scene scene = new Scene(root);

        label1 = (Label) scene.lookup("#label1");
        label2 = (Label) scene.lookup("#label2");
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

}
