package income.view;

import income.model.JobsEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;

/**
 * Created by Janusz on 17.11.2016.
 * access to EditJobDialog.fxml
 */
public class EditJobController {
    @FXML
    private TextField jobName;
    @FXML
    private TextField income;
    @FXML
    private TextArea describe;

    private Stage dialogStage;
    private JobsEntity job;
    private boolean okClicked = false;

    @FXML
    private void initialize() {

    }

@FXML
public void handleOk(){
    if(isInputValid()){
        job.setName(jobName.getText());
        job.setDeafultincome(new BigDecimal(income.getText()));
        job.setDescribe(describe.getText());
        okClicked=true;
        dialogStage.close();
    }
}

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setJob(JobsEntity job) {
        this.job = job;
        if (job != null) {
            jobName.setText(job.getName());
            if (job.getDeafultincome() == null) {
                income.setText("0");
            } else {
                income.setText(job.getDeafultincome().toString());
            }
            if (job.getDescribe() == null) {
                describe.setText("");
            } else {
                describe.setText(job.getDescribe());
            }
        }
    }

    public boolean isInputValid() {
        String errorMessage = "";
        if (jobName == null || jobName.getText().length() == 0) {
            errorMessage += "Nazwa pracy nie może być pusta!\n";
        }
        if (income == null || income.getText().length() == 0) {
            income.setText("0");
        }else{
            try {
                BigDecimal number=new BigDecimal(income.getText());
                if(number.doubleValue()<0.00)
                    throw new NumberFormatException();
            }catch (NumberFormatException e){
                errorMessage += "Zły format, wprowadź liczbę dodatnią!\n";
            }
        }
        if(errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(dialogStage);
            alert.setTitle("Złe dane");
            alert.setHeaderText("Wprowadź poprawne dane");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }


}
