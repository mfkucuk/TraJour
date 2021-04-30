package com.trajour.journey;

import com.trajour.model.User;
import javafx.scene.image.Image;
import com.trajour.db.DatabaseQuery;
import javafx.scene.image.ImageView;

import java.time.LocalDate;

import static com.trajour.db.DatabaseQuery.insertNewJourney;



/**
 * A simple Java class!
*/
public class Journey implements Comparable<Journey>, Postable {
    // Properties
    private String location;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private static int id;

    // Constructors
    public Journey(String location, String title, String description, LocalDate startDate, LocalDate endDate) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addNewJourney(User user) {
        insertNewJourney(this, user);
    }

    // Methods
    public Post post(String comments, Image image) {
        return new Post(this, comments, image);
    }

    /**
     * This method returns the location.
     * @return location 
    */
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * This method returns the title.
     * @return title
     */
    public String getTitle(){ return title; }

    /**
     * This method returns the startDate.
     * @return startDate
    */
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }


    /**
     * This method returns the endDate.
     * @return endDate
    */
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * This method returns the description.
     * @return description
    */
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Location: " + getLocation() + ", Start-End Date: " + startDate + " - " + endDate;
    }
	@Override
	public int compareTo(Journey o) {
        return startDate.compareTo(endDate);
	}
}
