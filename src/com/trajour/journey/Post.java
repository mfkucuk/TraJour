package com.trajour.journey;

import com.trajour.db.DatabaseQuery;
import com.trajour.user.Friend;
import com.trajour.user.User;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import java.io.File;

import static com.trajour.db.DatabaseQuery.*;

/**
 * A class that defines how posts are and extends GridPane
 * for constructing the post and implements Shareable.
 *
 *@author Ahmet Alperen Yılmazyıldız
 *@version 3.05.2021
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
    private Label journeyNameLabel;
    private VBox userVBox;
    private ColumnConstraints cc;

    /**
     * Getter method to get text.
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Setter method to set text.
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter method to get Journey.
     *
     * @return Journey
     */
    public Journey getTheJourney() {
        return theJourney;
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
    /**
     * This method defines how the posts will look and where they will appear to the user,
     * Sets the location, texts, photo, and inserts it to main feed of the user.
     * It also includes the users image, adjusts the fonts, and outlays.
     */
    public Post share(User user, VBox mainFeed) {
        journeyLocationLabel = new Label("Location: " + theJourney.getLocation());
        usernameLabel = new Label(user.getUsername());
        commentLabel = new Label("Comment: " + text);
        dateLabel = new Label("Date: " + theJourney.getStartDate() + " / " + theJourney.getEndDate());
        journeyNameLabel = new Label("Name: " + theJourney.getTitle());

        journeyPhotoView.setImage(journeyPhoto);

        if (journeyPhotoView.getImage() == null) {
            File file = getPostPhoto(user.getUserId(), getTheJourney().getTitle());
            journeyPhotoView.setImage(new Image(file.toURI().toString(), 40, 40, false, false));
        }

        userPhotoView.setImage(new Image(DatabaseQuery.getProfilePhotoFile(user.getUserId()).toURI().toString(), 40, 40, false, false));
        userVBox.getChildren().add(userPhotoView);
        userVBox.getChildren().add(usernameLabel);

        journeyLocationLabel.setFont(new Font("Arial Bold", 12));
        journeyNameLabel.setFont(new Font("Arial Bold", 12));
        usernameLabel.setFont(new Font("Arial Bold", 12));
        commentLabel.setFont(new Font("Arial Bold", 12));
        dateLabel.setFont(new Font("Arial Bold", 12));

        add(userVBox, 0, 0);
        add(journeyNameLabel, 0, 1);
        add(journeyLocationLabel, 1, 1);
        add(dateLabel, 2, 1);
        add(journeyPhotoView, 3, 1);
        add(commentLabel, 4, 1);

        mainFeed.getChildren().add(this);

        if (!findPostByTitle(this, user)) {
            insertPost(user, this);
        }

        return this;
    }

    /**
     * Creates friend post as a vBox by getting friend's journey,
     * photo of journey and friends' comment about the journey and
     * adds the post to main feed of the user.
     *
     * @param f
     * @param mainFeed
     * @return FriendsPost
     */
    public Post share(Friend f, VBox mainFeed) {
        journeyLocationLabel = new Label("Location: " + theJourney.getLocation());
        usernameLabel = new Label(f.getFriendName());
        commentLabel = new Label("Comment: " + text);
        dateLabel = new Label("Date: " + theJourney.getStartDate() + " / " + theJourney.getEndDate());
        journeyNameLabel = new Label("Name: " + theJourney.getTitle());

        journeyPhotoView.setImage(journeyPhoto);

        if (journeyPhotoView.getImage() == null) {
            File file = getPostPhoto(f.getFriendUserId(), getTheJourney().getTitle());
            journeyPhotoView.setImage(new Image(file.toURI().toString(), 40, 40, false, false));
        }

        userPhotoView.setImage(new Image(DatabaseQuery.getProfilePhotoFile(f.getFriendUserId()).toURI().toString(), 40, 40, false, false));
        userVBox.getChildren().add(userPhotoView);
        userVBox.getChildren().add(usernameLabel);

        journeyLocationLabel.setFont(new Font("Arial Bold", 12));
        journeyNameLabel.setFont(new Font("Arial Bold", 12));
        usernameLabel.setFont(new Font("Arial Bold", 12));
        commentLabel.setFont(new Font("Arial Bold", 12));
        dateLabel.setFont(new Font("Arial Bold", 12));

        add(userVBox, 0, 0);
        add(journeyNameLabel, 0, 1);
        add(journeyLocationLabel, 1, 1);
        add(dateLabel, 2, 1);
        add(journeyPhotoView, 3, 1);
        add(commentLabel, 4, 1);

        mainFeed.getChildren().add(this);

        return this;
    }
}