package income.view;

import income.Main;
import income.model.UsersEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Created by Janusz on 20.12.2016.
 */
public class LoginDialogController {

    @FXML
    TextField username;
    @FXML
    PasswordField password;
    @FXML
    Label warning;

    boolean loginStatus=false;
    Stage dialogStage;
    Main main;

    public void setMain(Main main) {
        this.main = main;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void initialize(){
        warning.setText("złe hasło lub login");
    }

    @FXML
    public void handleLogIn(){
        if(stringValidation(username.getText())) {
            UsersEntity user = main.findUserByUsername(username.getText().toLowerCase());
            if(checkUser(user)) {
                this.loginStatus = true;
                dialogStage.close();
            }
        }else {
            warning.setText("login ani hasło nie mogą być puste");
        }
    }

    @FXML
    public void handleRegistration(){
        loginStatus=false;

    }

    public boolean loginStatus(){
        return loginStatus;
    }

    private boolean stringValidation(String value){
        return !(value==null)&&!value.isEmpty();
    }

    private boolean checkUser(UsersEntity user){
        if(user!=null){
            if(user.getPassword().equals(password.getText())){
                return true;
            }else {
                warning.setText("złe hasło");
                return false;
            }
        }else{
            warning.setText("użytkownik nie istnieje");
            return false;
        }
    }
}
