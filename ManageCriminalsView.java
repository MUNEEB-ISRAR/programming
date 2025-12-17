package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import database.DatabaseManager;
import models.criminals.Criminal;
import java.util.List;

public class ManageCriminalsView extends VBox {

    private DatabaseManager dbManager;
    private ListView<Criminal> criminalListView;
    private TextArea detailsArea;
    private Label statusLabel;

    public ManageCriminalsView(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializeUI();
        loadCriminals();
    }

    private void initializeUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ecf0f1;");

        // Title
        Label title = new Label(" MANAGE CRIMINALS");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Main content
        HBox mainContent = new HBox(15);
        mainContent.setPrefHeight(500);

        // Left: Criminal List
        VBox listBox = new VBox(10);
        listBox.setPrefWidth(350);
        listBox.setPadding(new Insets(15));
        listBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        Label lblList = new Label("Criminal List:");
        lblList.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        criminalListView = new ListView<>();
        criminalListView.setPrefHeight(400);
        criminalListView.setCellFactory(lv -> new ListCell<Criminal>() {
            @Override
            protected void updateItem(Criminal criminal, boolean empty) {
                super.updateItem(criminal, empty);
                if (empty || criminal == null) {
                    setText(null);
                } else {
                    setText(criminal.getName() + " [" + criminal.getCriminalType() + "]");
                }
            }
        });

        criminalListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> displayCriminalDetails(newVal)
        );

        listBox.getChildren().addAll(lblList, criminalListView);

        // Right: Details and Actions
        VBox detailsBox = new VBox(10);
        detailsBox.setPrefWidth(450);
        detailsBox.setPadding(new Insets(15));
        detailsBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        Label lblDetails = new Label("Criminal Details:");
        lblDetails.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setPrefHeight(350);
        detailsArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        detailsArea.setText("Select a criminal to view details");

        // Action buttons
        HBox actionButtons = new HBox(10);
        actionButtons.setAlignment(Pos.CENTER_LEFT);

        Button btnToggleStatus = new Button("Toggle At Large/Captured");
        btnToggleStatus.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white;");
        btnToggleStatus.setOnAction(e -> toggleCriminalStatus());

        Button btnDelete = new Button("Delete Criminal");
        btnDelete.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnDelete.setOnAction(e -> deleteCriminal());

        Button btnRefresh = new Button("Refresh List");
        btnRefresh.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        btnRefresh.setOnAction(e -> loadCriminals());

        actionButtons.getChildren().addAll(btnToggleStatus, btnDelete, btnRefresh);

        detailsBox.getChildren().addAll(lblDetails, detailsArea, actionButtons);

        mainContent.getChildren().addAll(listBox, detailsBox);

        // Status label
        statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 13px;");

        this.getChildren().addAll(title, mainContent, statusLabel);
    }

    private void loadCriminals() {
        List<Criminal> criminals = dbManager.getDatabase().getAllCriminals();
        criminalListView.getItems().clear();
        criminalListView.getItems().addAll(criminals);
        showStatus("Loaded " + criminals.size() + " criminals", true);
    }

    private void displayCriminalDetails(Criminal criminal) {
        if (criminal == null) {
            detailsArea.setText("Select a criminal to view details");
            return;
        }

        StringBuilder details = new StringBuilder();
        details.append("═".repeat(50)).append("\n");
        details.append("  CRIMINAL PROFILE\n");
        details.append("═".repeat(50)).append("\n\n");

        details.append("ID:              ").append(criminal.getId()).append("\n");
        details.append("Name:            ").append(criminal.getName()).append("\n");
        details.append("Type:            ").append(criminal.getCriminalType()).append("\n");
        details.append("Age:             ").append(criminal.getAge()).append("\n");
        details.append("Gender:          ").append(criminal.getGender()).append("\n");
        details.append("Danger Level:    ").append(criminal.getDangerLevel()).append("\n");
        details.append("Risk Factor:     ").append(String.format("%.1f%%", criminal.getRiskFactor() * 100)).append("\n");
        details.append("Status:          ").append(criminal.isAtLarge() ? "AT LARGE ⚠️" : "CAPTURED ✓").append("\n\n");

        details.append("Modus Operandi:\n");
        details.append(criminal.getModusOperandi()).append("\n\n");

        if (criminal.getPsychologicalProfile() != null) {
            details.append("Psychological Profile:\n");
            details.append(criminal.getPsychologicalProfile()).append("\n\n");
        }

        details.append("Known Locations:\n");
        for (String location : criminal.getKnownLocations()) {
            details.append("  • ").append(location).append("\n");
        }

        details.append("\nPrior Crimes:\n");
        if (criminal.getPriorCrimes().isEmpty()) {
            details.append("  None on record\n");
        } else {
            for (String crime : criminal.getPriorCrimes()) {
                details.append("  • ").append(crime).append("\n");
            }
        }

        detailsArea.setText(details.toString());
    }

    private void toggleCriminalStatus() {
        Criminal selected = criminalListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showStatus("Please select a criminal first", false);
            return;
        }

        selected.setAtLarge(!selected.isAtLarge());
        dbManager.getDatabase().updateCriminal(selected);

        displayCriminalDetails(selected);
        showStatus("Status updated: " + selected.getName() + " is now " +
                (selected.isAtLarge() ? "AT LARGE" : "CAPTURED"), true);
    }

    private void deleteCriminal() {
        Criminal selected = criminalListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showStatus("Please select a criminal first", false);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText("Delete " + selected.getName() + "?");
        confirm.setContentText("This action cannot be undone.");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    dbManager.getDatabase().removeCriminal(selected.getId());
                    loadCriminals();
                    detailsArea.setText("Select a criminal to view details");
                    showStatus("Criminal deleted successfully", true);
                } catch (IllegalArgumentException e) {
                    showStatus("Error deleting criminal: " + e.getMessage(), false);
                }
            }
        });
    }

    private void showStatus(String message, boolean success) {
        statusLabel.setText((success ? " " : " ") + message);
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " +
                (success ? "#27ae60" : "#e74c3c") + "; -fx-padding: 10 0 0 0;");
    }
}
