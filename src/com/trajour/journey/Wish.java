package com.trajour.journey;

import java.time.LocalDate;

/**
 * A class that has the properties of users wish places to go
 * It has a start date but no end date unlike future journey.
 */
public class Wish {
    //Properties
    private String location;
    private LocalDate startDate;

    //Constructor
    public Wish(String location, LocalDate startDate) {
        this.location = location;
        this.startDate = startDate;
    }

    /**
     * Getter method for location
     *
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Getter method for startDate
     *
     * @return startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    /**
     * String representation of the wish
     */
    public String toString() {

        return "Location: " + getLocation() + " - Start Date: " + getStartDate();

    }
}
