package com.trajour.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
import com.trajour.user.User;
import org.controlsfx.control.Notifications;

import java.io.File;

import static com.trajour.db.DatabaseQuery.*;
import static com.trajour.main.MainController.buildNotification;

/**
 * Share journey page allows users to upload their journeys as a post.
 * It contains combo box to choose which journey will be uploaded. Users can upload
 * journey images and write a comment for their journeys and can share it as a post.
 * @author Selim Can Güler
 * @author Mehmet Feyyaz Küçük
 * @version 03 May 2021
 */
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

    /**
     * Initalize share journey page
     * @param user Current user
     * @param v Post vBox
     */
    public void initData(User user, VBox v) {
        currentUser = user;
        vbox = v;

        journeysOfCurrentUser = FXCollections.observableArrayList();
        journeysOfCurrentUser = getAllJourneysOfUser(currentUser);
        journeyComboBox.setItems(journeysOfCurrentUser);
    }

    /**
     * Opens file to add journey picture
     * @param event
     */
    @FXML
    public void addJourneyPicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));

        Stage stage = new Stage();
        this.selectedFile = fileChooser.showOpenDialog(stage);

        if (this.selectedFile != null && this.selectedFile.length() < 1048576) {
            Image img = new Image(this.selectedFile.toURI().toString(), 90,90, false, false);

            journeyImageView.setImage(img);
            pictureFeedBackLabel.setText("");
        }
    }

    /**
     * Adds journey to main feed as post which contains journey picture and user comment.
     * If necessary places did not filled by user or image size is larger than 5 MB user is warned by
     * notification
     * @param event
     */
    @FXML
    public void shareJourney(ActionEvent event) {
        if (!commentsTextArea.getText().isBlank() && !journeyComboBox.getSelectionModel().isEmpty() && journeyImageView.getImage() != null) {
            Journey selectedJourney = journeyComboBox.getSelectionModel().getSelectedItem();
            Post newPost = selectedJourney.post(commentsTextArea.getText(), journeyImageView.getImage());

            newPost.share(currentUser, vbox);

             if (!updateImageOfPost(selectedFile, currentUser, newPost.getTheJourney().getTitle())) {
                Notifications notification = buildNotification("Image Too Large", "Please upload a picture less than 5 MB", 5, Pos.BASELINE_CENTER);
                notification.showError();
            }
            else {
                updateImageOfPost(selectedFile, currentUser, newPost.getTheJourney().getTitle());
            }

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        }
        else {
            Notifications notification = buildNotification("Couldn't Share Journey", "Please complete all the necessary forms.", 5, Pos.BASELINE_CENTER);
            notification.showError();
        }
    }
}
