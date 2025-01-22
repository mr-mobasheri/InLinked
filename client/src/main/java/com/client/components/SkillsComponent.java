package com.client.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class SkillsComponent extends VBox {
    private Label skillsLabel;
    private Button editSkillsButton;
    private Button addSkillsButton;
    private VBox skillsContainer;

    private int textFieldCount = 0;
    private final int MAX_TEXTFIELDS = 5;

    private boolean isEditMode = false;

    public SkillsComponent() {
        this.setPrefHeight(514.0);
        this.setPrefWidth(900.0);
        this.setStyle("-fx-border-color: black; -fx-border-insets: 3; -fx-border-width: 2;");

        AnchorPane anchorPane = createAnchorPane();
        skillsLabel = createSkillsLabel();
        editSkillsButton = createEditSkillsButton();
        addSkillsButton = createAddSkillsButton();
        skillsContainer = createSkillsContainer();

        anchorPane.getChildren().addAll(skillsLabel, editSkillsButton);

        this.getChildren().addAll(anchorPane, addSkillsButton, skillsContainer);
    }

    private AnchorPane createAnchorPane() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefHeight(49.0);
        anchorPane.setPrefWidth(890.0);
        return anchorPane;
    }

    private Label createSkillsLabel() {
        Label label = new Label("Skills");
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setPrefHeight(44.0);
        label.setPrefWidth(758.0);
        label.setStyle("-fx-background-color: #0598ff;");
        label.setTextFill(Color.WHITE);
        label.setPadding(new Insets(0, 0, 0, 10.0));
        label.setFont(new Font("MRT_Sima", 28.0));
        return label;
    }

    private Button createEditSkillsButton() {
        Button button = new Button("Edit");
        button.setLayoutX(763.0);
        button.setPrefHeight(42.0);
        button.setPrefWidth(126.0);
        button.setStyle("-fx-background-color: #0598ff;");
        button.setTextFill(Color.WHITE);
        button.setFont(new Font(20.0));
        button.setOnAction(event -> {
            if (!isEditMode) {
                switchToEditMode();
            } else {
                switchToSaveMode();
            }
        });
        return button;
    }

    private void switchToEditMode() {
        isEditMode = true;
        editSkillsButton.setText("Save");
        addSkillsButton.setDisable(false);
        updateDeleteButtons(true); // Enable delete buttons
        // Enable text fields for editing
        for (int i = 0; i < skillsContainer.getChildren().size(); i++) {
            HBox hbox = (HBox) skillsContainer.getChildren().get(i);
            TextField textField = (TextField) hbox.getChildren().get(0);
            textField.setEditable(true);
            textField.setStyle("-fx-background-color: white;"); // Optional: Visual cue for editable fields
        }
    }

    private void switchToSaveMode() {
        isEditMode = false;
        editSkillsButton.setText("Edit");
        addSkillsButton.setDisable(true);
        updateDeleteButtons(false); // Disable delete buttons
        // Disable text fields for editing
        for (int i = 0; i < skillsContainer.getChildren().size(); i++) {
            HBox hbox = (HBox) skillsContainer.getChildren().get(i);
            TextField textField = (TextField) hbox.getChildren().get(0);
            textField.setEditable(false);
            textField.setStyle("-fx-background-color: lightgrey;"); // Optional: Visual cue for read-only fields
        }
    }

    private void updateDeleteButtons(boolean enable) {
        for (int i = 0; i < skillsContainer.getChildren().size(); i++) {
            HBox hbox = (HBox) skillsContainer.getChildren().get(i);
            Button deleteButton = (Button) hbox.getChildren().get(1);
            deleteButton.setDisable(!enable);
        }
    }

    private Button createAddSkillsButton() {
        Button button = new Button("Add Skills");
        button.setPrefHeight(30.0);
        button.setPrefWidth(861.0);
        button.setFont(new Font(14.0));
        button.setPadding(new Insets(0, 0, 0, 10.0));
        button.setOnAction(event -> handleAddSkills());
        return button;
    }

    private VBox createSkillsContainer() {
        VBox vbox = new VBox();
        vbox.setPrefHeight(418.0);
        vbox.setPrefWidth(890.0);
        vbox.setSpacing(20); // Increase spacing between TextFields
        return vbox;
    }

    private void handleAddSkills() {
        if (isEditMode && textFieldCount < MAX_TEXTFIELDS) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            TextField newTextField = new TextField();
            newTextField.setPrefSize(500, 50);
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> handleDeleteSkill(hbox));

            hbox.getChildren().addAll(newTextField, deleteButton);
            skillsContainer.getChildren().add(hbox);
            textFieldCount++;
            updateDeleteButtons(true); // Enable delete buttons
        }
    }

    private void handleDeleteSkill(HBox hbox) {
        skillsContainer.getChildren().remove(hbox);
        textFieldCount--;
        if (!isEditMode) {
            updateDeleteButtons(false); // Disable delete buttons if not in edit mode
        }
    }

    public Label getSkillsLabel() {
        return skillsLabel;
    }

    public Button getEditSkillsButton() {
        return editSkillsButton;
    }

    public Button getAddSkillsButton() {
        return addSkillsButton;
    }

    public VBox getSkillsContainer() {
        return skillsContainer;
    }
}