package models.criminals;

/**
 * Thief Criminal Type
 */
public class Thief extends Criminal {

    private String specialization;
    private double totalStolenValue;
    private String preferredTarget;
    private boolean worksSolo;

    public Thief(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Thief");
        setDangerLevel("LOW");
        setRiskFactor(0.3);
        setModusOperandi("Stealth-based theft operations");
        this.totalStolenValue = 0.0;
        this.worksSolo = true;
    }

    // Getters
    public String getSpecialization() {
        return specialization;
    }

    public double getTotalStolenValue() {
        return totalStolenValue;
    }

    public String getPreferredTarget() {
        return preferredTarget;
    }

    public boolean isWorksSolo() {
        return worksSolo;
    }

    // Setters
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setPreferredTarget(String target) {
        this.preferredTarget = target;
    }

    public void setWorksSolo(boolean solo) {
        this.worksSolo = solo;
    }

    public void addStolenValue(double value) {
        this.totalStolenValue += value;
    }
}

