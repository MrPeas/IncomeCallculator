package income.view;

import income.model.JobsEntity;
import income.util.AlertUtil;
import income.util.ConverterUtil;
import javafx.fxml.FXML;
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
    public void initialize() {
        income.setText("0");

    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {
            job.setName(jobName.getText());
            job.setDeafultincome(new BigDecimal(income.getText()));
            job.setDescribe(describe.getText());
            okClicked = true;
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

    private boolean isInputValid() {
        String errorMessage = "";
        String title = "Złe dane";
        String header = "Wprowadź poprawne dane";
        AlertUtil alert = new AlertUtil();
        if (!ConverterUtil.isParseToString(jobName.getText())) {
            errorMessage += "Nazwa pracy nie może być pusta!\n";
        }
        if (!ConverterUtil.isParseToBigDecimal(income.getText())) {
            errorMessage += "Zły format, wprowadź liczbę dodatnią!\n";
        }
        return alert.isValid(title, header, errorMessage, dialogStage);
    }


}
