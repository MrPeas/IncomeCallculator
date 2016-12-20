package income.view;

import income.Main;
import income.model.JobDetailsEntity;
import income.model.JobsEntity;
import income.util.AlertUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Janusz on 31.10.2016.
 * Control give access to JobOverview.fxml
 */
public class JobOverviewController {

    @FXML
    private TableView<JobsEntity> jobsTable;
    @FXML
    private TableColumn<JobsEntity, String> nameColumn;

    @FXML
    private TableView<JobDetailsEntity> jobsDetailsTable;
    @FXML
    private TableColumn<JobDetailsEntity, Date> dateColumn;
    @FXML
    private TableColumn<JobDetailsEntity, Double> hourColumn;
    @FXML
    private TableColumn<JobDetailsEntity, Double> incomeColumn;

    @FXML
    private Label yearIncome;
    @FXML
    private Label monthIncome;
    @FXML
    private Label hoursInYear;
    @FXML
    private Label hoursInMonth;

    private Main main;
    private Stage dialogStage;

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> {
            try {
                return JavaBeanStringPropertyBuilder.create()
                        .bean(cellData.getValue())
                        .name("name")
                        .build();
            } catch (NoSuchMethodException exc) {
                throw new RuntimeException(exc);
            }
        });
        jobsTable.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> setJobDetail(newValue));

    }

    @FXML
    public void handleNewJob() {
        JobsEntity job = new JobsEntity();
        boolean okClicked = main.showEditJob(job);
        if (okClicked) {
            job.setIdUser(main.getUserID());
            main.insertJob(job);
            main.getJobs().add(job);
        }
    }

    @FXML
    public void handleEditJob() {
        JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
        int index=main.getJobs().indexOf(selectedJob);
        if (selectedJob != null) {
            boolean okClicked = main.showEditJob(selectedJob);
            if (okClicked) {
                main.editJob(selectedJob);
                main.getJobs().set(index,selectedJob);
            }
        }
    }

    @FXML
    public void handleRemoveJob(){
        JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
        String tittle="usuwanie";
        String header="usunięcie pracy"+selectedJob.getName();
        String information="Na pewno chcesz usunąć prace: "+selectedJob.getName()+"?";
        if(AlertUtil.confirmDialog(tittle,header,information,dialogStage)){
            main.removeJob(selectedJob);
            main.getJobs().remove(selectedJob);
        }
    }

    @FXML
    public void handleNewJobDetail() {
        JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
        JobDetailsEntity jobDetail = new JobDetailsEntity();
        if (selectedJob != null) {
            boolean okClicked = main.showEditJobDetails(selectedJob, jobDetail);
            if (okClicked) {
                main.insertJobDetail(jobDetail);
                setJobDetail(selectedJob);
            }
        }
    }

    @FXML
    public void handleEditJobDetail() {
    JobDetailsEntity selectedJobDetails = jobsDetailsTable.getSelectionModel().getSelectedItem();
        JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
        int index=main.getJobsDetails().indexOf(selectedJobDetails);
        if (selectedJobDetails != null) {
        boolean okClicked = main.showEditJobDetails(selectedJob,selectedJobDetails);
        if (okClicked) {
            main.editJobsDetails(selectedJobDetails);
            main.getJobsDetails().set(index,selectedJobDetails);
        }
    }
}
@FXML
public void handleRemoveJobDetail(){
    JobDetailsEntity selectedJobDetails = jobsDetailsTable.getSelectionModel().getSelectedItem();
    JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
    String tittle="usuwanie";
    String header="usunięcie szczegółów pracy: "+selectedJob.getName();
    String information="Na pewno chcesz usunąć prace: "+selectedJobDetails.getWokrDate()+
            " "+selectedJobDetails.getIncome()+"?";
    if(AlertUtil.confirmDialog(tittle,header,information,dialogStage)){
        main.removeJobDetails(selectedJobDetails);
        main.getJobsDetails().remove(selectedJobDetails);
    }
}

    public void setMain(Main main) {
        this.main = main;
        jobsTable.setItems(main.getJobs());
    }
    public void setDialogStage(Stage stage){
        this.dialogStage=stage;
    }

    private void setJobDetail(JobsEntity job) {
        main.clearJobsDetails();
        if (job != null) {
            main.addJobsDetails(job);
            Summary summary = new Summary(job);
            setJobsDetailsTable(main.getJobsDetails());
            setJobDetailsColumns();
            yearIncome.setText(summary.getYearIncome());
            hoursInYear.setText(summary.getHourInYear());
            monthIncome.setText(summary.getMonthIncome());
            hoursInMonth.setText(summary.getMonthHours());
        }
    }

    private void setJobsDetailsTable(ObservableList<JobDetailsEntity> jobDetails) {
        jobsDetailsTable.setItems(jobDetails);
    }

    private void setJobDetailsColumns() {
        dateColumn.setCellValueFactory(cellData
                -> new ReadOnlyObjectWrapper<>(cellData.getValue().getWokrDate()));
        incomeColumn.setCellValueFactory(cellData
                -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIncome().doubleValue()));
        hourColumn.setCellValueFactory(cellData
                -> new ReadOnlyObjectWrapper<>(cellData.getValue().getHours()));
    }

    private class Summary {
        private int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        private int year = Calendar.getInstance().get(Calendar.YEAR);
        private List<JobDetailsEntity> jobsByMonth;
        private List<JobDetailsEntity> jobsByYear;

        Summary(JobsEntity job) {
            jobsByMonth = main.findJobsByMonth(job, month);
            jobsByYear = main.findJobsByYear(job, year);
        }

        private String getMonthHours() {
            return hourSum(jobsByMonth);
        }

        private String getMonthIncome() {
            return incomeSum(jobsByMonth);
        }

        private String getHourInYear() {
            return hourSum(jobsByYear);
        }

        private String getYearIncome() {
            return incomeSum(jobsByYear);
        }

        private String incomeSum(List<JobDetailsEntity> jobDetail) {
            BigDecimal income = new BigDecimal(0);
            BigDecimal hours;
            BigDecimal multiplyResult;
            for (JobDetailsEntity temp : jobDetail) {
                hours = new BigDecimal(temp.getHours());
                multiplyResult = temp.getIncome().multiply(hours).setScale(2, RoundingMode.HALF_DOWN);
                income = income.add(multiplyResult);
            }
            return income.toString() + " zł";
        }

        private String hourSum(List<JobDetailsEntity> jobDetail) {
            double hours = 0.0;
            for (JobDetailsEntity temp : jobDetail) {
                hours += temp.getHours();
            }
            return Double.toString(hours) + " h";
        }


    }

}
