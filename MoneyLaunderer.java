package models.criminals;

/**
 * Money Launderer Criminal Type
 */
public class MoneyLaunderer extends Criminal {

    private String primaryMethod;
    private double totalLaundered;
    private boolean hasBankingConnections;
    private String frontBusiness;
    private boolean internationalOperations;

    public MoneyLaunderer(String id, String name, int age, String gender) {
        super(id, name, age, gender, "Money Launderer");
        setDangerLevel("MEDIUM");
        setRiskFactor(0.5);
        setModusOperandi("Financial obfuscation and legitimization of illegal funds");
        this.totalLaundered = 0.0;
        this.internationalOperations = false;
    }

    // Getters
    public String getPrimaryMethod() {
        return primaryMethod;
    }

    public double getTotalLaundered() {
        return totalLaundered;
    }

    public boolean hasBankingConnections() {
        return hasBankingConnections;
    }

    public String getFrontBusiness() {
        return frontBusiness;
    }

    public boolean hasInternationalOperations() {
        return internationalOperations;
    }

    // Setters
    public void setPrimaryMethod(String method) {
        this.primaryMethod = method;
    }

    public void setHasBankingConnections(boolean has) {
        this.hasBankingConnections = has;
    }

    public void setFrontBusiness(String business) {
        this.frontBusiness = business;
    }

    public void setInternationalOperations(boolean international) {
        this.internationalOperations = international;
    }

    public void addLaunderedAmount(double amount) {
        this.totalLaundered += amount;
    }
}
