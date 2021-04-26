package trajour.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import trajour.model.User;

public class AddJourneyController {

    @FXML
    private Button removeJourneyButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addJourneyButton;

    private User currentUser;

    public void initData(User user) {
        currentUser = user;
    }
    public void addJourney(ActionEvent actionEvent) {
        // TODO Create journey and add it to the database
    }

    public void remove(ActionEvent actionEvent) {
        // TODO Remove the last added journey from the database
    }
}
