package com.trajour.login;

import com.trajour.Main;
import com.trajour.main.MainController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import com.trajour.user.User;
import org.controlsfx.control.Notifications;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

import static com.trajour.db.DatabaseQuery.*;
import static com.trajour.main.MainController.buildNotification;

/**
 * Controller for the login process
 * @author Selim Can Güler
 * @author Mehmet Feyyaz Küçük
 * @version 03 May 2021
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Label loginFeedbackLabel;

    @Override
    /**
     * Initializes the login page.
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSetNodesOnAction();

        // Set the initial focus to email text field.
        Platform.runLater(() -> emailTextField.requestFocus());
    }

    /**
     * Handles and validates login switches to the main page if the login is successful
     * @param event Event
     */
    public void handleLogin(ActionEvent event)  {
        String email = emailTextField.getText();
        String password = passwordField.getText();
        // Check if the text fields are empty or not
        if ( ! email.isBlank() && !  password.isBlank()) {
            // Login is successful
            if (validateLogin(email, password)) {
                String username = getUsernameByEmail(email);
                int userId = getUserIdByUsername(username);

                User currentUser = new User(userId, username, email);

                // Build and show notification
                Notifications notification = buildNotification("Login Successful!", "Welcome to TraJour, " + currentUser.getUsername() + ", you can navigate through the main page, map page and your profile via" +
                        " the menu bar at the top!", 6, Pos.CENTER);
                notification.showConfirm();

                // Redirect to main page
                openMainPage(event, currentUser);
            }
            else {
                loginFeedbackLabel.setText("Incorrect email or password.");
            }
        }
        else {
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
            Parent root = FXMLLoader.load((getClass().getResource("/com/trajour/login/register.fxml")));

            Stage registerStage = new Stage();
            registerStage.setTitle("Register");

            registerStage.setScene(new Scene(root, 800, 600));
            registerStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the main page
     * @param event Event
     */
    private void openMainPage(ActionEvent event, User user)  {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/trajour/main/main.fxml"));
            Parent mainPageParent = loader.load();
            Scene mainPageScene = new Scene(mainPageParent, Main.APPLICATION_WIDTH, Main.APPLICATION_HEIGHT);

            MainController mainWindowController = loader.getController();
            mainWindowController.initData(user);

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

    /**
     * Adds event listeners to text fields and buttons and adds style for buttons.
     */
    private void initSetNodesOnAction() {
        // Button effects
        DropShadow shadow = new DropShadow();

        loginButton.setOnMouseEntered(mouseEvent -> loginButton.setEffect(shadow));
        loginButton.setOnMouseExited(mouseEvent -> loginButton.setEffect(null));

        registerButton.setOnMouseEntered(mouseEvent -> registerButton.setEffect(shadow));
        registerButton.setOnMouseExited(mouseEvent -> registerButton.setEffect(null));

        // Event listeners for text fields and buttons
        passwordField.setOnAction((ActionEvent e) -> {
            if (!emailTextField.getText().isBlank() && !passwordField.getText().isBlank()) {
                handleLogin(e);
            }
            else {
                loginFeedbackLabel.setText("Please enter your email and password.");
            }
        });

        emailTextField.setOnAction((ActionEvent e) -> {
            if (!emailTextField.getText().isBlank() && !passwordField.getText().isBlank()) {
                handleLogin(e);
            }
            else {
                loginFeedbackLabel.setText("Please enter your email and password.");
            }
        });
    }
}
