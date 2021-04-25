package trajour.model;

import java.util.Date;

/**
 * A simple Java class!
*/
public class Journey implements Comparable<Journey>, Shareable {
    // Properties
    private int journeyID;
    private String location;
    private String description;
    private Date startDate;
    private Date endDate;

    // Constructors
    public Journey( int journeyID, String location, String description, Date startDate, Date endDate) {
        this.journeyID = journeyID;
        this.location = location;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    // Methods
    @Override
    public void share(Journey j) {

    }
    /**
     * This method returns the journey id.
     * @return journeyID 
    */
    public int getJourneyID() {
        return journeyID;
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
     * This method returns the startDate.
     * @return startDate
    */
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    /**
     * This method returns the endDate.
     * @return endDate
    */
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
        return "Location: " + getLocation() + ", Start-End Date: " + (1900 + startDate.getYear()) + "/" + startDate.getMonth()
                + "/" + startDate.getDay() + " - " + (1900 + endDate.getYear()) + "/" + endDate.getMonth() + "/" + endDate.getDay();
    }
	@Override
	public int compareTo(Journey o) {
        return startDate.compareTo(endDate);
	}
}
