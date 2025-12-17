package models.criminals;

/**
 * Human Trafficker Criminal Type
 */
public class HumanTrafficker extends Criminal {

    private String traffickingType;
    private int victimCount;
    private boolean internationalNetwork;
    private String targetDemographic;
    private boolean usesViolence;

    public HumanTrafficker(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Human Trafficker");
        setDangerLevel("EXTREME");
        setRiskFactor(0.95);
        setModusOperandi("Exploitation and trafficking of human beings");
        this.victimCount = 0;
        this.internationalNetwork = false;
        this.usesViolence = true;
    }

    // Getters
    public String getTraffickingType() {
        return traffickingType;
    }

    public int getVictimCount() {
        return victimCount;
    }

    public boolean hasInternationalNetwork() {
        return internationalNetwork;
    }

    public String getTargetDemographic() {
        return targetDemographic;
    }

    public boolean usesViolence() {
        return usesViolence;
    }

    // Setters
    public void setTraffickingType(String type) {
        this.traffickingType = type;
    }

    public void setInternationalNetwork(boolean international) {
        this.internationalNetwork = international;
    }

    public void setTargetDemographic(String demographic) {
        this.targetDemographic = demographic;
    }

    public void setUsesViolence(boolean uses) {
        this.usesViolence = uses;
    }

    public void incrementVictimCount() {
        this.victimCount++;
    }
}
