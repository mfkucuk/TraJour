package com.trajour.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

import static com.trajour.db.DatabaseQuery.validateRegistry;
import static com.trajour.view.MainController.buildNotification;

/**
 * Controller for the register process
 * @author Selim Can Güler
 * @version 28 April 2021
 */
public class RegisterController implements Initializable {
    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button registerButton;

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle){
        //Event listeners for buttons
        registerButton.setOnMouseEntered( mouseEvent -> registerButton.setTextFill(Color.BLACK));
        registerButton.setOnMouseExited( mouseEvent -> registerButton.setTextFill(Color.WHITE));

        cancelButton.setOnMouseEntered( mouseEvent -> cancelButton.setTextFill(Color.BLACK));
        cancelButton.setOnMouseExited( mouseEvent -> cancelButton.setTextFill(Color.WHITE));
    }
    /**
     * Closes the register window
     * @param event Event
     */
    public void cancelButtonOnAction(ActionEvent event) {
        Stage currentStage = (Stage) cancelButton.getScene().getWindow();
        currentStage.close();
    }

    /**
     * Registers the user
     * @param event Event
     */
    public void registerButtonOnAction(ActionEvent event) {
        if ( ! passwordTextField.getText().equals(confirmPasswordField.getText())) {
            Notifications notification = buildNotification("Registration Unsuccessful!", "Passwords do not match.", 5, Pos.BOTTOM_CENTER);
            notification.showWarning();
        }
        else {
            register();
        }
    }

    /**
     * Handles register process by connecting to database
     */
    public void register() {
        String username = usernameTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        if ( ! username.isBlank() && ! email.isBlank() && ! password.isBlank()) {
            if (validateRegistry(username, email, password) == 1) {
                Notifications notification = buildNotification("Registration Successful!", "Welcome to TraJour, fellow traveler!", 5, Pos.BOTTOM_CENTER);
                notification.showConfirm();
            }
            else if (validateRegistry(username, email, password) == -1) {
                Notifications notification = buildNotification("Registration Unsuccessful!", "Username is already used. Please try again.", 5, Pos.BOTTOM_CENTER);
                notification.showWarning();
            }
            else if (validateRegistry(username, email, password) == -2) {
                Notifications notification = buildNotification("Registration Unsuccessful!", "Email is already used. Please try again.", 5, Pos.BOTTOM_CENTER);
                notification.showWarning();
            }
        }
        else {
            Notifications notification = buildNotification("Registration Unsuccessful!", "Please complete all of the text fields.", 5, Pos.BOTTOM_CENTER);
            notification.showWarning();
        }
    }

    private String encryptPassword(String password) {
        // TODO A simple encryption of the registered passwords. Encrypted version will be saved in the DB.
        return "";
    }

    private String decryptPassword(String password) {
        // TODO Decryption of the password. This may be the wrong place for the method.
        return "";
    }
}
