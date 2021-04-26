package trajour.model;

import javafx.scene.image.Image;

/**
 * A simple Java class!
*/
public class Post implements Shareable{
    // Properties
    private String text;
    private Journey theJourney;
    private Image journeyPhoto;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Journey getTheJourney() {
        return theJourney;
    }

    public void setTheJourney(Journey theJourney) {
        this.theJourney = theJourney;
    }

    public Image getJourneyPhoto() {
        return journeyPhoto;
    }

    public void setJourneyPhoto(Image journeyPhoto) {
        this.journeyPhoto = journeyPhoto;
    }

    // Constructor
    public Post( Journey theJourney, String text, Image journeyPhoto ) {
        this.theJourney = theJourney;
        this.text = text;
        this.journeyPhoto = journeyPhoto;
    }

    @Override
    public Post share(User user) {
        return null;
    }
}