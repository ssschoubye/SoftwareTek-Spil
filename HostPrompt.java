import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Objects;

public class HostPrompt {
    Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(DimensionPrompt.class.getResource("HostPrompt.fxml"))));

    public HostPrompt() throws IOException {
    }

    public void runHostPrompt() throws IOException {
        IPhost = (Label) scene.lookup("#label1");
        InetAddress localHost = InetAddress.getLocalHost();
        String hostip = localHost.getHostAddress();
        setIP(hostip);
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(DimensionPrompt.class.getResource("dimension.fxml"))));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.showAndWait();
    }


    @FXML
    Button button;
    @FXML
    private TextField IPjoin;

    public void startGame() throws IOException, ClassNotFoundException {
        String IPinput = IPjoin.getText();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        ServerClient.runServerClient(IPinput);
    }

    @FXML
    private Label IPhost;

    @FXML
    public void setIP(String hostip){IPhost.setText(hostip);}




}
