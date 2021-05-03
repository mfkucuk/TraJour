package com.trajour.user;

/**
 * @author Selim Can Güler
 * @version 03 May 2021
 */
public class Friend {
    private String friendName;
    private String friendEmail;
    private int friendUserId;

    public Friend(String friendName, String friendEmail, int friendUserId) {
        this.friendEmail = friendEmail;
        this.friendName = friendName;
        this.friendUserId = friendUserId;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public int getFriendUserId() {
        return friendUserId;
    }

    @Override
    public String toString() {
        return getFriendName() + " - " + getFriendEmail();
    }
}
