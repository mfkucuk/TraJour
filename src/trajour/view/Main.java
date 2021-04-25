package trajour.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import trajour.db.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {
    protected final static int APPLICATION_WIDTH = 1400;
    protected final static int APPLICATION_HEIGHT = 1000;
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
        // TODO Create tables: friends

        try {
            // Create users table
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users(userId INTEGER NOT NULL UNIQUE AUTO_INCREMENT, " +
                    "username VARCHAR(20) NOT NULL UNIQUE, email VARCHAR(30) NOT NULL UNIQUE, password VARCHAR(20) NOT NULL, " +
                    "profile_photo BLOB, PRIMARY KEY(userId))");

            // Create journeys
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `trajour`.`journeys`" +  "( \t`journeyId` INT NOT NULL AUTO_INCREMENT," +
                    "\t`userId` INT NOT NULL,   `location` VARCHAR(57) NOT NULL,\t`description` TEXT(250) " +
                    "NOT NULL, `startDate` DATE NOT NULL, \t`endDate` DATE NOT NULL, FOREIGN KEY (userId) " +
                    "REFERENCES users(userId), PRIMARY KEY (`journeyId`), UNIQUE INDEX `journeyId_UNIQUE` " +
                    "(`journeyId` ASC) VISIBLE);");

            // Create posts
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `trajour`.`posts` (\n" +
                    "  `postId` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `userId` INT NOT NULL,\n" +
                    "  `post_comments` TEXT(250) NOT NULL,\n" +
                    "  `post_image` BLOB NOT NULL,\n" +
                    "  `post_rating` INT NOT NULL,\n" +
                    "  FOREIGN KEY (userId) REFERENCES users(userId),\n" +
                    "  PRIMARY KEY (`postId`),\n" +
                    "  UNIQUE INDEX `postId_UNIQUE` (`postId` ASC) VISIBLE);\n");
        }
        catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        } finally {
            conn.close();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/trajour/view/fxml/login.fxml"));
        primaryStage.setTitle("TraJour");
        primaryStage.setScene(new Scene(root, APPLICATION_WIDTH, APPLICATION_HEIGHT));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
