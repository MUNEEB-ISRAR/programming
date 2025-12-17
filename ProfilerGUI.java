package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import database.*;
import engine.*;
import data.SampleDataLoader;
import utils.Utils;

public class ProfilerGUI extends Application {

    private CriminalDatabase database;
    private DatabaseManager dbManager;
    private SimpleLearningModel learningModel;
    private ProbabilityEngine engine;
    private BorderPane mainLayout;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        // Initialize system components
        database = new CriminalDatabase();
        dbManager = new DatabaseManager(database);
        learningModel = new SimpleLearningModel();
        engine = new ProbabilityEngine(learningModel);

        // Load saved data or sample data
        if (Utils.savedDataExists()) {
            Utils.loadAllData(database);
        } else {
            SampleDataLoader.loadSampleData(database);
            Utils.setCriminalCounter(9);
            Utils.setSceneCounter(4);
        }

        // Create main layout
        mainLayout = new BorderPane();

        // Create top bar
        VBox topBar = createTopBar();
        mainLayout.setTop(topBar);

        // Create navigation menu
        VBox navMenu = createNavigationMenu();
        mainLayout.setLeft(navMenu);

        // Create welcome screen
        VBox welcomeScreen = createWelcomeScreen();
        mainLayout.setCenter(welcomeScreen);

        // Create status bar
        HBox statusBar = createStatusBar();
        mainLayout.setBottom(statusBar);

        // Create scene
        Scene scene = new Scene(mainLayout, 1200, 700);

        // Set up stage
        primaryStage.setTitle("Criminal Psychology Personality Profiler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createTopBar() {
        VBox topBar = new VBox();
        topBar.setPadding(new Insets(20));
        topBar.setStyle("-fx-background-color: #1a252f;");

        Label title = new Label(" CRIMINAL PSYCHOLOGY PERSONALITY PROFILER");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label("AI-Powered Criminal Analysis System");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6;");

        topBar.getChildren().addAll(title, subtitle);
        return topBar;
    }

