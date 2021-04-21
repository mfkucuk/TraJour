import java.awt.Image;
import java.util.ArrayList;

/**
 * User class.
 * @author Mehmet Feyyaz Kucuk
 */
public class User {
    
    // Properties
    String username;
    String email;
    Image profilePhoto;
    String password;
    ArrayList<User> friends;
    JourneyContainer<Journey> journeys;
    ArrayList<Post> posts;

    // Methods
    /**
     * Adds a user to the friends list.
     * @param username is username of the user to be added.
     * @return is the object with that username.
     */
    public User addFriend(String username) {
        return null;
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
     * @result is the boolean indicating whether sharing was successful.
     */
    public boolean shareJourney(Journey j) {
        return true;
    }

    /**
     * Searches the database and returns the user with matching username.
     * @param username is the username.
     * @return is the user found by the database.
     */
    public User findUserbyUsername(String username) {
        return null;
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
     * Returns password. 
     * @return is the password.
     */
    public String getPassword() {
        return password;
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
     * Sets the password.
     * @param password is the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Displays all the users in the friends list.
     */
    public void displayFriends() {
        for (User u : friends) {
            // Do something here
        }
    }

    /**
     * Sets the profile photo.
     * @param profilePhoto
     */
    public void setProfilePhoto(Image profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
