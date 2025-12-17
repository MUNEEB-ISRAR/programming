package database;

import models.*;
import models.criminals.Criminal;
import java.io.*;
import java.util.*;

/**
 * Criminal Database
 * Main storage and management system for criminals and crime scenes.
 */
public class CriminalDatabase implements Serializable {

    private Map<String, Criminal> criminals;
    private Map<String, CrimeScene> crimeScenes;

    /**
     * Constructor
     */
    public CriminalDatabase() {
        this.criminals = new HashMap<>();
        this.crimeScenes = new HashMap<>();
    }

    // ==================== CRIMINAL MANAGEMENT ====================

    /**
     * Add criminal to database
     */
    public void addCriminal(Criminal criminal) {
        if (criminal == null) {
            throw new IllegalArgumentException("Criminal cannot be null");
        }

        if (criminals.containsKey(criminal.getId())) {
            throw new IllegalArgumentException("Criminal with ID " + criminal.getId() + " already exists");
        }

        criminals.put(criminal.getId(), criminal);
    }

    /**
     * Remove criminal from database
     */
    public void removeCriminal(String criminalId) {
        if (criminalId == null || criminalId.trim().isEmpty()) {
            throw new IllegalArgumentException("Criminal ID cannot be empty");
        }

        if (!criminals.containsKey(criminalId)) {
            throw new IllegalArgumentException("Criminal not found: " + criminalId);
        }

        criminals.remove(criminalId);
    }

    /**
     * Get criminal by ID
     */
    public Criminal getCriminal(String criminalId) {
        return criminals.get(criminalId);
    }

    /**
     * Get all criminals
     */
    public List<Criminal> getAllCriminals() {
        return new ArrayList<>(criminals.values());
    }

    /**
     * Get criminal count
     */
    public int getCriminalCount() {
        return criminals.size();
    }

    /**
     * Check if criminal exists
     */
    public boolean hasCriminal(String criminalId) {
        return criminals.containsKey(criminalId);
    }

    /**
     * Update criminal
     */
    public void updateCriminal(Criminal criminal) {
        if (criminal == null) {
            throw new IllegalArgumentException("Criminal cannot be null");
        }

        if (!criminals.containsKey(criminal.getId())) {
            throw new IllegalArgumentException("Criminal not found: " + criminal.getId());
        }

        criminals.put(criminal.getId(), criminal);
    }

    // ==================== CRIME SCENE MANAGEMENT ====================

    /**
     * Add crime scene to database
     */
    public void addCrimeScene(CrimeScene scene) {
        if (scene == null) {
            throw new IllegalArgumentException("Crime scene cannot be null");
        }

        if (crimeScenes.containsKey(scene.getSceneId())) {
            throw new IllegalArgumentException("Crime scene with ID " + scene.getSceneId() + " already exists");
        }

        crimeScenes.put(scene.getSceneId(), scene);
    }

    /**
     * Remove crime scene from database
     */
    public void removeCrimeScene(String sceneId) {
        if (sceneId == null || sceneId.trim().isEmpty()) {
            throw new IllegalArgumentException("Scene ID cannot be empty");
        }

        if (!crimeScenes.containsKey(sceneId)) {
            throw new IllegalArgumentException("Crime scene not found: " + sceneId);
        }

        crimeScenes.remove(sceneId);
    }

    /**
     * Get crime scene by ID
     */
    public CrimeScene getCrimeScene(String sceneId) {
        return crimeScenes.get(sceneId);
    }

    /**
     * Get all crime scenes
     */
    public List<CrimeScene> getAllCrimeScenes() {
        return new ArrayList<>(crimeScenes.values());
    }

    /**
     * Get crime scene count
     */
    public int getSceneCount() {
        return crimeScenes.size();
    }

    /**
     * Check if crime scene exists
     */
    public boolean hasCrimeScene(String sceneId) {
        return crimeScenes.containsKey(sceneId);
    }

    // ==================== SEARCH OPERATIONS ====================

    /**
     * Search criminals by name
     */
    public List<Criminal> searchByName(String name) {
        List<Criminal> results = new ArrayList<>();
        String searchTerm = name.toLowerCase();

        for (Criminal criminal : criminals.values()) {
            if (criminal.getName().toLowerCase().contains(searchTerm)) {
                results.add(criminal);
            }
        }

        return results;
    }

    /**
     * Search criminals by type
     */
    public List<Criminal> searchByType(String type) {
        List<Criminal> results = new ArrayList<>();
        String searchType = type.toLowerCase();

        for (Criminal criminal : criminals.values()) {
            if (criminal.getCriminalType().toLowerCase().contains(searchType)) {
                results.add(criminal);
            }
        }

        return results;
    }

