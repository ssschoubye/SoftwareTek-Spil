

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class titlebarController {

    @FXML
    protected void onExitButtonClick(){
        Platform.exit();
    }



}
