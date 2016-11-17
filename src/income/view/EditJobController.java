package income.view;

import income.model.JobsEntity;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
            if (job.getDeafultIncome() == null) {
                income.setText("0");
            } else {
                income.setText(job.getDeafultIncome().toString());
            }
            describe.setText(job.getDescribe());
        }
    }

    public boolean isInputValid() {
        String errorMessage = "";
        if (jobName == null || jobName.getText().length() == 0) {
            errorMessage += "Nazwa pracy nie może być pusta!\n";
        }
        return true;
    }


}
