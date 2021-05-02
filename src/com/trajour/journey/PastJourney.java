package com.trajour.journey;

import com.trajour.db.DatabaseQuery;
import com.trajour.user.User;

import java.time.LocalDate;

public class PastJourney extends Journey {

    // Properties
    private String rating;

    // Constructor
    public PastJourney(String location, String title, String description, LocalDate startDate, LocalDate endDate, String rating) {
        super(location, title, description, startDate, endDate);
        this.rating = rating;
    }

    public void updateJourneyRating(User user, String rating) {
        DatabaseQuery.updateJourneyRating(this, user, rating);
    }

    public void deleteJourney(User currentUser) {
        DatabaseQuery.deleteJourney(this, currentUser);
    }

    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
}
