import database.*;
import models.*;
import models.criminals.*;
import engine.*;
import reports.*;
import data.SampleDataLoader;
import utils.*;
import java.util.*;

/**
 * Criminal Profiler Main Application
 * MAIN ENTRY POINT - CLI Interface
 */
public class CriminalProfilerApp {

    private CriminalDatabase database;
    private DatabaseManager dbManager;
    private SimpleLearningModel learningModel;
    private ProbabilityEngine engine;
    private Scanner scanner;

    public CriminalProfilerApp() {
        this.database = new CriminalDatabase();
        this.dbManager = new DatabaseManager(database);
        this.learningModel = new SimpleLearningModel();
        this.engine = new ProbabilityEngine(learningModel);
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        CriminalProfilerApp app = new CriminalProfilerApp();
        app.run();
    }

    public void run() {
        printWelcome();

        // Load data
        if (Utils.savedDataExists()) {
            System.out.print("\nSaved data found. Load it? (yes/no): ");
            String loadSaved = scanner.nextLine();
            if (loadSaved.equalsIgnoreCase("yes") || loadSaved.equalsIgnoreCase("y")) {
                Utils.loadAllData(database);
            } else {
                loadSampleDataPrompt();
            }
        } else {
            loadSampleDataPrompt();
        }

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("\nEnter choice: ");

            try {
                switch (choice) {
                    case 1:
                        viewAllCriminals();
                        break;
                    case 2:
                        addNewCriminal();
                        break;
                    case 3:
                        viewAllCrimeScenes();
                        break;
                    case 4:
                        createCrimeScene();
                        break;
                    case 5:
                        analyzeCrimeScene();
                        break;
                    case 6:
                        generateInvestigationReport();
                        break;
                    case 7:
                        viewDatabaseStatistics();
                        break;
                    case 8:
                        exportData();
                        break;
                    case 9:
                        saveData();
                        break;
                    case 0:
                        running = false;
                        Utils.saveAllData(database);
                        System.out.println("\n⏹ Shutting down Criminal Profiler System...");
                        System.out.println("✓ Data saved. System shut down successfully. Goodbye!");
                        break;
                    default:
                        System.out.println("❌ Invalid choice. Please try again.");
                }

            } catch (Exception e) {
                System.out.println("\n❌ Error: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private void loadSampleDataPrompt() {
        System.out.print("\nLoad sample data for testing? (yes/no): ");
        String loadData = scanner.nextLine();
        if (loadData.equalsIgnoreCase("yes") || loadData.equalsIgnoreCase("y")) {
            SampleDataLoader.loadSampleData(database);
            Utils.setCriminalCounter(9);
            Utils.setSceneCounter(4);
        }
    }

    private void printWelcome() {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     CRIMINAL PSYCHOLOGY PERSONALITY PROFILER              ║");
        System.out.println("║           AI-Powered Criminal Analysis System             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }

    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                      MAIN MENU");
        System.out.println("=".repeat(60));
        System.out.println("CRIMINAL MANAGEMENT:");
        System.out.println("  1. View All Criminals");
        System.out.println("  2. Add New Criminal");
        System.out.println("\nCRIME SCENE MANAGEMENT:");
        System.out.println("  3. View All Crime Scenes");
        System.out.println("  4. Create New Crime Scene");
        System.out.println("\nANALYSIS & REPORTS:");
        System.out.println("  5. Analyze Crime Scene (AI Profiling)");
        System.out.println("  6. Generate Investigation Report");
        System.out.println("\nSYSTEM:");
        System.out.println("  7. View Database Statistics");
        System.out.println("  8. Export Data");
        System.out.println("  9. Save Database");
        System.out.println("  0. Exit");
        System.out.println("=".repeat(60));
        System.out.println(database.getSummary());
    }

    // Option 1
    private void viewAllCriminals() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                  CRIMINAL DATABASE");
        System.out.println("=".repeat(60));

        List<Criminal> criminals = database.getAllCriminals();

        if (criminals.isEmpty()) {
            System.out.println("❌ No criminals in database.");
            return;
        }

        for (int i = 0; i < criminals.size(); i++) {
            Criminal c = criminals.get(i);
            System.out.println("\n" + (i + 1) + ". " + c.toString());
            System.out.println("   MO: " + c.getModusOperandi());
            System.out.println("   Locations: " + c.getKnownLocations());
            System.out.println("   Status: " + (c.isAtLarge() ? "⚠️ AT LARGE" : "✓ CAPTURED"));
        }
    }

    // Option 2
    private void addNewCriminal() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                  ADD NEW CRIMINAL");
        System.out.println("=".repeat(60));

        System.out.println("\nCriminal Types:");
        String[] types = DatabaseManager.getCriminalTypes();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        int typeChoice = getIntInput("\nSelect type (1-" + types.length + "): ");
        if (typeChoice < 1 || typeChoice > types.length) {
            System.out.println("❌ Invalid type.");
            return;
        }

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        int age = getIntInput("Enter age: ");

        System.out.print("Enter gender (Male/Female/Other): ");
        String gender = scanner.nextLine();

        try {
            String id = dbManager.addCriminalQuick(name, age, gender, types[typeChoice - 1]);
            System.out.println("\n✓ Criminal added successfully! ID: " + id);
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Failed to add criminal: " + e.getMessage());
        }
    }

    // Option 3
    private void viewAllCrimeScenes() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                    CRIME SCENES");
        System.out.println("=".repeat(60));

        List<CrimeScene> scenes = database.getAllCrimeScenes();

        if (scenes.isEmpty()) {
            System.out.println("❌ No crime scenes in database.");
            return;
        }

        for (int i = 0; i < scenes.size(); i++) {
            CrimeScene s = scenes.get(i);
            System.out.println("\n" + (i + 1) + ". " + s.toString());
            System.out.println("   Description: " + s.getDescription());
        }
    }