    private VBox createNavigationMenu() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(15));
        menu.setPrefWidth(220);
        menu.setStyle("-fx-background-color: #34495e;");

        Label menuTitle = new Label(" NAVIGATION");
        menuTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        Separator sep = new Separator();

        Button btnDashboard = createMenuButton(" Dashboard");
        Button btnCriminals = createMenuButton(" View Criminals");
        Button btnManageCriminals = createMenuButton(" Manage Criminals");
        Button btnAddCriminal = createMenuButton(" Add Criminal");
        Button btnScenes = createMenuButton(" Crime Scenes");
        Button btnAddScene = createMenuButton(" Add Crime Scene");
        Button btnAnalyze = createMenuButton(" AI Analysis");
        Button btnReports = createMenuButton(" Reports");
        Button btnStats = createMenuButton(" Statistics");

        btnDashboard.setOnAction(e -> showDashboard());
        btnCriminals.setOnAction(e -> showCriminalsView());
        btnManageCriminals.setOnAction(e -> showManageCriminalsView());
        btnAddCriminal.setOnAction(e -> showAddCriminalView());
        btnScenes.setOnAction(e -> showCrimeScenesView());
        btnAddScene.setOnAction(e -> showAddCrimeSceneView());
        btnAnalyze.setOnAction(e -> showAnalysisView());
        btnReports.setOnAction(e -> showReportsView());
        btnStats.setOnAction(e -> showStatsView());

        menu.getChildren().addAll(menuTitle, sep, btnDashboard, btnCriminals, btnManageCriminals,
                btnAddCriminal, btnScenes, btnAddScene, btnAnalyze, btnReports, btnStats);

        return menu;
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(190);
        btn.setPrefHeight(35);
        btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-font-size: 14px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px;"));
        return btn;
    }

    private VBox createWelcomeScreen() {
        VBox welcome = new VBox(20);
        welcome.setAlignment(Pos.CENTER);
        welcome.setPadding(new Insets(40));
        welcome.setStyle("-fx-background-color: #ecf0f1;");

        Label welcomeTitle = new Label("Welcome to Criminal Profiler System");
        welcomeTitle.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label info = new Label("AI-Powered Criminal Analysis");
        info.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");

        Label stats = new Label(String.format("Database: %d Criminals | %d Crime Scenes",
                database.getCriminalCount(),
                database.getSceneCount()));
        stats.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
        stats.setPadding(new Insets(20, 0, 0, 0));

        Label instruction = new Label("Select an option from the navigation menu to begin");
        instruction.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6;");

        welcome.getChildren().addAll(welcomeTitle, info, stats, instruction);
        return welcome;
    }

    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(10));
        statusBar.setStyle("-fx-background-color: #1a252f;");

        statusLabel = new Label("Ready | System Initialized");
        statusLabel.setStyle("-fx-text-fill: #95a5a6; -fx-font-size: 12px;");

        statusBar.getChildren().add(statusLabel);
        return statusBar;
    }

    private void showDashboard() {
        mainLayout.setCenter(createWelcomeScreen());
        updateStatus("Dashboard");
    }

    private void showCriminalsView() {
        VBox view = new VBox(15);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: #ecf0f1;");

        Label title = new Label(" CRIMINAL DATABASE");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextArea criminalsDisplay = new TextArea();
        criminalsDisplay.setEditable(false);
        criminalsDisplay.setPrefHeight(500);
        criminalsDisplay.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 13px;");

        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 80; i++) content.append("=");
        content.append("\n  CRIMINAL DATABASE\n");
        for (int i = 0; i < 80; i++) content.append("=");
        content.append("\n\n");

        var criminals = database.getAllCriminals();
        for (int i = 0; i < criminals.size(); i++) {
            var c = criminals.get(i);
            content.append(String.format("%d. %s\n", i + 1, c.toString()));
            content.append(String.format("   MO: %s\n", c.getModusOperandi()));
            content.append(String.format("   Locations: %s\n", c.getKnownLocations()));
            content.append(String.format("   Status: %s\n\n", c.isAtLarge() ? "AT LARGE" : "CAPTURED"));
        }

        criminalsDisplay.setText(content.toString());
        view.getChildren().addAll(title, criminalsDisplay);
        mainLayout.setCenter(view);
        updateStatus("Viewing Criminals Database");
    }

    private void showAddCriminalView() {
        AddCriminalView addView = new AddCriminalView(dbManager);
        mainLayout.setCenter(addView);
        updateStatus("Add Criminal");
    }

    private void showCrimeScenesView() {
        VBox view = new VBox(15);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: #ecf0f1;");

        Label title = new Label(" CRIME SCENES");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextArea scenesDisplay = new TextArea();
        scenesDisplay.setEditable(false);
        scenesDisplay.setPrefHeight(500);
        scenesDisplay.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 13px;");

        StringBuilder content = new StringBuilder();
        for (int i = 0; i < 80; i++) content.append("=");
        content.append("\n  CRIME SCENES\n");
        for (int i = 0; i < 80; i++) content.append("=");
        content.append("\n\n");

        var scenes = database.getAllCrimeScenes();
        for (int i = 0; i < scenes.size(); i++) {
            var s = scenes.get(i);
            content.append(String.format("%d. %s\n", i + 1, s.toString()));
            content.append(String.format("   Description: %s\n", s.getDescription()));
            content.append(String.format("   Evidence Count: %d\n\n", s.getEvidenceCount()));
        }

        scenesDisplay.setText(content.toString());
        view.getChildren().addAll(title, scenesDisplay);
        mainLayout.setCenter(view);
        updateStatus("Viewing Crime Scenes");
    }

    private void showAnalysisView() {
        AnalysisView analysisView = new AnalysisView(database, engine);
        mainLayout.setCenter(analysisView);
        updateStatus("AI Analysis");
    }

    private void showReportsView() {
        ReportView reportView = new ReportView(database, engine);
        mainLayout.setCenter(reportView);
        updateStatus("Reports");
    }

    private void showStatsView() {
        VBox view = new VBox(15);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: #ecf0f1;");

        Label title = new Label(" SYSTEM STATISTICS");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        TextArea statsDisplay = new TextArea();
        statsDisplay.setEditable(false);
        statsDisplay.setPrefHeight(500);
        statsDisplay.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 13px;");

        String stats = database.getStatisticsReport() + "\n\n" + learningModel.getLearningReport();
        statsDisplay.setText(stats);

        view.getChildren().addAll(title, statsDisplay);
        mainLayout.setCenter(view);
        updateStatus("Viewing Statistics");
    }

    private void updateStatus(String message) {
        statusLabel.setText("Status: " + message);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        // Save data when closing
        Utils.saveAllData(database);
        System.out.println("Application closed. Data saved.");
    }

    private void showAddCrimeSceneView() {
        AddCrimeSceneView addSceneView = new AddCrimeSceneView(dbManager);
        mainLayout.setCenter(addSceneView);
        updateStatus("Add Crime Scene");
    }

    private void showManageCriminalsView() {
        ManageCriminalsView manageView = new ManageCriminalsView(dbManager);
        mainLayout.setCenter(manageView);
        updateStatus("Manage Criminals");
    }
}
