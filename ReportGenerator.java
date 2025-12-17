package reports;

import models.*;
import models.criminals.Criminal;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Report Generator
 * Generates comprehensive investigation reports for crime scenes.
 */
public class ReportGenerator {

    /**
     * Generate comprehensive investigation report
     */
    public static String generateInvestigationReport(CrimeScene scene, List<Suspect> suspects) {
        StringBuilder report = new StringBuilder();

        // Header
        report.append("═".repeat(80)).append("\n");
        report.append("                    CRIMINAL INVESTIGATION REPORT\n");
        report.append("═".repeat(80)).append("\n\n");

        // Report metadata
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        report.append("Report Generated: ").append(LocalDateTime.now().format(formatter)).append("\n");
        report.append("Report Type: Crime Scene Analysis with Suspect Profiling\n");
        report.append("\n");

        // Crime Scene Information
        report.append("─".repeat(80)).append("\n");
        report.append("1. CRIME SCENE INFORMATION\n");
        report.append("─".repeat(80)).append("\n\n");

        report.append("Scene ID:           ").append(scene.getSceneId()).append("\n");
        report.append("Crime Type:         ").append(scene.getCrimeType()).append("\n");
        report.append("Location:           ").append(scene.getLocation()).append("\n");
        report.append("Time of Crime:      ").append(scene.getFormattedTimeOfCrime()).append("\n");
        report.append("Discovery Time:     ").append(scene.getFormattedDiscoveryTime()).append("\n");

        if (scene.getInvestigatorInCharge() != null) {
            report.append("Investigator:       ").append(scene.getInvestigatorInCharge()).append("\n");
        }

        if (scene.getVictimProfile() != null && !scene.getVictimProfile().isEmpty()) {
            report.append("Victim Profile:     ").append(scene.getVictimProfile()).append("\n");
        }

        if (scene.getWeatherConditions() != null && !scene.getWeatherConditions().isEmpty()) {
            report.append("Weather:            ").append(scene.getWeatherConditions()).append("\n");
        }

        report.append("Scene Secured:      ").append(scene.isSecured() ? "Yes" : "No").append("\n");

        if (scene.getDescription() != null && !scene.getDescription().isEmpty()) {
            report.append("\nDescription:\n");
            report.append(wrapText(scene.getDescription(), 80)).append("\n");
        }

        // Scene Characteristics
        if (!scene.getSceneCharacteristics().isEmpty()) {
            report.append("\nScene Characteristics:\n");
            for (var entry : scene.getSceneCharacteristics().entrySet()) {
                report.append("  • ").append(entry.getKey()).append(": ")
                        .append(entry.getValue()).append("\n");
            }
        }

        report.append("\n");

        // Evidence Section
        report.append("─".repeat(80)).append("\n");
        report.append("2. EVIDENCE COLLECTED\n");
        report.append("─".repeat(80)).append("\n\n");

        List<Evidence> evidenceList = scene.getEvidenceList();

        if (evidenceList.isEmpty()) {
            report.append("No evidence collected at this scene.\n");
        } else {
            report.append("Total Evidence Items: ").append(evidenceList.size()).append("\n\n");

            for (int i = 0; i < evidenceList.size(); i++) {
                Evidence evidence = evidenceList.get(i);
                report.append(String.format("Evidence #%d:\n", i + 1));
                report.append("  ID:          ").append(evidence.getEvidenceId()).append("\n");
                report.append("  Type:        ").append(evidence.getType()).append("\n");
                report.append("  Description: ").append(evidence.getDescription()).append("\n");
                report.append("  Location:    ").append(evidence.getLocation() != null ?
                        evidence.getLocation() : "Unknown").append("\n");
                report.append("  Collected:   ").append(evidence.getFormattedCollectionTime()).append("\n");

                if (evidence.getCollectedBy() != null) {
                    report.append("  Collected By: ").append(evidence.getCollectedBy()).append("\n");
                }

                if (!evidence.getAttributes().isEmpty()) {
                    report.append("  Attributes:\n");
                    for (var attr : evidence.getAttributes().entrySet()) {
                        report.append("    - ").append(attr.getKey()).append(": ")
                                .append(attr.getValue()).append("\n");
                    }
                }

                report.append("\n");
            }
        }

        // Suspect Analysis
        report.append("─".repeat(80)).append("\n");
        report.append("3. SUSPECT ANALYSIS & PROFILING\n");
        report.append("─".repeat(80)).append("\n\n");

        if (suspects.isEmpty()) {
            report.append("No suspects identified matching the crime scene profile.\n");
        } else {
            report.append("Total Suspects Analyzed: ").append(suspects.size()).append("\n");
            report.append("Top Suspects Listed Below (Ranked by Match Probability)\n\n");

            // List top 10 suspects
            int displayCount = Math.min(10, suspects.size());

            for (int i = 0; i < displayCount; i++) {
                Suspect suspect = suspects.get(i);
                Criminal criminal = suspect.getCriminal();

                report.append(String.format("SUSPECT #%d\n", i + 1));
                report.append("─".repeat(40)).append("\n");
                report.append("Name:              ").append(criminal.getName()).append("\n");
                report.append("ID:                ").append(criminal.getId()).append("\n");
                report.append("Type:              ").append(criminal.getCriminalType()).append("\n");
                report.append("Age:               ").append(criminal.getAge()).append("\n");
                report.append("Gender:            ").append(criminal.getGender()).append("\n");
                report.append("Match Probability: ").append(String.format("%.1f%%",
                        suspect.getProbabilityScore())).append("\n");
                report.append("Confidence Level:  ").append(suspect.getConfidence()).append("\n");
                report.append("Danger Level:      ").append(criminal.getDangerLevel()).append("\n");
                report.append("Risk Factor:       ").append(String.format("%.1f%%",
                        criminal.getRiskFactor() * 100)).append("\n");
                report.append("Status:            ").append(criminal.isAtLarge() ?
                        "AT LARGE ⚠️" : "CAPTURED ✓").append("\n\n");

                report.append("Modus Operandi:\n");
                report.append(wrapText(criminal.getModusOperandi(), 80)).append("\n\n");

                if (criminal.getPsychologicalProfile() != null) {
                    report.append("Psychological Profile:\n");
                    report.append(wrapText(criminal.getPsychologicalProfile(), 80)).append("\n\n");
                }

                report.append("Known Locations:\n");
                for (String location : criminal.getKnownLocations()) {
                    report.append("  • ").append(location).append("\n");
                }

                report.append("\nPrior Crimes:\n");
                if (criminal.getPriorCrimes().isEmpty()) {
                    report.append("  None on record\n");
                } else {
                    for (String crime : criminal.getPriorCrimes()) {
                        report.append("  • ").append(crime).append("\n");
                    }
                }

                report.append("\nAnalysis Reasoning:\n");
                report.append(wrapText(suspect.getReasoning(), 80)).append("\n");

                if (!suspect.getMatchingFactors().isEmpty()) {
                    report.append("\nMatching Factors:\n");
                    for (String factor : suspect.getMatchingFactors()) {
                        report.append("  ✓ ").append(factor).append("\n");
                    }
                }

                report.append("\n");
            }
        }

        // Recommendations
        report.append("─".repeat(80)).append("\n");
        report.append("4. INVESTIGATIVE RECOMMENDATIONS\n");
        report.append("─".repeat(80)).append("\n\n");

        if (!suspects.isEmpty()) {
            Suspect topSuspect = suspects.get(0);

            report.append("Primary Focus:\n");
            report.append("  Priority suspect: ").append(topSuspect.getCriminal().getName())
                    .append(" (").append(String.format("%.1f%%", topSuspect.getProbabilityScore()))
                    .append(" match)\n\n");

            if (topSuspect.getProbabilityScore() >= 70) {
                report.append("  Recommendation: HIGH PRIORITY - Immediate investigation warranted\n");
            } else if (topSuspect.getProbabilityScore() >= 40) {
                report.append("  Recommendation: MEDIUM PRIORITY - Further investigation recommended\n");
            } else {
                report.append("  Recommendation: LOW PRIORITY - Consider as potential lead\n");
            }

            report.append("\nSuggested Actions:\n");
            report.append("  1. Conduct detailed background check on top suspects\n");
            report.append("  2. Verify alibis for time of crime\n");
            report.append("  3. Cross-reference with known locations and MO patterns\n");
            report.append("  4. Review all evidence for additional connections\n");

            if (topSuspect.getCriminal().isAtLarge()) {
                report.append("  5. ALERT: Top suspect is currently AT LARGE - Exercise caution\n");
            }
        } else {
            report.append("No specific suspects identified. Recommend:\n");
            report.append("  1. Expand search parameters\n");
            report.append("  2. Collect additional evidence\n");
            report.append("  3. Review case details for missed connections\n");
            report.append("  4. Consider consulting with specialized units\n");
        }

        report.append("\n");

        // Footer
        report.append("═".repeat(80)).append("\n");
        report.append("                         END OF REPORT\n");
        report.append("═".repeat(80)).append("\n");
        report.append("\nThis report is computer-generated using AI-powered criminal profiling.\n");
        report.append("All recommendations should be verified by qualified investigators.\n");

        return report.toString();
    }

