package trajour.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static trajour.db.DatabaseQuery.validateRegistry;

/**
 * Controller for the register process
 * @author Selim Can Güler
 * @version 16 April 2021
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
        if ( ! usernameTextField.getText().isBlank() && ! emailTextField.getText().isBlank() && ! passwordTextField.getText().isBlank()) {
            validateRegistry(usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText());
            registrationFeedbackLabel.setText("Welcome to TraJour!");
        } else {
            registrationFeedbackLabel.setText("Please complete all of the text fields.");
        }
    }

    private String encryptPassword(String password) {
        // TODO A simple encryption of the registered passwords. Encrypted version will be saved in the DB.
        return "";
    }

    private String decyrptPassword(String password) {
        // TODO Decyrption of the password. This may be the wrong place for the method.
        return "";
    }
}
