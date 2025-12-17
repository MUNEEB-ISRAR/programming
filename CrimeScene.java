package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Crime Scene Model
 * Represents a crime scene with all associated evidence and characteristics.
 * Supported Crime Types
 * 1. Murder
 * 2. OrganizedCrime
 * 3. Theft
 * 4. DrugTrafficking
 * 5. Assault
 * 6. HumanTrafficking
 * 7. Fraud
 * 8. Robbery
 * 9. Arson
 * 10. SexualAssault
 * 11. Kidnapping
 * 12. Terrorism
 * 13. CyberCrime
 * 14. MoneyLaundering
 */
public class CrimeScene implements java.io.Serializable {

    // Valid crime types
    private static final String[] VALID_CRIME_TYPES = {
            "Murder", "Theft", "Assault", "Fraud", "Arson", "Kidnapping",
            "CyberCrime", "OrganizedCrime", "DrugTrafficking", "HumanTrafficking",
            "Robbery", "SexualAssault", "Terrorism", "MoneyLaundering"
    };

    // Core fields
    private String sceneId;
    private String crimeType;
    private String location;
    private String description;

    // Time tracking
    private LocalDateTime timeOfCrime;
    private LocalDateTime discoveryTime;

    // Evidence and characteristics
    private List<Evidence> evidenceList;
    private Map<String, String> sceneCharacteristics;

    // Victim and scene details
    private String victimProfile;
    private String weatherConditions;

    // Investigation tracking
    private boolean isSecured;
    private String investigatorInCharge;

    /**
     * Constructor with validation
     */
    public CrimeScene(String sceneId, String crimeType, String location) {
        // Validation
        if (sceneId == null || sceneId.trim().isEmpty()) {
            throw new IllegalArgumentException("Scene ID cannot be empty");
        }

        if (crimeType == null || crimeType.trim().isEmpty()) {
            throw new IllegalArgumentException("Crime type cannot be empty");
        }

        if (!isValidCrimeType(crimeType)) {
            throw new IllegalArgumentException(
                    "Invalid crime type: " + crimeType + ". Must be one of: " +
                            String.join(", ", VALID_CRIME_TYPES));
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Location cannot be empty");
        }

        // Initialize fields
        this.sceneId = sceneId;
        this.crimeType = crimeType;
        this.location = location;
        this.timeOfCrime = LocalDateTime.now();
        this.discoveryTime = LocalDateTime.now();
        this.evidenceList = new ArrayList<>();
        this.sceneCharacteristics = new HashMap<>();
        this.isSecured = false;
    }

