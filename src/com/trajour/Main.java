package com.trajour;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.trajour.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
* Launches the application
* @version 03 May 2021
*/
public class Main extends Application {
    public final static int APPLICATION_WIDTH = 1400;
    public final static int APPLICATION_HEIGHT = 1000;
    private final String dbName = "trajour";

    /**
     * Initializes database
     */
    @Override
    public void init() throws SQLException {
        // Create the database if it does not exists
        DatabaseConnection dbConnection = new DatabaseConnection();
        Connection conn = dbConnection.getConnection();

        // Creates the database if it does not exists
        try {
            Statement statement = conn.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/com/trajour/login/login.fxml"));
        primaryStage.setTitle("TraJour");
        primaryStage.setScene(new Scene(root, APPLICATION_WIDTH, APPLICATION_HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
