package income;

import income.model.JobsDetailsDao;
import income.model.JobsDetailsEntity;
import income.model.JobsEntity;
import income.view.ControlerJobOverview;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Income");
        initMenuBar();
        showJobOverwiev();

    }

    void initMenuBar() throws IOException {
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

    void showJobOverwiev() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/JobOverview.fxml"));
        AnchorPane jobOverview = loader.load();
        barMenu.setCenter(jobOverview);
        ControlerJobOverview controller = loader.getController();
        controller.setMain(this);
    }

    public static void main(String[] args) {
        launch(args);
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

    public List<JobsDetailsEntity> findJobsByYear(JobsEntity job, int year){
        return controller.jobsDetailsByYear(job,year);
    }
    public List<JobsDetailsEntity> findJobsByMonth(JobsEntity job, int month){
        return controller.jobsDetailsByMonth(job,month);
    }

}
