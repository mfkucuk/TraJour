package com.trajour.db;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Creates a connection between the database and the program.
 */
public final class DatabaseConnection {
    public Connection databaseLink;

    /**
     * Creates and returns a connection between the database and the program
     * @return Database connection
     */
    public Connection getConnection() {
        String dbName;
        String dbUser;
        String dbPassword;
        String dbUrl;

        dbName = "trajour";
        dbUser = "root";
        dbPassword = "BilTraJour06";
        dbUrl = "jdbc:mysql://localhost/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Can't load database. Be sure that the database" +
                    " url, database user, and password is correct", "Database Error.", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            e.getCause();
            System.exit(0);
        }

        return databaseLink;
    }
}