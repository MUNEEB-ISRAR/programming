package models.criminals;

/**
 * Arsonist Criminal Type
 */
public class Arsonist extends Criminal {

    private String accelerantType;
    private String targetType;
    private String motivation;
    private int fireCount;
    private boolean hasFirefightingKnowledge;

    public Arsonist(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Arsonist");
        setDangerLevel("HIGH");
        setRiskFactor(0.7);
        setModusOperandi("Deliberate fire-setting with specific patterns");
        this.fireCount = 0;
        this.hasFirefightingKnowledge = false;
    }

    // Getters
    public String getAccelerantType() {
        return accelerantType;
    }

    public String getTargetType() {
        return targetType;
    }

    public String getMotivation() {
        return motivation;
    }

    public int getFireCount() {
        return fireCount;
    }

    public boolean hasFirefightingKnowledge() {
        return hasFirefightingKnowledge;
    }

    // Setters
    public void setAccelerantType(String type) {
        this.accelerantType = type;
    }

    public void setTargetType(String type) {
        this.targetType = type;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public void setHasFirefightingKnowledge(boolean has) {
        this.hasFirefightingKnowledge = has;
    }

    public void incrementFireCount() {
        this.fireCount++;
    }
}
