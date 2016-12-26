package income.view;

import income.DAO.DAOUsers;
import income.DAO.DAOUsersImpl;
import income.model.UsersEntity;
import income.util.AlertUtil;
import income.util.ConverterUtil;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Janusz on 23.12.2016.
 */
public class RegistrationDialogController {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField passwordConfirm;

    boolean okClicked = false;
    private Stage dialogStage;
    private UsersEntity user;
    private DAOUsers daoUsers = new DAOUsersImpl();

    @FXML
    public void initialize() {
    }

    @FXML
    public void handleOk() {
        AlertUtil alert = new AlertUtil();
        if (isInputValid()) {
            user = new UsersEntity();
            user.setLogin(username.getText().toLowerCase());
            user.setPassword(password.getText());
            if (daoUsers.findByUsername(user.getLogin()) == null) {
                daoUsers.add(user);
                okClicked = true;
                dialogStage.close();
            } else {
                String title = "Error";
                String header = "Błąd rejestracji";
                String information = "Użytkownik o podanym nicku istnieje";
                alert.errorAlert(title, header, information, dialogStage);
            }
        }
    }

    @FXML
    public void handleCancel() {
        dialogStage.close();
    }

    public boolean isInputValid() {
        String errorMessage = "";
        String title = "Złe dane";
        String header = "Wprowadź poprawne dane";
        AlertUtil alert = new AlertUtil();
        if (!ConverterUtil.isParseToString(username.getText())) {
            errorMessage += "Nazwa pracy nie może być pusta!\n";
        }
        if (!ConverterUtil.isParseToString(password.getText())) {
            errorMessage += "hasło nie może być puste!\n";
        }
        if (!ConverterUtil.isParseToString(passwordConfirm.getText())) {
            errorMessage += "brak potwierdzenia hasła!\n";
        }
        if (password.getText().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
            errorMessage += "hasło musi zawierać cyfry i litery\n";
        }
        if (!passwordConfirm.getText().equals(password.getText())) {
            errorMessage += "hasła muszą być identyczne\n";
        }
        return alert.isValid(title, header, errorMessage, dialogStage);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;

    }
}
