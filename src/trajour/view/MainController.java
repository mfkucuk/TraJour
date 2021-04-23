package trajour.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import trajour.model.User;

import java.util.Objects;

public class MainController {
    @FXML
    private ImageView homePageButton;

    @FXML
    private ImageView mapPageButton;

    @FXML
    private Button discoveryPageButton;

    @FXML
    private ImageView profilePageButton;

    @FXML
    private TableView<?> futureJourneysTable;

    @FXML
    private TableView<?> pastJourneysTable;

    @FXML
    private Button shareJourneyButton;

    @FXML
    private Button achievementsButton;

    @FXML
    private Label welcomeMessage;

    private User currentUser;

    @FXML
    public void openProfilePage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/trajour/view/fxml/profilePage.fxml"));
            Parent profilePageParent = loader.load();
            Scene profilePageScene = new Scene(profilePageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the main windows controller
            ProfileController profileWindowController = loader.getController();
            profileWindowController.initData(currentUser);

            // Get the stage and change the scene
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(profilePageScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void initData(User user) {
        currentUser = user;
        welcomeMessage.setText("Welcome to your main feed " + user.getUsername() + "!");
    }

    @FXML
    public void openDiscoveryPage(ActionEvent event) {

    }

    @FXML
    public void openHomePage(ActionEvent event) {

    }

    @FXML
    public void openMapPage(ActionEvent event) {

    }

}
