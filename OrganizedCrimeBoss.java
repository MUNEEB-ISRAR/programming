package models.criminals;

/**
 * Organized Crime Boss Criminal Type
 */
public class OrganizedCrimeBoss extends Criminal {

    private String organizationName;
    private int memberCount;
    private String primaryActivity;
    private String territory;
    private boolean hasLegalBusinesses;

    public OrganizedCrimeBoss(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Organized Crime Boss");
        setDangerLevel("EXTREME");
        setRiskFactor(0.95);
        setModusOperandi("Leadership of organized criminal enterprise");
        this.memberCount = 0;
        this.hasLegalBusinesses = false;
    }

    // Getters
    public String getOrganizationName() {
        return organizationName;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public String getPrimaryActivity() {
        return primaryActivity;
    }

    public String getTerritory() {
        return territory;
    }

    public boolean hasLegalBusinesses() {
        return hasLegalBusinesses;
    }

    // Setters
    public void setOrganizationName(String name) {
        this.organizationName = name;
    }

    public void setMemberCount(int count) {
        this.memberCount = count;
    }

    public void setPrimaryActivity(String activity) {
        this.primaryActivity = activity;
    }

    public void setTerritory(String territory) {
        this.territory = territory;
    }

    public void setHasLegalBusinesses(boolean has) {
        this.hasLegalBusinesses = has;
    }
}

