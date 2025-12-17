package gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import database.DatabaseManager;
import models.*;

public class AddCrimeSceneView extends VBox {

    private DatabaseManager dbManager;
    private ComboBox<String> cmbCrimeType;
    private TextField txtLocation;
    private TextArea txtDescription;
    private TextField txtVictimProfile;
    private TextField txtWeather;
    private TextField txtInvestigator;
    private VBox evidenceContainer;
    private Label statusLabel;

    public AddCrimeSceneView(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializeUI();
    }

    private void initializeUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ecf0f1;");

        // Title
        Label title = new Label("+ ADD NEW CRIME SCENE");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Form container
        VBox formBox = new VBox(15);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        // Crime Type
        Label lblType = new Label("Crime Type:");
        lblType.setStyle("-fx-font-weight: bold;");
        cmbCrimeType = new ComboBox<>();
        cmbCrimeType.getItems().addAll(CrimeScene.getValidCrimeTypes());
        cmbCrimeType.setPromptText("Select crime type...");
        cmbCrimeType.setPrefWidth(400);

        // Location
        Label lblLocation = new Label("Location:");
        lblLocation.setStyle("-fx-font-weight: bold;");
        txtLocation = new TextField();
        txtLocation.setPromptText("Enter crime scene location...");
        txtLocation.setPrefWidth(400);

        // Description
        Label lblDescription = new Label("Description:");
        lblDescription.setStyle("-fx-font-weight: bold;");
        txtDescription = new TextArea();
        txtDescription.setPromptText("Enter detailed description of the crime scene...");
        txtDescription.setPrefHeight(80);
        txtDescription.setWrapText(true);

        // Victim Profile
        Label lblVictim = new Label("Victim Profile (Optional):");
        lblVictim.setStyle("-fx-font-weight: bold;");
        txtVictimProfile = new TextField();
        txtVictimProfile.setPromptText("e.g., Female, 25 years old, professional");
        txtVictimProfile.setPrefWidth(400);

        // Weather Conditions
        Label lblWeather = new Label("Weather Conditions (Optional):");
        lblWeather.setStyle("-fx-font-weight: bold;");
        txtWeather = new TextField();
        txtWeather.setPromptText("e.g., Rainy, Clear night");
        txtWeather.setPrefWidth(400);

        // Investigator
        Label lblInvestigator = new Label("Investigator in Charge:");
        lblInvestigator.setStyle("-fx-font-weight: bold;");
        txtInvestigator = new TextField();
        txtInvestigator.setPromptText("Enter investigator name...");
        txtInvestigator.setPrefWidth(400);

        // Evidence Section
        Label lblEvidence = new Label("Evidence:");
        lblEvidence.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        evidenceContainer = new VBox(10);
        evidenceContainer.setPadding(new Insets(10));
        evidenceContainer.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; " +
                "-fx-border-radius: 3; -fx-background-radius: 3;");

        Button btnAddEvidence = new Button("+ Add Evidence");
        btnAddEvidence.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        btnAddEvidence.setOnAction(e -> addEvidenceField());

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Button btnCreate = new Button("Create Crime Scene");
        btnCreate.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnCreate.setOnAction(e -> createCrimeScene());

        Button btnClear = new Button("Clear Form");
        btnClear.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnClear.setOnAction(e -> clearForm());

        buttonBox.getChildren().addAll(btnCreate, btnClear);

        // Status label
        statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 13px;");

        // Add all to form
        formBox.getChildren().addAll(
                lblType, cmbCrimeType,
                lblLocation, txtLocation,
                lblDescription, txtDescription,
                lblVictim, txtVictimProfile,
                lblWeather, txtWeather,
                lblInvestigator, txtInvestigator,
                new Separator(),
                lblEvidence, evidenceContainer, btnAddEvidence,
                new Separator(),
                buttonBox
        );

        // Scrollable content
        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(15, title, formBox, statusLabel);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        this.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
    }

    private void addEvidenceField() {
        HBox evidenceRow = new HBox(10);
        evidenceRow.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        ComboBox<String> cmbEvidenceType = new ComboBox<>();
        cmbEvidenceType.getItems().addAll(Evidence.getValidEvidenceTypes());
        cmbEvidenceType.setPromptText("Type");
        cmbEvidenceType.setPrefWidth(150);

        TextField txtEvidenceDesc = new TextField();
        txtEvidenceDesc.setPromptText("Description");
        txtEvidenceDesc.setPrefWidth(250);

        Button btnRemove = new Button("✖");
        btnRemove.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnRemove.setOnAction(e -> evidenceContainer.getChildren().remove(evidenceRow));

        evidenceRow.getChildren().addAll(cmbEvidenceType, txtEvidenceDesc, btnRemove);
        evidenceContainer.getChildren().add(evidenceRow);
    }

    private void createCrimeScene() {
        try {
            // Validate inputs
            if (cmbCrimeType.getValue() == null) {
                showStatus("Please select a crime type", false);
                return;
            }

            if (txtLocation.getText().trim().isEmpty()) {
                showStatus("Please enter a location", false);
                return;
            }

            // Create crime scene
            String sceneId = dbManager.addCrimeSceneQuick(
                    cmbCrimeType.getValue(),
                    txtLocation.getText().trim()
            );

            CrimeScene scene = dbManager.getDatabase().getCrimeScene(sceneId);

            // Set optional fields
            if (!txtDescription.getText().trim().isEmpty()) {
                scene.setDescription(txtDescription.getText().trim());
            }

            if (!txtVictimProfile.getText().trim().isEmpty()) {
                scene.setVictimProfile(txtVictimProfile.getText().trim());
            }

            if (!txtWeather.getText().trim().isEmpty()) {
                scene.setWeatherConditions(txtWeather.getText().trim());
            }

            if (!txtInvestigator.getText().trim().isEmpty()) {
                scene.secureScene(txtInvestigator.getText().trim());
            }

            // Add evidence
            for (var node : evidenceContainer.getChildren()) {
                if (node instanceof HBox) {
                    HBox row = (HBox) node;
                    ComboBox<String> typeCombo = (ComboBox<String>) row.getChildren().get(0);
                    TextField descField = (TextField) row.getChildren().get(1);

                    if (typeCombo.getValue() != null && !descField.getText().trim().isEmpty()) {
                        dbManager.addEvidenceToScene(sceneId, typeCombo.getValue(), descField.getText().trim());
                    }
                }
            }

            showStatus("Crime scene created successfully! ID: " + sceneId, true);
            clearForm();

        } catch (Exception e) {
            showStatus("Error creating crime scene: " + e.getMessage(), false);
        }
    }

    private void clearForm() {
        cmbCrimeType.setValue(null);
        txtLocation.clear();
        txtDescription.clear();
        txtVictimProfile.clear();
        txtWeather.clear();
        txtInvestigator.clear();
        evidenceContainer.getChildren().clear();
    }

    private void showStatus(String message, boolean success) {
        statusLabel.setText((success ? " " : " ") + message);
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " +
                (success ? "#27ae60" : "#e74c3c") + "; -fx-padding: 10 0 0 0;");
    }
}