    // Option 4
    private void createCrimeScene() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("                CREATE CRIME SCENE");
        System.out.println("=".repeat(60));

        System.out.println("\nCrime Types:");
        String[] types = CrimeScene.getValidCrimeTypes();
        for (int i = 0; i < types.length; i++) {
            System.out.println((i + 1) + ". " + types[i]);
        }

        int typeChoice = getIntInput("\nSelect type (1-" + types.length + "): ");
        if (typeChoice < 1 || typeChoice > types.length) {
            System.out.println("❌ Invalid type.");
            return;
        }

        System.out.print("Enter location: ");
        String location = scanner.nextLine();

        try {
            String id = dbManager.addCrimeSceneQuick(types[typeChoice - 1], location);
            System.out.println("\n✓ Crime scene created! ID: " + id);

            System.out.print("\nAdd evidence now? (yes/no): ");
            if (scanner.nextLine().equalsIgnoreCase("yes")) {
                addEvidenceToScene(id);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\n❌ Failed to create crime scene: " + e.getMessage());
        }
    }

    private void addEvidenceToScene(String sceneId) {
        String[] evidenceTypes = Evidence.getValidEvidenceTypes();
        boolean addMore = true;

        while (addMore) {
            System.out.println("\nEvidence Types:");
            for (int i = 0; i < evidenceTypes.length; i++) {
                System.out.println((i + 1) + ". " + evidenceTypes[i]);
            }

            int typeChoice = getIntInput("\nSelect type (1-" + evidenceTypes.length + "): ");
            if (typeChoice < 1 || typeChoice > evidenceTypes.length) continue;

            System.out.print("Description: ");
            String desc = scanner.nextLine();

            try {
                String evidenceId = dbManager.addEvidenceToScene(sceneId,
                        evidenceTypes[typeChoice - 1], desc);
                System.out.println("✓ Evidence added: " + evidenceId);
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Failed to add evidence: " + e.getMessage());
            }

            System.out.print("\nAdd more evidence? (yes/no): ");
            addMore = scanner.nextLine().equalsIgnoreCase("yes");
        }
    }

