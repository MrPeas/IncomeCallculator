package income.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * Created by Janusz on 05.12.2016.
 */
public class AlertUtil {
    public boolean isValid(String title, String header, String errorMessage, Stage dialogStage) {
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert = setAlertText(title, header, errorMessage, alert);
            alert.showAndWait();
            return false;
        }
    }

    public boolean confirmDialog(String title, String header, String information, Stage dialogStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(dialogStage);
        alert = setAlertText(title, header, information, alert);
        Optional<ButtonType> button = alert.showAndWait();
        if (button.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }

    }

    private Alert setAlertText(String title, String header,
                                      String text, Alert alert) {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        return alert;
    }
}
