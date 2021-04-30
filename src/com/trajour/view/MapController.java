package com.trajour.view;

import com.trajour.journey.Journey;
import com.trajour.model.User;

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
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.WorldMapView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import static com.trajour.db.DatabaseQuery.findJourneyByUser;
import static com.trajour.db.DatabaseQuery.insertNewJourney;
import static com.trajour.view.MainController.buildNotification;

public class MapController implements Initializable {
    @FXML
    private Button homePageButton;

    @FXML
    private Button mapPageButton;

    @FXML
    private Button profilePageButton;

    @FXML
    private Slider zoomSlider;

    @FXML
    private WorldMapView worldMapView;

    @FXML
    private Button addJourneyButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextArea journeyDescriptionTextArea;

    @FXML
    private TextField selectedCountryField;

    @FXML
    private CheckBox showLocationsCheckBox;

    @FXML
    private Button pickRandomCountryButton;

    @FXML
    private Button showDistanceButton;

    @FXML
    private TextField countryNumberTextField;

    @FXML
    private TextField journeyTitleTextField;

    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize button styling
        DropShadow shadow = new DropShadow(7, Color.WHITE);
        homePageButton.setOnMouseEntered(mouseEvent -> homePageButton.setEffect(shadow));
        homePageButton.setOnMouseExited(mouseEvent -> homePageButton.setEffect(null));

        mapPageButton.setOnMouseEntered(mouseEvent -> mapPageButton.setEffect(shadow));
        mapPageButton.setOnMouseExited(mouseEvent -> mapPageButton.setEffect(null));

        profilePageButton.setOnMouseEntered(mouseEvent -> profilePageButton.setEffect(shadow));
        profilePageButton.setOnMouseExited(mouseEvent -> profilePageButton.setEffect(null));

        DropShadow blackShadow = new DropShadow();
        addJourneyButton.setOnMouseEntered(mouseEvent -> addJourneyButton.setEffect(blackShadow));
        addJourneyButton.setOnMouseExited(mouseEvent -> addJourneyButton.setEffect(null));

        pickRandomCountryButton.setOnMouseEntered(mouseEvent -> pickRandomCountryButton.setEffect(blackShadow));
        pickRandomCountryButton.setOnMouseExited(mouseEvent -> pickRandomCountryButton.setEffect(null));

        showDistanceButton.setOnMouseEntered(mouseEvent -> showDistanceButton.setEffect(blackShadow));
        showDistanceButton.setOnMouseExited(mouseEvent -> showDistanceButton.setEffect(null));

//        worldMapView.setCountryViewFactory(country -> {
//            WorldMapView.CountryView view = new WorldMapView.CountryView(country);
//            view.setOnMouseEntered(mouseEvent -> view.setStyle("-fx-background-color: #000000;"));
//            return view; });
//
//        worldMapView.setLocationViewFactory(location -> {
//            Circle circle = new Circle();
//            circle.getStyleClass().add("location");
//            circle.setRadius(4);
//            circle.setTranslateX(-4); // translate to center node on location
//            circle.setTranslateY(-4);
//            return circle; });

