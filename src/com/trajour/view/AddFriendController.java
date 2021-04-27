package com.trajour.view;

import com.trajour.db.DatabaseQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.trajour.model.User;

public class AddFriendController {

    private User currentUser;
    private TreeTableView friendsTreeTableView;

    @FXML
    private TextField friendEmailTextField;

    @FXML
    private TextField friendUsernameTextField;

    @FXML
    private Button addFriendButton;

    @FXML
    private Label addFriendFeedbackLabel;

    public void initData(User user) {
        currentUser = user;;
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
        else if ( ! friendUsernameTextField.getText().isBlank()) {
            if (DatabaseQuery.findUserByUsername(friendUsernameTextField.getText())) {
                if ( ! DatabaseQuery.findFriendByUsername(friendUsernameTextField.getText())) {
                    DatabaseQuery.insertFriendByUsername(friendUsernameTextField.getText(), currentUser);
                    addFriendFeedbackLabel.setText("Friend successfully added.");

                }
                else {
                    addFriendFeedbackLabel.setText("You are already friends.");
                }
            }
        }
        else if ( ! friendEmailTextField.getText().isBlank()) {
            if (DatabaseQuery.findUserByEmail(friendEmailTextField.getText())) {
                if ( ! DatabaseQuery.findFriendByEmail(friendEmailTextField.getText())) {
                    DatabaseQuery.insertFriendByEmail(friendEmailTextField.getText(), currentUser.getUsername());
                    addFriendFeedbackLabel.setText("Friend successfully added.");
                }
            }
        }
        else {
            addFriendFeedbackLabel.setText("Use only one of the text fields.");
        }
    }
}
