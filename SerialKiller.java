package models.criminals;

/**
 * Serial Killer Criminal Type
 */
public class SerialKiller extends Criminal {

    private String signature;
    private int victimCount;
    private String victimType;
    private boolean organized;
    private String coolingOffPeriod;

    public SerialKiller(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Serial Killer");
        setDangerLevel("EXTREME");
        setRiskFactor(0.95);
        setModusOperandi("Methodical killing with specific victim selection patterns");
        this.victimCount = 0;
        this.organized = true;
    }

    // Getters
    public String getSignature() {
        return signature;
    }

    public int getVictimCount() {
        return victimCount;
    }

    public String getVictimType() {
        return victimType;
    }

    public boolean isOrganized() {
        return organized;
    }

    public String getCoolingOffPeriod() {
        return coolingOffPeriod;
    }

    // Setters
    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setVictimCount(int count) {
        this.victimCount = count;
    }

    public void setVictimType(String type) {
        this.victimType = type;
    }

    public void setOrganized(boolean organized) {
        this.organized = organized;
    }

    public void setCoolingOffPeriod(String period) {
        this.coolingOffPeriod = period;
    }

    public void incrementVictimCount() {
        this.victimCount++;
    }
}
