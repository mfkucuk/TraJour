package com.trajour.main;

import com.trajour.Main;
import com.trajour.journey.FutureJourney;
import com.trajour.journey.Journey;
import com.trajour.journey.PastJourney;
import com.trajour.journey.Post;
import com.trajour.map.MapController;
import com.trajour.user.Friend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.trajour.user.User;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import com.trajour.profile.ProfileController;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.trajour.db.DatabaseQuery.*;

/**
 * This class contain three main features: main feed for user posts, future journey table that contains user's
 * future journey informations and lastly, past journey table that contains user's past journey which
 * also enables users to rate their journeys.
 * @version 3 May 2021
 *  * @author Selim Can Güler
 *  * @author Mehmet Feyyaz Küçük
 */
public class MainController implements Initializable {

    @FXML
    private Button homePageButton;

    @FXML
    private Button mapPageButton;

    @FXML
    private Button profilePageButton;

    @FXML
    private TableView<FutureJourney> futureJourneysTable;

    @FXML
    private TableView<PastJourney> pastJourneysTable;

    @FXML
    private TableColumn<FutureJourney, String> futureJourneysCountryColumn;

    @FXML
    private TableColumn<FutureJourney, String> futureJourneysTitleColumn;

    @FXML
    private TableColumn<FutureJourney, String> futureJourneysDescriptionColumn;

    @FXML
    private TableColumn<FutureJourney, LocalDate> futureJourneysStartDateColumn;

    @FXML
    private TableColumn<FutureJourney, LocalDate> futureJourneysEndDateColumn;

    @FXML
    private TableColumn<PastJourney, String> pastJourneysCountryColumn;

    @FXML
    private TableColumn<PastJourney, String> pastJourneysTitleColumn;

    @FXML
    private TableColumn<PastJourney, String> pastJourneysRatingColumn;

    @FXML
    private TableColumn<PastJourney, String> pastJourneysDescriptionColumn;

    @FXML
    private TableColumn<PastJourney, LocalDate> pastJourneysStartDateColumn;

    @FXML
    private TableColumn<PastJourney, LocalDate> pastJourneysEndDateColumn;

    @FXML
    private Button shareJourneyButton;

    @FXML
    private Label welcomeMessage;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox vboxMainFeed;

    @FXML
    private MenuItem contextItemRefreshFutureTable;

    @FXML
    private MenuItem contextItemAddFutureJourney;

    @FXML
    private MenuItem contextItemDeleteFutureJourney;

    @FXML
    private MenuItem contextItemRefreshPastTable;

    @FXML
    private MenuItem contextItemAddPastJourney;

    @FXML
    private MenuItem contextItemDeletePastJourney;

    @FXML
    private MenuItem contextItemAddPastRating;

    @FXML
    private Button addFutureJourneyButton;

    @FXML
    private Button removeFutureJourneyButton;

    @FXML
    private Button addPastJourneyButton;

    @FXML
    private Button removePastJourneyButton;

    @FXML
    private TextField ratingTextField;

    @FXML
    private Button setPastJourneyRatingButton;

    private User currentUser;
    private ObservableList<FutureJourney> futureJourneysList;
    private ObservableList<PastJourney> pastJourneysList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtons();

