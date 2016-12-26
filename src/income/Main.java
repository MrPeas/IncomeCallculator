package income;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Income");
        RootController rootController=new RootController(primaryStage);
        boolean logging = rootController.loginDialog();
        if (logging) {
            rootController.initMenuBar();
        }
        primaryStage.setOnCloseRequest(e -> Platform.exit());
    }
}
