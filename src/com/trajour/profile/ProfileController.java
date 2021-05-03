package com.trajour.profile;

import com.trajour.db.DatabaseQuery;
import com.trajour.journey.Journey;
import com.trajour.journey.Wish;
import com.trajour.user.Friend;
import com.trajour.Main;
import com.trajour.main.MainController;
import com.trajour.map.MapController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import com.trajour.user.User;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static com.trajour.db.DatabaseQuery.*;
import static com.trajour.main.MainController.buildNotification;

/**
 * Profile page contains curren journeys list, friends list and wish list.
 * User can add friends from profile page, see their current journey, search from their
 * past and future journeys and create wishes for their possible travels. User also can add
 * profile picture and change their current passwords in profile page.
 * @author Selim Can Güler
 * @author Mehmet Feyyaz Küçük
 * @version 03 May 2021
 */
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
    private MenuItem refresh2MenuItem;

    @FXML
    private Label friendsLabel;

    @FXML
    private ListView<Wish> wishlistListView;

    @FXML
    private ListView<Journey> searchedJourneysListView;

    @FXML
    private TextField searchJourneyTextField;

    @FXML
    private Button removeFriendButton;

    @FXML
    private Button searchButton;

    @FXML
    private Button addWishButton;

    private VBox wishlistMenu;
    private PopOver wishlistPopOver;
    private Label wishlistLabel;
    private TextField locationLabel;
    private DatePicker startDatePicker;
    private Button confirmWishButton;

    @FXML
    private TextField friendEmailTextField;

    @FXML
    private TextField friendUsernameTextField;

    @FXML
    private Button addButton;

    private Label addFriendLabel;
    private Label addFriendOrLabel;
    private VBox addFriendMenu;
    private PopOver addFriendPopOver;

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

        // Get all wishes
        setWishlist();

        // Context menu item
        refresh2MenuItem = new MenuItem("Refresh");
        refreshMenuItem.setOnAction(actionEvent -> initData(currentUser));
        refresh2MenuItem.setOnAction(actionEvent -> initData(currentUser));

        // Wishlist pop over
        wishlistLabel = new Label("Add Wish");
        wishlistLabel.setFont(new Font("Arial Bold", 24));
        locationLabel = new TextField();
        locationLabel.setPromptText("Location");
        startDatePicker = new DatePicker();
        startDatePicker.setValue(LocalDate.now());
        confirmWishButton = new Button("Add");
        confirmWishButton.setStyle("-fx-background-radius: 10; -fx-background-color: #C00000; -fx-text-fill: #FFFFFF");
        confirmWishButton.setOnAction(event -> {
            if (locationLabel.getText().isBlank()) {
                Notifications notification = buildNotification("Empty Field Error", "Please fill in the location field", 6, Pos.BASELINE_CENTER);
                notification.showError();
            }
            else {
                currentUser.addWish(locationLabel.getText(), startDatePicker.getValue());

                Notifications notification = buildNotification("Wish added", "Your wish is successfully added to your wishlist", 6, Pos.BASELINE_CENTER);
                notification.showConfirm();

                initData(currentUser);
            }
        });
        wishlistMenu = new VBox(wishlistLabel, locationLabel, startDatePicker, confirmWishButton);
        wishlistMenu.setPadding(new Insets(10));
        wishlistMenu.setAlignment(Pos.CENTER);
        wishlistMenu.setPrefHeight(150.0);
        wishlistMenu.setPrefWidth(200.0);
        wishlistMenu.setSpacing(20);
        wishlistMenu.setStyle("-fx-background-color:#BFFCC6");
        wishlistPopOver = new PopOver(wishlistMenu);

        wishlistListView.setContextMenu(new ContextMenu(refresh2MenuItem));

        addFriendLabel = new Label("Add Friend");
        addFriendLabel.setFont(new Font("Arial Bold", 24));
        addFriendOrLabel = new Label("OR");
        addFriendOrLabel.setFont(new Font("Arial Bold", 16));
        friendEmailTextField = new TextField();
        friendEmailTextField.setPromptText("Email");
        friendUsernameTextField = new TextField();
        friendUsernameTextField.setPromptText("Username");
        addButton = new Button("Add");
        addButton.setOnAction(event -> {
            if (friendEmailTextField.getText().isBlank() && friendUsernameTextField.getText().isBlank()) {
                Notifications notification = buildNotification("Text field error", "Fill out one of the text fields.", 4, Pos.BASELINE_CENTER);
                notification.showError();
            }
            else if ( ! friendEmailTextField.getText().isBlank() && ! friendUsernameTextField.getText().isBlank()) {
                Notifications notification = buildNotification("Text field error", "Use only one of the text fields.", 4, Pos.BASELINE_CENTER);
                notification.showError();
            }
            else if ( ! friendUsernameTextField.getText().isBlank()) {
                if (DatabaseQuery.findUserByUsername(friendUsernameTextField.getText())) {
                    if (friendUsernameTextField.getText().equals(currentUser.getUsername())) {
                        Notifications notification = buildNotification("Text field error", "I'm afraid you cannot be friends with yourself.", 4, Pos.BASELINE_CENTER);
                        notification.showError();
                    }
                    else if ( ! findFriendByUsername(friendUsernameTextField.getText(), currentUser)) {
                        currentUser.addFriendByName(friendUsernameTextField.getText(), getEmailByUsername(friendUsernameTextField.getText()));
                        Notifications notification = buildNotification("Confirmation", "Friend successfully added.", 4, Pos.BASELINE_CENTER);
                        notification.showConfirm();
                    }
                    else {
                        Notifications notification = buildNotification("Text field error", "You are already friends.", 4, Pos.BASELINE_CENTER);
                        notification.showError();
                    }
                }
                else {
                    Notifications notification = buildNotification("Text field error", "No such user exists.", 4, Pos.BASELINE_CENTER);
                    notification.showError();
                }
            }
            else if ( ! friendEmailTextField.getText().isBlank()) {
                if (findUserByEmail(friendEmailTextField.getText())) {
                    if (friendEmailTextField.getText().equals(currentUser.getEmail())) {
                        Notifications notification = buildNotification("Text field error", "I'm afraid you cannot be friends with yourself.", 4, Pos.BASELINE_CENTER);
                        notification.showError();
                    }
                    else if ( ! findFriendByEmail(friendEmailTextField.getText(), currentUser)) {
                        currentUser.addFriendByEmail(getUsernameByEmail(friendEmailTextField.getText()), friendEmailTextField.getText());
                        Notifications notification = buildNotification("Confirmation", "Friend successfully added.", 4, Pos.BASELINE_CENTER);
                        notification.showConfirm();
                    }
                    else {
                        Notifications notification = buildNotification("Text field error", "You are already friends.", 4, Pos.BASELINE_CENTER);
                        notification.showError();
                    }
                }
                else {
                    Notifications notification = buildNotification("Text field error", "No such user exists.", 4, Pos.BASELINE_CENTER);
                    notification.showError();
                }
            }
        });
        addButton.setStyle("-fx-background-radius: 10; -fx-background-color: #C00000; -fx-text-fill: #FFFFFF");
        addFriendMenu = new VBox(addFriendLabel, friendEmailTextField, addFriendOrLabel, friendUsernameTextField, addButton);
        addFriendMenu.setPadding(new Insets(10));
        addFriendMenu.setAlignment(Pos.CENTER);
        addFriendMenu.setPrefHeight(272);
        addFriendMenu.setPrefWidth(450);
        addFriendMenu.setSpacing(20);
        addFriendMenu.setStyle("-fx-background-color:#BFFCC6");
        addFriendPopOver = new PopOver(addFriendMenu);
    }

    /**
     * Adds wish to wishlist
     * @param event
     */
    @FXML
    public void addWish(ActionEvent event) {
        wishlistPopOver.show(addWishButton);
    }

    /**
     * Removes wish from wishlist
     * @param event
     */
    @FXML
    public void removeWish(ActionEvent event) {
        ObservableList<Wish> wishes = wishlistListView.getSelectionModel().getSelectedItems();

        currentUser.removeWish(wishes);

        wishlistListView.getItems().removeAll(wishes);
        openProfilePage(event);
    }

    /**
     * Searches journey from db and adds results to listview
     * @param e
     */
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

    /**
     * Button handler for removing friend
     * @param e
     */
    @FXML
    public void handleRemoveFriend(ActionEvent e) {
        ObservableList<Friend> friends = friendsListView.getSelectionModel().getSelectedItems();

        currentUser.removeFriend(friends);

        friendsListView.getItems().removeAll(friends);
        openProfilePage(e);
    }

    /**
     * Opens add friend pop-over
     * @param event
     */
    @FXML
    public void openAddFriendPage(ActionEvent event) {
        addFriendPopOver.show(addFriendButton);
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
        loader.setLocation(getClass().getResource("/com/trajour/map/map.fxml"));

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

    /**
     * Opens add picture page
     * @param event
     */
    @FXML
    public void openAddPicturePage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {

            if (!currentUser.addPicture(selectedFile)) {
                Notifications notification = buildNotification("Image Too Large.", "Please upload an image less than 4 mb's", 5, Pos.BASELINE_CENTER);
                notification.showWarning();
            }
            else {
                Image img = new Image(selectedFile.toURI().toString(), 70, 70, true, false);

                profilePhotoView.setImage(img);
                currentUser.addPicture(selectedFile);
            }
        }
        else {
            Notifications notifications = buildNotification("File Upload Issue", "Couldn't Upload Picture", 5, Pos.BASELINE_CENTER);
            notifications.showWarning();
        }
    }

    /**
     * Opens change picture page
     * @param event
     */
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

    /**
     * Sets profile picture
     */
    private void setProfilePic() {
        profilePhotoFile = getProfilePhotoFile(currentUser.getUserId());
        Image profileImage = new Image(profilePhotoFile.toURI().toString(), 80, 80, false, false);
        profilePhotoView.setImage(profileImage);
    }

    /**
     * Sets currents journeys to current journey listview if there are any
     * @param allJourneys
     */
    private void setCurrentJourneys(ObservableList<Journey> allJourneys) {
        ObservableList<Journey> currentJourneys = FXCollections.observableArrayList();
        for (Journey j : allJourneys) {
            if (j.getStartDate().compareTo(LocalDate.now()) <= 0 && j.getEndDate().compareTo(LocalDate.now()) >= 0) {
                currentJourneys.add(j);
            }
        }

        currentJourneyListView.setItems(currentJourneys);
    }

    /**
     * Sets list of friends in friends listview
     */
    private void setFriendsList() {
        ObservableList<Friend> friends = getAllFriendsOfUser(currentUser);
        friendsListView.setItems(friends);
        friendsLabel.setText("Friends (" + friends.size() + ")");
    }

    /**
     * Sets list of wishes in wishlist listview
     */
    private void setWishlist() {
        ObservableList<Wish> wishes = getAllWishesOfUser(currentUser);
        wishlistListView.setItems(wishes);
    }

    /**
     * Gets journey information and set autocomplete search suggestions for search journey bar
     * @param allJourneys
     */
    private void setSearchSuggestions(ObservableList<Journey> allJourneys) {

        for (int i = 0; i < allJourneys.size() ; i++) {
            suggestions.add( i, allJourneys.get(i).getTitle() );
        }

        autoComplete = TextFields.bindAutoCompletion(searchJourneyTextField, suggestions);

    }
}
