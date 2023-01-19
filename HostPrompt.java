import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

public class HostPrompt {
    static Stage stage = new Stage(); //static so that it can be accessed from other classes
    public void runHostPrompt() throws IOException {//Launches the stage further explanation of stages and scenes can be found in the menu class.
        Parent root = FXMLLoader.load(getClass().getResource("OnlineDesign.fxml"));
        Scene scene = new Scene(root);
        IPhost = (Label) scene.lookup("#IPhost"); //The IPhost label is found in the FXML file and is assigned to the IPhost variable.
        InetAddress localHost = InetAddress.getLocalHost(); //The localHost variable is assigned the local host address.
        String hostip = localHost.getHostAddress(); //The hostip variable is assigned the local host address.
        setIP(hostip); //Sets the text of the IPhost label to the local host address.

        stage.setScene(scene);
        stage.show();
    }


    @FXML
    Button button; //This corresponds to a button named button in the FXML file.
    @FXML
    private TextField IPjoin; //This corresponds to a text field named IPjoin in the FXML file.
    public static String IPinput; //This is the variable that will be used to store the IP address that the user inputs.

    public static boolean firstTime = true;
    public void joinGame() throws IOException, ClassNotFoundException, InterruptedException {
        //The joinGame is called through the onAction function of the button in the FXML file.
        IPinput = IPjoin.getText(); //Gets the IP address input.
        Stage stage = (Stage) button.getScene().getWindow();//Close the stage and the start the controller.
        stage.close();
        PlayOnlineClient playOnlineClient = new PlayOnlineClient();
        playOnlineClient.gameStart();

    }

    @FXML
    private Label IPhost; //The IPhost label is found in the FXML file and is assigned to the IPhost variable.

    @FXML
    private void setIP(String hostip){IPhost.setText(hostip);} //Sets the text of the IPhost label to the local host address.

    @FXML
    private Button hostgame;
    @FXML
    private void startHost() throws IOException, ClassNotFoundException, InterruptedException {
        Stage stage = (Stage) hostgame.getScene().getWindow();//Finds the stage that the button
        stage.close();
        PlayOnlineHost playOnlineHost = new PlayOnlineHost(); //Initialises an object of the type "PlayOnlineHost".
        playOnlineHost.gameStart();// Starts the host controller.
    }

    public static String getIPinput(){
        return(IPinput);
    }
    public static void stageClose(){
        stage.close();
    }

}
