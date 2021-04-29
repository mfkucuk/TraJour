package com.trajour.journey;

import com.trajour.db.DatabaseQuery;
import com.trajour.model.User;
import com.trajour.view.MapController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.FileNotFoundException;


/**
 * A simple Java class!
*/
public class Post extends GridPane implements Shareable {
    // Properties
    private String text;
    private Journey theJourney;
    private Image journeyPhoto;

    // Components
    private ImageView journeyPhotoView;
    private ImageView userPhotoView;
    private Label journeyLocationLabel;
    private Label usernameLabel;
    private Label dateLabel;
    private Label commentLabel;
    private Label ratingLabel;
    private VBox userVBox;
    private ColumnConstraints cc;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Journey getTheJourney() {
        return theJourney;
    }

    public void setTheJourney(Journey theJourney) {
        this.theJourney = theJourney;
    }

    public Image getJourneyPhoto() {
        return journeyPhoto;
    }

    public void setJourneyPhoto(Image journeyPhoto) {
        this.journeyPhoto = journeyPhoto;
    }

    // Constructor
    public Post( Journey theJourney, String text, Image journeyPhoto ) {
        this.theJourney = theJourney;
        this.text = text;
        this.journeyPhoto = journeyPhoto;
        journeyPhotoView = new ImageView();
        userPhotoView = new ImageView();
        userVBox = new VBox();
        cc = new ColumnConstraints();
        cc.setFillWidth(true);
        cc.setHgrow(Priority.ALWAYS);
        this.setGridLinesVisible(true);
        this.setPadding(new Insets(20));
        this.setMinWidth(USE_COMPUTED_SIZE);
        this.setMaxWidth(USE_COMPUTED_SIZE);
        this.setPrefWidth(USE_COMPUTED_SIZE);
        for (int i = 0; i < 4; i++) {
            this.getColumnConstraints().add(cc);
        }
    }

    @Override
    public Post share(User user, VBox mainFeed) {
        journeyLocationLabel = new Label("Location: " + theJourney.getLocation());
        usernameLabel = new Label(user.getUsername());
        commentLabel = new Label("Comment: " + text);
        dateLabel = new Label("Date: " + theJourney.getStartDate() + " / " + theJourney.getEndDate());
        // ratingLabel = new Label( "" + ((PastJourney) theJourney).getRating());
        journeyPhotoView.setImage(journeyPhoto);
        userPhotoView.setImage(new Image(DatabaseQuery.getProfilePhotoFile(user).toURI().toString(), 40, 40, false, false));
        userVBox.getChildren().add(userPhotoView);
        userVBox.getChildren().add(usernameLabel);

        journeyLocationLabel.setFont(new Font("Arial Bold", 12));
        usernameLabel.setFont(new Font("Arial Bold", 12));
        commentLabel.setFont(new Font("Arial Bold", 12));
        dateLabel.setFont(new Font("Arial Bold", 12));




        add(userVBox, 0, 0);
        add(journeyLocationLabel, 0, 1);
        add(dateLabel, 1, 1);
        add(journeyPhotoView, 2, 1);
        add(commentLabel, 3, 1);
        // getChildren().add(ratingLabel);

        mainFeed.getChildren().add(this);
        return this;
    }



}