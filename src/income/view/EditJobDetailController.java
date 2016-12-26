package income.view;

import income.DAO.DAOJobDetails;
import income.DAO.DAOJobDetailsImpl;
import income.model.JobDetailsEntity;
import income.model.JobsEntity;
import income.util.AlertUtil;
import income.util.ConverterUtil;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.sql.Date;


/**
 * Created by Janusz on 05.12.2016.
 */
public class EditJobDetailController {

    private Stage dialogStage;
    private JobDetailsEntity jobDetail;
    private JobsEntity job;
    private DAOJobDetails daoJobDetails = new DAOJobDetailsImpl();
    private boolean okClicked = false;
    private boolean edit = false;

    @FXML
    private TextField amountOfHours;
    @FXML
    private TextField hourlyWage;
    @FXML
    private DatePicker date;


    @FXML
    public void initialize() {
    }


    @FXML
    private void handleOk() {
        if (isInputValid()) {
            jobDetail.setIdJob(job.getId());
            jobDetail.setHours(Double.parseDouble(amountOfHours.getText()));
            jobDetail.setIncome(new BigDecimal(hourlyWage.getText()));
            jobDetail.setWokrDate(Date.valueOf(date.getValue()));
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

    private boolean isInputValid() {
        String errorMessage = "";
        String title = "Złe dane";
        String header = "Wprowadź poprawne dane";
        AlertUtil alert = new AlertUtil();
        try {
            if (daoJobDetails.isExistJobDetail(job.getId(), Date.valueOf(date.getValue())) && !edit) {
                errorMessage += "Data już istnieje w bazie\n";
            }

        } catch (NullPointerException e) {
            errorMessage += "Wybierz date\n";
        }
        if (!ConverterUtil.isParseToBigDecimal(hourlyWage.getText())) {
            errorMessage += "Zły format liczby, wprowadź liczbę dodatnią!(np: 10.6)\n";
        }
        if (!ConverterUtil.isParseToBigDecimal(amountOfHours.getText())) {
            errorMessage += "Zły format liczby, wprowadź liczbę dodatnią!(np: 10.6)\n";
        }
        return alert.isValid(title, header, errorMessage, dialogStage);
    }

    public void setJob(JobsEntity job) {
        this.job = job;
        hourlyWage.setText(job.getDeafultincome().toString());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setJobDetail(JobDetailsEntity jobDetail) {
        this.jobDetail = jobDetail;
        if (jobDetail != null) {
            amountOfHours.setText(jobDetail.getHours().toString());
            hourlyWage.setText(jobDetail.getIncome().toString());
            date.setValue(jobDetail.getWokrDate().toLocalDate());
            edit = true;
        } else {
            this.jobDetail = new JobDetailsEntity();
        }
    }

}
