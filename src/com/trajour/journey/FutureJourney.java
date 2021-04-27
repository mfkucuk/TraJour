package com.trajour.journey;

import java.time.LocalDate;

/**
 * A subclass of journey for future journeys of the user.
 */
public class FutureJourney extends Journey {
    
    // Constructor
    public FutureJourney(int journeyID, String location, String description, LocalDate startDate, LocalDate endDate) {
        super(journeyID, location, description, startDate, endDate);
    }

    /**
     * Converts future journey to a current one.
     */
    public CurrentJourney convertToCurrentJourney() {
        return new CurrentJourney(getJourneyID(), getLocation(), getDescription(), getStartDate(), getEndDate());
    }
}
