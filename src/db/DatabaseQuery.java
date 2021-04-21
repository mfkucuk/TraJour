package db;

import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseQuery {
    private static DatabaseConnection dbConnection;
    private static Connection conn;

    public static int findUserByUsername(String username) {
        try {
            dbConnection = new DatabaseConnection();
            conn = dbConnection.getConnection();

            Statement statement = conn.createStatement();
            String query = "SELECT COUNT(*) FROM users WHERE username = '" + username + "'";
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                if (rs.getInt(1) == 1) {
                    System.out.println("Added friend");
                    return 1;
                }
                else {
                    System.out.println("No such user exists");
                    return 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static boolean validateLogin(String email, String password) {
        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        String verifyLoginQuery = "SELECT COUNT(*) FROM users WHERE email = '" + email + "' AND password = '" + password + "';";
        try {
            Statement statement = conn.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLoginQuery);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }

    public static void validateRegistery(String username, String email, String password) {
        String insertFieldsQuery;
        String insertValuesQuery;
        String registerQuery;
        Statement query;

        dbConnection = new DatabaseConnection();
        conn = dbConnection.getConnection();

        // TODO Check if the username or email already exists
        insertFieldsQuery = "INSERT INTO users(username, email, password) ";
        insertValuesQuery = "VALUES ('" + username + "', '" + email + "', '" + password + "');";
        registerQuery = insertFieldsQuery + insertValuesQuery;

        try {
            query = conn.createStatement();
            query.executeUpdate(registerQuery);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
