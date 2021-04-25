package trajour.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import trajour.model.User;

import static trajour.db.DatabaseQuery.findPasswordByUsername;
import static trajour.db.DatabaseQuery.updatePassword;

public class ChangePasswordController {


    @FXML
    private PasswordField oldPasswordTextField;

    @FXML
    private PasswordField newPasswordTextField;

    @FXML
    private PasswordField reEnteredNewPasswordTextField;

    @FXML
    private Button changeButton;

    @FXML
    private Label feedbackLabel;

    private User currentUser;

    public void initData(User user) {
        currentUser = user;
    }

    @FXML
    void changePassword(ActionEvent event) {
        if ( ! oldPasswordTextField.getText().isBlank() && ! newPasswordTextField.getText().isBlank() && ! reEnteredNewPasswordTextField.getText().isBlank()) {
            if (! newPasswordTextField.getText().equals(reEnteredNewPasswordTextField.getText())) {
                feedbackLabel.setText("Your new passwords do not match.");
            }
            else {
                boolean result = findPasswordByUsername(currentUser.getUsername(), oldPasswordTextField.getText());
                if (result) {
                    updatePassword(currentUser.getUsername(), newPasswordTextField.getText());
                    feedbackLabel.setText("Password successfully changed.");
                }
                else {
                    feedbackLabel.setText("Your old password does not match.");
                }
            }
        }
        else {
            feedbackLabel.setText("Please fill in all the text fields");
        }
    }
}
