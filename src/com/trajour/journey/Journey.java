package com.trajour.journey;

import com.trajour.user.User;
import javafx.scene.image.Image;

import java.time.LocalDate;

import static com.trajour.db.DatabaseQuery.findJourneyByUser;
import static com.trajour.db.DatabaseQuery.insertNewJourney;


/**
 * Journey class implements Comparable<Journey> and Postable, this class
 * has the properties of journeys including location, title, description,
 * start and end dates and auto generated id's. It has the methods to alter
 * the journey.
 *
 *@author Ahmet Alperen YılmazYıldız
 *@version 4.05.2021
 *
*/
public class Journey implements Comparable<Journey>, Postable {

    // Properties
    private String location;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private static int id;

    // Constructor
    public Journey(String location, String title, String description, LocalDate startDate, LocalDate endDate) {
        this.location = location;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Methods

    /**
     * A method that checks if the specified journey exists,
     * if not create the journey if it exists return false.
     *
     * @param currentUser
     * @return false if the journey exists
     */
    public boolean addNewJourney(User currentUser) {
        if (findJourneyByUser(this, currentUser)) {
            return false;
        }
        else {
            insertNewJourney(this, currentUser);
            return true;
        }
    }

    /**
     * Method that posts the designated journey
     *
     * @param comments
     * @param image
     * @return Post
     */
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

    /**
     * This method returns the endDate.
     * @return endDate
    */
    public LocalDate getEndDate() {
        return endDate;
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
        return "Location: " + getLocation() + ", Title: " + getTitle() + ", Start-End Date: " + startDate + " - " + endDate;
    }

	@Override
    public int compareTo(Journey o) {
        return startDate.compareTo(endDate);
	}
}
