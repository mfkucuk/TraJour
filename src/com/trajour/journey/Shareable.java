package com.trajour.journey;

import com.trajour.user.User;
import javafx.scene.layout.VBox;

public interface Shareable {
    Post share(User u, VBox mainFeed);
}
