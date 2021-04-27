package com.trajour.journey;

import java.time.LocalDate;

public class PastJourney extends Journey {

    // Properties
    private int rating;

    // Constructor
    public PastJourney(String location, String description, LocalDate startDate, LocalDate endDate, int rating) {
        super(location, description, startDate, endDate);
        this.rating = rating;
    }

    /**
     * Returns the rating of the past journey.
     * @return is the rating.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating of the past journey.
     * @param rating is the rating.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
    
}
