package income.view;

import income.DAO.DAOUsers;
import income.DAO.DAOUsersImpl;
import income.Main;
import income.model.UsersEntity;
import income.util.AlertUtil;
import income.util.ConverterUtil;
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
    private Stage dialogStage;
    private Main main;
    private DAOUsers daoUsers=new DAOUsersImpl();

    public void setMain(Main main) {
        this.main = main;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void initialize(){
    }

    @FXML
    private void handleLogIn(){
        UsersEntity user;
        if(ConverterUtil.isParseToString(username.getText())) {
            user = daoUsers.findByUsername(username.getText().toLowerCase());
            if(checkUser(user)) {
                this.loginStatus = true;
                main.setUserId(user.getId());
                main.setUsername(user.getLogin());
                user.setLogin("");
                user.setPassword("");
                dialogStage.close();
            }
        }else {
            warning.setText("login ani hasło nie mogą być puste");
        }
    }

    @FXML
    private void handleRegistration(){
        AlertUtil alert=new AlertUtil();
        boolean success=main.registerDialog(dialogStage);
        if(success){
            String title="rejstracja";
            String header="Rejetracja powiodła się";
            String information="użytkownik został dodany";
            alert.informationAlert(title,header,information,dialogStage);
        }
    }

    public boolean loginStatus(){
        return loginStatus;
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
