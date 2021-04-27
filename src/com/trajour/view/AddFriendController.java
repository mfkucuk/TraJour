package com.trajour.view;

import com.trajour.db.DatabaseQuery;
import com.trajour.model.Friend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.trajour.model.User;

import javax.xml.crypto.Data;

public class AddFriendController {

    private User currentUser;


    @FXML
    private TextField friendEmailTextField;

    @FXML
    private TextField friendUsernameTextField;

    @FXML
    private Button addFriendButton;

    @FXML
    private Label addFriendFeedbackLabel;

    @FXML
    private TreeTableView friendsTreeTableView;

    public void initData(User user) {

        currentUser = user;
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
                    TreeItem<Friend> friend = new TreeItem<>(new Friend(friendUsernameTextField.getText(), DatabaseQuery.getEmailByUsername(friendUsernameTextField.getText())));
                    ProfileController.rootItem.getChildren().add(friend);
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
                    TreeItem<Friend> friend = new TreeItem<>(new Friend(DatabaseQuery.getUsernameByEmail(friendEmailTextField.getText()), friendEmailTextField.getText()));
                    ProfileController.rootItem.getChildren().add(friend);
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
