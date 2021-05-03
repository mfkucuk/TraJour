package com.trajour.journey;

import java.time.LocalDate;

/**
 * A subclass of journey for current journeys of the user.
 *
 *@author Ahmet Alperen YılmazYıldız
 *@version 4.05.2021
 */
public class CurrentJourney extends Journey {
    
    // Properties
    private String rating;

    // Constructor
    public CurrentJourney(String location, String title, String description, LocalDate startDate, LocalDate endDate) {
        super(location, title, description, startDate, endDate);
        rating = "-";
    }
}
