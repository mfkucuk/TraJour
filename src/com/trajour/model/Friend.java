package com.trajour.model;

import javafx.scene.control.TreeItem;

public class Friend extends TreeItem {
    private String username;
    private String email;

    public Friend(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
