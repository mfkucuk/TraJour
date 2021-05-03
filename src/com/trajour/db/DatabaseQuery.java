package com.trajour.db;

import com.trajour.journey.Wish;
import com.trajour.user.Friend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.trajour.journey.Journey;
import com.trajour.journey.Post;
import com.trajour.user.User;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;

/**
 * This class holds all the database queries that are used throughout the application. The queries include getting
 * information related to journeys, posts, wishes, users, friends of users; checking if a specific journey, post, user,
 * friend or wish already exists; deleting friends, wishes, journeys; updating the ratings of journeys, profile photos,
 * updating post images, passwords; inserting new posts, journeys, wishes, friends.
 * @author Selim Can Güler
 * @author Mehmet Feyyaz Küçük
 * @version 03 May 2021
 */
public final class DatabaseQuery {
    private static DatabaseConnection dbConnection;
    private static Connection conn;

    public static ObservableList<Wish> getAllWishesOfUser(User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        ObservableList<Wish> result = FXCollections.observableArrayList();

        String query = "SELECT * FROM wishlist WHERE userId = " + user.getUserId();

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String location = rs.getString("location");
                LocalDate startDate = rs.getDate("startDate").toLocalDate();

                Wish wish = new Wish(location, startDate);
                result.add(wish);
            }

