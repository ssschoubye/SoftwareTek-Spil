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
        Parent root = FXMLLoader.load(getClass().getResource("menuvis.fxml"));
        Scene scene = new Scene(root);
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
        int [] dim = DimensionPrompt.start1();
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

