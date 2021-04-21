package model;

/**
 * A simple Java class!
*/
public class JourneyAchievement extends Achievement
{
    // Properties
    int countryLimit;
    int numberOfJourneysLimit;
    
    // Constructors
    public JourneyAchievement( String definition, int achievementNumber, int countryLimit) {
        super( achievementNumber, definition);
        this.countryLimit = countryLimit;
    }
    public JourneyAchievement( int numberOfJourneysLimit, String definition, int achievementNumber) {
        super( achievementNumber, definition);
        this.numberOfJourneysLimit = numberOfJourneysLimit;
    }
    // Methods
    
    
}