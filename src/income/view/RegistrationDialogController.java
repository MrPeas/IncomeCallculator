package income.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Janusz on 23.12.2016.
 */
public class RegistrationDialogController {

    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private TextField passwordConfirm;

    boolean okClick=false;


    public void initialize() {

    }


    public void handleOk() {

    }

    public void handleCancel() {

    }


    public boolean isInputValid() {
        return false;
    }


    public boolean isOkClicked() {
        return false;
    }


    public void setDialogStage(Stage dialogStage) {

    }
}
