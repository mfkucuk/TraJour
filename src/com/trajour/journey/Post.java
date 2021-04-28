package com.trajour.journey;

import com.trajour.model.User;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;


/**
 * A simple Java class!
*/
public class Post extends FlowPane implements Shareable {
    // Properties
    private String text;
    private Journey theJourney;
    private Image journeyPhoto;

    // Components
    private ImageView journeyPhotoView;
    private Label journeyLocationLabel;
    private Label usernameLabel;
    private Label dateLabel;
    private Label commentLabel;
    private Label ratingLabel;

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
        this.journeyPhotoView = new ImageView();
    }

    @Override
    public Post share(User user, VBox mainFeed) {
        journeyLocationLabel = new Label(theJourney.getLocation());
        usernameLabel = new Label(user.getUsername());
        commentLabel = new Label(theJourney.getDescription());
        dateLabel = new Label(theJourney.getStartDate() + "-" + theJourney.getEndDate());
        // ratingLabel = new Label( "" + ((PastJourney) theJourney).getRating());
        journeyPhotoView.setImage(journeyPhoto);

        getChildren().add(journeyLocationLabel);
        getChildren().add(usernameLabel);
        getChildren().add(dateLabel);
        // getChildren().add(ratingLabel);
        getChildren().add(journeyPhotoView);
        getChildren().add(commentLabel);


        mainFeed.getChildren().add(this);
        return this;
    }

}