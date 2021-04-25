package trajour.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import trajour.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button homePageButton;

    @FXML
    private Button mapPageButton;

    @FXML
    private Button profilePageButton;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DropShadow shadow = new DropShadow(10, Color.WHITE);
        homePageButton.setOnMouseEntered(mouseEvent -> homePageButton.setEffect(shadow));
        homePageButton.setOnMouseExited(mouseEvent -> homePageButton.setEffect(null));

        mapPageButton.setOnMouseEntered(mouseEvent -> mapPageButton.setEffect(shadow));
        mapPageButton.setOnMouseExited(mouseEvent -> mapPageButton.setEffect(null));

        profilePageButton.setOnMouseEntered(mouseEvent -> profilePageButton.setEffect(shadow));
        profilePageButton.setOnMouseExited(mouseEvent -> profilePageButton.setEffect(null));

        DropShadow blackShadow = new DropShadow();
        shareJourneyButton.setOnMouseEntered(mouseEvent -> shareJourneyButton.setEffect(blackShadow));
        shareJourneyButton.setOnMouseExited(mouseEvent -> shareJourneyButton.setEffect(null));
    }
    /**
     * Initializes the user of the session.
     * @param user Current user of the session
     */
    public void initData(User user) {
        currentUser = user;
        welcomeMessage.setText("Welcome to your main feed " + user.getUsername() + "!");
    }

    /**
     * Opens the profile page
     */
    @FXML
    public void openProfilePage(ActionEvent event) {
        try {
            // Get the parent and create the scene
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
     * Opens the discovery page.
     */
    @FXML
    public void openDiscoveryPage(ActionEvent event) {

    }

    /**
     * Opens the home page.
     */
    @FXML
    public void openHomePage(ActionEvent event) {

    }

    /**
     * Opens map page.
     */
    @FXML
    public void openMapPage(ActionEvent event) {

    }

    @FXML
    public void openShareJourneyPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/trajour/view/fxml/share_journey.fxml"));

            Parent shareJourneyPageParent = loader.load();
            Scene shareJourneyPageScene = new Scene(shareJourneyPageParent, 480, 800);

            // Get access to the main windows controller
            ShareJourneyController shareJourneyController = loader.getController();
            shareJourneyController.initData(currentUser);

            // Get the stage and change the scene
            Stage window = new Stage();

            window.setScene(shareJourneyPageScene);
            window.show();
        }
        catch (IOException e) {
            e.getCause();
            System.out.println("Something wrong with the fxml file");
        }
    }
}