    /**
     * Search crime scenes by type
     */
    public List<CrimeScene> searchScenesByType(String crimeType) {
        List<CrimeScene> results = new ArrayList<>();
        String searchType = crimeType.toLowerCase();

        for (CrimeScene scene : crimeScenes.values()) {
            if (scene.getCrimeType().toLowerCase().contains(searchType)) {
                results.add(scene);
            }
        }

        return results;
    }

    /**
     * Search crime scenes by location
     */
    public List<CrimeScene> searchScenesByLocation(String location) {
        List<CrimeScene> results = new ArrayList<>();
        String searchLocation = location.toLowerCase();

        for (CrimeScene scene : crimeScenes.values()) {
            if (scene.getLocation().toLowerCase().contains(searchLocation)) {
                results.add(scene);
            }
        }

        return results;
    }

    // ==================== STATISTICS ====================

    /**
     * Get database summary
     */
    public String getSummary() {
        return String.format("Database: %d Criminals | %d Crime Scenes",
                getCriminalCount(), getSceneCount());
    }

    /**
     * Get detailed statistics report
     */
    public String getStatisticsReport() {
        StringBuilder report = new StringBuilder();

        report.append("=== DATABASE STATISTICS ===\n\n");
        report.append("OVERVIEW:\n");
        report.append(String.format("  Total Criminals: %d\n", getCriminalCount()));
        report.append(String.format("  Total Crime Scenes: %d\n", getSceneCount()));

        // Criminal type breakdown
        Map<String, Integer> typeCount = new HashMap<>();
        for (Criminal criminal : criminals.values()) {
            String type = criminal.getCriminalType();
            typeCount.put(type, typeCount.getOrDefault(type, 0) + 1);
        }

        report.append("\nCRIMINAL TYPES:\n");
        for (Map.Entry<String, Integer> entry : typeCount.entrySet()) {
            report.append(String.format("  %-25s: %d\n", entry.getKey(), entry.getValue()));
        }

        // Crime type breakdown
        Map<String, Integer> crimeCount = new HashMap<>();
        for (CrimeScene scene : crimeScenes.values()) {
            String type = scene.getCrimeType();
            crimeCount.put(type, crimeCount.getOrDefault(type, 0) + 1);
        }

        report.append("\nCRIME TYPES:\n");
        for (Map.Entry<String, Integer> entry : crimeCount.entrySet()) {
            report.append(String.format("  %-25s: %d\n", entry.getKey(), entry.getValue()));
        }

        // Danger levels
        int extreme = 0, high = 0, medium = 0, low = 0;
        for (Criminal criminal : criminals.values()) {
            switch (criminal.getDangerLevel()) {
                case "EXTREME": extreme++; break;
                case "HIGH": high++; break;
                case "MEDIUM": medium++; break;
                case "LOW": low++; break;
            }
        }

        report.append("\nDANGER LEVELS:\n");
        report.append(String.format("  Extreme: %d\n", extreme));
        report.append(String.format("  High: %d\n", high));
        report.append(String.format("  Medium: %d\n", medium));
        report.append(String.format("  Low: %d\n", low));

        // Status
        int atLarge = 0, captured = 0;
        for (Criminal criminal : criminals.values()) {
            if (criminal.isAtLarge()) {
                atLarge++;
            } else {
                captured++;
            }
        }

        report.append("\nSTATUS:\n");
        report.append(String.format("  At Large: %d\n", atLarge));
        report.append(String.format("  Captured: %d\n", captured));

        return report.toString();
    }

    /**
     * Export criminals to CSV
     */
    public void exportCriminalsToCSV(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header
            writer.println("ID,Name,Age,Gender,Type,Danger Level,Status,MO");

            // Data
            for (Criminal criminal : criminals.values()) {
                writer.printf("%s,%s,%d,%s,%s,%s,%s,\"%s\"%n",
                        criminal.getId(),
                        criminal.getName(),
                        criminal.getAge(),
                        criminal.getGender(),
                        criminal.getCriminalType(),
                        criminal.getDangerLevel(),
                        criminal.isAtLarge() ? "At Large" : "Captured",
                        criminal.getModusOperandi().replace("\"", "\"\"")
                );
            }

            System.out.println(" Criminals exported to " + filename);
        }
    }

    /**
     * Clear all data
     */
    public void clearAll() {
        criminals.clear();
        crimeScenes.clear();
    }
}
