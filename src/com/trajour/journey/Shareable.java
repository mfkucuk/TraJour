package com.trajour.journey;

import com.trajour.user.Friend;
import com.trajour.user.User;
import javafx.scene.layout.VBox;

/**
 * Interface Shareable
 *
 * @author Ahmet Alperen Yılmazyıldız
 * @version 1.05.2021
 */
public interface Shareable {

    Post share(User u, VBox mainFeed);
    Post share(Friend f, VBox mainfeed);
}
