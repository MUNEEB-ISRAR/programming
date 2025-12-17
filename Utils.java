package utils;

import database.CriminalDatabase;
import models.*;
import models.criminals.Criminal;

import java.io.*;
import java.util.regex.Pattern;

/**
 * Utility Class
 * Contains validation, ID generation, and data persistence functions.
 */
public class Utils {

    // ==================== ID GENERATION ====================

    private static int criminalCounter = 1;
    private static int sceneCounter = 1;
    private static int evidenceCounter = 1;

    /**
     * Generate unique criminal ID
     */
    public static String generateCriminalId() {
        return String.format("CRIM%05d", criminalCounter++);
    }

    /**
     * Generate unique scene ID
     */
    public static String generateSceneId() {
        return String.format("SCENE%04d", sceneCounter++);
    }

    /**
     * Generate unique evidence ID
     */
    public static String generateEvidenceId(String sceneId) {
        return String.format("%s-E%03d", sceneId, evidenceCounter++);
    }

    /**
     * Reset evidence counter for new scene
     */
    public static void resetEvidenceCounter() {
        evidenceCounter = 1;
    }

    /**
     * Set criminal counter (for loading saved data)
     */
    public static void setCriminalCounter(int count) {
        criminalCounter = count;
    }

    /**
     * Set scene counter (for loading saved data)
     */
    public static void setSceneCounter(int count) {
        sceneCounter = count;
    }

    // ==================== VALIDATION ====================

    /**
     * Validate name
     */
    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        if (name.length() < 2 || name.length() > 50) {
            return false;
        }

        return Pattern.matches("[a-zA-Z\\s'-]+", name);
    }

    /**
     * Validate age
     */
    public static boolean isValidAge(int age) {
        return age >= 10 && age <= 120;
    }

    /**
     * Validate gender
     */
    public static boolean isValidGender(String gender) {
        if (gender == null || gender.trim().isEmpty()) {
            return false;
        }

        String normalized = gender.trim().toLowerCase();
        return normalized.equals("male") || normalized.equals("female") || normalized.equals("other");
    }

    /**
     * Validate ID format
     */
    public static boolean isValidId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        return Pattern.matches("CRIM\\d{5}", id) || Pattern.matches("SCENE\\d{4}", id);
    }

    /**
     * Validate location
     */
    public static boolean isValidLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return false;
        }

        return location.length() >= 3 && location.length() <= 100;
    }

    /**
     * Validate description
     */
    public static boolean isValidDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return false;
        }

        return description.length() >= 10 && description.length() <= 1000;
    }

    /**
     * Sanitize input string
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }

        return input.trim().replaceAll("[<>\"']", "");
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    /**
     * Validate phone number
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        String cleaned = phone.replaceAll("[\\s()-]", "");
        return Pattern.matches("\\d{10,15}", cleaned);
    }

    // ==================== DATA PERSISTENCE ====================

    private static final String DATA_FILE = "criminal_database.dat";

    /**
     * Save all database data to file
     */
    public static boolean saveAllData(CriminalDatabase database) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(database);
            System.out.println("✓ Database saved successfully");
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error saving database: " + e.getMessage());
            return false;
        }
    }

    /**
     * Load all database data from file
     */
    public static boolean loadAllData(CriminalDatabase database) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            CriminalDatabase loaded = (CriminalDatabase) ois.readObject();

            // Copy data to provided database
            for (Criminal criminal : loaded.getAllCriminals()) {
                database.addCriminal(criminal);
            }

            for (CrimeScene scene : loaded.getAllCrimeScenes()) {
                database.addCrimeScene(scene);
            }

            System.out.println("✓ Database loaded successfully");
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠ Could not load saved data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if saved data exists
     */
    public static boolean savedDataExists() {
        File file = new File(DATA_FILE);
        return file.exists() && file.length() > 0;
    }

    /**
     * Delete saved data file
     */
    public static boolean deleteSavedData() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * Get saved data file size
     */
    public static long getSavedDataSize() {
        File file = new File(DATA_FILE);
        return file.exists() ? file.length() : 0;
    }
}

