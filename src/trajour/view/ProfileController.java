package trajour.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import trajour.model.SingletonUserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private Button homePageButton;

    @FXML
    private ImageView mapButton;

    @FXML
    private Button discoveryPageButton;

    @FXML
    private Button profilePageButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView profilePhotoView;

    @FXML
    private ListView<?> upcomingJourneysListView;

    @FXML
    private Button addFriendButton;

    @FXML
    private ListView<?> friendsListView;

    @FXML
    private Button signOutButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button privacyButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    public void exit(ActionEvent event) {
        System.exit(1);
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

    @FXML
    public void openProfilePage(ActionEvent event) {

    }

    @FXML
    public void signOut(ActionEvent event) {
        try {
            Parent loginPageParent = FXMLLoader.load(getClass().getResource("/trajour/view/loginPage.fxml"));
            Scene loginPageScene = new Scene(loginPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(loginPageScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(SingletonUserSession.getUsername());
    }
}
