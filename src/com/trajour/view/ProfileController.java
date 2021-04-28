package com.trajour.view;

import com.sun.source.tree.Tree;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.trajour.model.User;
import javafx.util.Callback;

import java.io.File;

import static com.trajour.db.DatabaseQuery.*;

public class ProfileController {
    @FXML
    private Button homePageButton;

    @FXML
    private Button mapPageButton;

    @FXML
    private Button addPictureButton;

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
    private Button signOutButton;

    @FXML
    private Button privacyButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private ListView<String> friendsListView;

    @FXML
    private MenuItem refreshMenuItem;

    @FXML
    private Label friendsLabel;

    private User currentUser;
    private File profilePhotoFile;

    public void initData(User user) {
        currentUser = user;
        usernameLabel.setText(currentUser.getUsername());

        DropShadow shadow = new DropShadow(5, Color.WHITE);
        homePageButton.setOnMouseEntered(mouseEvent -> homePageButton.setEffect(shadow));
        homePageButton.setOnMouseExited(mouseEvent -> homePageButton.setEffect(null));

        mapPageButton.setOnMouseEntered(mouseEvent -> mapPageButton.setEffect(shadow));
        mapPageButton.setOnMouseExited(mouseEvent -> mapPageButton.setEffect(null));

        profilePageButton.setOnMouseEntered(mouseEvent -> profilePageButton.setEffect(shadow));
        profilePageButton.setOnMouseExited(mouseEvent -> profilePageButton.setEffect(null));

        DropShadow blackShadow = new DropShadow();
        addFriendButton.setOnMouseEntered(mouseEvent -> addFriendButton.setEffect(blackShadow));
        addFriendButton.setOnMouseExited(mouseEvent -> addFriendButton.setEffect(null));

        signOutButton.setOnMouseEntered(mouseEvent -> signOutButton.setEffect(blackShadow));
        signOutButton.setOnMouseExited(mouseEvent -> signOutButton.setEffect(null));

        changePasswordButton.setOnMouseEntered(mouseEvent -> changePasswordButton.setEffect(blackShadow));
        changePasswordButton.setOnMouseExited(mouseEvent -> changePasswordButton.setEffect(null));

        privacyButton.setOnMouseEntered(mouseEvent -> privacyButton.setEffect(blackShadow));
        privacyButton.setOnMouseExited(mouseEvent -> privacyButton.setEffect(null));

        addPictureButton.setOnMouseEntered(mouseEvent -> addPictureButton.setEffect(blackShadow));
        addPictureButton.setOnMouseExited(mouseEvent -> addPictureButton.setEffect(null));

        profilePhotoFile = getProfilePhotoFile(currentUser);
        Image profileImage = new Image(profilePhotoFile.toURI().toString(), 40, 40, false, false);
        profilePhotoView.setImage(profileImage);

        ObservableList<String> friends = FXCollections.observableArrayList();
        friends = getAllFriendsOfUser(currentUser);

        friendsListView.setItems(friends);
        friendsLabel.setText("Friends (" + friends.size() + ")");

        refreshMenuItem.setOnAction(actionEvent -> initData(currentUser));
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
            loader.setLocation(getClass().getResource("/com/trajour/view/fxml/main.fxml"));
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
            loader.setLocation(getClass().getResource("/com/trajour/view/fxml/profile.fxml"));
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

    @FXML
    public void openAddFriendPage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/view/fxml/add_friend.fxml"));
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image img = new Image(selectedFile.toURI().toString(), 40, 40, false, false);

            profilePhotoView.setImage(img);

            updateImage(selectedFile, currentUser);
        }
        else {
            // TODO Warn the user with a popup or use ControlsFX notifications?
        }
    }

    @FXML
    public void openChangePasswordPage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/view/fxml/change_password.fxml"));
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

    /**
     * Signs out and goes back to the login page
     * @param event Event
     */
    @FXML
    public void signOut(ActionEvent event) {
        try {
            Parent loginPageParent = FXMLLoader.load(getClass().getResource("/com/trajour/view/fxml/login.fxml"));
            Scene loginPageScene = new Scene(loginPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(loginPageScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
