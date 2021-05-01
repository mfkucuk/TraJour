package com.trajour.journey;

import java.time.LocalDate;

public class CurrentJourney extends Journey {
    
    // Properties
    private String rating;

    // Constructor
    public CurrentJourney(String location, String title, String description, LocalDate startDate, LocalDate endDate) {
        super(location, title, description, startDate, endDate);
        rating = "-";
    }

}
