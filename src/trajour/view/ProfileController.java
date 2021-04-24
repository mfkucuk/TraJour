package trajour.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import trajour.model.User;

public class ProfileController {
    @FXML
    private Button homePageButton;

    @FXML
    private Button mapPageButton;

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

    private User currentUser;

    /**
     * Initializes the data related to the page
     * @param user Current user of the session
     */
    public void initData(User user) {
        currentUser = user;
        usernameLabel.setText(currentUser.getUsername());
    }

    /**
     * Opens the home page.
     * @param event Event
     */
    @FXML
    public void openHomePage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/trajour/view/fxml/main.fxml"));
            Parent mainPageParent = loader.load();
            Scene mainPageScene = new Scene(mainPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the main windows controller
            MainController mainWindowController = loader.getController();
            mainWindowController.initData(currentUser);

            // Get the stage and change the scene
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(mainPageScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Opens the profile page.
     * @param event Event
     */
    @FXML
    public void openProfilePage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/trajour/view/fxml/profile.fxml"));
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

    /**
     * Opens the map page
     * @param event Event
     */
    @FXML
    public void openMapPage(ActionEvent event) {
        // TODO
    }

    /**
     * Opens the discovery page
     * @param event Event
     */
    @FXML
    public void openDiscoveryPage(ActionEvent event) {
        // TODO
    }

    /**
     * Signs out and goes back to the login page
     * @param event Event
     */
    @FXML
    public void signOut(ActionEvent event) {
        try {
            Parent loginPageParent = FXMLLoader.load(getClass().getResource("/trajour/view/fxml/login.fxml"));
            Scene loginPageScene = new Scene(loginPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(loginPageScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openAddFriendPage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/trajour/view/fxml/add_friend.fxml"));
            Parent addFriendPageParent = loader.load();
            Scene addFriendPageScene = new Scene(addFriendPageParent, 480, 327);

            // Get access to the main windows controller
            AddFriendController addFriendController = loader.getController();
            addFriendController.initData(currentUser);

            // Get the stage and change the scene
            Stage window = new Stage();

            window.setScene(addFriendPageScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    public void openAddPicturePage(ActionEvent event) {

    }

    @FXML
    public void openChangePasswordPage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/trajour/view/fxml/change_password.fxml"));
            Parent changePasswordPageParent = loader.load();
            Scene changePasswordPageScene = new Scene(changePasswordPageParent, 480, 327);

            // Get access to the main windows controller
            ChangePasswordController changePasswordWindowController = loader.getController();
            changePasswordWindowController.initData(currentUser);

            // Get the stage and change the scene
            Stage window = new Stage();

            window.setScene(changePasswordPageScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
