package com.trajour.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.trajour.db.DatabaseQuery.validateRegistry;

/**
 * Controller for the register process
 * @author Selim Can Güler
 * @version 25 April 2021
 */
public class RegisterController {
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

    @FXML
    private Label registrationFeedbackLabel;

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
            registrationFeedbackLabel.setText("Passwords do not match.");
        } else {
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
                registrationFeedbackLabel.setText("Welcome to TraJour!");
            }
            else if (validateRegistry(username, email, password) == -1) {
                registrationFeedbackLabel.setText("Username is already used. Please try again.");
            }
            else if (validateRegistry(username, email, password) == -2) {
                registrationFeedbackLabel.setText("Email is already used. Please try again.");
            }
            else {
                registrationFeedbackLabel.setText("Registration was unsuccessful for some reason even the programmer couldn't understand!!!");
            }

        } else {
            registrationFeedbackLabel.setText("Please complete all of the text fields.");
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
