package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import database.CriminalDatabase;
import engine.ProbabilityEngine;
import models.*;
import models.criminals.Criminal;

import java.util.List;

public class AnalysisView extends VBox {

    private CriminalDatabase database;
    private ProbabilityEngine engine;
    private ComboBox<CrimeScene> cmbScenes;
    private VBox resultsContainer;
    private Label statusLabel;

    public AnalysisView(CriminalDatabase database, ProbabilityEngine engine) {
        this.database = database;
        this.engine = engine;
        initializeUI();
    }

    private void initializeUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ecf0f1;");

        // Title
        Label title = new Label(" AI CRIME SCENE ANALYSIS");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitle = new Label("Analyze crime scenes and identify potential suspects");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        // Selection box
        VBox selectionBox = new VBox(10);
        selectionBox.setPadding(new Insets(20));
        selectionBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        Label lblScene = new Label("Select Crime Scene:");
        lblScene.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        cmbScenes = new ComboBox<>();
        cmbScenes.setPrefWidth(500);
        cmbScenes.setPromptText("Choose a crime scene to analyze...");
        loadScenes();

        Button btnAnalyze = new Button(" Analyze Scene");
        btnAnalyze.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-padding: 10 20;");
        btnAnalyze.setOnAction(e -> analyzeScene());

        selectionBox.getChildren().addAll(lblScene, cmbScenes, btnAnalyze);

        // Results container
        resultsContainer = new VBox(15);
        resultsContainer.setPadding(new Insets(20));
        resultsContainer.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        Label initialMsg = new Label("Select a crime scene and click 'Analyze Scene' to begin");
        initialMsg.setStyle("-fx-font-size: 14px; -fx-text-fill: #95a5a6; -fx-padding: 40;");
        initialMsg.setAlignment(Pos.CENTER);
        resultsContainer.getChildren().add(initialMsg);

        // Status
        statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 13px;");

        // Add all
        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(15, title, subtitle, selectionBox, resultsContainer, statusLabel);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        this.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }

    private void loadScenes() {
        List<CrimeScene> scenes = database.getAllCrimeScenes();
        cmbScenes.getItems().clear();
        cmbScenes.getItems().addAll(scenes);

        cmbScenes.setButtonCell(new ListCell<CrimeScene>() {
            @Override
            protected void updateItem(CrimeScene scene, boolean empty) {
                super.updateItem(scene, empty);
                if (empty || scene == null) {
                    setText(null);
                } else {
                    setText(scene.getSceneId() + " - " + scene.getCrimeType() + " at " + scene.getLocation());
                }
            }
        });

        cmbScenes.setCellFactory(lv -> new ListCell<CrimeScene>() {
            @Override
            protected void updateItem(CrimeScene scene, boolean empty) {
                super.updateItem(scene, empty);
                if (empty || scene == null) {
                    setText(null);
                } else {
                    setText(scene.getSceneId() + " - " + scene.getCrimeType() + " at " + scene.getLocation());
                }
            }
        });
    }

    private void analyzeScene() {
        CrimeScene selectedScene = cmbScenes.getValue();

        if (selectedScene == null) {
            showStatus("Please select a crime scene first", false);
            return;
        }

        showStatus("Analyzing crime scene...", true);

        // Perform analysis
        List<Criminal> criminals = database.getAllCriminals();
        List<Suspect> suspects = engine.analyzeCrimeScene(selectedScene, criminals);

        // Display results
        displayResults(selectedScene, suspects);

        showStatus("Analysis complete! Found " + suspects.size() + " potential suspects", true);
    }

    private void displayResults(CrimeScene scene, List<Suspect> suspects) {
        resultsContainer.getChildren().clear();

        // Scene info
        Label sceneTitle = new Label("Analysis Results for: " + scene.getSceneId());
        sceneTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label sceneInfo = new Label(String.format("%s at %s | Evidence: %d items",
                scene.getCrimeType(),
                scene.getLocation(),
                scene.getEvidenceCount()));
        sceneInfo.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        resultsContainer.getChildren().addAll(sceneTitle, sceneInfo, new Separator());

        if (suspects.isEmpty()) {
            Label noResults = new Label("No suspects found matching the crime scene profile");
            noResults.setStyle("-fx-font-size: 14px; -fx-text-fill: #e74c3c; -fx-padding: 20;");
            resultsContainer.getChildren().add(noResults);
            return;
        }

        // Display top suspects
        Label resultsHeader = new Label("Top Suspects (Ranked by Probability):");
        resultsHeader.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #34495e;");
        resultsContainer.getChildren().add(resultsHeader);

        for (int i = 0; i < Math.min(10, suspects.size()); i++) {
            Suspect suspect = suspects.get(i);
            VBox suspectCard = createSuspectCard(suspect, i + 1);
            resultsContainer.getChildren().add(suspectCard);
        }
    }

    private VBox createSuspectCard(Suspect suspect, int rank) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));

        // Color based on probability
        String borderColor;
        if (suspect.getProbabilityScore() >= 70) {
            borderColor = "#e74c3c"; // Red for high probability
        } else if (suspect.getProbabilityScore() >= 40) {
            borderColor = "#f39c12"; // Orange for medium
        } else {
            borderColor = "#95a5a6"; // Gray for low
        }

        card.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: " + borderColor + "; " +
                "-fx-border-width: 2; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Rank and name
        HBox header = new HBox(15);
        header.setAlignment(Pos.CENTER_LEFT);

        Label rankLabel = new Label("#" + rank);
        rankLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: " + borderColor + ";");

        Label nameLabel = new Label(suspect.getCriminal().getName());
        nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        header.getChildren().addAll(rankLabel, nameLabel);

        // Criminal details
        Label typeLabel = new Label("Type: " + suspect.getCriminal().getCriminalType());
        typeLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");

        Label idLabel = new Label("ID: " + suspect.getCriminal().getId());
        idLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #95a5a6;");

        // Probability
        Label probLabel = new Label(String.format("Match Probability: %.1f%% (%s)",
                suspect.getProbabilityScore(), suspect.getConfidence()));
        probLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: " + borderColor + ";");

        // Reasoning
        Label reasoningTitle = new Label("Analysis:");
        reasoningTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

        Label reasoningLabel = new Label(suspect.getReasoning());
        reasoningLabel.setWrapText(true);
        reasoningLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #495057;");

        // Matching factors
        if (!suspect.getMatchingFactors().isEmpty()) {
            Label factorsTitle = new Label("Matching Factors:");
            factorsTitle.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #34495e;");

            VBox factorsList = new VBox(3);
            for (String factor : suspect.getMatchingFactors()) {
                Label factorLabel = new Label("• " + factor);
                factorLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #6c757d;");
                factorsList.getChildren().add(factorLabel);
            }

            card.getChildren().addAll(header, typeLabel, idLabel, new Separator(),
                    probLabel, new Separator(),
                    reasoningTitle, reasoningLabel, new Separator(),
                    factorsTitle, factorsList);
        } else {
            card.getChildren().addAll(header, typeLabel, idLabel, new Separator(),
                    probLabel, new Separator(),
                    reasoningTitle, reasoningLabel);
        }

        return card;
    }

    private void showStatus(String message, boolean success) {
        statusLabel.setText((success ? " " : " ") + message);
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " +
                (success ? "#27ae60" : "#e74c3c") + "; -fx-padding: 10 0 0 0;");
    }
}