            return result;
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return result;
    }

    public static ObservableList<Post> getAllPostsOfUser(User currentUser) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        ObservableList<Post> result = FXCollections.observableArrayList();

        String query = "SELECT * FROM posts WHERE userId = " + currentUser.getUserId();

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String postTitle = rs.getString("post_title");
                String postLocation = rs.getString("post_location");
                LocalDate startDate = rs.getDate("post_start_date").toLocalDate();
                LocalDate endDate = rs.getDate("post_end_date").toLocalDate();
                String postComments = rs.getString("post_comments");
                File image = getPostPhoto(currentUser.getUserId(), postTitle);
                Image realImage = new Image(image.toURI().toString(), 90, 90, false, false);

                Journey postJourney = new Journey(postLocation, postTitle, postComments, startDate, endDate);
                Post newPost = new Post(postJourney, postComments, realImage);

                result.add(newPost);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return result;
    }
    public static ObservableList<Post> getAllPostsOfFriend(Friend friend) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        ObservableList<Post> result = FXCollections.observableArrayList();

        String query = "SELECT * FROM posts WHERE userId = " + friend.getFriendUserId();

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String postTitle = rs.getString("post_title");
                String postLocation = rs.getString("post_location");
                LocalDate startDate = rs.getDate("post_start_date").toLocalDate();
                LocalDate endDate = rs.getDate("post_end_date").toLocalDate();
                String postComments = rs.getString("post_comments");
                File image = getPostPhoto(friend.getFriendUserId(), postTitle);
                Image realImage = new Image(image.toURI().toString(), 90, 90, false, false);

                Journey postJourney = new Journey(postLocation, postTitle, postComments, startDate, endDate);
                Post newPost = new Post(postJourney, postComments, realImage);

                result.add(newPost);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return result;
    }

    public static ObservableList<Journey> getAllJourneysOfUser(User currentUser) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        ObservableList<Journey> result = FXCollections.observableArrayList();

        String query = "SELECT userId, title, location, description, startDate, endDate FROM journeys WHERE userId = " + currentUser.getUserId();

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String country = rs.getString("location");
                String title = rs.getString("title");
                String description = rs.getString("description");
                LocalDate startDate = rs.getDate("startDate").toLocalDate();
                LocalDate endDate = rs.getDate("endDate").toLocalDate();

                Journey j = new Journey(country, title, description, startDate, endDate);
                result.add(j);
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return result;
    }


    public static ObservableList<Friend> getAllFriendsOfUser(User currentUser) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        ObservableList<Friend> result = FXCollections.observableArrayList();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT friendName, friendEmail FROM friends WHERE userID = " + currentUser.getUserId();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("friendName");
                String email = rs.getString("friendEmail");

                Friend f = new Friend(name, email, getUserIdByUsername(name));
                result.add(f);
            }

            return result;
        } catch (SQLException e) {
            e.getCause();
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Gets the username of a user by their email
     * @param email Email to search
     * @return The username of the user with the specified email
     */
    public static String getUsernameByEmail(String email) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        try {
            Statement statement = conn.createStatement();
            String query = "SELECT username FROM users WHERE email = '" + email + "'";
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Gets the email of a user by their username
     * @param username Username to search
     * @return The email of the user with the specified username
     */
    public static String getEmailByUsername(String username) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        try {
            Statement statement = conn.createStatement();
            String query = "SELECT email FROM users WHERE username = '" + username + "'";
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Gets the userId of the user by checking their username.
     * @param username Username of the user.
     * @return The userId of the user with the specified username. Returns -1 if no user with the specified username exists.
     */
    public static int getUserIdByUsername(String username) {
        try {
            if (! findUserByUsername(username)) {
                return -1;
            }

            dbConnection = new DatabaseConnection();
            conn = dbConnection.getConnection();

            Statement statement = conn.createStatement();
            String query = "SELECT userId FROM users WHERE username = '" + username + "'";
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return -1;
    }

    public static File getPostPhoto(int userId, String postTitle) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        InputStream input;
        FileOutputStream output;
        String query = "SELECT post_image FROM posts where userId = " + userId + " AND post_title = '" + postTitle + "' AND post_image IS NOT NULL";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            File newFile = new File("src/resources/post_photo_" + postTitle + ".png");
            output = new FileOutputStream(newFile);

            if (rs.next()) {
                input = rs.getBinaryStream("post_image");

                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
            }

            return newFile;
        }
        catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            e.getCause();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getJourneyRating(Journey j, User currentUser) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT rating FROM journeys WHERE userId = " + currentUser.getUserId() + " AND title = '" + j.getTitle()
                + "' AND location = '" + j.getLocation() + "' AND description = '" + j.getDescription() + "' AND startDate = '" +
                Date.valueOf(j.getStartDate()) + "' AND endDate = '" + Date.valueOf(j.getEndDate()) + "'";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getString("rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return "-";
    }

    public static File getProfilePhotoFile(int userId) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        InputStream input;
        FileOutputStream output;
        String query = "SELECT profile_photo FROM users where userId = " + userId + " AND profile_photo IS NOT NULL";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            File newFile = new File("src/resources/profile_photo_" + userId + ".png");
            output = new FileOutputStream(newFile);

            if (rs.next()) {
                input = rs.getBinaryStream("profile_photo");

                byte[] buffer = new byte[1024];
                while (input.read(buffer) > 0) {
                    output.write(buffer);
                }
            }

            return newFile;
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
            e.getCause();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Validates the login attempt by checking the entered email and password in the database.
     * @param email Entered email
     * @param password Entered password
     * @return True if login is successful, false otherwise
     */
    public static boolean validateLogin(String email, String password) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String verifyLoginQuery = "SELECT COUNT(*) FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
        try {
            Statement statement = conn.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLoginQuery);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     *
     * @param username Entered username by the user
     * @param email Entered email by the user
     * @param password Entered password by the user
     * @return 1 if the process is successful, 0 if the process was unsuccessful for some reason (?), -1 if a user with
     * the entered username already exists, -2 if a user with the entered email already exists.
     */
    public static int validateRegistry(String username, String email, String password) {

        if (findUserByUsername(username)) {
            return -1;
        }
        if (findUserByEmail(email)) {
            return -2;
        }

        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String insertFieldsQuery = "INSERT INTO users(username, email, password) ";
        String insertValuesQuery = "VALUES ('" + username + "', '" + email + "', '" + password + "');";
        String registerQuery = insertFieldsQuery + insertValuesQuery;

        try {
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(registerQuery);
            if (result >= 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Finds a user by checking a certain username in the database. Since usernames are unique a user can be identified
     * by their username
     * @param username Username to search for
     * @return True if user exists, false if the user does not exist
     */
    public static boolean findUserByUsername(String username) {
        try {
            // Set the connection
            dbConnection = new DatabaseConnection();
            conn = dbConnection.getConnection();

            String query = "SELECT COUNT(*) FROM users WHERE username = '" + username + "'";

            //Execute the query
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Finds a user by checking a certain email in the database. Since emails are unique a user can be identified
     * by their email
     * @param email Email to search for
     * @return True if the user is found, false if the user wasn't found
     */
    public static boolean findUserByEmail(String email) {
        try {
            // Set the connection
            dbConnection = new DatabaseConnection();
            conn = dbConnection.getConnection();

            // Execute the query
            Statement statement = conn.createStatement();
            String query = "SELECT COUNT(*) FROM users where email = '" + email + "'";
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1; // Returns true if there is only one user
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Compares the password parameter with the actual password of the user that is stored in the database.
     * @param username Username of the user
     * @param password Password of the user
     * @return True if the passwords match, false otherwise
     */
    public static boolean findPasswordByUsername(String username, String password) {
        try {
            // Set the connection
            dbConnection = new DatabaseConnection();
            conn = dbConnection.getConnection();

            String query = "SELECT password FROM users where username = '" + username + "'";

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getString(1).equals(password); // Returns true if the passwords match
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Finds a friend of the current user using the name of the friend
     * @param friendName Name of the searched friend
     * @param user Current user
     * @return True if a friend with the given username exists, false otherwise
     */
    public static boolean findFriendByUsername(String friendName, User user) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT COUNT(*) friendName FROM friends WHERE userId = " + user.getUserId() + " AND friendName = '" + friendName + "'";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1; // Returns true if there is a friend with the given username
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Searches the database by email to find a friend of the current user.
     * @param email Email of the searched friend
     * @return True if the other user is a friend of the current user, false otherwise
     */
    public static boolean findFriendByEmail(String email, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT COUNT(*) friendName FROM friends WHERE userId = " + user.getUserId() + " AND friendName = '" + email + "'";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1; // Returns true if there is a friend with the given email
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Finds a journey of the current user with the exact same specifications of the given journey parameter.
     * @param journey Journey to look for
     * @param user Current user
     * @return True if the journey is found, false otherwise
     */
    public static boolean findJourneyByUser(Journey journey, User user) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT COUNT(*) journeyId FROM journeys WHERE userId = " + user.getUserId() + " AND location = '" +
                journey.getLocation() + "' AND title = '" + journey.getTitle() + "' AND description = '" + journey.getDescription() + "' " +
                "AND startDate = '" + Date.valueOf(journey.getStartDate()) + "' AND endDate = '" + Date.valueOf(journey.getEndDate()) + "'";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1; // Returns true if there is a journey with the exact specifications
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Finds a journey of the current user by checking journey titles.
     * @param title Title of the searched journey
     * @param currentUser Current user
     * @return  True if the searched journey exists, false otherwise
     */
    public static boolean findJourneyByJourneyTitle(String title, User currentUser) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT COUNT(*) title FROM journeys WHERE title = '" + title + "' AND userId = " + currentUser.getUserId();

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) >= 1;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Finds a post of the user checking the post titles. Since post titles for a user is unique it is possible to
     * search posts by their titles while guaranteeing that the journey is really the one that is looked for.
     * @param post Searched post
     * @param user Current user
     * @return True if a journey with the same title exists, false otherwise
     */
    public static boolean findPostByTitle(Post post, User user) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT COUNT(*) FROM posts WHERE post_title = '" + post.getTheJourney().getTitle() + "' AND userId = " + user.getUserId();

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Inserts a new wish created by the user to the database.
     * @param newWish Wish to add to database
     * @param user Current user
     * @return True if the wish is successfully added to the database, false otherwise.
     */
    public static boolean insertWishByUser(Wish newWish, User user) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "INSERT INTO wishlist(location, startDate, userId) VALUES('" + newWish.getLocation() + "', '" +
                Date.valueOf(newWish.getStartDate()) + "', " + user.getUserId() +")";

        try {
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(query);

            return result > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Inserts a new post created by the user to the database.
     * @param user Current user
     * @param post Post to add to database
     * @return True if the post is added to database, false otherwise
     */
    public static boolean insertPost(User user, Post post) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query  = "INSERT INTO posts(userId, post_title, post_location, post_start_date, post_end_date, " +
                "post_comments) VALUES(" + user.getUserId() + ", '" + post.getTheJourney().getTitle() + "', '" +
                post.getTheJourney().getLocation() + "', '" + Date.valueOf(post.getTheJourney().getStartDate()) + "', '"
                + Date.valueOf(post.getTheJourney().getEndDate()) + "', '" + post.getText() + "')";

        try {
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(query);

            return result > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Inserts a new friend by its name for the current user to the database. The friend addition works both ways, meaning
     * that it is enough for one user to add another user as friend for them to be registered as friends without friend
     * requests.
     * @param friend Friend to add
     * @param currentUser Current user
     */
    public static void insertFriendByUsername(Friend friend, User currentUser) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "INSERT INTO friends(friendUserId, userId, friendName, friendEmail) VALUES(" +
                friend.getFriendUserId() + ", " + currentUser.getUserId() + ", "
                + "'" + friend.getFriendName() + "', " + "'" + friend.getFriendEmail() + "')";
        String query2 = "INSERT INTO friends(friendUserId, userId, friendName, friendEmail) VALUES(" +
                currentUser.getUserId() + ", " + friend.getFriendUserId()
                + ", '" + currentUser.getUsername() + "', '" + currentUser.getEmail() + "')";

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Inserts a new friend by its email for the current user to the database. The friend addition works both ways, meaning
     * that it is enough for one user to add another user as friend for them to be registered as friends without friend
     * requests.
     * @param friend Friend to add
     * @param user Current user
     */
    public static void insertFriendByEmail(Friend friend, User user) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "INSERT INTO friends(friendUserId, userId, friendName, friendEmail) VALUES(" +
                friend.getFriendUserId() + ", "  + user.getUserId() + ", "
                + "'" + friend.getFriendName() + "', " + "'" + friend.getFriendEmail() + "')";
        String query2 = "INSERT INTO friends(friendUserId, userId, friendName, friendEmail) VALUES(" +  user.getUserId()
                +  friend.getFriendUserId() + ", '" + user.getUsername() + "', '" + user.getEmail() + "')";

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            statement.executeUpdate(query2);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Inserts a new journey for the current user to the database.
     * @param journey Journey to add
     * @param user Current user
     * @return True if the insertion is successful, false otherwise
     */
    public static boolean insertNewJourney(Journey journey, User user) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        int userId = user.getUserId();
        String location = journey.getLocation();
        String title = journey.getTitle();
        String description = journey.getDescription();
        LocalDate startDate = journey.getStartDate();
        LocalDate endDate = journey.getEndDate();

        String query = "INSERT INTO journeys(userId, title, location, description, startDate, endDate) VALUES('" + userId + "', '" + title +
                "', '" + location + "', '" + description + "', '" + Date.valueOf(startDate) + "', '" + Date.valueOf(endDate) + "')";

        try {
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(query);
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Updates the specified journey rating with the given rating.
     * @param j Journey to update
     * @param user Current user
     * @param rating New rating of the journey
     * @return True if the rating is successful, false otherwise
     */
    public static boolean updateJourneyRating(Journey j, User user, String rating) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "UPDATE journeys SET rating = '" + rating + "' WHERE userId = " + user.getUserId() +
                " AND title = '" + j.getTitle() + "' AND location = '" + j.getLocation() + "' AND description = '"
                + j.getDescription() + "' AND startDate = '" + Date.valueOf(j.getStartDate()) + "' AND endDate = '" +
                Date.valueOf(j.getEndDate()) + "'";
        try {
            Statement statement = conn.createStatement();
            return statement.executeUpdate(query) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Updates the image of a post by streaming the source image and saving the binary stream in the database
     * @param img Image that will be updated
     * @param user Current user
     * @param postTitle The post that will be updated
     * @return True if the post image is updated successfully, false otherwise
     */
    public static boolean updateImageOfPost(File img, User user, String postTitle) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();
        String query = "UPDATE posts SET post_image=? WHERE userId = " + user.getUserId() + " AND post_title = '" + postTitle + "'";
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            File theFile = new File(img.getAbsolutePath());
            FileInputStream inputStream = new FileInputStream(theFile);

            ps.setBinaryStream(1, inputStream);

            int result = ps.executeUpdate();
            return result > 0;
        }
        catch (SQLException | FileNotFoundException e) {
            e.getCause();
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Updates the profile image of the current user by streaming the source image and saving the binary stream in the
     * database.
     * @param img New profile image of the current user
     * @param user Current user
     * @return True if the update is successful, false otherwise
     */
    public static boolean updateImage(File img, User user) {
        // Set the connection
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        if (img.length() > 4194304) {
            return false;
        }

        // File too large
        String query = "UPDATE users SET profile_photo=? WHERE userId = " + user.getUserId();
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            File theFile = new File(img.getAbsolutePath());
            FileInputStream inputStream = new FileInputStream(theFile);

            ps.setBinaryStream(1, inputStream);

            int result = ps.executeUpdate();
            return result > 0;
        }
        catch (SQLException | FileNotFoundException e) {
            e.getCause();
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Updates the password of the user with the given username.
     * @param username      Username of the user
     * @param newPassword   New password of the user
     */
    public static void updatePassword(String username, String newPassword) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "UPDATE users SET password = '" + newPassword + "' WHERE username = '" + username + "'";

        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    /**
     * Deletes the given journey from the database.
     * @param journey Journey that will be deleted from the database
     * @param user Current user
     * @return True if the deletion is successful, false otherwise
     */
    public static boolean deleteJourney(Journey journey, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "DELETE FROM journeys WHERE userId = " + user.getUserId() + " AND title = '" + journey.getTitle() +
                "' AND location = '" + journey.getLocation() + "' AND description = '" + journey.getDescription() +
                "' AND startDate = '" + Date.valueOf(journey.getStartDate()) + "' AND endDate = '" + Date.valueOf(journey.getEndDate()) + "'";

        try {
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(query);

            return result > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Deletes the given friend of the current user from the database.
     * @param friend Friend that will be deleted
     * @param user Current user
     * @return True if the deletion is successful, false otherwise
     */
    public static boolean deleteFriend(Friend friend, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "DELETE FROM friends WHERE userId = " + user.getUserId() + " AND friendName = '"
                + friend.getFriendName() + "' AND friendEmail = '" + friend.getFriendEmail() + "' AND friendUserId = " + friend.getFriendUserId();

        try {
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(query);

            return result > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     * Deletes the given wish of the current user from the database.
     * @param wish Wish that will be deleted
     * @param user Current user
     * @return True if the deletion is successful, false otherwise
     */
    public static boolean deleteWishByUser(Wish wish, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "DELETE FROM wishlist WHERE location = '" + wish.getLocation() + "' AND startDate = '" +
                Date.valueOf(wish.getStartDate()) + "' AND userId = " + user.getUserId();

        try {
            Statement statement = conn.createStatement();
            int result = statement.executeUpdate(query);

            return result > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }
}
