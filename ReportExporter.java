package reports;

import models.*;
import java.io.*;
import java.util.*;

/**
 * Report Exporter
 * Exports reports and investigation data to various formats.
 */
public class ReportExporter {

    /**
     * Save report to text file
     */
    public static void saveReportToFile(String report, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.print(report);
            System.out.println("✓ Report saved to " + filename);
        }
    }

    /**
     * Export suspects to CSV
     */
    public static void exportSuspectsToCSV(List<Suspect> suspects, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header
            writer.println("Rank,Criminal ID,Name,Type,Probability,Confidence,Danger Level,Status");

            // Data
            for (int i = 0; i < suspects.size(); i++) {
                Suspect suspect = suspects.get(i);
                var criminal = suspect.getCriminal();

                writer.printf("%d,%s,%s,%s,%.1f%%,%s,%s,%s%n",
                        i + 1,
                        criminal.getId(),
                        criminal.getName(),
                        criminal.getCriminalType(),
                        suspect.getProbabilityScore(),
                        suspect.getConfidence(),
                        criminal.getDangerLevel(),
                        criminal.isAtLarge() ? "At Large" : "Captured"
                );
            }

            System.out.println("✓ Suspects exported to " + filename);
        }
    }

    /**
     * Export evidence to CSV
     */
    public static void exportEvidenceToCSV(CrimeScene scene, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // Header
            writer.println("Evidence ID,Type,Description,Location,Collected Time");

            // Data
            for (Evidence evidence : scene.getEvidenceList()) {
                writer.printf("%s,%s,\"%s\",%s,%s%n",
                        evidence.getEvidenceId(),
                        evidence.getType(),
                        evidence.getDescription().replace("\"", "\"\""),
                        evidence.getLocation() != null ? evidence.getLocation() : "Unknown",
                        evidence.getFormattedCollectionTime()
                );
            }

            System.out.println("✓ Evidence exported to " + filename);
        }
    }

    /**
     * Export complete investigation package
     */
    public static void exportInvestigationPackage(CrimeScene scene, List<Suspect> suspects,
                                                  String report, String packageName) throws IOException {

        // Create directory for package
        File dir = new File("investigation_" + packageName);
        if (!dir.exists()) {
            dir.mkdir();
        }

        // Save main report
        saveReportToFile(report, dir.getPath() + "/full_report.txt");

        // Export suspects CSV
        exportSuspectsToCSV(suspects, dir.getPath() + "/suspects.csv");

        // Export evidence CSV
        exportEvidenceToCSV(scene, dir.getPath() + "/evidence.csv");

        // Create summary file
        createSummaryFile(scene, suspects, dir.getPath() + "/summary.txt");

        System.out.println("✓ Complete investigation package exported to: " + dir.getPath());
    }

    /**
     * Create summary file
     */
    private static void createSummaryFile(CrimeScene scene, List<Suspect> suspects,
                                          String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("═══════════════════════════════════════════");
            writer.println("     INVESTIGATION PACKAGE SUMMARY");
            writer.println("═══════════════════════════════════════════");
            writer.println();

            writer.println("CRIME SCENE INFORMATION:");
            writer.println("  Scene ID: " + scene.getSceneId());
            writer.println("  Crime Type: " + scene.getCrimeType());
            writer.println("  Location: " + scene.getLocation());
            writer.println("  Time of Crime: " + scene.getFormattedTimeOfCrime());
            writer.println("  Evidence Items: " + scene.getEvidenceCount());
            writer.println();

            writer.println("ANALYSIS RESULTS:");
            writer.println("  Total Suspects Identified: " + suspects.size());

            if (!suspects.isEmpty()) {
                Suspect top = suspects.get(0);
                writer.println("  Top Suspect: " + top.getCriminal().getName());
                writer.println("  Match Probability: " + String.format("%.1f%%", top.getProbabilityScore()));
                writer.println("  Confidence: " + top.getConfidence());
            }

            writer.println();
            writer.println("PACKAGE CONTENTS:");
            writer.println("  1. full_report.txt - Complete investigation report");
            writer.println("  2. suspects.csv - Ranked suspect list");
            writer.println("  3. evidence.csv - Evidence inventory");
            writer.println("  4. summary.txt - This file");
            writer.println();
            writer.println("═══════════════════════════════════════════");
        }
    }

    /**
     * Export to JSON format
     */
    public static void exportToJSON(CrimeScene scene, List<Suspect> suspects, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("{");
            writer.println("  \"crime_scene\": {");
            writer.println("    \"id\": \"" + scene.getSceneId() + "\",");
            writer.println("    \"type\": \"" + scene.getCrimeType() + "\",");
            writer.println("    \"location\": \"" + scene.getLocation() + "\",");
            writer.println("    \"time\": \"" + scene.getFormattedTimeOfCrime() + "\",");
            writer.println("    \"evidence_count\": " + scene.getEvidenceCount());
            writer.println("  },");

            writer.println("  \"suspects\": [");
            for (int i = 0; i < suspects.size(); i++) {
                Suspect s = suspects.get(i);
                var c = s.getCriminal();

                writer.println("    {");
                writer.println("      \"rank\": " + (i + 1) + ",");
                writer.println("      \"id\": \"" + c.getId() + "\",");
                writer.println("      \"name\": \"" + c.getName() + "\",");
                writer.println("      \"type\": \"" + c.getCriminalType() + "\",");
                writer.println("      \"probability\": " + s.getProbabilityScore() + ",");
                writer.println("      \"confidence\": \"" + s.getConfidence() + "\"");
                writer.print("    }");

                if (i < suspects.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }
            writer.println("  ]");
            writer.println("}");

            System.out.println("✓ Data exported to JSON: " + filename);
        }
    }

    /**
     * Export crime scene details to text
     */
    public static void exportSceneDetails(CrimeScene scene, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("═══════════════════════════════════════════");
            writer.println("         CRIME SCENE DETAILS");
            writer.println("═══════════════════════════════════════════");
            writer.println();

            writer.println("Scene ID: " + scene.getSceneId());
            writer.println("Crime Type: " + scene.getCrimeType());
            writer.println("Location: " + scene.getLocation());
            writer.println("Time of Crime: " + scene.getFormattedTimeOfCrime());
            writer.println("Discovery Time: " + scene.getFormattedDiscoveryTime());
            writer.println("Secured: " + (scene.isSecured() ? "Yes" : "No"));

            if (scene.getInvestigatorInCharge() != null) {
                writer.println("Investigator: " + scene.getInvestigatorInCharge());
            }

            writer.println();

            if (scene.getDescription() != null) {
                writer.println("Description:");
                writer.println(scene.getDescription());
                writer.println();
            }

            writer.println("EVIDENCE (" + scene.getEvidenceCount() + " items):");
            writer.println("─".repeat(43));

            int count = 1;
            for (Evidence evidence : scene.getEvidenceList()) {
                writer.println();
                writer.println("Evidence #" + count++);
                writer.println("  ID: " + evidence.getEvidenceId());
                writer.println("  Type: " + evidence.getType());
                writer.println("  Description: " + evidence.getDescription());
                writer.println("  Location: " + (evidence.getLocation() != null ? evidence.getLocation() : "Unknown"));
                writer.println("  Collected: " + evidence.getFormattedCollectionTime());
            }

            writer.println();
            writer.println("═══════════════════════════════════════════");

            System.out.println("✓ Scene details exported to " + filename);
        }
    }
}

