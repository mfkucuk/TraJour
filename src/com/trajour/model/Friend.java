package com.trajour.model;

public class Friend {
    private String friendName;
    private String friendEmail;

    public Friend(String friendName, String friendEmail) {
        this.friendEmail = friendEmail;
        this.friendName = friendName;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    @Override
    public String toString() {
        return getFriendName() + " - " + getFriendEmail();
    }
}
