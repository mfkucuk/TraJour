package trajour.view;

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

import java.awt.*;
import java.io.IOException;
import java.net.URI;

import static trajour.db.DatabaseQuery.validateLogin;

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

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    /**
     * Handles and validates login switches to the main page if the login is successful
     * @param event Event
     */
    public void login(ActionEvent event)  {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        // Check if the text fields are empty or not
        if ( ! email.isBlank() && !  password.isBlank()) {
            if (validateLogin(email, password)) {
                // TODO Wait for a few seconds so that the user can understand login is successful, then redirect to the the main page
                openMainPage(event);
            } else {
                loginFeedbackLabel.setText("Incorrect email or password.");
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
            Parent root = FXMLLoader.load((getClass().getResource("/trajour/view/registerPage.fxml")));

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
     */
    private void openMainPage(ActionEvent event)  {
        try {
            Parent mainPageParent = FXMLLoader.load(getClass().getResource("/trajour/view/mainPage.fxml"));
            Scene mainPageScene = new Scene(mainPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(mainPageScene);
            window.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the app website
     * @param event Event
     */
    @FXML
    private void openTheWebsite(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("http://a.yilmazyildiz.ug.bilkent.edu.tr/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
