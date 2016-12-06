package income.util;

import javafx.stage.Stage;

/**
 * Created by Janusz on 05.12.2016.
 */
public class AlertUtil {
    public static boolean isValid(String title, String header, String errorMessage,Stage dialogStage) {
        if (errorMessage.length() == 0) {
            return true;
        } else {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
