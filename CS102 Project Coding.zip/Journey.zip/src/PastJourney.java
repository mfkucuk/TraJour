import java.util.Date;

public class PastJourney extends Journey {

    // Properties
    private int rating;

    // Constructor
    public PastJourney(int journeyID, String location, String description, Date startDate, Date endDate, int rating) {
        super(journeyID, location, description, startDate, endDate);
        this.rating = rating;
    }

    /**
     * Returns the rating of the past journey.
     * @return is the rating.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating of the past journey.
     * @param rating is the rating.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
    
}
