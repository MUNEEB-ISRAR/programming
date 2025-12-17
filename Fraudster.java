package models.criminals;

/**
 * Fraudster Criminal Type
 */
public class Fraudster extends Criminal {

    private String fraudType;
    private double totalDefraudedAmount;
    private String methodOfContact;
    private boolean usesOnlineTools;
    private int victimCount;

    public Fraudster(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Fraudster");
        setDangerLevel("MEDIUM");
        setRiskFactor(0.5);
        setModusOperandi("Deception and manipulation for financial gain");
        this.totalDefraudedAmount = 0.0;
        this.victimCount = 0;
        this.usesOnlineTools = false;
    }

    // Getters
    public String getFraudType() {
        return fraudType;
    }

    public double getTotalDefraudedAmount() {
        return totalDefraudedAmount;
    }

    public String getMethodOfContact() {
        return methodOfContact;
    }

    public boolean usesOnlineTools() {
        return usesOnlineTools;
    }

    public int getVictimCount() {
        return victimCount;
    }

    // Setters
    public void setFraudType(String type) {
        this.fraudType = type;
    }

    public void setMethodOfContact(String method) {
        this.methodOfContact = method;
    }

    public void setUsesOnlineTools(boolean uses) {
        this.usesOnlineTools = uses;
    }

    public void addDefraudedAmount(double amount) {
        this.totalDefraudedAmount += amount;
    }

    public void incrementVictimCount() {
        this.victimCount++;
    }
}

