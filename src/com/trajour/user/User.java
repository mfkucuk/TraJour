package com.trajour.user;

import com.trajour.db.DatabaseQuery;
import com.trajour.journey.Wish;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.File;
import java.time.LocalDate;

import static com.trajour.db.DatabaseQuery.*;


/**
 * User class.
 * @author Mehmet Feyyaz Kucuk
 * @author Selim Can Güler
 * @version 03 May 2021
 */
public class User {

    // Properties
    private int userId;
    private String username;
    private String email;
    private Image profilePhoto;

    //Constructor
    public User(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    /**
     * Removes the users requested friend
     *
     * @param friendsToRemove
     */
    public void removeFriend(ObservableList<com.trajour.user.Friend> friendsToRemove) {
        if ( ! friendsToRemove.isEmpty()) {
            for (com.trajour.user.Friend f : friendsToRemove) {
                DatabaseQuery.deleteFriend(f, this);
            }
        }
    }

    /**
     * Adds friend by e-mail from the database
     *
     * @param nameOfFriend
     * @param emailOfFriend
     */
    public void addFriendByEmail(String nameOfFriend, String emailOfFriend) {
        Friend friend = new Friend(nameOfFriend, emailOfFriend, getUserIdByUsername(nameOfFriend));
        insertFriendByEmail(friend, this);
    }

    /**
     * Adds friend by name from the database
     *
     * @param nameOfFriend
     * @param emailOfFriend
     */
    public void addFriendByName(String nameOfFriend, String emailOfFriend) {
        Friend friend = new Friend(nameOfFriend, emailOfFriend, getUserIdByUsername(nameOfFriend));
        insertFriendByUsername(friend, this);
    }

    /**
     * Adds a picture to the database
     *
     * @param img
     * @return true if update is successful
     */
    public boolean addPicture(File img) {
        return DatabaseQuery.updateImage(img, this);
    }

    /**
     * Changes the password of the user
     *
     * @param oldPassword
     * @param newPassword
     * @return false if oldPassword and newPassword is same
     */
    public boolean updatePassword(String oldPassword, String newPassword) {
        if (!findPasswordByUsername(getUsername(), oldPassword)) {
            return false;
        } else {
            DatabaseQuery.updatePassword(getUsername(), newPassword);
            return true;
        }
    }

    /**
     * Adds a wish to the user
     *
     * @param location
     * @param startDate
     * @return true if update is successful
     */
    public boolean addWish(String location, LocalDate startDate) {
        Wish wish = new Wish(location, startDate);
        return DatabaseQuery.insertWishByUser(wish, this);
    }

    /**
     *
     * @param wishlist
     * @return true if update is successful
     */
    public boolean removeWish(ObservableList<Wish> wishlist) {
        if (! wishlist.isEmpty()) {
            for (Wish w : wishlist) {
                DatabaseQuery.deleteWishByUser(w, this);
            }
        }

        return true;
    }

    /**
     * Returns username.
     *
     * @return is the username.  
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Returns email.
     *
     * @return is the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter method for userId
     *
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter method for returning an image
     *
     * @return profilePhoto
     */
    public Image getProfilePhoto() {
        return profilePhoto;
    }
}
