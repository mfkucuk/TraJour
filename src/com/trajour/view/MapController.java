package com.trajour.view;

import com.trajour.journey.Journey;
import com.trajour.model.User;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.WorldMapView;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Scanner;

import static com.trajour.db.DatabaseQuery.insertNewJourney;

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

    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    }

    public void initData(User user) {
        currentUser = user;
        // countriesComboBox.setItems();

        // From the JavaFX tutorial in Oracle's website, disables the cells that corresponds to the date
        // selected in the startDate and all the cells corresponding to the preceding dates
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
                            }
                        };
                    }
                };
        endDatePicker.setDayCellFactory(dayCellFactory);

        endDatePicker.setValue(startDatePicker.getValue().plusDays(1));

        worldMapView.setOnMouseClicked(mouseEvent -> {
            try {
                selectedCountryField.setText(countryCodeToCountryName(worldMapView.getSelectedCountries().get(0).name()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void handleAddJourney(ActionEvent event) throws FileNotFoundException {
        ObservableList<WorldMapView.Country> selectedCountry = worldMapView.getSelectedCountries();

        // Check whether the user chose only 1 country
        if (selectedCountry.size() > 1) {
            JOptionPane.showMessageDialog(null, "Please choose only 1 country.",
                    "Selection Error", JOptionPane.INFORMATION_MESSAGE);
        }

        if (selectedCountry.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please choose at least 1 country by left clicking" +
                            " on a country on the map.", "Selection Error", JOptionPane.INFORMATION_MESSAGE);
        }

        // Add the journey to the database
        String journeyDesc = journeyDescriptionTextArea.getText();
        LocalDate start = startDatePicker.getValue();
        LocalDate end = endDatePicker.getValue();
        String country = countryCodeToCountryName(selectedCountry.get(0).name());

        Journey j = new Journey(country, journeyDesc, start, end);

        insertNewJourney(j, currentUser);

        // Build notification
        Notifications notificationBuilder = Notifications.create()
                .title("Added Journey!")
                .text("Journey is successfully added.")
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.showConfirm();

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
            initData(currentUser);

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

        // TODO Possible errors with countries with multiple names
        while (in.hasNextLine()) {
            String line = in.next();
            String[] pieces = line.split(",");
            if (pieces.length > 1) {
                if (pieces[1].equals(code)) {
                    return pieces[0];
                }
            }
        }

        return "";
    }
}
