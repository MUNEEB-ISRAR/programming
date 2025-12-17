package models.criminals;

/**
 * Sexual Offender Criminal Type
 */
public class SexualOffender extends Criminal {

    private String offenseType;
    private int victimCount;
    private String targetDemographic;
    private boolean hasReoffended;
    private String huntingGround;

    public SexualOffender(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Sexual Offender");
        setDangerLevel("EXTREME");
        setRiskFactor(0.9);
        setModusOperandi("Sexual predation and assault");
        this.victimCount = 0;
        this.hasReoffended = false;
    }

    // Getters
    public String getOffenseType() {
        return offenseType;
    }

    public int getVictimCount() {
        return victimCount;
    }

    public String getTargetDemographic() {
        return targetDemographic;
    }

    public boolean hasReoffended() {
        return hasReoffended;
    }

    public String getHuntingGround() {
        return huntingGround;
    }

    // Setters
    public void setOffenseType(String type) {
        this.offenseType = type;
    }

    public void setTargetDemographic(String demographic) {
        this.targetDemographic = demographic;
    }

    public void setHasReoffended(boolean reoffended) {
        this.hasReoffended = reoffended;
    }

    public void setHuntingGround(String ground) {
        this.huntingGround = ground;
    }

    public void incrementVictimCount() {
        this.victimCount++;
    }
}

