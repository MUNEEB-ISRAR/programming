package models.criminals;

/**
 * Robber Criminal Type
 */
public class Robber extends Criminal {

    private String targetType;
    private String weaponType;
    private boolean worksInGroup;
    private int robberyCount;
    private double totalStolen;

    public Robber(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Robber");
        setDangerLevel("HIGH");
        setRiskFactor(0.7);
        setModusOperandi("Armed confrontation for theft");
        this.robberyCount = 0;
        this.totalStolen = 0.0;
        this.worksInGroup = false;
    }

    // Getters
    public String getTargetType() {
        return targetType;
    }

    public String getWeaponType() {
        return weaponType;
    }

    public boolean worksInGroup() {
        return worksInGroup;
    }

    public int getRobberyCount() {
        return robberyCount;
    }

    public double getTotalStolen() {
        return totalStolen;
    }

    // Setters
    public void setTargetType(String type) {
        this.targetType = type;
    }

    public void setWeaponType(String weapon) {
        this.weaponType = weapon;
    }

    public void setWorksInGroup(boolean inGroup) {
        this.worksInGroup = inGroup;
    }

    public void incrementRobberyCount() {
        this.robberyCount++;
    }

    public void addStolenAmount(double amount) {
        this.totalStolen += amount;
    }
}

