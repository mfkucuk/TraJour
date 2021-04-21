/**
 * A simple Java class!
*/
public class Post
{
    // Properties
    private String text;
    private Journey theJourney;
    private boolean visibilityOfRating;
    private boolean visibilityOfDate;
    private int likes;
    private Image journeyPhoto;
    // Constructors
    
    public Post( Journey theJourney, String text, boolean visibilityOfRating, boolean visibilityOfDate ) {
        this.theJourney = theJourney;
        this.text = text;
        this.visibilityOfRating = visibilityOfRating;
        this.visibilityOfDate = visibilityOfDate;
        this.likes = 0;
    }
    
    // Methods
    public String getComments() {
        return text;
    }
    
    public void setComments( String comment ) {
        this.text = comment;
    }
    public void setJourneyPhoto( Image photo) {
        this.journeyPhoto = photo;
    }
    public boolean hideRating() {
        this.visibilityOfRating = false;
    }
    public boolean hideDate() {
        this.visibilityOfDate = false;
    }
    public void increaseLikeCount() {
        likes++;
    }
    public void decreaseLikeCount() {
        likes--;
    }

    
}