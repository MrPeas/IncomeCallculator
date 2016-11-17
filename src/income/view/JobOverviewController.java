package income.view;

import income.Main;
import income.model.JobsDetailsEntity;
import income.model.JobsEntity;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.adapter.JavaBeanStringPropertyBuilder;
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
                addListener((observable, oldValue, newValue) -> showJobDetail(newValue));

    }
@FXML
public void handleNewJob(){
    JobsEntity job = new JobsEntity();
    boolean okClicked=main.showEditJob(job);
    if(okClicked){
        main.getJobs().add(job);
    }

    }
    private void showJobDetail(JobsEntity job) {
        main.clearJobsDetails();
        if (job != null) {
            main.addJobsDetails(job);
            jobsDetailsTable.setItems(main.getJobsDetails());
            hourColumn.setCellValueFactory(cellData
                    -> new ReadOnlyObjectWrapper<>(cellData.getValue().getHours()));
            incomeColumn.setCellValueFactory(cellData
                    -> new ReadOnlyObjectWrapper<>(cellData.getValue().getIncome().doubleValue()));
            dateColumn.setCellValueFactory(cellData
                    -> new ReadOnlyObjectWrapper<>(cellData.getValue().getWokrDate()));

            List<JobsDetailsEntity> temp=main.findJobsByYear
                    (job,Calendar.getInstance().get(Calendar.YEAR));
            yearIncome.setText(currentYearIncome(temp));
            hoursInYear.setText(currentYearHours(temp));
            temp=main.findJobsByMonth(job, Calendar.getInstance().get(Calendar.MONTH)+1);
            monthIncome.setText(currentMonthIncome(temp));
            hoursInMonth.setText(currentMonthHours(temp));
        }
    }

    public void setMain(Main main) {
        this.main = main;
        jobsTable.setItems(main.getJobs());
    }

    //private method

    private String currentYearIncome(List<JobsDetailsEntity> jobDetail){
        BigDecimal income=new BigDecimal(0);
        for(JobsDetailsEntity temp:jobDetail){
                income=income.add(temp.getIncome().multiply(new BigDecimal(temp.getHours())));
            }
        return income.toString()+" zł";
    }

    private String currentYearHours(List<JobsDetailsEntity> jobDetail) {
        double hours=0.0;
        for(JobsDetailsEntity temp:jobDetail){
            hours+=temp.getHours();
        }
        return Double.toString(hours)+" h";
    }

    private String currentMonthIncome(List<JobsDetailsEntity> jobDetail){
        BigDecimal income=new BigDecimal(0);
        for(JobsDetailsEntity temp:jobDetail){
            income=income.add(temp.getIncome().multiply(new BigDecimal(temp.getHours())));
        }
        return income.toString()+" zł";
    }
    private String currentMonthHours(List<JobsDetailsEntity> jobDetail){
        double hours=0.0;
        for(JobsDetailsEntity temp:jobDetail){
            hours+=temp.getHours();
        }
        return Double.toString(hours)+" h";
    }

}
