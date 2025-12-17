package models.criminals;

/**
 * Drug Trafficker Criminal Type
 */
public class DrugTrafficker extends Criminal {

    private String primaryDrug;
    private String operationScale;
    private boolean hasCartelConnections;
    private boolean usesViolence;
    private double totalStreetValue;

    public DrugTrafficker(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Drug Trafficker");
        setDangerLevel("EXTREME");
        setRiskFactor(0.85);
        setModusOperandi("Large-scale drug distribution network");
        this.totalStreetValue = 0.0;
        this.operationScale = "LOCAL";
    }

    // Getters
    public String getPrimaryDrug() {
        return primaryDrug;
    }

    public String getOperationScale() {
        return operationScale;
    }

    public boolean hasCartelConnections() {
        return hasCartelConnections;
    }

    public boolean usesViolence() {
        return usesViolence;
    }

    public double getTotalStreetValue() {
        return totalStreetValue;
    }

    // Setters
    public void setPrimaryDrug(String drug) {
        this.primaryDrug = drug;
    }

    public void setOperationScale(String scale) {
        this.operationScale = scale;
    }

    public void setHasCartelConnections(boolean has) {
        this.hasCartelConnections = has;
    }

    public void setUsesViolence(boolean uses) {
        this.usesViolence = uses;
    }

    public void addStreetValue(double value) {
        this.totalStreetValue += value;
    }
}

