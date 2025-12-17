package engine;

import models.*;
import models.criminals.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Probability Engine
 * Analyzes crime scenes and calculates suspect probabilities.
 * Uses the learning model's adaptive weights for scoring.
 */
public class ProbabilityEngine {

    private SimpleLearningModel learningModel;

    /**
     * Constructor with learning model
     */
    public ProbabilityEngine(SimpleLearningModel learningModel) {
        if (learningModel == null) {
            throw new IllegalArgumentException("Learning model cannot be null");
        }
        this.learningModel = learningModel;
    }

    /**
     * Constructor without learning model (creates new one)
     */
    public ProbabilityEngine() {
        this.learningModel = new SimpleLearningModel();
    }

    // ==================== MAIN ANALYSIS METHOD ====================

    /**
     * Analyze crime scene and rank suspects
     */
    public List<Suspect> analyzeCrimeScene(CrimeScene scene, List<Criminal> criminals) {
        if (scene == null) {
            throw new IllegalArgumentException("Crime scene cannot be null");
        }

        if (criminals == null || criminals.isEmpty()) {
            return new ArrayList<>();
        }

        List<Suspect> suspects = new ArrayList<>();

        // Analyze each criminal
        for (Criminal criminal : criminals) {
            Suspect suspect = new Suspect(criminal);
            calculateSuspectScore(suspect, scene);
            suspects.add(suspect);
        }

        // Sort by probability (highest first)
        Collections.sort(suspects);
        return suspects;
    }

    // ==================== SCORING METHODS ====================

    /**
     * Calculate comprehensive score for a suspect
     */
    private void calculateSuspectScore(Suspect suspect, CrimeScene scene) {
        Criminal criminal = suspect.getCriminal();
        StringBuilder reasoning = new StringBuilder();

        // Rule 1: Crime Type Match
        double crimeTypeScore = scoreCrimeTypeMatch(criminal, scene);
        if (crimeTypeScore > 0) {
            suspect.addFeatureScore("CRIME_TYPE_MATCH", crimeTypeScore);
            suspect.addMatchingFactor("Crime type matches");
            reasoning.append("Crime type matches profile. ");
        }

        // Rule 2: Location Proximity
        double locationScore = scoreLocationProximity(criminal, scene);
        if (locationScore > 0) {
            suspect.addFeatureScore("LOCATION_PROXIMITY", locationScore);
            suspect.addMatchingFactor("Known to operate in area");
            reasoning.append("Operates in this location. ");
        }

        // Rule 3: MO Similarity
        double moScore = scoreMOSimilarity(criminal, scene);
        if (moScore > 0) {
            suspect.addFeatureScore("MO_SIMILARITY", moScore);
            suspect.addMatchingFactor("MO matches");
            reasoning.append("MO matches scene characteristics. ");
        }

        // Rule 4: Evidence Matches
        scoreEvidenceMatches(suspect, criminal, scene, reasoning);

        // Rule 5: Prior Crimes
        double priorScore = scorePriorCrimes(criminal, scene);
        if (priorScore > 0) {
            suspect.addFeatureScore("PRIOR_CRIMES", priorScore);
            suspect.addMatchingFactor("History of similar crimes");
            reasoning.append("Has committed similar crimes. ");
        }

        // Rule 6: Victim Profile Match
        double victimScore = scoreVictimProfile(criminal, scene);
        if (victimScore > 0) {
            suspect.addFeatureScore("VICTIM_PROFILE_MATCH", victimScore);
            suspect.addMatchingFactor("Victim profile matches");
            reasoning.append("Victim profile matches. ");
        }

        // Rule 7: Type-Specific Scoring
        scoreTypeSpecificFeatures(suspect, criminal, scene, reasoning);

        // Set reasoning
        if (reasoning.length() == 0) {
            reasoning.append("No significant matches found.");
        }
        suspect.setReasoning(reasoning.toString().trim());
    }

    /**
     * Score crime type match
     */
    private double scoreCrimeTypeMatch(Criminal criminal, CrimeScene scene) {
        String crimeType = scene.getCrimeType().toLowerCase();
        String criminalType = criminal.getCriminalType().toLowerCase();

        // Direct type matches
        Map<String, String> typeMatches = new HashMap<>();
        typeMatches.put("murder", "serial killer");
        typeMatches.put("theft", "thief");
        typeMatches.put("assault", "violent offender");
        typeMatches.put("fraud", "fraudster");
        typeMatches.put("arson", "arsonist");
        typeMatches.put("kidnapping", "kidnapper");
        typeMatches.put("cybercrime", "cyber criminal");
        typeMatches.put("organizedcrime", "organized crime boss");
        typeMatches.put("drugtrafficking", "drug trafficker");
        typeMatches.put("humantrafficking", "human trafficker");
        typeMatches.put("robbery", "robber");
        typeMatches.put("sexualassault", "sexual offender");
        typeMatches.put("terrorism", "terrorist");
        typeMatches.put("moneylaundering", "money launderer");

        for (Map.Entry<String, String> entry : typeMatches.entrySet()) {
            if (crimeType.contains(entry.getKey()) && criminalType.contains(entry.getValue())) {
                return learningModel.getWeight("CRIME_TYPE_MATCH");
            }
        }

        return 0.0;
    }

    /**
     * Score location proximity
     */
    private double scoreLocationProximity(Criminal criminal, CrimeScene scene) {
        if (criminal.operatesInLocation(scene.getLocation())) {
            return learningModel.getWeight("LOCATION_PROXIMITY");
        }
        return 0.0;
    }

