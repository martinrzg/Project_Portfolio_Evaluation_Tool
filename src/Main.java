import controllers.Home;
import controllers.Welcome;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //new Welcome().launchWelcomeScene(primaryStage);
        new Home().displayHomeScreen(primaryStage);
        /*Parent root = FXMLLoader.load(getClass().getResource("fxml/Welcome.fxml"));
        primaryStage.setTitle("Project Portfolio Evaluation Tool");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(true);
        primaryStage.show();
        */

    }


    public static void main(String[] args) {
        launch(args);
    }
}
