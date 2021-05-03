package com.trajour.journey;

import com.trajour.db.DatabaseQuery;
import com.trajour.user.User;

import java.time.LocalDate;

/**
 * A class that has the past journey actions.
 * It extends from Journey class.
 *
 * @author Ahmet Alperen Yılmazyıldız
 * @version 4.05.2021
 */
public class PastJourney extends Journey {

    // Properties
    private String rating;

    // Constructor
    public PastJourney(String location, String title, String description, LocalDate startDate, LocalDate endDate, String rating) {
        super(location, title, description, startDate, endDate);
        this.rating = rating;
    }

    /**
     * A method that updates the rating of the journey on database
     *
     * @param user
     * @param rating
     */
    public void updateJourneyRating(User user, String rating) {
        DatabaseQuery.updateJourneyRating(this, user, rating);
    }

    /**
     * A method that deletes the journey from the database
     *
     * @param currentUser
     */
    public void deleteJourney(User currentUser) {
        DatabaseQuery.deleteJourney(this, currentUser);
    }

    /**
     * Getter method for rating
     *
     * @return rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * Setter method for rating
     *
     * @param rating
     */
    public void setRating(String rating) {
        this.rating = rating;
    }
}
