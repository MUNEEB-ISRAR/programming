package models;

import models.criminals.Criminal;
import java.util.*;

/**
 * Suspect Model
 * Represents a criminal with calculated probability and analysis.
 */
public class Suspect implements Comparable<Suspect> {

    private Criminal criminal;
    private double probabilityScore;
    private Map<String, Double> featureScores;
    private List<String> matchingFactors;
    private String reasoning;

    /**
     * Constructor
     */
    public Suspect(Criminal criminal) {
        this.criminal = criminal;
        this.probabilityScore = 0.0;
        this.featureScores = new HashMap<>();
        this.matchingFactors = new ArrayList<>();
        this.reasoning = "";
    }

    // ==================== GETTERS ====================

    public Criminal getCriminal() {
        return criminal;
    }

    public double getProbabilityScore() {
        return probabilityScore;
    }

    public Map<String, Double> getFeatureScores() {
        return new HashMap<>(featureScores);
    }

    public List<String> getMatchingFactors() {
        return new ArrayList<>(matchingFactors);
    }

    public String getReasoning() {
        return reasoning;
    }

    // ==================== SCORING ====================

    /**
     * Add feature score
     */
    public void addFeatureScore(String feature, double score) {
        featureScores.put(feature, score);
        recalculateProbability();
    }

    /**
     * Add matching factor
     */
    public void addMatchingFactor(String factor) {
        if (!matchingFactors.contains(factor)) {
            matchingFactors.add(factor);
        }
    }

    /**
     * Set reasoning
     */
    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }

    /**
     * Recalculate total probability
     */
    private void recalculateProbability() {
        double total = 0.0;
        for (double score : featureScores.values()) {
            total += score;
        }
        this.probabilityScore = Math.min(100.0, total);
    }

    /**
     * Get confidence level
     */
    public String getConfidence() {
        if (probabilityScore >= 80) return "VERY HIGH";
        if (probabilityScore >= 60) return "HIGH";
        if (probabilityScore >= 40) return "MEDIUM";
        if (probabilityScore >= 20) return "LOW";
        return "VERY LOW";
    }

    // ==================== COMPARABLE ====================

    @Override
    public int compareTo(Suspect other) {
        return Double.compare(other.probabilityScore, this.probabilityScore);
    }

    @Override
    public String toString() {
        return String.format("%s - %.1f%% match (%s confidence)",
                criminal.getName(),
                probabilityScore,
                getConfidence());
    }
}

