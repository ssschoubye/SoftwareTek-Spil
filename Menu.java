import java.awt.*;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;




public class Menu extends Application {

    public static void Menu(String[]args){
        launch(args);
    }

    @FXML
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("visual.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    Button button1;

    @FXML
    private void startGame(){


        System.out.println("Start Game");
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
}