    /**
     * Score MO similarity
     */
    private double scoreMOSimilarity(Criminal criminal, CrimeScene scene) {
        String mo = criminal.getModusOperandi().toLowerCase();
        String organized = scene.getCharacteristic("organization");

        if (organized != null) {
            if (organized.equalsIgnoreCase("organized") && mo.contains("organized")) {
                return learningModel.getWeight("MO_SIMILARITY");
            }

            if (organized.equalsIgnoreCase("disorganized") &&
                    (mo.contains("disorganized") || mo.contains("impulsive"))) {
                return learningModel.getWeight("MO_SIMILARITY");
            }
        }

        return 0.0;
    }

    /**
     * Score all evidence matches
     */
    private void scoreEvidenceMatches(Suspect suspect, Criminal criminal,
                                      CrimeScene scene, StringBuilder reasoning) {

        for (Evidence evidence : scene.getEvidenceList()) {
            String type = evidence.getType();

            switch (type.toUpperCase()) {
                case "WEAPON":
                    if (criminal instanceof ViolentOffender) {
                        ViolentOffender vo = (ViolentOffender) criminal;
                        String weaponType = evidence.getAttribute("type");
                        if (weaponType != null && vo.getWeaponPreference() != null &&
                                vo.getWeaponPreference().toLowerCase().contains(weaponType.toLowerCase())) {
                            suspect.addFeatureScore("WEAPON_MATCH",
                                    learningModel.getWeight("WEAPON_MATCH"));
                            suspect.addMatchingFactor("Weapon preference matches");
                            reasoning.append("Weapon type matches preference. ");
                        }
                    } else if (criminal instanceof Robber) {
                        Robber robber = (Robber) criminal;
                        String weaponType = evidence.getAttribute("type");
                        if (weaponType != null && robber.getWeaponType() != null &&
                                robber.getWeaponType().toLowerCase().contains(weaponType.toLowerCase())) {
                            suspect.addFeatureScore("WEAPON_MATCH",
                                    learningModel.getWeight("WEAPON_MATCH"));
                            suspect.addMatchingFactor("Weapon type matches");
                            reasoning.append("Weapon matches. ");
                        }
                    }
                    break;

                case "DIGITAL":
                    if (criminal instanceof CyberCriminal) {
                        suspect.addFeatureScore("DIGITAL_EVIDENCE",
                                learningModel.getWeight("DIGITAL_EVIDENCE"));
                        suspect.addMatchingFactor("Digital evidence present");
                        reasoning.append("Digital evidence links to cyber activity. ");
                    }
                    break;

                case "SURVEILLANCE":
                    suspect.addFeatureScore("SURVEILLANCE_FOOTAGE",
                            learningModel.getWeight("SURVEILLANCE_FOOTAGE") * 0.5);
                    suspect.addMatchingFactor("Surveillance available");
                    break;

                case "FINANCIAL":
                    if (criminal instanceof Fraudster || criminal instanceof MoneyLaunderer) {
                        suspect.addFeatureScore("FINANCIAL_RECORDS",
                                learningModel.getWeight("FINANCIAL_RECORDS"));
                        suspect.addMatchingFactor("Financial records present");
                        reasoning.append("Financial evidence relevant. ");
                    }
                    break;

                case "WITNESS":
                    suspect.addFeatureScore("WITNESS_TESTIMONY",
                            learningModel.getWeight("WITNESS_TESTIMONY") * 0.7);
                    suspect.addMatchingFactor("Witness testimony");
                    break;
            }
        }
    }

    /**
     * Score prior crimes
     */
    private double scorePriorCrimes(Criminal criminal, CrimeScene scene) {
        if (criminal.hasCommitted(scene.getCrimeType())) {
            return learningModel.getWeight("PRIOR_CRIMES");
        }
        return 0.0;
    }

    /**
     * Score victim profile match
     */
    private double scoreVictimProfile(Criminal criminal, CrimeScene scene) {
        String victimProfile = scene.getVictimProfile();
        if (victimProfile == null || victimProfile.trim().isEmpty()) {
            return 0.0;
        }

        // Check if criminal targets similar victims
        if (criminal instanceof SerialKiller) {
            SerialKiller sk = (SerialKiller) criminal;
            if (sk.getVictimType() != null &&
                    victimProfile.toLowerCase().contains(sk.getVictimType().toLowerCase())) {
                return learningModel.getWeight("VICTIM_PROFILE_MATCH");
            }
        }

        return 0.0;
    }

    /**
     * Score type-specific features
     */
    private void scoreTypeSpecificFeatures(Suspect suspect, Criminal criminal,
                                           CrimeScene scene, StringBuilder reasoning) {
        // Add danger level consideration
        String danger = criminal.getDangerLevel();
        if (danger.equals("EXTREME")) {
            suspect.addFeatureScore("DANGER_LEVEL",
                    learningModel.getWeight("DANGER_LEVEL"));
        }

        // Add risk factor
        double risk = criminal.getRiskFactor();
        if (risk > 0.7) {
            suspect.addFeatureScore("RISK_FACTOR",
                    learningModel.getWeight("RISK_FACTOR") * risk);
        }
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Get top N suspects
     */
    public List<Suspect> getTopSuspects(CrimeScene scene, List<Criminal> criminals, int count) {
        List<Suspect> all = analyzeCrimeScene(scene, criminals);
        return all.stream().limit(count).collect(Collectors.toList());
    }

    /**
     * Get learning model
     */
    public SimpleLearningModel getLearningModel() {
        return learningModel;
    }

    /**
     * Set learning model
     */
    public void setLearningModel(SimpleLearningModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Learning model cannot be null");
        }
        this.learningModel = model;
    }
}

