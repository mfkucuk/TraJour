/**
 * A simple Java class!
*/
public class UserAchievement extends Achievement
{
    // Properties
    int friendsLimit;
    // Constructors
    public UserAchievement( String definition, int achivementNumber, int friendsLimit) {
        super( achivementNumber, definition);
        this.friendsLimit = friendsLimit;
    }
    // Methods
    
}