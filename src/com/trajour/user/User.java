package com.trajour.user;

import com.trajour.db.DatabaseQuery;
import com.trajour.journey.Journey;
import com.trajour.journey.Wish;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.File;
import java.time.LocalDate;

import static com.trajour.db.DatabaseQuery.*;

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

    public void addFriendByEmail(String nameOfFriend, String emailOfFriend) {
        Friend friend = new Friend(nameOfFriend, emailOfFriend);
        insertFriendByEmail(friend, this);
    }

    public void addFriendByName(String nameOfFriend, String emailOfFriend) {
        Friend friend = new Friend(nameOfFriend, emailOfFriend);
        insertFriendByUsername(friend, this);
    }

    public boolean addPicture(File img) {
        return DatabaseQuery.updateImage(img, this);
    }

    public boolean updatePassword(String oldPassword, String newPassword) {
        if (!findPasswordByUsername(getUsername(), oldPassword)) {
            return false;
        } else {
            DatabaseQuery.updatePassword(getUsername(), newPassword);
            return true;
        }
    }

    public boolean addWish(String location, LocalDate startDate) {
        Wish wish = new Wish(location, startDate);
        return DatabaseQuery.insertWishByUser(wish, this);
    }

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
