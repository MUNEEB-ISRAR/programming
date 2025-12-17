package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import database.CriminalDatabase;
import engine.ProbabilityEngine;
import models.*;
import models.criminals.Criminal;
import reports.*;
import java.util.List;

public class ReportView extends VBox {

    private CriminalDatabase database;
    private ProbabilityEngine engine;
    private ComboBox<CrimeScene> cmbScenes;
    private TextArea reportDisplay;
    private Label statusLabel;

    public ReportView(CriminalDatabase database, ProbabilityEngine engine) {
        this.database = database;
        this.engine = engine;
        initializeUI();
    }

    private void initializeUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ecf0f1;");

        // Title
        Label title = new Label(" INVESTIGATION REPORTS");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label subtitle = new Label("Generate comprehensive investigation reports");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #7f8c8d;");

        // Selection box
        VBox selectionBox = new VBox(10);
        selectionBox.setPadding(new Insets(20));
        selectionBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        Label lblScene = new Label("Select Crime Scene:");
        lblScene.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        cmbScenes = new ComboBox<>();
        cmbScenes.setPrefWidth(500);
        cmbScenes.setPromptText("Choose a crime scene...");
        loadScenes();

        HBox buttonBox = new HBox(15);

        Button btnGenerate = new Button(" Generate Report");
        btnGenerate.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-padding: 10 20;");
        btnGenerate.setOnAction(e -> generateReport());

        Button btnExport = new Button(" Export to File");
        btnExport.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-padding: 10 20;");
        btnExport.setOnAction(e -> exportReport());

        buttonBox.getChildren().addAll(btnGenerate, btnExport);

        selectionBox.getChildren().addAll(lblScene, cmbScenes, buttonBox);

        // Report display
        VBox displayBox = new VBox(10);
        displayBox.setPadding(new Insets(20));
        displayBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        Label lblReport = new Label("Generated Report:");
        lblReport.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        reportDisplay = new TextArea();
        reportDisplay.setEditable(false);
        reportDisplay.setPrefHeight(400);
        reportDisplay.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        reportDisplay.setText("Select a crime scene and click 'Generate Report' to create an investigation report.");

        displayBox.getChildren().addAll(lblReport, reportDisplay);

        // Status
        statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 13px;");

        // Add all
        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(15, title, subtitle, selectionBox, displayBox, statusLabel);
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

    private void generateReport() {
        CrimeScene selectedScene = cmbScenes.getValue();

        if (selectedScene == null) {
            showStatus("Please select a crime scene first", false);
            return;
        }

        showStatus("Generating investigation report...", true);

        try {
            // Analyze scene
            List<Criminal> criminals = database.getAllCriminals();
            List<Suspect> suspects = engine.analyzeCrimeScene(selectedScene, criminals);

            // Generate report
            String report = ReportGenerator.generateInvestigationReport(selectedScene, suspects);

            // Display report
            reportDisplay.setText(report);

            showStatus("Report generated successfully!", true);

        } catch (Exception e) {
            showStatus("Error generating report: " + e.getMessage(), false);
        }
    }

    private void exportReport() {
        CrimeScene selectedScene = cmbScenes.getValue();

        if (selectedScene == null) {
            showStatus("Please generate a report first", false);
            return;
        }

        if (reportDisplay.getText().equals("Select a crime scene and click 'Generate Report' to create an investigation report.")) {
            showStatus("Please generate a report first", false);
            return;
        }

        try {
            String filename = "investigation_" + selectedScene.getSceneId() + ".txt";
            ReportExporter.saveReportToFile(reportDisplay.getText(), filename);
            showStatus("Report exported to " + filename, true);

        } catch (Exception e) {
            showStatus("Error exporting report: " + e.getMessage(), false);
        }
    }

    private void showStatus(String message, boolean success) {
        statusLabel.setText((success ? " " : " ") + message);
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " +
                (success ? "#27ae60" : "#e74c3c") + "; -fx-padding: 10 0 0 0;");
    }
}

