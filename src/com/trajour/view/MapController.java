package com.trajour.view;

import com.trajour.journey.Journey;
import com.trajour.model.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.WorldMapView;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDate;

public class MapController {
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

    private User currentUser;

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
    }

    @FXML
    void handleAddJourney(ActionEvent event) {
        ObservableList<WorldMapView.Country> selectedCountry = worldMapView.getSelectedCountries();

        if (selectedCountry.size() > 1) {
            JOptionPane.showMessageDialog(null, "Please choose only 1 country.",
                    "Selection Error", JOptionPane.INFORMATION_MESSAGE);
        }

        if (selectedCountry.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please choose at least 1 country by left clicking" +
                            " on a country on the map.", "Selection Error", JOptionPane.INFORMATION_MESSAGE);
        }

        String journeyDesc = journeyDescriptionTextArea.getText();


            // TODO Add journey to database
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
}
