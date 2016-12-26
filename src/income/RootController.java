package income;

import income.model.JobDetailsEntity;
import income.model.JobsEntity;
import income.model.UsersEntity;
import income.view.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Janusz on 26.12.2016.
 */


public class RootController {
    private Stage primaryStage;
    private UsersEntity user;

    RootController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initMenuBar() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/UpBar.fxml"));
        Parent root = loader.load();
        BorderPane barMenu = (BorderPane) root;
        primaryStage.setScene(new Scene(root, 800, 600));
        UpBarController controller = loader.getController();
        controller.initialize(user.getLogin());
        primaryStage.show();
        showJobOverview(user.getId(),barMenu);
    }

    private void showJobOverview(long idUser,BorderPane barMenu) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/JobOverview.fxml"));
        AnchorPane jobOverview = loader.load();
        barMenu.setCenter(jobOverview);
        JobOverviewController controller = loader.getController();
        controller.setDialogStage(primaryStage);
        controller.setRootController(this, idUser);
    }

    public boolean showEditJob(JobsEntity job) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String resource = "view/EditJobDialog.fxml";
            String tittle = "Edycja pracy";
            Stage dialogStage = createStage(resource, tittle, loader, primaryStage);
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
            Stage dialogStage = createStage(resource, tittle, loader, primaryStage);
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
            Stage dialogStage = createStage(resource, tittle, loader, primaryStage);
            LoginDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRootController(this);
            dialogStage.showAndWait();
            return controller.loginStatus();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerDialog(Stage owner) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String resource = "view/RegistrationDialog.fxml";
            String tittle = "rejestracja";
            Stage dialogStage = createStage(resource, tittle, loader, owner);
            RegistrationDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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

    public void setUser(UsersEntity user) {
        this.user = user;
    }
}
