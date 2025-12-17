package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Evidence Model
 * Represents physical or digital evidence collected from crime scenes.
 * 1. Weapon
 * 2. Digital
 * 3. Witness
 * 4. Document
 * 5. Fiber
 * 6. Ballistic
 * 7. Trace
 * 8. Toxicology
 * 9. Surveillance
 * 10. Financial
 */
public class Evidence implements Serializable {

    // Valid evidence types
    private static final String[] VALID_TYPES = {
            "Weapon", "Digital", "Witness", "Document", "Fiber", "Ballistic",
            "Trace", "Toxicology", "Surveillance", "Financial"
    };

    // Core fields
    private String evidenceId;
    private String type;
    private String description;
    private String location;

    // Tracking fields
    private LocalDateTime collectedTime;
    private String collectedBy;

    // Flexible attribute storage
    private Map<String, String> attributes;

    /**
     * Constructor with validation
     */
    public Evidence(String evidenceId, String type, String description, String location) {
        // Validation
        if (evidenceId == null || evidenceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Evidence ID cannot be empty");
        }

        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Evidence type cannot be empty");
        }

        if (!isValidEvidenceType(type)) {
            throw new IllegalArgumentException(
                    "Invalid evidence type: " + type + ". Must be one of: " +
                            String.join(", ", VALID_TYPES));
        }

        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Evidence description cannot be empty");
        }

        // Initialize fields
        this.evidenceId = evidenceId;
        this.type = type;
        this.description = description;
        this.location = location;
        this.collectedTime = LocalDateTime.now();
        this.attributes = new HashMap<>();
    }

    /**
     * Check if evidence type is valid
     */
    private boolean isValidEvidenceType(String type) {
        for (String validType : VALID_TYPES) {
            if (validType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get list of valid evidence types
     */
    public static String[] getValidEvidenceTypes() {
        return VALID_TYPES.clone();
    }

    // ==================== ATTRIBUTE MANAGEMENT ====================

    /**
     * Add custom attribute
     */
    public void addAttribute(String key, String value) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Attribute key cannot be empty");
        }
        attributes.put(key, value);
    }

    /**
     * Get attribute value
     */
    public String getAttribute(String key) {
        return attributes.get(key);
    }

    /**
     * Check if attribute exists
     */
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    /**
     * Remove attribute
     */
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    /**
     * Get all attributes
     */
    public Map<String, String> getAttributes() {
        return new HashMap<>(attributes); // Return copy for safety
    }

    // ==================== GETTERS ====================

    public String getEvidenceId() {
        return evidenceId;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public LocalDateTime getCollectedTime() {
        return collectedTime;
    }

    public String getCollectedBy() {
        return collectedBy;
    }

    // ==================== SETTERS ====================

    public void setCollectedBy(String person) {
        if (person != null && !person.trim().isEmpty()) {
            this.collectedBy = person;
        }
    }

    public void setCollectedTime(LocalDateTime time) {
        if (time != null && !time.isAfter(LocalDateTime.now())) {
            this.collectedTime = time;
        } else {
            throw new IllegalArgumentException("Collection time cannot be in the future");
        }
    }

    public void setDescription(String description) {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description;
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Get formatted collection time
     */
    public String getFormattedCollectionTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return collectedTime.format(formatter);
    }

    /**
     * Get detailed description
     */
    public String getDetailedDescription() {
        StringBuilder details = new StringBuilder();
        details.append("Evidence ID: ").append(evidenceId).append("\n");
        details.append("Type: ").append(type).append("\n");
        details.append("Description: ").append(description).append("\n");
        details.append("Location: ").append(location != null ? location : "Unknown").append("\n");
        details.append("Collected: ").append(getFormattedCollectionTime()).append("\n");

        if (collectedBy != null) {
            details.append("Collected By: ").append(collectedBy).append("\n");
        }

        if (!attributes.isEmpty()) {
            details.append("Attributes:\n");
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                details.append("  - ").append(entry.getKey())
                        .append(": ").append(entry.getValue()).append("\n");
            }
        }

        return details.toString();
    }

    // ==================== OBJECT METHODS ====================

    @Override
    public String toString() {
        return String.format("%s [%s] - %s (Location: %s)",
                evidenceId, type, description,
                location != null ? location : "Unknown");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evidence evidence = (Evidence) o;
        return evidenceId.equals(evidence.evidenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evidenceId);
    }
}

