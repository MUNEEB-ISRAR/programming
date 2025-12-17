package models.criminals;

import java.io.Serializable;
import java.util.*;

/**
 * Criminal Base Class
 * Abstract base class for all criminal types.
 */
public abstract class Criminal implements Serializable {

    // Core attributes
    private String id;
    private String name;
    private int age;
    private String gender;
    private String criminalType;

    // Behavioral attributes
    private String modusOperandi;
    private String psychologicalProfile;
    private List<String> knownLocations;
    private List<String> priorCrimes;

    // Status attributes
    private boolean atLarge;
    private String dangerLevel;
    private double riskFactor;

    /**
     * Constructor
     */
    public Criminal(String id, String name, int age, String gender, String criminalType) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.criminalType = criminalType;
        this.knownLocations = new ArrayList<>();
        this.priorCrimes = new ArrayList<>();
        this.atLarge = true;
        this.dangerLevel = "MEDIUM";
        this.riskFactor = 0.5;
        this.modusOperandi = "Unknown";
    }

    // ==================== GETTERS ====================

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getCriminalType() {
        return criminalType;
    }

    public String getModusOperandi() {
        return modusOperandi;
    }

    public String getPsychologicalProfile() {
        return psychologicalProfile;
    }

    public List<String> getKnownLocations() {
        return new ArrayList<>(knownLocations);
    }

    public List<String> getPriorCrimes() {
        return new ArrayList<>(priorCrimes);
    }

    public boolean isAtLarge() {
        return atLarge;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public double getRiskFactor() {
        return riskFactor;
    }

    // ==================== SETTERS ====================

    public void setModusOperandi(String mo) {
        this.modusOperandi = mo;
    }

    public void setPsychologicalProfile(String profile) {
        this.psychologicalProfile = profile;
    }

    public void setAtLarge(boolean atLarge) {
        this.atLarge = atLarge;
    }

    public void setDangerLevel(String level) {
        this.dangerLevel = level;
    }

    public void setRiskFactor(double factor) {
        this.riskFactor = Math.max(0.0, Math.min(1.0, factor));
    }

    // ==================== LIST OPERATIONS ====================

    public void addKnownLocation(String location) {
        if (!knownLocations.contains(location)) {
            knownLocations.add(location);
        }
    }

    public void addPriorCrime(String crime) {
        if (!priorCrimes.contains(crime)) {
            priorCrimes.add(crime);
        }
    }

    public boolean operatesInLocation(String location) {
        return knownLocations.stream()
                .anyMatch(loc -> loc.equalsIgnoreCase(location));
    }

    public boolean hasCommitted(String crimeType) {
        return priorCrimes.stream()
                .anyMatch(crime -> crime.equalsIgnoreCase(crimeType));
    }

    // ==================== OBJECT METHODS ====================

    @Override
    public String toString() {
        return String.format("%s (%s) - %s, Age %d [%s]",
                name, id, criminalType, age, dangerLevel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Criminal criminal = (Criminal) o;
        return id.equals(criminal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