        // To make scroll pane scrollable
        mainScrollPane.setFitToWidth(false);
    }
    /**
     * Initializes the user of the session.
     * @param user Current user of the session
     */
    public void initData(User user) {
        currentUser = user;
        welcomeMessage.setText("Welcome to your main feed " + user.getUsername() + "!");

        setFutureJourneyTableViewCells();

        setPastJourneyTableViewCells();

        setContextItems();

        setInitialMainFeedPosts();

        setFriendsMainFeedPosts();
    }

    /**
     * Initializes user's friends' posts in the main feed
     */
    private void setFriendsMainFeedPosts() {
        ObservableList<Friend> allFriendsOfUser = getAllFriendsOfUser(currentUser);
        for (Friend f : allFriendsOfUser) {
            ObservableList<Post> allPostsOfFriend = getAllPostsOfFriend(f);
            for (Post p : allPostsOfFriend) {
                p.share(f, vboxMainFeed);
            }
        }
    }

    /**
     * Opens the profile page
     */
    @FXML
    public void openProfilePage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/profile/profile.fxml"));
            Parent profilePageParent = loader.load();
            Scene profilePageScene = new Scene(profilePageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the profile windows controller
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
     * Button handler for adding future journey which directs to map page
     * @param event
     */
    @FXML
    private void handleAddFutureJourney(ActionEvent event) {
        openMapPage(event);
    }
    /**
     * Button handler for adding past journey which directs to map page
     * @param event
     */
    @FXML
    private void handleAddPastJourney(ActionEvent event) {
        openMapPage(event);
    }
    /**
     * Button handler for removing future journey
     * @param actionEvent
     */
    @FXML
    public void handleRemoveFutureJourney(ActionEvent actionEvent) {
        handleDeleteJourneyFromFutureJourneys();
    }
    /**
     * Button handler for removing past journey
     * @param event
     */
    @FXML
    private void handleRemovePastJourney(ActionEvent event) {
        handleDeleteJourneyFromPastJourneys();
    }
    /**
     * Button handler for set rating for past journey
     * @param event
     */
    @FXML
    private void handleSetRatingOfPastJourney(ActionEvent event) {
        handleAddRatingToTable();
    }

    /**
     *  Adds journey ratings to the past journey table. Journey must be rated between 0 and 10,
     *  user is warned if they do not enter a numeric value between 0 and 10. Also, user has to choose a journey in
     *  order to rate their journey. Thus, if user did not choose any journey from table and try to rate, they are warned.
     */
    private void handleAddRatingToTable() {
        ObservableList<PastJourney> chosenJourneys = pastJourneysTable.getSelectionModel().getSelectedItems();

        if ( ! isNumeric(ratingTextField.getText())) {
            Notifications notificationBuilder = buildNotification("Couldn't Rate Journey", "Please write a numeric value between 0 and 10.",
                    5, Pos.BASELINE_CENTER);
            notificationBuilder.showWarning();
        }
        else if (ratingTextField.getText().isBlank()) {
            Notifications notificationBuilder = buildNotification("Couldn't Rate Journey", "Please write a value between 0 and 10.",
                    5, Pos.BASELINE_CENTER);
            notificationBuilder.showWarning();
        }
        else if (Integer.parseInt(ratingTextField.getText()) > 10 || Integer.parseInt(ratingTextField.getText()) < 0) {
            Notifications notificationBuilder = buildNotification("Couldn't Rate Journey", "Please write a value between 0 and 10.",
                    5, Pos.BASELINE_CENTER);
            notificationBuilder.showWarning();
        }
        else if (chosenJourneys.isEmpty()) {
            Notifications notificationBuilder = buildNotification("Couldn't Rate Journey", "Please choose a location",
                    5, Pos.BASELINE_CENTER);
            notificationBuilder.showWarning();
        }
        else {
            for (PastJourney pj : chosenJourneys) {
                pj.updateJourneyRating(currentUser, ratingTextField.getText());
            }

            Notifications notificationBuilder = buildNotification("Rated Journey", "Rated the journey successfully!",
                    5, Pos.BASELINE_CENTER);
            notificationBuilder.showConfirm();

            handleOpenMainPage();
        }
    }

    /**
     * Deletes future journeys from future journey table
     */
    private void handleDeleteJourneyFromFutureJourneys() {
        ObservableList<FutureJourney> chosenJourneys = futureJourneysTable.getSelectionModel().getSelectedItems();

        if ( ! chosenJourneys.isEmpty()) {
            for (FutureJourney fj : chosenJourneys) {
                deleteJourney(fj, currentUser);
            }

            futureJourneysTable.getItems().removeAll(chosenJourneys);
        } else {
            Notifications notificationBuilder = buildNotification("Couldn't Delete Journey", "Please choose a " +
                    "journey or multiple journeys to delete.", 5, Pos.BASELINE_CENTER);

            notificationBuilder.showWarning();
        }

        handleOpenMainPage();
    }

    /**
     * Deletes past journeys from past journey table
     */
    private void handleDeleteJourneyFromPastJourneys() {
        ObservableList<PastJourney> chosenJourneys = pastJourneysTable.getSelectionModel().getSelectedItems();

        if ( ! chosenJourneys.isEmpty() ) {
            for (PastJourney pj : chosenJourneys) {
                pj.deleteJourney(currentUser);
            }

            pastJourneysTable.getItems().removeAll(chosenJourneys);
        } else {
            Notifications notificationBuilder = buildNotification("Couldn't Delete Journey",
                    "Please choose a journey or multiple journeys to delete.", 5, Pos.BASELINE_CENTER);
            notificationBuilder.showWarning();
        }

        handleOpenMainPage();
    }

    /**
     * Initializes button effects
     */
    private void initButtons() {
        DropShadow shadow = new DropShadow(7, Color.WHITE);
        homePageButton.setOnMouseEntered(mouseEvent -> homePageButton.setEffect(shadow));
        homePageButton.setOnMouseExited(mouseEvent -> homePageButton.setEffect(null));

        mapPageButton.setOnMouseEntered(mouseEvent -> mapPageButton.setEffect(shadow));
        mapPageButton.setOnMouseExited(mouseEvent -> mapPageButton.setEffect(null));

        profilePageButton.setOnMouseEntered(mouseEvent -> profilePageButton.setEffect(shadow));
        profilePageButton.setOnMouseExited(mouseEvent -> profilePageButton.setEffect(null));

        DropShadow blackShadow = new DropShadow();
        addFutureJourneyButton.setOnMouseEntered(mouseEvent -> addFutureJourneyButton.setEffect(blackShadow));
        addFutureJourneyButton.setOnMouseExited(mouseEvent -> addFutureJourneyButton.setEffect(null));

        removeFutureJourneyButton.setOnMouseEntered(mouseEvent -> removeFutureJourneyButton.setEffect(blackShadow));
        removeFutureJourneyButton.setOnMouseExited(mouseEvent -> removeFutureJourneyButton.setEffect(null));

        addPastJourneyButton.setOnMouseEntered(mouseEvent -> addPastJourneyButton.setEffect(blackShadow));
        addPastJourneyButton.setOnMouseExited(mouseEvent -> addPastJourneyButton.setEffect(null));

        shareJourneyButton.setOnMouseEntered(mouseEvent -> shareJourneyButton.setEffect(blackShadow));
        shareJourneyButton.setOnMouseExited(mouseEvent -> shareJourneyButton.setEffect(null));

        removePastJourneyButton.setOnMouseEntered(mouseEvent -> removePastJourneyButton.setEffect(blackShadow));
        removePastJourneyButton.setOnMouseExited(mouseEvent -> removePastJourneyButton.setEffect(null));

        setPastJourneyRatingButton.setOnMouseEntered(mouseEvent -> setPastJourneyRatingButton.setEffect(blackShadow));
        setPastJourneyRatingButton.setOnMouseExited(mouseEvent -> setPastJourneyRatingButton.setEffect(null));
    }

    private ObservableList<PastJourney> selectPastJourneys(User user) {
        ObservableList<PastJourney> result = FXCollections.observableArrayList();
        ObservableList<Journey> allJourneys = getAllJourneysOfUser(user);

        for (Journey j : allJourneys) {
            if (j.getEndDate().compareTo(LocalDate.now()) < 0) {
                PastJourney pastJourney = new PastJourney(j.getLocation(), j.getTitle(), j.getDescription(), j.getStartDate(), j.getEndDate(), getJourneyRating(j, user));
                result.add(pastJourney);
            }
        }

        return result;
    }

    private ObservableList<FutureJourney> selectFutureJourneys(User user) {
        ObservableList<FutureJourney> result = FXCollections.observableArrayList();
        ObservableList<Journey> allJourneys = getAllJourneysOfUser(user);

        for (Journey j : allJourneys) {
            if (j.getStartDate().compareTo(LocalDate.now()) > 0) {
                FutureJourney futureJourney = new FutureJourney(j.getLocation(), j.getTitle(), j.getDescription(), j.getStartDate(), j.getEndDate());
                result.add(futureJourney);
            }
        }

        return result;
    }

    /**
     * Adds cells to the future journey table when new journey added
     */
    private void setFutureJourneyTableViewCells() {
        // Init future journeys table
        futureJourneysList = selectFutureJourneys(currentUser);

        futureJourneysCountryColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        futureJourneysTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        futureJourneysDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        futureJourneysStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        futureJourneysEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        futureJourneysTable.setItems(futureJourneysList);
    }

    /**
     * Adds cells to the past journey table when new past journey added
     */
    private void setPastJourneyTableViewCells() {
        // Init past journeys table
        pastJourneysList = selectPastJourneys(currentUser);

        pastJourneysCountryColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        pastJourneysTitleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        pastJourneysRatingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        pastJourneysDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        pastJourneysStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        pastJourneysEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

        pastJourneysTable.setItems(pastJourneysList);
    }

    /**
     * Initializes context items
     */
    private void setContextItems() {
        contextItemRefreshPastTable.setOnAction(actionEvent -> setPastJourneyTableViewCells());
        contextItemRefreshFutureTable.setOnAction(actionEvent -> setFutureJourneyTableViewCells());

        contextItemAddFutureJourney.setOnAction(actionEvent -> handleOpenMapPage());
        contextItemAddPastJourney.setOnAction(actionEvent -> handleOpenMapPage());

        contextItemAddPastRating.setOnAction(actionEvent -> handleAddRatingToTable());

        contextItemDeleteFutureJourney.setOnAction(actionEvent -> handleDeleteJourneyFromFutureJourneys());
        contextItemDeletePastJourney.setOnAction(actionEvent -> handleDeleteJourneyFromPastJourneys());
    }

    /**
     * Initialize user's post in main feed
     */
    private void setInitialMainFeedPosts() {
        ObservableList<Post> allPostsOfUser = getAllPostsOfUser(currentUser);
        for (Post p : allPostsOfUser) {
            p.share(currentUser, vboxMainFeed);
        }
    }

    public static Notifications buildNotification(String title, String text, int duration, Pos pos) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(duration))
                .position(pos);
        notificationBuilder.darkStyle();

        return notificationBuilder;
    }

    public static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Opens main page
     */
    private void handleOpenMapPage() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/trajour/map/map.fxml"));

        try {
            Parent mapPageParent = loader.load();
            Scene mapPageScene = new Scene(mapPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the map windows controller
            MapController mapController = loader.getController();
            mapController.initData(currentUser);

            // Get the stage and change the scene
            Stage window = (Stage) homePageButton.getScene().getWindow();

            window.setScene(mapPageScene);
            window.show();
        }
        catch (IOException e) {
            e.getCause();
            e.printStackTrace();
        }
    }

    /**
     * Opens main page
     */
    private void handleOpenMainPage() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/trajour/main/main.fxml"));

        try {
            Parent mapPageParent = loader.load();
            Scene mapPageScene = new Scene(mapPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the map windows controller
            MainController mainController = loader.getController();
            mainController.initData(currentUser);

            // Get the stage and change the scene
            Stage window = (Stage) homePageButton.getScene().getWindow();

            window.setScene(mapPageScene);
            window.show();
        }
        catch (IOException e) {
            e.getCause();
            e.printStackTrace();
        }
    }

    /**
     * Opens the home page.
     */
    @FXML
    public void openHomePage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/trajour/main/main.fxml"));

        try {
            Parent mainPageParent = loader.load();
            Scene mainPageScene = new Scene(mainPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the main windows controller
            MainController mainController = loader.getController();
            mainController.initData(currentUser);

            // Get the stage and change the scene
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(mainPageScene);
            window.show();
        }
        catch (IOException e) {
            e.getCause();
            e.printStackTrace();
        }
    }

    /**
     * Opens map page.
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
     * Opens share journey page
     */
    @FXML
    public void openShareJourneyPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/main/share_journey.fxml"));

            Parent shareJourneyPageParent = loader.load();
            Scene shareJourneyPageScene = new Scene(shareJourneyPageParent, 480, 800);

            // Get access to the main windows controller
            ShareJourneyController shareJourneyController = loader.getController();
            shareJourneyController.initData(currentUser, vboxMainFeed);

            // Get the stage and change the scene
            Stage window = new Stage();

            window.setScene(shareJourneyPageScene);
            window.show();
        }
        catch (IOException e) {
            e.getCause();
            e.printStackTrace();
        }
    }
}
