package model;

import java.util.Date;
import java.util.Scanner;

/**
 * A simple Java class!
*/
public class Journey implements Comparable<Journey>
{
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
    
    /**
     * This method returns the startDate.
     * @return startDate
    */
    public Date getStartDate() {
        return startDate;
    }
    /**
     * This method returns the endDate.
     * @return endDate
    */
    public Date getEndDate() {
        return endDate;
    }
    /**
     * This method returns the description.
     * @return description
    */
    public String getDescription() {
        return description;
    }
    //public int compareTo( Journey j1, Journey j2) {
      //  if ( j1.equals)
    //}

	@Override
	public int compareTo(Journey o) {
        Journey newVariable = (Journey) o;
		if ( ( this.journeyID ==  newVariable.journeyID ) & ( this.location.equals( newVariable.location)) & ( this.description.equals( newVariable.description)) & (this.startDate.equals( newVariable.startDate) & (this.endDate.equals( newVariable.endDate))) ) {
            return 1;
        }
        return 0;
	}

}
