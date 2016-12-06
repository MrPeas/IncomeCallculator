package income;

import income.model.JobsDetailsEntity;
import income.model.JobsEntity;
import income.view.EditJobController;
import income.view.EditJobDetailController;
import income.view.JobOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane barMenu;
    private ObservableList<JobsEntity> jobs = FXCollections.observableArrayList();
    private ObservableList<JobsDetailsEntity> jobsDetails = FXCollections.observableArrayList();
    private Controller controller = new Controller(this);
    private long userID = 1;

    public long getUserID() {
        return userID;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Income");
        initMenuBar();
        showJobOverview();

    }

    private void initMenuBar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/UpBar.fxml"));
        Parent root = loader.load();
        barMenu = (BorderPane) root;
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        try {
            controller.LoadJobsDataFromBase();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void showJobOverview() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/JobOverview.fxml"));
        AnchorPane jobOverview = loader.load();
        barMenu.setCenter(jobOverview);
        JobOverviewController controller = loader.getController();
        controller.setMain(this);
    }

    public boolean showEditJob(JobsEntity job) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EditJobDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edycja pracy");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            EditJobController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setJob(job);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showEditJobDetails(JobsEntity job,JobsDetailsEntity jodDetails) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/EditJobDetailDialog.fxml"));
            AnchorPane dialog = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edycja szczegółów pracy");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(dialog);
            dialogStage.setScene(scene);

            EditJobDetailController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setJob(job);
            controller.setJobDetail(jodDetails);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ObservableList<JobsEntity> getJobs() {
        return jobs;
    }

    public ObservableList<JobsDetailsEntity> getJobsDetails() {
        return jobsDetails;
    }

    public void addJobsDetails(JobsEntity jobs) {
        this.jobsDetails.addAll(controller.loadJobsDetailFromBase(jobs));
    }

    public void addJobs(List<JobsEntity> jobs) {
        this.jobs.addAll(jobs);
    }

    public void clearJobsDetails() {
        jobsDetails.clear();
    }

    public List<JobsDetailsEntity> findJobsByYear(JobsEntity job, int year) {
        return controller.jobsDetailsByYear(job, year);
    }

    public List<JobsDetailsEntity> findJobsByMonth(JobsEntity job, int month) {
        return controller.jobsDetailsByMonth(job, month);
    }

    public void insertJob(JobsEntity job) {
        controller.insertJob(job);
    }

    public void editJob(JobsEntity job) {
        controller.editJob(job);
    }

    public void insertJobDetail(JobsDetailsEntity jobDetail) {
        controller.insertJobDetail(jobDetail);
    }
}
