import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;

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
        String username;
        String email;
        String password;
        String insertFieldsQuery;
        String insertValuesQuery;
        String registerQuery;
        Statement query;

        DatabaseConnection connection = new DatabaseConnection();
        Connection connectToDB = connection.getConnection();

        username = usernameTextField.getText();
        email = emailTextField.getText();
        password = passwordTextField.getText();

        insertFieldsQuery = "INSERT INTO users(username, email, password) ";
        insertValuesQuery = "VALUES ('" + username + "', '" + email + "', '" + password + "');";
        registerQuery = insertFieldsQuery + insertValuesQuery;

        try {
            query = connectToDB.createStatement();
            query.executeUpdate(registerQuery);

            registrationFeedbackLabel.setText("Welcome to TraJour fellow traveler!");

        } catch (Exception e) {
            e.printStackTrace();
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
