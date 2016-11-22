package income.view;

import income.Main;
import income.model.JobsDetailsEntity;
import income.model.JobsEntity;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
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
    private TableView<JobsDetailsEntity> jobsDetailsTable;
    @FXML
    private TableColumn<JobsDetailsEntity, Date> dateColumn;
    @FXML
    private TableColumn<JobsDetailsEntity, Double> hourColumn;
    @FXML
    private TableColumn<JobsDetailsEntity, Double> incomeColumn;

    @FXML
    private Label yearIncome;
    @FXML
    private Label monthIncome;
    @FXML
    private Label hoursInYear;
    @FXML
    private Label hoursInMonth;

    private Main main;

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
public void handleEditJob(){
    JobsEntity selectedJob = jobsTable.getSelectionModel().getSelectedItem();
    main.getJobs().remove(selectedJob);
    if(selectedJob!=null){
        boolean okClicked=main.showEditJob(selectedJob);
        if(okClicked){
            main.editJob(selectedJob);
            main.getJobs().add(selectedJob);
        }
    }
}
    public void setMain(Main main) {
        this.main = main;
        jobsTable.setItems(main.getJobs());
    }

    private void setJobDetail(JobsEntity job) {
        main.clearJobsDetails();
        if (job != null) {
            main.addJobsDetails(job);
            SummaryIncome summaryIncome=new SummaryIncome(job);
            setJobsDetailsTable(main.getJobsDetails());
            setWorkHourColumn();
            setDateColumn();
            setIncomeColumn();
            summaryIncome.setYearIncome();
            summaryIncome.setHourInYear();
            summaryIncome.setMonthIncome();
            summaryIncome.setMonthHours();
        }
    }

    private void setJobsDetailsTable(ObservableList<JobsDetailsEntity> job) {
        jobsDetailsTable.setItems(job);
    }

    private void setDateColumn() {
        dateColumn.setCellValueFactory(cellData
                -> new ReadOnlyObjectWrapper<>(cellData.getValue().getWokrDate()));
    }

    private void setIncomeColumn() {
        incomeColumn.setCellValueFactory(cellData
                -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIncome().doubleValue()));
    }

    private void setWorkHourColumn() {
        hourColumn.setCellValueFactory(cellData
                -> new ReadOnlyObjectWrapper<>(cellData.getValue().getHours()));
    }


    private class SummaryIncome{
        private JobsEntity job;
        SummaryIncome(JobsEntity job){
            this.job=job;
        }

        private void setMonthHours() {
            hoursInMonth.setText(currentMonthHours(main.findJobsByMonth
                    (job, Calendar.getInstance().get(Calendar.MONTH) + 1)));
        }

        private void setMonthIncome() {
            monthIncome.setText(currentMonthIncome(main.findJobsByMonth
                    (job, Calendar.getInstance().get(Calendar.MONTH) + 1)));
        }

        private void setHourInYear() {
            hoursInYear.setText(currentYearHours(main.findJobsByYear
                    (job, Calendar.getInstance().get(Calendar.YEAR))));
        }

        private void setYearIncome() {
            yearIncome.setText(currentYearIncome(main.findJobsByYear
                    (job, Calendar.getInstance().get(Calendar.YEAR))));
        }
        private String currentYearIncome(List<JobsDetailsEntity> jobDetail) {
            BigDecimal income = new BigDecimal(0);
            for (JobsDetailsEntity temp : jobDetail) {
                income = income.add(temp.getIncome().multiply(new BigDecimal(temp.getHours())));
            }
            return income.toString() + " zł";
        }

        private String currentYearHours(List<JobsDetailsEntity> jobDetail) {
            double hours = 0.0;
            for (JobsDetailsEntity temp : jobDetail) {
                hours += temp.getHours();
            }
            return Double.toString(hours) + " h";
        }

        private String currentMonthIncome(List<JobsDetailsEntity> jobDetail) {
            BigDecimal income = new BigDecimal(0);
            for (JobsDetailsEntity temp : jobDetail) {
                income = income.add(temp.getIncome().multiply(new BigDecimal(temp.getHours())));
            }
            return income.toString() + " zł";
        }

        private String currentMonthHours(List<JobsDetailsEntity> jobDetail) {
            double hours = 0.0;
            for (JobsDetailsEntity temp : jobDetail) {
                hours += temp.getHours();
            }
            return Double.toString(hours) + " h";
        }

    }

}
