package engine;

import models.Suspect;
import java.io.*;
import java.util.*;

/**
 * Simple Learning Model
 * Manages feature weights for criminal profiling analysis.
 * Weights determine the importance of different evidence types and patterns.
 */
public class SimpleLearningModel {

    // Feature weights
    private Map<String, Double> weights;

    // Weight constraints
    private static final double MIN_WEIGHT = 5.0;
    private static final double MAX_WEIGHT = 50.0;

    /**
     * Constructor - initializes with default weights
     */
    public SimpleLearningModel() {
        this.weights = new LinkedHashMap<>();
        initializeDefaultWeights();
    }

    /**
     * Initialize default weights
     */
    private void initializeDefaultWeights() {
        // Crime matching weights
        weights.put("CRIME_TYPE_MATCH", 25.0);
        weights.put("MO_SIMILARITY", 20.0);

        // Location weights
        weights.put("LOCATION_PROXIMITY", 20.0);

        // Evidence weights
        weights.put("WEAPON_MATCH", 20.0);
        weights.put("DIGITAL_EVIDENCE", 15.0);
        weights.put("WITNESS_TESTIMONY", 12.0);
        weights.put("SURVEILLANCE_FOOTAGE", 18.0);
        weights.put("FINANCIAL_RECORDS", 15.0);

        // Pattern weights
        weights.put("PRIOR_CRIMES", 15.0);
        weights.put("VICTIM_PROFILE_MATCH", 10.0);
        weights.put("TIME_PATTERN", 10.0);
        weights.put("SCENE_ORGANIZATION", 12.0);

        // Behavioral weights
        weights.put("RISK_FACTOR", 8.0);
        weights.put("DANGER_LEVEL", 10.0);
    }

    // ==================== WEIGHT ACCESS ====================

    /**
     * Get current weight for a feature
     */
    public double getWeight(String feature) {
        return weights.getOrDefault(feature, 10.0); // Default 10.0 if not found
    }

    /**
     * Get all current weights
     */
    public Map<String, Double> getAllWeights() {
        return new LinkedHashMap<>(weights);
    }

    /**
     * Manually set a weight
     */
    public void setWeight(String feature, double weight) {
        if (weight < MIN_WEIGHT || weight > MAX_WEIGHT) {
            throw new IllegalArgumentException(
                    String.format("Weight must be between %.1f and %.1f", MIN_WEIGHT, MAX_WEIGHT));
        }
        weights.put(feature, weight);
    }

    // ==================== STATISTICS ====================

    /**
     * Get learning statistics report
     */
    public String getLearningReport() {
        StringBuilder report = new StringBuilder();
        report.append("=== ANALYSIS MODEL WEIGHTS ===\n\n");
        report.append("FEATURE WEIGHTS:\n");

        List<Map.Entry<String, Double>> sortedWeights = new ArrayList<>(weights.entrySet());
        sortedWeights.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        for (Map.Entry<String, Double> entry : sortedWeights) {
            String feature = entry.getKey();
            double weight = entry.getValue();
            report.append(String.format("  %-25s: %5.1f\n", feature, weight));
        }

        return report.toString();
    }

    /**
     * Get most important features
     */
    public List<String> getMostReliableFeatures(int count) {
        return weights.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Get least important features
     */
    public List<String> getLeastReliableFeatures(int count) {
        return weights.entrySet().stream()
                .sorted((a, b) -> Double.compare(a.getValue(), b.getValue()))
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Reset to default weights
     */
    public void resetToDefaults() {
        initializeDefaultWeights();
        System.out.println("✓ Analysis model reset to default weights");
    }
}

