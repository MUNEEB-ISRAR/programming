package models.criminals;

/**
 * Cyber Criminal Type
 */
public class CyberCriminal extends Criminal {

    private String specialization;
    private String skillLevel;
    private boolean partOfGroup;
    private String preferredTarget;
    private int systemsCompromised;
    private double financialDamage;

    public CyberCriminal(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Cyber Criminal");
        setDangerLevel("MEDIUM");
        setRiskFactor(0.6);
        setModusOperandi("Digital intrusion and data theft");
        this.systemsCompromised = 0;
        this.financialDamage = 0.0;
        this.skillLevel = "INTERMEDIATE";
    }

    // Getters
    public String getSpecialization() {
        return specialization;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public boolean isPartOfGroup() {
        return partOfGroup;
    }

    public String getPreferredTarget() {
        return preferredTarget;
    }

    public int getSystemsCompromised() {
        return systemsCompromised;
    }

    public double getFinancialDamage() {
        return financialDamage;
    }

    // Setters
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setSkillLevel(String level) {
        this.skillLevel = level;
    }

    public void setPartOfGroup(boolean partOf) {
        this.partOfGroup = partOf;
    }

    public void setPreferredTarget(String target) {
        this.preferredTarget = target;
    }

    public void addSystemCompromised() {
        this.systemsCompromised++;
    }

    public void addFinancialDamage(double damage) {
        this.financialDamage += damage;
    }
}

