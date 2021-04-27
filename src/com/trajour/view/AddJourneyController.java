package com.trajour.view;

import com.trajour.journey.Journey;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import com.trajour.model.User;

import java.time.LocalDate;

public class AddJourneyController {

    @FXML
    private ComboBox<String> countriesComboBox;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextArea journeyDescription;

    @FXML
    private Button addJourneyButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<?> journeysTable;

    @FXML
    private Button removeJourneyButton;

    private User currentUser;

    public void initData(User user) {
        currentUser = user;
        // countriesComboBox.setItems();

        // From the JavaFX tutorial in Oracle's website, disables the cells that corresponds to the date
        // selected in the startDate and all the cells corresponding to the preceding dates
        startDate.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        startDate.getValue().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        endDate.setDayCellFactory(dayCellFactory);

        endDate.setValue(startDate.getValue().plusDays(1));
    }

    @FXML
    void handleAddJourney(ActionEvent event) {
        String country = countriesComboBox.getSelectionModel().getSelectedItem();
        String journeyDesc = journeyDescription.getText();

        if (!countriesComboBox.getSelectionModel().isEmpty() && journeyDesc.isBlank()) {
            Journey j = new Journey(country, journeyDesc, startDate.getValue(), endDate.getValue());
            // TODO Add journey to database
        }
    }


    @FXML
    void handleRemoveLastJourney(ActionEvent event) {
        // TODO Remove the latest journey from database
    }

    @FXML
    void handleCancel() {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }
}
