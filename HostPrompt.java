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
    //Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(HostPrompt.class.getResource("OnlineDesign.fxml"))));
    int dim;

    public void runHostPrompt() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OnlineDesign.fxml"));
        Scene scene = new Scene(root);
        IPhost = (Label) scene.lookup("#IPhost");
        InetAddress localHost = InetAddress.getLocalHost();
        String hostip = localHost.getHostAddress();
        setIP(hostip);
        Stage stage = new Stage();
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
        OnlineController onlineController = new OnlineController();
        onlineController.onlineGame(isHost, firstTime);
    }

    @FXML
    private Label IPhost;

    @FXML
    private void setIP(String hostip){IPhost.setText(hostip);}

    @FXML
    private void startHost() throws IOException, ClassNotFoundException, InterruptedException {
        boolean isHost = true;
        OnlineController onlineController = new OnlineController();
        onlineController.onlineGame(isHost, firstTime);
    }

    public static String getIPinput(){
        return(IPinput);
    }

}
