package trajour.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import trajour.model.User;

public class AddFriendController {

    private User currentUser;

    public void initData(User user) {
        currentUser = user;
    }

    @FXML
    private TextField friendEmailTextField;

    @FXML
    private TextField friendUsernameTextField;

    @FXML
    private Button addFriendButton;

    @FXML
    public void addFriend(ActionEvent event) {

    }
}
