package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Controller for the login process
 * @author Selim Can Güler
 * @version 16 April 2021
 */
public class LoginController  {
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Hyperlink learnMoreHyperlink;

    @FXML
    private Label loginFeedbackLabel;

    /**
     * Handles and validates login
     * @param event Event
     */
    public void login(ActionEvent event) {
        if ( !emailTextField.getText().isBlank() && !passwordField.getText().isBlank()) {
            validateLogin(emailTextField.getText(), passwordField.getText());
        } else {
            loginFeedbackLabel.setText("Please enter your email and password.");
        }
    }

    /**
     * Creates a new register stage
     * @param event Event
     */
    public void openRegisterPage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load((getClass().getResource("registerPage.fxml")));

            Stage registerStage = new Stage();
            registerStage.setTitle("Register");

            registerStage.setScene(new Scene(root, 700, 600));
            registerStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the app website
     * @param event Event
     */
    public void openTheWebsite(ActionEvent event) {
        // TODO Redirect to TraJour website
    }

    /**
     * Validates the login by connecting to database and checking whether there are matching email and
     * password combinations
     * @param email Email entered by the user
     * @param password Password entered by the user
     */
    public void validateLogin(String email, String password) {
        DatabaseConnection connect;
        Connection connectDB;
        String verifyLoginQuery;

        connect = new DatabaseConnection();
        connectDB = connect.getConnection();

        verifyLoginQuery = "SELECT COUNT(*) FROM users WHERE email = '" + email + "' AND password = '" + password + "';";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLoginQuery);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginFeedbackLabel.setText("Welcome to TraJour!");
                } else {
                    loginFeedbackLabel.setText("Invalid email or password. Please try again");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}