package gui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import database.DatabaseManager;
import utils.Utils;

public class AddCriminalView extends VBox {

    private DatabaseManager dbManager;
    private ComboBox<String> cmbCriminalType;
    private TextField txtName;
    private TextField txtAge;
    private ComboBox<String> cmbGender;
    private Label statusLabel;

    public AddCriminalView(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        initializeUI();
    }

    private void initializeUI() {
        this.setSpacing(15);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #ecf0f1;");

        // Title
        Label title = new Label("+ ADD NEW CRIMINAL");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Form container
        VBox formBox = new VBox(15);
        formBox.setPadding(new Insets(20));
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 5;");

        // Criminal Type
        Label lblType = new Label("Criminal Type:");
        lblType.setStyle("-fx-font-weight: bold;");
        cmbCriminalType = new ComboBox<>();
        cmbCriminalType.getItems().addAll(DatabaseManager.getCriminalTypes());
        cmbCriminalType.setPromptText("Select criminal type...");
        cmbCriminalType.setPrefWidth(400);

        // Name
        Label lblName = new Label("Name:");
        lblName.setStyle("-fx-font-weight: bold;");
        txtName = new TextField();
        txtName.setPromptText("Enter criminal's full name...");
        txtName.setPrefWidth(400);

        // Age
        Label lblAge = new Label("Age:");
        lblAge.setStyle("-fx-font-weight: bold;");
        txtAge = new TextField();
        txtAge.setPromptText("Enter age (10-120)...");
        txtAge.setPrefWidth(400);

        // Gender
        Label lblGender = new Label("Gender:");
        lblGender.setStyle("-fx-font-weight: bold;");
        cmbGender = new ComboBox<>();
        cmbGender.getItems().addAll("Male", "Female", "Other");
        cmbGender.setPromptText("Select gender...");
        cmbGender.setPrefWidth(400);

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Button btnAdd = new Button("Add Criminal");
        btnAdd.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnAdd.setOnAction(e -> addCriminal());

        Button btnClear = new Button("Clear Form");
        btnClear.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnClear.setOnAction(e -> clearForm());

        buttonBox.getChildren().addAll(btnAdd, btnClear);

        // Status label
        statusLabel = new Label("");
        statusLabel.setStyle("-fx-font-size: 13px;");

        // Add all to form
        formBox.getChildren().addAll(
                lblType, cmbCriminalType,
                lblName, txtName,
                lblAge, txtAge,
                lblGender, cmbGender,
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

    private void addCriminal() {
        try {
            // Validate inputs
            if (cmbCriminalType.getValue() == null) {
                showStatus("Please select a criminal type", false);
                return;
            }

            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                showStatus("Please enter a name", false);
                return;
            }

            // Validate name
            if (!Utils.isValidName(name)) {
                showStatus("Invalid name. Use only letters, spaces, hyphens and apostrophes (2-50 characters)", false);
                return;
            }

            int age;
            try {
                age = Integer.parseInt(txtAge.getText().trim());
            } catch (NumberFormatException e) {
                showStatus("Please enter a valid age number", false);
                return;
            }

            // Validate age
            if (!Utils.isValidAge(age)) {
                showStatus("Invalid age. Must be between 10 and 120", false);
                return;
            }

            if (cmbGender.getValue() == null) {
                showStatus("Please select a gender", false);
                return;
            }

            String gender = cmbGender.getValue();

            // Validate gender
            if (!Utils.isValidGender(gender)) {
                showStatus("Invalid gender selection", false);
                return;
            }

            // Add criminal
            String id = dbManager.addCriminalQuick(name, age, gender, cmbCriminalType.getValue());

            showStatus("Criminal added successfully! ID: " + id, true);
            clearForm();

        } catch (IllegalArgumentException e) {
            showStatus("Error: " + e.getMessage(), false);
        } catch (Exception e) {
            showStatus("Unexpected error: " + e.getMessage(), false);
        }
    }

    private void clearForm() {
        cmbCriminalType.setValue(null);
        txtName.clear();
        txtAge.clear();
        cmbGender.setValue(null);
    }

    private void showStatus(String message, boolean success) {
        statusLabel.setText((success ? " " : " ") + message);
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: " +
                (success ? "#27ae60" : "#e74c3c") + "; -fx-padding: 10 0 0 0;");
    }
}

