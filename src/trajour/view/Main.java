package trajour.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    protected final static int APPLICATION_WIDTH = 1400;
    protected final static int APPLICATION_HEIGHT = 1000;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/trajour/view/loginPage.fxml"));
        primaryStage.setTitle("TraJour");
        primaryStage.setScene(new Scene(root, APPLICATION_WIDTH, APPLICATION_HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