        // Zoom in and out
        zoomSlider.valueProperty().addListener((observableValue, number, t1) -> worldMapView.setZoomFactor(zoomSlider.getValue()));
    }

    public void initData(User user) {
        currentUser = user;

        // From the JavaFX tutorial in Oracle's website, disables the cells that corresponds to the date
        // selected in the startDate and all the cells corresponding to the preceding dates
        // https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm#CCHHJBEA
        startDatePicker.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        startDatePicker.getValue().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                                long p = ChronoUnit.DAYS.between(
                                        startDatePicker.getValue(), item
                                );

                                Tooltip tt = (new Tooltip("You're about to stay for " + p + " days"));
                                tt.setFont(new Font(14));

                                setTooltip(tt);
                            }
                        };
                    }
                };

        endDatePicker.setDayCellFactory(dayCellFactory);

        endDatePicker.setValue(startDatePicker.getValue().plusDays(1));

        // Map zoom functionality
        worldMapView.setOnMouseClicked(mouseEvent -> {
            try {
                if (!worldMapView.getSelectedCountries().isEmpty()) {
                    selectedCountryField.setText(countryCodeToCountryName(worldMapView.getSelectedCountries().get(0).name()));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        worldMapView.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> {
            double movement = scrollEvent.getDeltaY() / 40;
            zoomSlider.setValue(zoomSlider.getValue() + movement);
        });
    }

    @FXML
    void handlePickRandomCountry(ActionEvent event) {
        String selectedCountryName = getRandomCountry();

        selectedCountryField.setText(selectedCountryName);
    }

    @FXML
    void handleShowDistance(ActionEvent event) {

    }

    @FXML
    void handleShowLocations(ActionEvent event) {

    }

    @FXML
    void handleAddJourney(ActionEvent event) throws FileNotFoundException {
        ObservableList<WorldMapView.Country> selectedCountry = worldMapView.getSelectedCountries();

        // Check whether the user chose only 1 country
        if (selectedCountry.size() > 1) {
            Notifications notification = buildNotification("Selection Error", "Please choose only 1 country.", 8, Pos.BASELINE_CENTER);
            notification.showError();
            return;
        }

        if (selectedCountry.size() == 0) {
            Notifications notification = buildNotification("Selection Error", "Please choose at least" +
                    " 1 country by left clicking on a country on the map.", 8, Pos.BASELINE_CENTER);
            notification.showError();
            return;
        }

        // Add the journey to the database
        String journeyDesc = journeyDescriptionTextArea.getText();
        String title = journeyTitleTextField.getText();
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();

        String country = countryCodeToCountryName(selectedCountry.get(0).name());

        Journey j = new Journey(country, title, journeyDesc, start, end);

        if (findJourneyByUser(j, currentUser)) {
            Notifications notification = buildNotification("Journey Already Exists", "A journey with the " +
                    "exact same specifications already exists in your journeys list. ", 8, Pos.BASELINE_CENTER);
            notification.showError();
        }
        else {
            insertNewJourney(j, currentUser);

            // Build notification
            Notifications notificationBuilder = buildNotification("Added Journey!", "Journey is successfully " +
                    "added. Journey details: \nCountry: " + j.getLocation() + ", Description: " + j.getDescription() +
                    ", Dates: " + j.getStartDate() + " - " + j.getEndDate(), 8, Pos.BASELINE_CENTER);

            notificationBuilder.showConfirm();
        }
    }

    @FXML
    void openHomePage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/view/fxml/main.fxml"));
            Parent mainPageParent = loader.load();
            Scene mainPageScene = new Scene(mainPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the main window controller
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

    @FXML
    void openMapPage(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/trajour/view/fxml/mapxz.fxml"));

        try {
            Parent mapPageParent = loader.load();
            Scene mapPageScene = new Scene(mapPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the map window controller
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
    void openProfilePage(ActionEvent event) {
        try {
            // Get the parent and create the scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/view/fxml/profile.fxml"));
            Parent profilePageParent = loader.load();
            Scene profilePageScene = new Scene(profilePageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            // Get access to the profile window controller
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

    private String countryCodeToCountryName(String code) throws FileNotFoundException {
        Scanner in = new Scanner(new File("src/resources/countries_with_codes.csv"));
        StringBuilder result = new StringBuilder();

        // TODO Possible errors with countries with multiple names
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String[] pieces = line.split(",");

            if (pieces.length > 1) {
                if (pieces[pieces.length - 1].equals(code)) {
                    for (int i = 0; i < pieces.length - 1; i++) {
                        result.append(pieces[i]).append(" ");
                    }
                }
            }
        }

        return result.toString();
    }

//    @FXML
//    void handlePickRandomCountry(ActionEvent event) {
//        ObservableList<WorldMapView.Country> selectedCountries = FXCollections.observableArrayList();
//        ObservableList<String> selectedCountryNames = getRandomCountries(Integer.valueOf(countryNumberTextField.getText()));
//
//        for (String s : selectedCountryNames) {
//            selectedCountries.add(WorldMapView.Country.valueOf(s));
//        }
//
//        worldMapView.setCountrySelectionMode(WorldMapView.SelectionMode.MULTIPLE);
//        worldMapView.setSelectedCountries(selectedCountries);
//    }
//
    private int getRandomNumber() {
        return getRandomNumber(173, 2);
    }

    private int getRandomNumber(final int MAX, final int MIN) {
        ArrayList<Integer> result = new ArrayList<>();
        Random rand = new Random();

        final int MAX_NUM = MAX;
        final int MIN_NUM = MIN;

        int randNum = rand.nextInt(MAX - MIN + 1) + MIN;

        return randNum;
    }

    private String getRandomCountry() {
        try {
            ObservableList<String> countries = FXCollections.observableArrayList();
            int randomNum = getRandomNumber();

            Scanner in = new Scanner(new File("src/resources/countries.csv"));
            while (in.hasNextLine()) {
                for (int i = 1; i < randomNum; i++) {
                    in.nextLine();
                }

                String line = in.nextLine();
                String[] pieces = line.split(",");
                return pieces[3];
            }
        } catch (FileNotFoundException e) {
            e.getCause();
            e.printStackTrace();
        }

        return "";
    }

//    private ObservableList<String> getRandomCountries(int numberOfRandomCountries) {
//        try {
//            if (numberOfRandomCountries > 246) {
//                throw new IllegalArgumentException("There are only 246 countries");
//            }
//
//            ObservableList<String> countries = FXCollections.observableArrayList();
//            ArrayList<Integer> randomNums = getRandomNumber(numberOfRandomCountries);
//
//            Scanner in = new Scanner(new File("src/resources/countries.csv"));
//            while (in.hasNextLine()) {
//                for (int i = 0; i < numberOfRandomCountries; i++) {
//                    in = new Scanner(new File("src/resources/countries.csv"));
//                    int j = randomNums.get(i);
//
//                    while (j > 1) {
//                        in.nextLine();
//                        j--;
//                    }
//
//                    String line = in.nextLine();
//                    String[] pieces = line.split(",");
//                    countries.add(pieces[3]);
//
//                    if (i == numberOfRandomCountries - 1)
//                        break;
//                }
//
//                break;
//            }
//
//            in.close();
//            return countries;
//        } catch (FileNotFoundException e) {
//            e.getCause();
//            e.printStackTrace();
//        }
//
//        return FXCollections.observableArrayList();
//    }
}
