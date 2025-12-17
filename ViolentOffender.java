package models.criminals;

/**
 * Violent Offender Criminal Type
 */
public class ViolentOffender extends Criminal {

    private String weaponPreference;
    private boolean impulseControl;
    private boolean substanceAbuse;
    private String triggerType;
    private int assaultCount;

    public ViolentOffender(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Violent Offender");
        setDangerLevel("HIGH");
        setRiskFactor(0.75);
        setModusOperandi("Violent confrontation with victims");
        this.impulseControl = false;
        this.assaultCount = 0;
    }

    // Getters
    public String getWeaponPreference() {
        return weaponPreference;
    }

    public boolean hasImpulseControl() {
        return impulseControl;
    }

    public boolean hasSubstanceAbuse() {
        return substanceAbuse;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public int getAssaultCount() {
        return assaultCount;
    }

    // Setters
    public void setWeaponPreference(String weapon) {
        this.weaponPreference = weapon;
    }

    public void setImpulseControl(boolean control) {
        this.impulseControl = control;
    }

    public void setSubstanceAbuse(boolean abuse) {
        this.substanceAbuse = abuse;
    }

    public void setTriggerType(String trigger) {
        this.triggerType = trigger;
    }

    public void incrementAssaultCount() {
        this.assaultCount++;
    }
}

