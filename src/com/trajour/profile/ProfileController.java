package com.trajour.profile;

import com.trajour.journey.Journey;
import com.trajour.user.Friend;
import com.trajour.main.Main;
import com.trajour.main.MainController;
import com.trajour.map.MapController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import com.trajour.user.User;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static com.trajour.db.DatabaseQuery.*;
import static com.trajour.main.MainController.buildNotification;

public class ProfileController {
    @FXML
    private Button homePageButton;

    @FXML
    private Button mapPageButton;

    @FXML
    private Button addPictureButton;

    @FXML
    private Button profilePageButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private ImageView profilePhotoView;

    @FXML
    private ListView<Journey> currentJourneyListView;

    @FXML
    private Button addFriendButton;

    @FXML
    private Button signOutButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private ListView<Friend> friendsListView;

    @FXML
    private MenuItem refreshMenuItem;

    @FXML
    private Label friendsLabel;

    @FXML
    private ListView<?> wishlistListView;

    @FXML
    private ListView<Journey> searchedJourneysListView;

    @FXML
    private TextField searchJourneyTextField;

    @FXML
    private Button removeFriendButton;

    @FXML
    private Button searchButton;

    private User currentUser;
    private File profilePhotoFile;
    private AutoCompletionBinding autoComplete;
    private ObservableList<String> suggestions = FXCollections.observableArrayList();

    public void initData(User user) {
        currentUser = user;
        usernameLabel.setText(currentUser.getUsername());

        initButtons();

        setProfilePic();

        // Get all journeys
        ObservableList<Journey> allJourneys = getAllJourneysOfUser(currentUser);

        setCurrentJourneys(allJourneys);

        setFriendsList();

        setSearchSuggestions(allJourneys);

        // Context menu item
        refreshMenuItem.setOnAction(actionEvent -> initData(currentUser));
    }

    @FXML
    public void addWish() {
        // TODO
    }

    @FXML
    public void removeWish() {
        // TODO
    }

    @FXML
    void searchJourney(ActionEvent e ){
        searchedJourneysListView.getItems().removeAll(searchedJourneysListView.getItems());

        ObservableList<Journey> journeys = getAllJourneysOfUser(currentUser);
        ObservableList<Journey> matchingJourneys = FXCollections.observableArrayList();
        String input = searchJourneyTextField.getText();

        for( Journey j: journeys ) {
            if( j.getTitle().toLowerCase().contains(input.toLowerCase()) || j.getLocation().toLowerCase().contains(input.toLowerCase()) ||
                    j.getDescription().toLowerCase().contains(input.toLowerCase()) || j.getEndDate().toString().toLowerCase().contains(input.toLowerCase())
                    || j.getStartDate().toString().toLowerCase().contains(input.toLowerCase())) {
                matchingJourneys.add(j);
            }
        }

        searchedJourneysListView.getItems().addAll(matchingJourneys);
    }

    @FXML
    public void handleRemoveFriend(ActionEvent e) {
        ObservableList<Friend> friends = friendsListView.getSelectionModel().getSelectedItems();

        currentUser.removeFriend(friends);

        friendsListView.getItems().removeAll(friends);
        openProfilePage(e);
    }

    @FXML
    public void openAddFriendPage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/profile/add_friend.fxml"));
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

    /**
     * Opens the home page.
     * @param event Event
     */
    @FXML
    public void openHomePage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/main/main.fxml"));
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
            loader.setLocation(getClass().getResource("/com/trajour/profile/profile.fxml"));
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/trajour/map/mapxz.fxml"));

        try {
            Parent mapPageParent = loader.load();
            Scene mapPageScene = new Scene(mapPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the map windows controller
            MapController mapController = loader.getController();
            mapController.initData(currentUser);

            // Get the stage and change the scene
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(mapPageScene);
            window.show();
        }
        catch (IOException e) {
            e.getCause();
            e.printStackTrace();
        }
    }

    @FXML
    public void openAddPicturePage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {

            if (!currentUser.addPicture(selectedFile)) {
                Notifications notification = buildNotification("Image Too Large.", "Please upload an image less than 1 mb's", 5, Pos.BASELINE_CENTER);
                notification.showWarning();
            }
            else {
                Image img = new Image(selectedFile.toURI().toString(), 80, 80, false, false);

                profilePhotoView.setImage(img);
                currentUser.addPicture(selectedFile);
            }
        }
        else {
            Notifications notifications = buildNotification("File Upload Issue", "Couldn't Upload Picture", 5, Pos.BASELINE_CENTER);
            notifications.showWarning();
        }
    }

    @FXML
    public void openChangePasswordPage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/profile/change_password.fxml"));
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
            Parent loginPageParent = FXMLLoader.load(getClass().getResource("/com/trajour/login/login.fxml"));
            Scene loginPageScene = new Scene(loginPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(loginPageScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initButtons() {
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

        addPictureButton.setOnMouseEntered(mouseEvent -> addPictureButton.setEffect(blackShadow));
        addPictureButton.setOnMouseExited(mouseEvent -> addPictureButton.setEffect(null));

        removeFriendButton.setOnMouseEntered(mouseEvent -> removeFriendButton.setEffect(blackShadow));
        removeFriendButton.setOnMouseExited(mouseEvent -> removeFriendButton.setEffect(null));
    }

    private void setProfilePic() {
        profilePhotoFile = getProfilePhotoFile(currentUser);
        Image profileImage = new Image(profilePhotoFile.toURI().toString(), 80, 80, false, false);
        profilePhotoView.setImage(profileImage);
    }

    private void setCurrentJourneys(ObservableList<Journey> allJourneys) {
        ObservableList<Journey> currentJourneys = FXCollections.observableArrayList();
        for (Journey j : allJourneys) {
            if (j.getStartDate().compareTo(LocalDate.now()) <= 0 && j.getEndDate().compareTo(LocalDate.now()) >= 0) {
                currentJourneys.add(j);
            }
        }

        currentJourneyListView.setItems(currentJourneys);
    }

    private void setFriendsList() {
        ObservableList<Friend> friends = getAllFriendsOfUser(currentUser);
        friendsListView.setItems(friends);
        friendsLabel.setText("Friends (" + friends.size() + ")");
    }

    private void setSearchSuggestions(ObservableList<Journey> allJourneys) {

        for (int i = 0; i < allJourneys.size() ; i++) {
            suggestions.add( i, allJourneys.get(i).getTitle() );
        }

        autoComplete = TextFields.bindAutoCompletion(searchJourneyTextField, suggestions);

    }
}
