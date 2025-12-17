package models.criminals;

/**
 * Terrorist Criminal Type
 */
public class Terrorist extends Criminal {

    private String ideology;
    private String affiliation;
    private boolean partOfCell;
    private String targetType;
    private int attackCount;

    public Terrorist(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Terrorist");
        setDangerLevel("EXTREME");
        setRiskFactor(1.0);
        setModusOperandi("Ideologically motivated violence against civilians");
        this.attackCount = 0;
        this.partOfCell = false;
    }

    // Getters
    public String getIdeology() {
        return ideology;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public boolean isPartOfCell() {
        return partOfCell;
    }

    public String getTargetType() {
        return targetType;
    }

    public int getAttackCount() {
        return attackCount;
    }

    // Setters
    public void setIdeology(String ideology) {
        this.ideology = ideology;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public void setPartOfCell(boolean partOf) {
        this.partOfCell = partOf;
    }

    public void setTargetType(String type) {
        this.targetType = type;
    }

    public void incrementAttackCount() {
        this.attackCount++;
    }
}

