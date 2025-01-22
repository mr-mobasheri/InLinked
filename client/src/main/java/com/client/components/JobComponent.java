package com.client.components;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JobComponent extends VBox {

    private VBox introductionVBox1;
    private AnchorPane anchorPane1, anchorPane2;
    private Label jobPositionLabel;
    private Button editEducationButton;
    private TextField nameOfTheSchoolTextField, fieldOfStudyTextField, companyNameTextField, workPlaceTextField;
    private DatePicker startStudyingDatePicker, endOfEducationDatePicker;
    private CheckBox informCheckBox;
    private TextArea activitiesTextArea, descriptionTextArea;
    private RadioButton fullTimeRadioButton, partTimeRadioButton, selfEmploymentRadioButton, freelanceRadioButton, contractualRadioButton, internRadioButton;

    private boolean isEditMode = false;

    public JobComponent() {
        setPrefSize(900, 402);

        introductionVBox1 = new VBox();
        introductionVBox1.setPrefSize(900, 401);
        introductionVBox1.setStyle("-fx-border-color: black; -fx-border-insets: 3; -fx-border-width: 2;");

        anchorPane1 = new AnchorPane();
        anchorPane1.setPrefSize(890, 41);

        jobPositionLabel = new Label("Job Position");
        jobPositionLabel.setAlignment(javafx.geometry.Pos.CENTER);
        jobPositionLabel.setPrefSize(758, 44);
        jobPositionLabel.setStyle("-fx-background-color: #0598ff;");
        jobPositionLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        jobPositionLabel.setPadding(new Insets(10, 0, 0, 10));
        jobPositionLabel.setFont(new Font("MRT_Sima", 28));

        editEducationButton = new Button("Edit");
        editEducationButton.setLayoutX(763);
        editEducationButton.setPrefSize(126, 42);
        editEducationButton.setStyle("-fx-background-color: #0598ff;");
        editEducationButton.setTextFill(javafx.scene.paint.Color.WHITE);
        editEducationButton.setFont(new Font(20));
        editEducationButton.setOnAction(e -> toggleEditMode());

        anchorPane1.getChildren().addAll(jobPositionLabel, editEducationButton);

        anchorPane2 = new AnchorPane();
        anchorPane2.setPrefSize(889, 238);

        nameOfTheSchoolTextField = new TextField();
        nameOfTheSchoolTextField.setLayoutX(60);
        nameOfTheSchoolTextField.setLayoutY(14);
        nameOfTheSchoolTextField.setPrefSize(107, 25);

        Label titleLabel = new Label("Title");
        titleLabel.setLayoutX(1);
        titleLabel.setLayoutY(18);
        titleLabel.setFont(new Font(17));

        fieldOfStudyTextField = new TextField();
        fieldOfStudyTextField.setLayoutX(248);
        fieldOfStudyTextField.setLayoutY(15);
        fieldOfStudyTextField.setPrefSize(107, 25);

        Label fieldOfStudyLabel = new Label("Field of Study");
        fieldOfStudyLabel.setLayoutX(177);
        fieldOfStudyLabel.setLayoutY(19);
        fieldOfStudyLabel.setFont(new Font(11));

        startStudyingDatePicker = new DatePicker();
        startStudyingDatePicker.setLayoutX(453);
        startStudyingDatePicker.setLayoutY(16);
        startStudyingDatePicker.setPrefSize(91, 25);

        Label startWorkLabel = new Label("Start work");
        startWorkLabel.setLayoutX(367);
        startWorkLabel.setLayoutY(19);

        endOfEducationDatePicker = new DatePicker();
        endOfEducationDatePicker.setLayoutX(655);
        endOfEducationDatePicker.setLayoutY(15);
        endOfEducationDatePicker.setPrefSize(91, 25);

        Label endOfWorkLabel = new Label("End of work");
        endOfWorkLabel.setLayoutX(559);
        endOfWorkLabel.setLayoutY(20);

        informCheckBox = new CheckBox("Are you employed");
        informCheckBox.setLayoutX(765);
        informCheckBox.setLayoutY(19);
        informCheckBox.setMnemonicParsing(false);
        informCheckBox.setOnAction(e -> informCheckBoxPressed());
        activitiesTextArea = new TextArea();
        activitiesTextArea.setLayoutX(221);
        activitiesTextArea.setLayoutY(50);
        activitiesTextArea.setPrefSize(212, 175);
        activitiesTextArea.setPromptText("Description of activities and associations");

        descriptionTextArea = new TextArea();
        descriptionTextArea.setLayoutX(440);
        descriptionTextArea.setLayoutY(51);
        descriptionTextArea.setPrefSize(386, 174);
        descriptionTextArea.setPromptText("Description");

        companyNameTextField = new TextField();
        companyNameTextField.setLayoutX(96);
        companyNameTextField.setLayoutY(81);
        companyNameTextField.setPrefSize(111, 25);

        Label companyNameLabel = new Label("Company name");
        companyNameLabel.setLayoutX(8);
        companyNameLabel.setLayoutY(84);

        workPlaceTextField = new TextField();
        workPlaceTextField.setLayoutX(96);
        workPlaceTextField.setLayoutY(125);
        workPlaceTextField.setPrefSize(111, 25);

        Label workPlaceLabel = new Label("Work place");
        workPlaceLabel.setLayoutX(22);
        workPlaceLabel.setLayoutY(129);

        Label employmentTypeLabel = new Label("Employment type");
        employmentTypeLabel.setLayoutX(19);
        employmentTypeLabel.setLayoutY(216);

        anchorPane2.getChildren().addAll(
                nameOfTheSchoolTextField, titleLabel, fieldOfStudyTextField, fieldOfStudyLabel,
                startStudyingDatePicker, startWorkLabel, endOfEducationDatePicker, endOfWorkLabel,
                informCheckBox, activitiesTextArea, descriptionTextArea, companyNameTextField,
                companyNameLabel, workPlaceTextField, workPlaceLabel, employmentTypeLabel
        );

        fullTimeRadioButton = new RadioButton("Full time");
        partTimeRadioButton = new RadioButton("Part time");
        selfEmploymentRadioButton = new RadioButton("Self-employment");
        freelanceRadioButton = new RadioButton("Freelance");
        contractualRadioButton = new RadioButton("Contractual");
        internRadioButton = new RadioButton("Intern");

        introductionVBox1.getChildren().addAll(anchorPane1, anchorPane2, fullTimeRadioButton, partTimeRadioButton, selfEmploymentRadioButton, freelanceRadioButton, contractualRadioButton, internRadioButton);
        getChildren().add(introductionVBox1);

        setEditMode(false); // Start in non-edit mode
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;
        setEditMode(isEditMode);
    }

    private void setEditMode(boolean editable) {
        nameOfTheSchoolTextField.setEditable(editable);
        fieldOfStudyTextField.setEditable(editable);
        startStudyingDatePicker.setDisable(!editable);
        endOfEducationDatePicker.setDisable(!editable);
        informCheckBox.setDisable(!editable);
        activitiesTextArea.setEditable(editable);
        descriptionTextArea.setEditable(editable);
        companyNameTextField.setEditable(editable);
        workPlaceTextField.setEditable(editable);

        editEducationButton.setText(editable ? "Save" : "Edit");
    }

    private void informCheckBoxPressed() {
        // Handle checkbox action
    }
}