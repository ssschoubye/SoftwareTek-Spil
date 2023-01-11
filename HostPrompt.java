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


    public void runHostPrompt(int x, int y) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("OnlineDesign.fxml"));
        Scene scene = new Scene(root);
        dim = x;
        IPhost = (Label) scene.lookup("#IPhost");
        InetAddress localHost = InetAddress.getLocalHost();
        String hostip = localHost.getHostAddress();
        setIP(hostip);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        Server.serverStart();

    }


    @FXML
    Button button;
    @FXML
    private TextField IPjoin;

    public void startGame() throws IOException, ClassNotFoundException {
        String IPinput = IPjoin.getText();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        ClientHandler.stopServer();
        ServerClient.runServerClient(IPinput,dim);
    }

    @FXML
    private Label IPhost;

    @FXML
    private void setIP(String hostip){IPhost.setText(hostip);}

}
