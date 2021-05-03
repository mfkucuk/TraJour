package com.trajour.user;

/**
 * Friend class that has the properties of friend
 *
 * @author Selim Can Güler
 * @version 03 May 2021
 */
public class Friend {

    //Properties
    private String friendName;
    private String friendEmail;
    private int friendUserId;

    //Constructor
    public Friend(String friendName, String friendEmail, int friendUserId) {
        this.friendEmail = friendEmail;
        this.friendName = friendName;
        this.friendUserId = friendUserId;
    }

    /**
     * Getter friend for friendName
     *
     * @return friendname
     */
    public String getFriendName() {
        return friendName;
    }

    /**
     * Getter method for friendUserId
     *
     * @return friendEmail
     */
    public String getFriendEmail() {
        return friendEmail;
    }

    /**
     * Getter method for friendUserID
     *
     * @return friendUserId
     */
    public int getFriendUserId() {
        return friendUserId;
    }

    @Override
    public String toString() {
        return getFriendName() + " - " + getFriendEmail();
    }
}
