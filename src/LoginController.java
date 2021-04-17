import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
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
    
    private WebView browser;
    private WebEngine webEngine;

    /**
     * Handles and validates login switches to the main page if the login is successful
     * @param event Event
     */
    public void login(ActionEvent event) throws IOException {
        // Check if the text fields are empty or not
        if ( ! emailTextField.getText().isBlank() && !  passwordField.getText().isBlank()) {
            if (validateLogin(emailTextField.getText(), passwordField.getText())) {
                // TODO Wait for a few seconds so that the user can understand login is successful, then redirect to the the main page
                openMainPage(event);
            }
        } else {
            loginFeedbackLabel.setText("Please enter your email and password.");
        }
    }

    /**
     * Creates a new register stage
     * @param event Event
     */
    @FXML
    private void openRegisterPage(ActionEvent event) {
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
     * Opens the main page
     * @param event
     * @throws IOException In case the fxml file is missing
     */
    private void openMainPage(ActionEvent event) throws IOException {
        // TODO Need the main page FXML file
        Parent mainPageParent = FXMLLoader.load(getClass().getResource("mainPage.fxml"));
        Scene mainPageScene = new Scene(mainPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(mainPageScene);
        window.show();
    }

    /**
     * Opens the app website
     * @param event Event
     */
    @FXML
    private void openTheWebsite(ActionEvent event) {
        browser = new WebView();
        webEngine = browser.getEngine();
        webEngine.load("../public_html");
    }

    /**
     * Validates the login by connecting to database and checking whether there are matching email and
     * password combinations
     * @param email Email entered by the user
     * @param password Password entered by the user
     * @return True if validation is successful, false if validation is unsuccessful
     */
    private boolean validateLogin(String email, String password) {
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
                    return true;
                } else {
                    loginFeedbackLabel.setText("Invalid email or password. Please try again");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return false;
    }
}
