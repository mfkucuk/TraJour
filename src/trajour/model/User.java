package trajour.model;

import java.awt.*;

/**
 * User class.
 * @author Mehmet Feyyaz Kucuk
 */
public class User {
    // Properties
    String username;
    String email;
    Image profilePhoto;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(String username, String email, Image profilePhoto) {
        this(username, email);
        this.profilePhoto = profilePhoto;
    }

    // Methods
    /**
     * Adds a user to the friends list.
     * @param username is username of the user to be added.
     * @return is the object with that username.
     */
    public boolean addFriendByUsername(String username) {
        // TODO Search the database, check whether a user with the given username exists or the user is already a friend
        return false;
    }

    public boolean addFriendByEmail(String email) {
        // TODO Search the database, check whether a user with the given username exists or the user is already a friend
        return false;
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

    /**
     * Sets the username.
     * @param username is the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the email.
     * @param email is the email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Displays all the users in the friends list.
     */
    public void displayFriends() {
        // Do something here
    }

    /**
     * Sets the profile photo.
     * @param profilePhoto Profile photo of the user
     */
    public void setProfilePhoto(Image profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
