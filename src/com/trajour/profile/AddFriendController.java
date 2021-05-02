package com.trajour.profile;

import com.trajour.db.DatabaseQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.trajour.user.User;
import javafx.scene.effect.DropShadow;

import java.net.URL;
import java.util.ResourceBundle;

import static com.trajour.db.DatabaseQuery.*;

public class AddFriendController implements Initializable {

    private User currentUser;

    @FXML
    private TextField friendEmailTextField;

    @FXML
    private TextField friendUsernameTextField;

    @FXML
    private Button addFriendButton;

    @FXML
    private Label addFriendFeedbackLabel;

    public void initData(User user) {
        currentUser = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Button effects
        DropShadow shadow = new DropShadow();
        addFriendButton.setOnMouseEntered(mouseEvent -> addFriendButton.setEffect(shadow));
        addFriendButton.setOnMouseExited(mouseEvent -> addFriendButton.setEffect(null));

        friendEmailTextField.setOnAction(event -> addFriend(event));
        friendUsernameTextField.setOnAction(event -> addFriend(event));
    }

    /**
     * Adds friend either by checking the database by username or email. If a user with the specified username or
     * email does not exist, the user is warned. If the user exists but they are already a friend of the current user
     * the user is warned again. If none of these apply, the user is added as a friend of the current user.
     * @param event
     */
    @FXML
    public void addFriend(ActionEvent event) {
        if (friendEmailTextField.getText().isBlank() && friendUsernameTextField.getText().isBlank()) {
            addFriendFeedbackLabel.setText("Fill out one of the text fields.");
        }
        else if ( ! friendEmailTextField.getText().isBlank() && ! friendUsernameTextField.getText().isBlank()) {
            addFriendFeedbackLabel.setText("Use only one of the text fields.");
        }
        else if ( ! friendUsernameTextField.getText().isBlank()) {
            if (DatabaseQuery.findUserByUsername(friendUsernameTextField.getText())) {
                if (friendUsernameTextField.getText().equals(currentUser.getUsername())) {
                    addFriendFeedbackLabel.setText("I'm afraid you cannot be friends with yourself.");
                }
                else if ( ! findFriendByUsername(friendUsernameTextField.getText(), currentUser)) {
                    currentUser.addFriendByName(friendUsernameTextField.getText(), getEmailByUsername(friendUsernameTextField.getText()));
                    addFriendFeedbackLabel.setText("Friend successfully added.");
                }
                else {
                    addFriendFeedbackLabel.setText("You are already friends.");
                }
            }
            else {
                addFriendFeedbackLabel.setText("No such user exists.");
            }
        }
        else if ( ! friendEmailTextField.getText().isBlank()) {
            if (findUserByEmail(friendEmailTextField.getText())) {
                if (friendEmailTextField.getText().equals(currentUser.getEmail())) {
                    addFriendFeedbackLabel.setText("I'm afraid you cannot be friends with yourself.");
                }
                else if ( ! findFriendByEmail(friendEmailTextField.getText(), currentUser)) {
                    currentUser.addFriendByEmail(getUsernameByEmail(friendEmailTextField.getText()), friendEmailTextField.getText());
                    addFriendFeedbackLabel.setText("Friend successfully added.");
                }
                else {
                    addFriendFeedbackLabel.setText("You are already friends.");
                }
            }
            else {
                addFriendFeedbackLabel.setText("No such user exists.");
            }
        }
    }
}
