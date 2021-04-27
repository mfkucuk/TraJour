package com.trajour.journey;

import java.time.LocalDate;

public class CurrentJourney extends Journey {
    
    // Properties
    private int rating;

    // Constructor
    public CurrentJourney(int journeyID, String location, String description, LocalDate startDate, LocalDate endDate) {
        super(journeyID, location, description, startDate, endDate);
        rating = -1;
    }

    /**
     * Returns the rating of the current journey.
     * @return is the rating.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating of the current journey.
     * @param rating is the rating.
     */
    public void setRating(int rating) {
        this.rating = rating;    
    }
    
    /**
     * Converts future journey to a current one.
     */
    public PastJourney convertToPastJourney(int rating) {
        return new PastJourney(getJourneyID(), getLocation(), getDescription(), getStartDate(), getEndDate(), rating);
    }

}
