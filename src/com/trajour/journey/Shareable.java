package com.trajour.journey;

import com.trajour.model.User;
import javafx.scene.layout.VBox;

public interface Shareable {
    Post share(User u, VBox mainFeed);
}
