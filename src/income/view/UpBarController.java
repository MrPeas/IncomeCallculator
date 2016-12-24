package income.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Created by Janusz on 24.12.2016.
 */
public class UpBarController {
    @FXML
    private Label username;

    @FXML
    public void initialize(String username){
        this.username.setText(username);

    }
}
