package com.trajour.journey;

import com.trajour.model.User;
import javafx.scene.layout.VBox;

public interface Shareable {
    boolean share(User u, VBox mainFeed);
}
