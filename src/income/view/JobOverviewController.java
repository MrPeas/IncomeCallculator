package income.view;

import income.DAO.DAOJobDetails;
import income.DAO.DAOJobDetailsImpl;
import income.DAO.DAOJobs;
import income.DAO.DAOJobsImpl;
import income.RootController;
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

    private RootController rootController;
    private Stage dialogStage;
    private DAOJobs daoJobs = new DAOJobsImpl();
    private DAOJobDetails daoJobDetails = new DAOJobDetailsImpl();
    private long userId;

    @FXML
    public void initialize() {
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
    private void handleNewJob() {
        JobsEntity job = new JobsEntity();
        boolean okClicked = rootController.showEditJob(job);
        if (okClicked) {
            job.setIdUser(userId);
            daoJobs.add(job);
            daoJobs.addJobToList(job);
        }
    }

    @FXML
    private void handleEditJob() {
        JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
        int index = daoJobs.jobIndex(selectedJob);
        if (selectedJob != null) {
            boolean okClicked = rootController.showEditJob(selectedJob);
            if (okClicked) {
                daoJobs.update(selectedJob);
                daoJobs.updateJobInList(selectedJob, index);
            }
        }
    }

    @FXML
    private void handleRemoveJob() {
        JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
        AlertUtil alert = new AlertUtil();
        String tittle = "usuwanie";
        String header = "usunięcie pracy" + selectedJob.getName();
        String information = "Na pewno chcesz usunąć prace: " + selectedJob.getName() + "?";
        if (alert.confirmDialog(tittle, header, information, dialogStage)) {
            daoJobs.remove(selectedJob.getId());
            daoJobs.removeJobFromList(selectedJob);
        }
    }

    @FXML
    private void handleNewJobDetail() {
        JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
        JobDetailsEntity jobDetail = new JobDetailsEntity();
        if (selectedJob != null) {
            boolean okClicked = rootController.showEditJobDetails(selectedJob, jobDetail);
            if (okClicked) {
                daoJobDetails.add(jobDetail);
                daoJobDetails.addJobDetailToList(jobDetail);
            }
        }
    }

    @FXML
    private void handleEditJobDetail() {
        JobDetailsEntity selectedJobDetails = jobsDetailsTable.getSelectionModel().getSelectedItem();
        JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
        int index = daoJobDetails.getJobDetails().indexOf(selectedJobDetails);
        if (selectedJobDetails != null) {
            boolean okClicked = rootController.showEditJobDetails(selectedJob, selectedJobDetails);
            if (okClicked) {
                daoJobDetails.update(selectedJobDetails);
                daoJobDetails.updateListJobDetail(selectedJobDetails, index);
            }
        }
    }

    @FXML
    private void handleRemoveJobDetail() {
        JobDetailsEntity selectedJobDetails = jobsDetailsTable.getSelectionModel().getSelectedItem();
        JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
        if (selectedJobDetails != null) {
            AlertUtil alert = new AlertUtil();
            String tittle = "usuwanie";
            String header = "usunięcie szczegółów pracy: " + selectedJob.getName();
            String information = "Na pewno chcesz usunąć prace: "
                    + selectedJobDetails.getWokrDate() +
                    " " + selectedJobDetails.getIncome() + "?";
            if (alert.confirmDialog(tittle, header, information, dialogStage)) {
                daoJobDetails.remove(selectedJobDetails.getId());
                daoJobDetails.removeJobsDetailFromList(selectedJobDetails);
            }
        }
    }

    public void setRootController(RootController rootController, long id) {
        this.rootController = rootController;
        userId = id;
        jobsTable.setItems(daoJobs.findByIdUser(id));
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    private void setJobDetail(JobsEntity job) {
        if (job != null) {
            List<JobDetailsEntity> jobsByMonth = getJobByMonth(job);
            List<JobDetailsEntity> jobsByYear = getJobByYear(job);
            setJobsDetailsTable(getJobByIdJob(job.getId()));
            setJobDetailsColumns();
            yearIncome.setText(incomeSum(jobsByYear));
            hoursInYear.setText(hourSum(jobsByYear));
            monthIncome.setText(incomeSum(jobsByMonth));
            hoursInMonth.setText(hourSum(jobsByMonth));
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

    private List<JobDetailsEntity> getJobByMonth(JobsEntity job) {
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        return daoJobDetails.findJobsDetailsByMonth(job.getId(), month);
    }

    private List<JobDetailsEntity> getJobByYear(JobsEntity job) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return daoJobDetails.findJobsDetailsByYear(job.getId(), year);
    }

    private ObservableList<JobDetailsEntity> getJobByIdJob(long id) {
        return daoJobDetails.findByIdJob(id);
    }

    private String incomeSum(List<JobDetailsEntity> jobDetail) {
        BigDecimal income = new BigDecimal(0);
        BigDecimal hours, multiplyResult;
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