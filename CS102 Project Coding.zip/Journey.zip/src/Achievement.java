/**
 * A simple Java class!
*/
public class Achievement
{
    // Properties
    private boolean achieved;
    private int achievementNumber;
    private String achievementDefinition;
    // Constructors
    public Achievement( int achievementNumber, String achievementDefinition){
        this.achievementDefinition = achievementDefinition;
        this.achievementNumber = achievementNumber;
        achieved = false;
    }

    // Methods
    public String getAchievementDefinition() {
        return achievementDefinition;
    }
    public int getAchievementNumber() {
        return achievementNumber;
    }
    public Achievement getAchievement() {
        return this;
    }
    public void setAchievement( boolean achieved) {
        this.achieved = achieved;
    }
    public void isAchieved() {
        this.achieved = true;
    }
    public void validate() {
        //bu metod nasil yapilir bilemedim
    }
}