    /**
     * Wrap text to specified width
     */
    private static String wrapText(String text, int width) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        StringBuilder wrapped = new StringBuilder();
        String[] words = text.split("\\s+");
        int lineLength = 0;

        for (String word : words) {
            if (lineLength + word.length() + 1 > width) {
                wrapped.append("\n");
                lineLength = 0;
            }

            if (lineLength > 0) {
                wrapped.append(" ");
                lineLength++;
            }

            wrapped.append(word);
            lineLength += word.length();
        }

        return wrapped.toString();
    }

    /**
     * Generate summary report
     */
    public static String generateSummaryReport(CrimeScene scene, List<Suspect> suspects) {
        StringBuilder summary = new StringBuilder();

        summary.append("CASE SUMMARY\n");
        summary.append("─".repeat(40)).append("\n");
        summary.append("Scene: ").append(scene.getSceneId()).append("\n");
        summary.append("Type: ").append(scene.getCrimeType()).append("\n");
        summary.append("Location: ").append(scene.getLocation()).append("\n");
        summary.append("Evidence: ").append(scene.getEvidenceCount()).append(" items\n");
        summary.append("Suspects: ").append(suspects.size()).append(" identified\n");

        if (!suspects.isEmpty()) {
            summary.append("\nTop Suspect: ").append(suspects.get(0).getCriminal().getName())
                    .append(" (").append(String.format("%.1f%%", suspects.get(0).getProbabilityScore()))
                    .append(")\n");
        }

        return summary.toString();
    }
}
