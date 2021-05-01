package com.trajour.db;

import com.trajour.journey.Wish;
import com.trajour.model.Friend;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.trajour.journey.Journey;
import com.trajour.journey.Post;
import com.trajour.model.User;

import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;

/**
 * Helper class for database queries
 * @author Selim Can Güler
 * @version 25 April 2021
 */
public final class DatabaseQuery {
    private static DatabaseConnection dbConnection;
    private static Connection conn;

    public static ObservableList<Journey> getAllJourneysOfUser(User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        ObservableList<Journey> result = FXCollections.observableArrayList();

        String query = "SELECT userId, title, location, description, startDate, endDate FROM journeys WHERE userId = " + user.getUserId();

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

    public static ObservableList<Friend> getAllFriendsOfUser(User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        ObservableList<Friend> result = FXCollections.observableArrayList();
        try {
            Statement statement = conn.createStatement();
            String query = "SELECT friendName, friendEmail FROM friends WHERE userID = " + user.getUserId();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("friendName");
                String email = rs.getString("friendEmail");

                Friend f = new Friend(name, email);
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

    public static String getJourneyRating(Journey j, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT rating FROM journeys WHERE userId = " + user.getUserId() + " AND title = '" + j.getTitle()
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

    public static File getProfilePhotoFile(User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        InputStream input;
        FileOutputStream output;
        String query = "SELECT profile_photo FROM users where userId = " + user.getUserId() + " AND profile_photo IS NOT NULL";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            File newFile = new File("src/resources/profile_photo_" + user.getUserId() + ".png");
            output = new FileOutputStream(newFile);

            if (rs.next()) {
                input = rs.getBinaryStream("profile_photo");

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

    public static boolean updateJourneyRating(Journey j, User user, String rating) {
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
     * Finds a user by checking a certain username in the database.
     * @param username Username to search
     * @return True if user exists, false if the user does not exist
     */
    public static boolean findUserByUsername(String username) {
        try {
            dbConnection = new DatabaseConnection();
            conn = dbConnection.getConnection();

            Statement statement = conn.createStatement();
            String query = "SELECT COUNT(*) FROM users WHERE username = '" + username + "'";
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
     * Finds a user by checking a certain email in the database.
     * @param email Email to search
     * @return True if the user is found, false if the user wasn't found
     */
    public static boolean findUserByEmail(String email) {
        try {
            dbConnection = new DatabaseConnection();
            conn = dbConnection.getConnection();

            Statement statement = conn.createStatement();
            String query = "SELECT COUNT(*) FROM users where email = '" + email + "'";
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }


    /**
     * Compares the password parameter with the actual password stored in the database that is specific to user.
     * @param username Username of the user
     * @param password Password of the user
     * @return True if the passwords match, false otherwise
     */
    public static boolean findPasswordByUsername(String username, String password) {
        try {
            dbConnection = new DatabaseConnection();
            conn = dbConnection.getConnection();

            Statement statement = conn.createStatement();
            String query = "SELECT password FROM users where username = '" + username + "'";
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getString(1).equals(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    /**
     *
     * @param friendName Name of the searched friend
     * @return True if a friend with the given username exists, false otherwise
     */
    public static boolean findFriendByUsername(String friendName, User u) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT COUNT(*) friendName FROM friends WHERE userId = " + u.getUserId() + " AND friendName = '" + friendName + "'";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1;
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
    public static boolean findFriendByEmail(String email, User u) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT COUNT(*) friendName FROM friends WHERE userId = " + u.getUserId() + " AND friendName = '" + email + "'";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    public static boolean findJourneyByUser(Journey j, User u) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "SELECT COUNT(*) journeyId FROM journeys WHERE userId = " + u.getUserId() + " AND location = '" +
                j.getLocation() + "' AND title = '" + j.getTitle() + "' AND description = '" + j.getDescription() + "' " +
                "AND startDate = '" + Date.valueOf(j.getStartDate()) + "' AND endDate = '" + Date.valueOf(j.getEndDate()) + "'";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1) == 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    public static boolean insertWishByUser(Wish newWish, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "INSERT INTO wishlist(location, startDate, userId) VALUES('" + newWish.getLocation() + "', " +
                Date.valueOf(newWish.getStartDate()) + ", " + user.getUserId() +")";

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



    public static void insertFriendByUsername(Friend friend, User currentUser) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "INSERT INTO friends(userId, friendName, friendEmail) VALUES(" + currentUser.getUserId() + ", "
                + "'" + friend.getFriendName() + "', " + "'" + friend.getFriendEmail() + "')";
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public static void insertFriendByEmail(Friend friend, String username) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "INSERT INTO friends(userId, friendName, friendEmail) VALUES(" + getUserIdByUsername(username) + ", "
                + "'" + friend.getFriendEmail() + "', " + "'" + friend.getFriendName() + "')";
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public static void insertPost(Post post, User user) {
        // TODO
//        dbConnection = new DatabaseConnection();
//        conn = dbConnection.getConnection();
//
//        String imageQuery = "UPDATE posts SET post_image=? WHERE "
//        String query = "INSERT INTO posts(userId, postComments, postRating) VALUES(" + user.getUserId() + ", '"
//                + post.getText() + "', " +  post.getRating();

    }

    public static boolean updateImage(File img, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        // File too large
        if (img.length() > 5000000)
            return false;

        String query = "UPDATE users SET profile_photo=? WHERE userId = " + user.getUserId();
        try {
            PreparedStatement ps = conn.prepareStatement(query);

            File theFile = new File(img.getAbsolutePath());
            FileInputStream inputStream = new FileInputStream(theFile);

            ps.setBinaryStream(1, inputStream);

            ps.executeUpdate();
            return true;
        } catch (SQLException | FileNotFoundException e) {
            e.getCause();
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Updates the password of the user with the given username
     * @param username      Username of the user
     * @param newPassword   New password of the user
     */
    public static void updatePassword(String username, String newPassword) {
        try {
            dbConnection = new DatabaseConnection();
            conn = dbConnection.getConnection();

            Statement statement = conn.createStatement();
            String query = "UPDATE users SET password = '" + newPassword + "' WHERE username = '" + username + "'";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            e.getCause();
        }
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

    public static boolean insertNewJourney(Journey j, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        int userId = user.getUserId();
        String location = j.getLocation();
        String title = j.getTitle();
        String description = j.getDescription();
        LocalDate startDate = j.getStartDate();
        LocalDate endDate = j.getEndDate();

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

    public static boolean deleteJourney(Journey j, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "DELETE FROM journeys WHERE userId = " + user.getUserId() + " AND title = '" + j.getTitle() +
                "' AND location = '" + j.getLocation() + "' AND description = '" + j.getDescription() +
                "' AND startDate = '" + Date.valueOf(j.getStartDate()) + "' AND endDate = '" + Date.valueOf(j.getEndDate()) + "'";

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

    public static boolean removeFriend(Friend f, User user) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String query = "DELETE FROM friends WHERE userId = " + user.getUserId() + " AND friendName = '"
                + f.getFriendName() + "' AND friendEmail = '" + f.getFriendEmail() + "'";

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
}
