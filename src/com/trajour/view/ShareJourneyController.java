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

import static com.trajour.db.DatabaseQuery.*;

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

    private File selectedFile;
    private VBox vbox;

    private User currentUser;
    private ObservableList<Journey> journeysOfCurrentUser;

    public void initData(User user, VBox v) {
        currentUser = user;
        vbox = v;

        journeysOfCurrentUser = FXCollections.observableArrayList();
        journeysOfCurrentUser = getAllJourneysOfUser(currentUser);
        journeyComboBox.setItems(journeysOfCurrentUser);
    }

    @FXML
    public void addJourneyPicture(ActionEvent event) {
        // TODO Multiple files
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));

        Stage stage = new Stage();
        this.selectedFile = fileChooser.showOpenDialog(stage);

        if (this.selectedFile != null) {
            Image img = new Image(this.selectedFile.toURI().toString(), 90,90, false, false);

            journeyImageView.setImage(img);
            pictureFeedBackLabel.setText("");
        }
    }

    @FXML
    public void shareJourney(ActionEvent event) {
        if (!commentsTextArea.getText().isBlank() && !journeyComboBox.getSelectionModel().isEmpty() && journeyImageView.getImage() != null) {
            Journey selectedJourney = journeyComboBox.getSelectionModel().getSelectedItem();
            Post newPost = selectedJourney.post(commentsTextArea.getText(), journeyImageView.getImage());

            // TODO Add the post to database
            if (newPost.share(currentUser, vbox)) {
                insertPost(currentUser, newPost);
                updateImageOfPost(selectedFile, currentUser, newPost.getTheJourney().getTitle());
            }
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
        else {
            feedBackLabel.setText("Make sure that you have completed all the forms.");
        }
    }
}
