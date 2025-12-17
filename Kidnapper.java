package models.criminals;

/**
 * Kidnapper Criminal Type
 */
public class Kidnapper extends Criminal {

    private String motivation;
    private boolean demandsRansom;
    private int victimCount;
    private String targetDemographic;
    private double totalRansom;

    public Kidnapper(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Kidnapper");
        setDangerLevel("EXTREME");
        setRiskFactor(0.9);
        setModusOperandi("Abduction and confinement of victims");
        this.victimCount = 0;
        this.totalRansom = 0.0;
        this.demandsRansom = false;
    }

    // Getters
    public String getMotivation() {
        return motivation;
    }

    public boolean demandsRansom() {
        return demandsRansom;
    }

    public int getVictimCount() {
        return victimCount;
    }

    public String getTargetDemographic() {
        return targetDemographic;
    }

    public double getTotalRansom() {
        return totalRansom;
    }

    // Setters
    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public void setDemandsRansom(boolean demands) {
        this.demandsRansom = demands;
    }

    public void setTargetDemographic(String demographic) {
        this.targetDemographic = demographic;
    }

    public void incrementVictimCount() {
        this.victimCount++;
    }

    public void addRansomAmount(double amount) {
        this.totalRansom += amount;
    }
}

