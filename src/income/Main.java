package income;

import income.model.JobDetailsEntity;
import income.model.JobsEntity;
import income.view.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane barMenu;
    private long userId;
    private String username;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Income");
        boolean logging;
        logging = loginDialog();
        if (logging) {
            initMenuBar(username);
            showJobOverview(userId);
        }
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }

    private void initMenuBar(String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/UpBar.fxml"));
        Parent root = loader.load();
        barMenu = (BorderPane) root;
        primaryStage.setScene(new Scene(root, 800, 600));
        UpBarController controller=loader.getController();
        controller.initialize(username);
        primaryStage.show();
    }

    private void showJobOverview(long idUser) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/JobOverview.fxml"));
        AnchorPane jobOverview = loader.load();
        barMenu.setCenter(jobOverview);
        JobOverviewController controller = loader.getController();
        controller.setDialogStage(primaryStage);
        controller.setMain(this, idUser);
    }

    public boolean showEditJob(JobsEntity job) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String resource = "view/EditJobDialog.fxml";
            String tittle = "Edycja pracy";
            Stage dialogStage = createStage(resource, tittle, loader,primaryStage);
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

    public boolean showEditJobDetails(JobsEntity job, JobDetailsEntity jodDetails) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String resource = "view/EditJobDetailDialog.fxml";
            String tittle = "Edycja szczegółów pracy";
            Stage dialogStage = createStage(resource, tittle, loader,primaryStage);
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

    public boolean loginDialog() {
        try {
            FXMLLoader loader = new FXMLLoader();
            String resource = "view/LoginDialog.fxml";
            String tittle = "logowanie";
            Stage dialogStage = createStage(resource, tittle, loader,primaryStage);
            LoginDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            dialogStage.showAndWait();
            return controller.loginStatus();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerDialog(Stage owner){
        try {
            FXMLLoader loader = new FXMLLoader();
            String resource = "view/RegistrationDialog.fxml";
            String tittle = "rejestracja";
            Stage dialogStage = createStage(resource, tittle, loader,owner);
            RegistrationDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private Stage createStage(String resource, String tittle, FXMLLoader loader, Stage owner) throws IOException {
        loader.setLocation(Main.class.getResource(resource));
        AnchorPane dialog = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle(tittle);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(owner);
        Scene scene = new Scene(dialog);
        dialogStage.setScene(scene);
        return dialogStage;
    }
}
