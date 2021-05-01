package com.trajour.journey;

import java.time.LocalDate;

public class Wish {
    private String location;
    private LocalDate startDate;

    public Wish(String location, LocalDate startDate) {
        this.location = location;
        this.startDate = startDate;
    }

    public String getLocation() {
        return location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
}
