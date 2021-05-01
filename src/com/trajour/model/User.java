package com.trajour.model;

import com.trajour.db.DatabaseQuery;
import com.trajour.journey.Journey;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.io.File;

import static com.trajour.db.DatabaseQuery.removeFriend;
import static com.trajour.view.MainController.buildNotification;

/**
 * User class.
 * @author Mehmet Feyyaz Kucuk
 */
public class User {
    // Properties
    private int userId;
    private String username;
    private String email;
    private Image profilePhoto;

    public User(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public void removeFriend(ObservableList<Friend> friendsToRemove) {
        if ( ! friendsToRemove.isEmpty()) {
            for (Friend f : friendsToRemove) {
                DatabaseQuery.removeFriend(f, this);
            }
        }
    }

    public boolean addPicture(File img) {
        return DatabaseQuery.updateImage(img, this);
    }

    public boolean addWish() {
        return false;
    }

    public boolean removeWish() {
        return false;
    }

    // Methods
    public boolean addFriendByUsername(String username) {
        // TODO Search the database, check whether a user with the given username exists or the user is already a friend
        return false;
    }
    

    public boolean addFriendByEmail(String email) {
        // TODO Search the database, check whether a user with the given username exists or the user is already a friend
        return false;
    }


    /**
     * Returns username.
     * @return is the username.  
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Returns email.
     * @return is the email.
     */
    public String getEmail() {
        return email;
    }

    public int getUserId() {
        return userId;
    }

    /**
     * Adds a new journey.
     * @param newJourney is the journey to be added.
     * @return is the boolean indicating whether adding was successful.
     */
    public boolean addJourney(Journey newJourney) {
        return true;
    }

    /**
     * Shares the specified journey to friends.
     * @param j is the shared journey.
     * @return  is the boolean indicating whether sharing was successful.
     */
    public boolean shareJourney(Journey j) {
        return true;
    }

}