    /**
     * Check if crime type is valid
     */
    private boolean isValidCrimeType(String type) {
        for (String validType : VALID_CRIME_TYPES) {
            if (validType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get list of valid crime types
     */
    public static String[] getValidCrimeTypes() {
        return VALID_CRIME_TYPES.clone();
    }

    // ==================== EVIDENCE MANAGEMENT ====================

    /**
     * Add evidence to scene
     */
    public void addEvidence(Evidence evidence) {
        if (evidence == null) {
            throw new IllegalArgumentException("Cannot add null evidence");
        }

        if (evidenceList.contains(evidence)) {
            throw new IllegalArgumentException(
                    "Evidence " + evidence.getEvidenceId() + " already exists in scene");
        }

        evidenceList.add(evidence);
    }

    /**
     * Remove evidence from scene
     */
    public void removeEvidence(String evidenceId) {
        boolean removed = evidenceList.removeIf(e -> e.getEvidenceId().equals(evidenceId));
        if (!removed) {
            throw new IllegalArgumentException(
                    "Evidence " + evidenceId + " not found in scene");
        }
    }

    /**
     * Get evidence by ID
     */
    public Evidence getEvidence(String evidenceId) {
        return evidenceList.stream()
                .filter(e -> e.getEvidenceId().equals(evidenceId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Check if scene has specific evidence type
     */
    public boolean hasEvidenceType(String type) {
        return evidenceList.stream()
                .anyMatch(e -> e.getType().equalsIgnoreCase(type));
    }

    /**
     * Get all evidence of specific type
     */
    public List<Evidence> getEvidenceByType(String type) {
        List<Evidence> result = new ArrayList<>();
        for (Evidence e : evidenceList) {
            if (e.getType().equalsIgnoreCase(type)) {
                result.add(e);
            }
        }
        return result;
    }

    /**
     * Get evidence count
     */
    public int getEvidenceCount() {
        return evidenceList.size();
    }

    // ==================== CHARACTERISTIC MANAGEMENT ====================

    /**
     * Add scene characteristic
     */
    public void addCharacteristic(String key, String value) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Characteristic key cannot be empty");
        }
        sceneCharacteristics.put(key, value);
    }

    /**
     * Get characteristic value
     */
    public String getCharacteristic(String key) {
        return sceneCharacteristics.get(key);
    }

    /**
     * Check if characteristic exists
     */
    public boolean hasCharacteristic(String key) {
        return sceneCharacteristics.containsKey(key);
    }

    /**
     * Remove characteristic
     */
    public void removeCharacteristic(String key) {
        sceneCharacteristics.remove(key);
    }

    // ==================== SCENE SECURITY ====================

    /**
     * Secure the crime scene
     */
    public void secureScene(String investigator) {
        if (investigator == null || investigator.trim().isEmpty()) {
            throw new IllegalArgumentException("Investigator name cannot be empty");
        }
        this.isSecured = true;
        this.investigatorInCharge = investigator;
    }

    /**
     * Unsecure the scene
     */
    public void unsecureScene() {
        this.isSecured = false;
    }

    // ==================== GETTERS ====================

    public String getSceneId() {
        return sceneId;
    }

    public String getCrimeType() {
        return crimeType;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimeOfCrime() {
        return timeOfCrime;
    }

    public LocalDateTime getDiscoveryTime() {
        return discoveryTime;
    }

    public List<Evidence> getEvidenceList() {
        return new ArrayList<>(evidenceList); // Return copy
    }

    public Map<String, String> getSceneCharacteristics() {
        return new HashMap<>(sceneCharacteristics); // Return copy
    }

    public String getVictimProfile() {
        return victimProfile;
    }

    public String getWeatherConditions() {
        return weatherConditions;
    }

    public boolean isSecured() {
        return isSecured;
    }

    public String getInvestigatorInCharge() {
        return investigatorInCharge;
    }

    // ==================== SETTERS ====================

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeOfCrime(LocalDateTime time) {
        if (time != null && time.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Crime time cannot be in the future");
        }
        this.timeOfCrime = time;
    }

    public void setDiscoveryTime(LocalDateTime time) {
        if (time != null && time.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Discovery time cannot be in the future");
        }
        this.discoveryTime = time;
    }

    public void setVictimProfile(String profile) {
        this.victimProfile = profile;
    }

    public void setWeatherConditions(String conditions) {
        this.weatherConditions = conditions;
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Get formatted time of crime
     */
    public String getFormattedTimeOfCrime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timeOfCrime.format(formatter);
    }

    /**
     * Get formatted discovery time
     */
    public String getFormattedDiscoveryTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return discoveryTime.format(formatter);
    }

    /**
     * Get scene summary
     */
    public String getSummary() {
        return String.format("Crime Scene %s: %s at %s on %s",
                sceneId, crimeType, location, getFormattedTimeOfCrime());
    }

    // ==================== OBJECT METHODS ====================

    @Override
    public String toString() {
        return String.format("Crime Scene [%s] - %s at %s (Evidence: %d, Secured: %s)",
                sceneId, crimeType, location, evidenceList.size(), isSecured ? "Yes" : "No");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrimeScene scene = (CrimeScene) o;
        return sceneId.equals(scene.sceneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sceneId);
    }
}
