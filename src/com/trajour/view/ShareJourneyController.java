package com.trajour.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.trajour.journey.Journey;
import com.trajour.journey.Post;
import com.trajour.model.User;

import java.io.File;

import static com.trajour.db.DatabaseQuery.getJourneysOfTheUser;

public class ShareJourneyController {
    @FXML
    private ComboBox<Journey> journeyComboBox;

    @FXML
    private Button shareButton;

    @FXML
    private Button addPictureButton;

    @FXML
    private TextArea commentsTextArea;

    @FXML
    private ImageView journeyImageView;

    @FXML
    private Label pictureFeedBackLabel;

    @FXML
    private Label feedBackLabel;

    private VBox vbox;

    private User currentUser;
    private ObservableList<Journey> journeysOfCurrentUser;

    public void initData(User user, VBox v) {
        currentUser = user;
        vbox = v;

        // TODO Init journeys of the user in 'journeyComboBox'
        journeysOfCurrentUser = FXCollections.observableArrayList();
        journeysOfCurrentUser = getJourneysOfTheUser(currentUser);
        System.out.println(journeysOfCurrentUser.toString());

        journeyComboBox.setItems(journeysOfCurrentUser);
    }

    @FXML
    public void addJourneyPicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        Stage stage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image img = new Image(selectedFile.toURI().toString(), 40, 40, false, false);

            journeyImageView.setImage(img);
        }
    }

    @FXML
    public void shareJourney(ActionEvent event) {
        if (!commentsTextArea.getText().isBlank() && !journeyComboBox.getSelectionModel().isEmpty() && journeyImageView.getImage() != null) {
            Journey selectedJourney = journeyComboBox.getSelectionModel().getSelectedItem();
            Post newPost = selectedJourney.post(commentsTextArea.getText(), journeyImageView.getImage());

            // TODO Add the post to database
            newPost.share(currentUser, vbox);
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
        else {
            feedBackLabel.setText("Make sure that you have completed all the forms.");
        }
    }
}
