import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

public class HostPrompt {
    //Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(HostPrompt.class.getResource("OnlineDesign.fxml"))));
    int dim;

    static Stage stage = new Stage();
    public void runHostPrompt() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("OnlineDesign.fxml"));
        Scene scene = new Scene(root);
        IPhost = (Label) scene.lookup("#IPhost");
        InetAddress localHost = InetAddress.getLocalHost();
        String hostip = localHost.getHostAddress();
        setIP(hostip);

        stage.setScene(scene);
        stage.show();
    }


    @FXML
    Button button;
    @FXML
    private TextField IPjoin;
    public static String IPinput;

    public static boolean firstTime = true;
    public void joinGame() throws IOException, ClassNotFoundException, InterruptedException {
        IPinput = IPjoin.getText(); //Gets the IP address input.
        Stage stage = (Stage) button.getScene().getWindow();//Close the stage and the start the controller.
        stage.close();
        boolean isHost = false;
        PlayOnlineClient playOnlineClient = new PlayOnlineClient();
        playOnlineClient.gameStart();

    }

    @FXML
    private Label IPhost;

    @FXML
    private void setIP(String hostip){IPhost.setText(hostip);}

    @FXML
    private Button hostgame;
    @FXML
    private void startHost() throws IOException, ClassNotFoundException, InterruptedException {
        boolean isHost = true;
        Stage stage = (Stage) hostgame.getScene().getWindow();//Close the stage and the start the controller.
        stage.close();
        PlayOnlineHost playOnlineHost = new PlayOnlineHost();
        playOnlineHost.gameStart();
    }

    public static String getIPinput(){
        return(IPinput);
    }
    public static void stageClose(){
        stage.close();
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
