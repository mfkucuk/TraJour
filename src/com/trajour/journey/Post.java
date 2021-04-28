package com.trajour.journey;

import com.trajour.db.DatabaseQuery;
import com.trajour.model.User;
import com.trajour.view.MapController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
    private HBox journeyHBox;

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
        journeyHBox = new HBox();
        this.setVgap(20);
        this.setGridLinesVisible(true);
        this.setPadding(new Insets(20));
    }

    @Override
    public Post share(User user, VBox mainFeed) {
            journeyLocationLabel = new Label(theJourney.getLocation());
            usernameLabel = new Label(user.getUsername());
            commentLabel = new Label(theJourney.getDescription());
            dateLabel = new Label(theJourney.getStartDate() + " / " + theJourney.getEndDate());
            // ratingLabel = new Label( "" + ((PastJourney) theJourney).getRating());
            journeyPhotoView.setImage(journeyPhoto);
            userPhotoView.setImage(new Image(DatabaseQuery.getProfilePhotoFile(user).toURI().toString(), 40, 40, false, false));
            userVBox.getChildren().add(userPhotoView);
            userVBox.getChildren().add(usernameLabel);

            journeyHBox.getChildren().add(journeyLocationLabel);
            journeyHBox.getChildren().add(dateLabel);
            journeyHBox.getChildren().add(journeyPhotoView);
            journeyHBox.getChildren().add(commentLabel);

            journeyHBox.setSpacing(150);

            add(userVBox, 0, 0);
            add(journeyHBox, 0, 1);
            // getChildren().add(ratingLabel);

            mainFeed.getChildren().add(this);
            return this;
    }



}