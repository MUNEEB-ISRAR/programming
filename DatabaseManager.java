package database;

import models.*;
import models.criminals.*;
import utils.Utils;
import java.util.*;

/**
 * Database Manager
 * Provides high-level operations for managing the criminal database.
 */
public class DatabaseManager {

    private CriminalDatabase database;

    /**
     * Constructor
     */
    public DatabaseManager(CriminalDatabase database) {
        if (database == null) {
            throw new IllegalArgumentException("Database cannot be null");
        }
        this.database = database;
    }

    /**
     * Get the database
     */
    public CriminalDatabase getDatabase() {
        return database;
    }

    // ==================== QUICK ADD METHODS ====================

    /**
     * Quick add criminal with minimal info
     */
    public String addCriminalQuick(String name, int age, String gender, String type) {
        // Validate inputs
        if (!Utils.isValidName(name)) {
            throw new IllegalArgumentException("Invalid name: " + name);
        }

        if (!Utils.isValidAge(age)) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }

        if (!Utils.isValidGender(gender)) {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }

        String id = Utils.generateCriminalId();
        Criminal criminal = createCriminalByType(id, name, age, gender, type);

        database.addCriminal(criminal);
        return id;
    }

    /**
     * Quick add crime scene with minimal info
     */
    public String addCrimeSceneQuick(String crimeType, String location) {
        if (!Utils.isValidLocation(location)) {
            throw new IllegalArgumentException("Invalid location: " + location);
        }

        String id = Utils.generateSceneId();
        CrimeScene scene = new CrimeScene(id, crimeType, location);

        database.addCrimeScene(scene);
        Utils.resetEvidenceCounter();

        return id;
    }

    /**
     * Add evidence to a scene
     */
    public String addEvidenceToScene(String sceneId, String type, String description) {
        CrimeScene scene = database.getCrimeScene(sceneId);

        if (scene == null) {
            throw new IllegalArgumentException("Crime scene not found: " + sceneId);
        }

        String evidenceId = Utils.generateEvidenceId(sceneId);
        Evidence evidence = new Evidence(evidenceId, type, description, scene.getLocation());

        scene.addEvidence(evidence);
        return evidenceId;
    }

    // ==================== CRIMINAL FACTORY ====================

    /**
     * Create criminal by type
     */
    private Criminal createCriminalByType(String id, String name, int age, String gender, String type) {
        switch (type.toLowerCase()) {
            case "serial killer":
                return new SerialKiller(id, name, age, gender);
            case "thief":
                return new Thief(id, name, age, gender);
            case "violent offender":
                return new ViolentOffender(id, name, age, gender);
            case "fraudster":
                return new Fraudster(id, name, age, gender);
            case "arsonist":
                return new Arsonist(id, name, age, gender);
            case "drug trafficker":
                return new DrugTrafficker(id, name, age, gender);
            case "cyber criminal":
                return new CyberCriminal(id, name, age, gender);
            case "robber":
                return new Robber(id, name, age, gender);
            case "kidnapper":
                return new Kidnapper(id, name, age, gender);
            case "money launderer":
                return new MoneyLaunderer(id, name, age, gender);
            case "organized crime boss":
                return new OrganizedCrimeBoss(id, name, age, gender);
            case "human trafficker":
                return new HumanTrafficker(id, name, age, gender);
            case "sexual offender":
                return new SexualOffender(id, name, age, gender);
            case "terrorist":
                return new Terrorist(id, name, age, gender);
            default:
                throw new IllegalArgumentException("Unknown criminal type: " + type);
        }
    }

    /**
     * Get all criminal types
     */
    public static String[] getCriminalTypes() {
        return new String[] {
                "Serial Killer", "Thief", "Violent Offender", "Fraudster", "Arsonist",
                "Drug Trafficker", "Cyber Criminal", "Robber", "Kidnapper", "Money Launderer",
                "Organized Crime Boss", "Human Trafficker", "Sexual Offender", "Terrorist"
        };
    }

    // ==================== SEARCH METHODS ====================

    /**
     * Search criminals by name
     */
    public List<Criminal> searchCriminalsByName(String name) {
        List<Criminal> results = new ArrayList<>();
        String searchTerm = name.toLowerCase();

        for (Criminal criminal : database.getAllCriminals()) {
            if (criminal.getName().toLowerCase().contains(searchTerm)) {
                results.add(criminal);
            }
        }

        return results;
    }

    /**
     * Search criminals by type
     */
    public List<Criminal> searchCriminalsByType(String type) {
        List<Criminal> results = new ArrayList<>();
        String searchType = type.toLowerCase();

        for (Criminal criminal : database.getAllCriminals()) {
            if (criminal.getCriminalType().toLowerCase().contains(searchType)) {
                results.add(criminal);
            }
        }

        return results;
    }

    /**
     * Search criminals by location
     */
    public List<Criminal> searchCriminalsByLocation(String location) {
        List<Criminal> results = new ArrayList<>();

        for (Criminal criminal : database.getAllCriminals()) {
            if (criminal.operatesInLocation(location)) {
                results.add(criminal);
            }
        }

        return results;
    }

    /**
     * Get criminals at large
     */
    public List<Criminal> getCriminalsAtLarge() {
        List<Criminal> results = new ArrayList<>();

        for (Criminal criminal : database.getAllCriminals()) {
            if (criminal.isAtLarge()) {
                results.add(criminal);
            }
        }

        return results;
    }

    /**
     * Get high danger criminals
     */
    public List<Criminal> getHighDangerCriminals() {
        List<Criminal> results = new ArrayList<>();

        for (Criminal criminal : database.getAllCriminals()) {
            if (criminal.getDangerLevel().equals("EXTREME") ||
                    criminal.getDangerLevel().equals("HIGH")) {
                results.add(criminal);
            }
        }

        return results;
    }

    // ==================== STATISTICS ====================

    /**
     * Get statistics by criminal type
     */
    public Map<String, Integer> getCriminalTypeStatistics() {
        Map<String, Integer> stats = new HashMap<>();

        for (Criminal criminal : database.getAllCriminals()) {
            String type = criminal.getCriminalType();
            stats.put(type, stats.getOrDefault(type, 0) + 1);
        }

        return stats;
    }

    /**
     * Get statistics by crime type
     */
    public Map<String, Integer> getCrimeTypeStatistics() {
        Map<String, Integer> stats = new HashMap<>();

        for (CrimeScene scene : database.getAllCrimeScenes()) {
            String type = scene.getCrimeType();
            stats.put(type, stats.getOrDefault(type, 0) + 1);
        }

        return stats;
    }

    /**
     * Get total evidence count
     */
    public int getTotalEvidenceCount() {
        int total = 0;
        for (CrimeScene scene : database.getAllCrimeScenes()) {
            total += scene.getEvidenceCount();
        }
        return total;
    }
}
