package com.trajour.view;

import com.trajour.journey.FutureJourney;
import com.trajour.journey.Journey;
import com.trajour.journey.PastJourney;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import com.trajour.model.User;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.trajour.db.DatabaseQuery.getAllJourneysOfUser;

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
    private TableColumn<FutureJourney, String> futureJourneysDescriptionColumn;

    @FXML
    private TableColumn<FutureJourney, LocalDate> futureJourneysStartDateColumn;

    @FXML
    private TableColumn<FutureJourney, LocalDate> futureJourneysEndDateColumn;

    @FXML
    private TableColumn<PastJourney, String> pastJourneysCountryColumn;

    @FXML
    private TableColumn<PastJourney, Integer> pastJourneysRatingColumn;

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
    private Button achievementsButton;

    @FXML
    private ScrollPane mainScrollPane;

    @FXML
    private VBox vboxMainFeed;

    private User currentUser;
    private ObservableList<FutureJourney> futureJourneysList;
    private ObservableList<PastJourney> pastJourneysList;

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

        futureJourneysList = selectFutureJourneys(currentUser);

        futureJourneysCountryColumn.setCellValueFactory(new PropertyValueFactory<FutureJourney, String>("location"));
        futureJourneysDescriptionColumn.setCellValueFactory(new PropertyValueFactory<FutureJourney, String>("description"));
        futureJourneysStartDateColumn.setCellValueFactory(new PropertyValueFactory<FutureJourney, LocalDate>("startDate"));
        futureJourneysEndDateColumn.setCellValueFactory(new PropertyValueFactory<FutureJourney, LocalDate>("endDate"));

        futureJourneysTable.setItems(futureJourneysList);


        pastJourneysList = selectPastJourneys(currentUser);


    }

    /**
     * Opens the profile page
     */
    @FXML
    public void openProfilePage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/view/fxml/profile.fxml"));
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
     * Opens the home page.
     */
    @FXML
    public void openHomePage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/trajour/view/fxml/main.fxml"));

        try {
            Parent mainPageParent = loader.load();
            Scene mainPageScene = new Scene(mainPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the main windows controller
            initData(currentUser);

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
        loader.setLocation(getClass().getResource("/com/trajour/view/fxml/mapxz.fxml"));

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
    public void openShareJourneyPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/view/fxml/share_journey.fxml"));

            Parent shareJourneyPageParent = loader.load();
            Scene shareJourneyPageScene = new Scene(shareJourneyPageParent, 478, 774);

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
            e.printStackTrace();
        }
    }

    private ObservableList<PastJourney> selectPastJourneys(User user) {
        ObservableList<PastJourney> result = FXCollections.observableArrayList();
        ObservableList<Journey> allJourneys = getAllJourneysOfUser(user);
        // TODO check each journey and add the ones that have an end date before LocalDate.now() to the result list

        for (Journey j : allJourneys) {
            if (j.getEndDate().compareTo(LocalDate.now()) < 0) {

                // result.add(j);
            }
        }
        return null;
    }

    private ObservableList<FutureJourney> selectFutureJourneys(User user) {
        ObservableList<FutureJourney> result = FXCollections.observableArrayList();
        ObservableList<Journey> allJourneys = getAllJourneysOfUser(user);

        for (Journey j : allJourneys) {
            if (j.getStartDate().compareTo(LocalDate.now()) > 0) {
                FutureJourney futureJourney = new FutureJourney(j.getLocation(), j.getDescription(), j.getStartDate(), j.getEndDate());
                result.add(futureJourney);
            }
        }

        return result;
    }
}
