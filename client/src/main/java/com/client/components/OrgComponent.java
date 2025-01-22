package com.client.components;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class OrgComponent extends VBox {

    private TextField nameOfTheSchoolTextField;
    private TextField fieldOfStudyTextField;
    private DatePicker startStudyingDatePicker;
    private DatePicker endOfEducationDatePicker;
    private CheckBox informCheckBox;
    private Button editEducationButton;

    private boolean isEditable = false;

    public OrgComponent() {
        // Initialize VBox properties
        this.setPrefHeight(189.0);
        this.setPrefWidth(900.0);
        this.setStyle("-fx-border-color: black; -fx-border-insets: 3; -fx-border-width: 2;");

        // Create and configure the first AnchorPane
        AnchorPane anchorPane1 = new AnchorPane();
        anchorPane1.setPrefHeight(41.0);
        anchorPane1.setPrefWidth(890.0);

        Label jobPositionLabel = new Label("Organization");
        jobPositionLabel.setAlignment(javafx.geometry.Pos.CENTER);
        jobPositionLabel.setPrefHeight(44.0);
        jobPositionLabel.setPrefWidth(758.0);
        jobPositionLabel.setStyle("-fx-background-color: #0598ff;");
        jobPositionLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        jobPositionLabel.setPadding(new Insets(10.0));
        jobPositionLabel.setFont(new Font("MRT_Sima", 28.0));

        editEducationButton = new Button("Edit");
        editEducationButton.setLayoutX(763.0);
        editEducationButton.setPrefHeight(42.0);
        editEducationButton.setPrefWidth(126.0);
        editEducationButton.setStyle("-fx-background-color: #0598ff;");
        editEducationButton.setTextFill(javafx.scene.paint.Color.WHITE);
        editEducationButton.setFont(new Font(20.0));
        editEducationButton.setOnAction(e -> toggleEditMode());

        anchorPane1.getChildren().addAll(jobPositionLabel, editEducationButton);

        // Create and configure the second AnchorPane
        AnchorPane anchorPane2 = new AnchorPane();
        anchorPane2.setPrefHeight(238.0);
        anchorPane2.setPrefWidth(889.0);

        nameOfTheSchoolTextField = new TextField();
        nameOfTheSchoolTextField.setLayoutX(91.0);
        nameOfTheSchoolTextField.setLayoutY(14.0);
        nameOfTheSchoolTextField.setPrefHeight(25.0);
        nameOfTheSchoolTextField.setPrefWidth(83.0);
        nameOfTheSchoolTextField.setEditable(false);

        Label organizationNameLabel = new Label("organization name");
        organizationNameLabel.setLayoutX(1.0);
        organizationNameLabel.setLayoutY(18.0);
        organizationNameLabel.setFont(new Font(10.0));

        fieldOfStudyTextField = new TextField();
        fieldOfStudyTextField.setLayoutX(248.0);
        fieldOfStudyTextField.setLayoutY(15.0);
        fieldOfStudyTextField.setPrefHeight(25.0);
        fieldOfStudyTextField.setPrefWidth(107.0);
        fieldOfStudyTextField.setEditable(false);

        Label positionLabel = new Label("position");
        positionLabel.setLayoutX(192.0);
        positionLabel.setLayoutY(19.0);
        positionLabel.setFont(new Font(11.0));

        startStudyingDatePicker = new DatePicker();
        startStudyingDatePicker.setLayoutX(453.0);
        startStudyingDatePicker.setLayoutY(16.0);
        startStudyingDatePicker.setPrefHeight(25.0);
        startStudyingDatePicker.setPrefWidth(91.0);
        startStudyingDatePicker.setDisable(true);

        Label startActivityLabel = new Label("Start activity");
        startActivityLabel.setLayoutX(367.0);
        startActivityLabel.setLayoutY(19.0);
        endOfEducationDatePicker = new DatePicker();
        endOfEducationDatePicker.setLayoutX(655.0);
        endOfEducationDatePicker.setLayoutY(15.0);
        endOfEducationDatePicker.setPrefHeight(25.0);
        endOfEducationDatePicker.setPrefWidth(91.0);
        endOfEducationDatePicker.setDisable(true);

        Label endOfActivityLabel = new Label("End of activity");
        endOfActivityLabel.setLayoutX(559.0);
        endOfActivityLabel.setLayoutY(20.0);

        informCheckBox = new CheckBox("Are you active");
        informCheckBox.setLayoutX(765.0);
        informCheckBox.setLayoutY(19.0);
        informCheckBox.setDisable(true);
        informCheckBox.setOnAction(e -> endOfEducationDatePicker.setDisable(informCheckBox.isSelected()));

        anchorPane2.getChildren().addAll(
                nameOfTheSchoolTextField, organizationNameLabel, fieldOfStudyTextField, positionLabel,
                startStudyingDatePicker, startActivityLabel, endOfEducationDatePicker, endOfActivityLabel, informCheckBox
        );

        // Add anchor panes to the VBox
        this.getChildren().addAll(anchorPane1, anchorPane2);
    }

    private void toggleEditMode() {
        isEditable = !isEditable;
        editEducationButton.setText(isEditable ? "Save" : "Edit");

        nameOfTheSchoolTextField.setEditable(isEditable);
        fieldOfStudyTextField.setEditable(isEditable);
        startStudyingDatePicker.setDisable(!isEditable);
        endOfEducationDatePicker.setDisable(!isEditable || informCheckBox.isSelected());
        informCheckBox.setDisable(!isEditable);
    }

    // Getters for the components
    public TextField getNameOfTheSchoolTextField() {
        return nameOfTheSchoolTextField;
    }

    public TextField getFieldOfStudyTextField() {
        return fieldOfStudyTextField;
    }

    public DatePicker getStartStudyingDatePicker() {
        return startStudyingDatePicker;
    }

    public DatePicker getEndOfEducationDatePicker() {
        return endOfEducationDatePicker;
    }

    public CheckBox getInformCheckBox() {
        return informCheckBox;
    }

    public Button getEditEducationButton() {
        return editEducationButton;
    }
}