    // Option 5 - MAIN ANALYSIS
    private void analyzeCrimeScene() {
        List<CrimeScene> scenes = database.getAllCrimeScenes();

        if (scenes.isEmpty()) {
            System.out.println("❌ No crime scenes to analyze.");
            return;
        }

        System.out.println("\nSelect Crime Scene:");
        for (int i = 0; i < scenes.size(); i++) {
            System.out.println((i + 1) + ". " + scenes.get(i).getSummary());
        }

        int choice = getIntInput("\nChoose scene (1-" + scenes.size() + "): ");
        if (choice < 1 || choice > scenes.size()) {
            System.out.println("❌ Invalid choice.");
            return;
        }

        CrimeScene scene = scenes.get(choice - 1);
        System.out.println("\n🧠 ANALYZING CRIME SCENE: " + scene.getSceneId());
        System.out.println("Using AI-powered profiling...\n");

        List<Criminal> criminals = database.getAllCriminals();
        List<Suspect> suspects = engine.analyzeCrimeScene(scene, criminals);

        System.out.println("=".repeat(60));
        System.out.println("              SUSPECT RANKING RESULTS");
        System.out.println("=".repeat(60));

        for (int i = 0; i < Math.min(5, suspects.size()); i++) {
            Suspect s = suspects.get(i);
            System.out.println("\n#" + (i + 1) + " " + s.toString());
            System.out.println("    " + s.getReasoning());
        }
    }

    // Option 6
    private void generateInvestigationReport() {
        List<CrimeScene> scenes = database.getAllCrimeScenes();

        if (scenes.isEmpty()) {
            System.out.println("❌ No crime scenes available.");
            return;
        }

        System.out.println("\nSelect scene for report:");
        for (int i = 0; i < scenes.size(); i++) {
            System.out.println((i + 1) + ". " + scenes.get(i).getSummary());
        }

        int choice = getIntInput("\nChoose (1-" + scenes.size() + "): ");
        if (choice < 1 || choice > scenes.size()) return;

        CrimeScene scene = scenes.get(choice - 1);
        List<Suspect> suspects = engine.analyzeCrimeScene(scene, database.getAllCriminals());

        String report = ReportGenerator.generateInvestigationReport(scene, suspects);
        System.out.println("\n" + report);

        System.out.print("\nSave to file? (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            try {
                String filename = "investigation_" + scene.getSceneId() + ".txt";
                ReportExporter.saveReportToFile(report, filename);
            } catch (Exception e) {
                System.out.println("❌ Error saving report: " + e.getMessage());
            }
        }
    }

    // Option 7
    private void viewDatabaseStatistics() {
        System.out.println("\n" + database.getStatisticsReport());
        System.out.println("\n" + learningModel.getLearningReport());
    }

    // Option 8
    private void exportData() {
        System.out.println("\n📤 EXPORT OPTIONS:");
        System.out.println("1. Export criminals to CSV");
        System.out.println("2. Export complete investigation package");

        int choice = getIntInput("\nChoose (1-2): ");

        try {
            if (choice == 1) {
                database.exportCriminalsToCSV("criminals_export.csv");
            } else if (choice == 2) {
                List<CrimeScene> scenes = database.getAllCrimeScenes();

                if (scenes.isEmpty()) {
                    System.out.println("❌ No scenes to export.");
                    return;
                }

                System.out.println("\nSelect scene:");
                for (int i = 0; i < scenes.size(); i++) {
                    System.out.println((i + 1) + ". " + scenes.get(i).getSummary());
                }

                int sceneChoice = getIntInput("\nChoose (1-" + scenes.size() + "): ");
                if (sceneChoice >= 1 && sceneChoice <= scenes.size()) {
                    CrimeScene scene = scenes.get(sceneChoice - 1);
                    List<Suspect> suspects = engine.analyzeCrimeScene(scene, database.getAllCriminals());
                    String report = ReportGenerator.generateInvestigationReport(scene, suspects);
                    ReportExporter.exportInvestigationPackage(scene, suspects, report, scene.getSceneId());
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Export failed: " + e.getMessage());
        }
    }

    // Option 9
    private void saveData() {
        if (Utils.saveAllData(database)) {
            System.out.println("✓ Database saved successfully!");
        } else {
            System.out.println("❌ Failed to save database.");
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("❌ Invalid input. Enter a number.");
                scanner.nextLine();
            }
        }
    }
}
