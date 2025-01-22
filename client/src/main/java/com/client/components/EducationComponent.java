package com.client.components;

import com.client.InLinkedApplication;
import com.client.models.Education;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EducationComponent extends VBox {

    // UI components
    private VBox introductionVBox1;
    private AnchorPane topAnchorPane;
    private Label educationLabel;
    private Button editEducationButton;
    private AnchorPane mainAnchorPane;
    private TextField nameOfTheSchoolTextField;
    private TextField fieldOfStudyTextField;
    private DatePicker startStudyingDatePicker;
    private DatePicker endOfEducationDatePicker;
    private TextField gradeTextField;
    private CheckBox informCheckBox;
    private TextArea activitiesTextArea;
    private TextArea descriptionTextArea;

    // Flag for editable state
    private boolean editable = false;

    // Constructor
    public EducationComponent() {
        // Set initial VBox settings
        this.setPrefSize(890, 281);

        // Initialize UI components
        initializeComponents();

        // Handle edit button click event
        editEducationButton.setOnAction(e -> handleEditEducationButton());

        // Initially set fields to non-editable
        setEditableFields(false);
    }

    // Method to initialize UI components
    private void initializeComponents() {
        // VBox inside main VBox
        introductionVBox1 = new VBox();
        introductionVBox1.setPrefSize(890, 279);
        introductionVBox1.setStyle("-fx-border-color: black; -fx-border-insets: 3; -fx-border-width: 2;");

        // Top AnchorPane inside VBox
        topAnchorPane = new AnchorPane();
        topAnchorPane.setPrefSize(890, 41);

        // Education label
        educationLabel = new Label("Education");
        educationLabel.setPrefSize(758, 44);
        educationLabel.setAlignment(javafx.geometry.Pos.CENTER);
        educationLabel.setStyle("-fx-background-color: #0598ff;");
        educationLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        educationLabel.setPadding(new Insets(0, 0, 0, 10));
        educationLabel.setFont(new Font("MRT_Sima", 28));

        // Edit button
        editEducationButton = new Button("Edit");
        editEducationButton.setLayoutX(763);
        editEducationButton.setPrefSize(126, 42);
        editEducationButton.setStyle("-fx-background-color: #0598ff;");
        editEducationButton.setTextFill(javafx.scene.paint.Color.WHITE);
        editEducationButton.setFont(new Font(20));

        // Add education label and edit button to top AnchorPane
        topAnchorPane.getChildren().addAll(educationLabel, editEducationButton);

        // Main AnchorPane inside VBox
        mainAnchorPane = new AnchorPane();
        mainAnchorPane.setPrefSize(889, 238);

        // Name of the school text field
        nameOfTheSchoolTextField = new TextField();
        nameOfTheSchoolTextField.setLayoutX(106);
        nameOfTheSchoolTextField.setLayoutY(14);
        nameOfTheSchoolTextField.setPrefSize(107, 25);

        // Name of the school label
        Label nameOfTheSchoolLabel = new Label("Name of the school");
        nameOfTheSchoolLabel.setLayoutX(3);
        nameOfTheSchoolLabel.setLayoutY(18);
        nameOfTheSchoolLabel.setFont(new Font(11));

        // Field of study text field
        fieldOfStudyTextField = new TextField();
        fieldOfStudyTextField.setLayoutX(294);
        fieldOfStudyTextField.setLayoutY(15);
        fieldOfStudyTextField.setPrefSize(107, 25);

        // Field of study label
        Label fieldOfStudyLabel = new Label("Field of Study");
        fieldOfStudyLabel.setLayoutX(223);
        fieldOfStudyLabel.setLayoutY(19);
        fieldOfStudyLabel.setFont(new Font(11));

        // Start studying date picker
        startStudyingDatePicker = new DatePicker();
        startStudyingDatePicker.setLayoutX(499);
        startStudyingDatePicker.setLayoutY(16);
        startStudyingDatePicker.setPrefSize(91, 25);
        // Start studying label
        Label startStudyingLabel = new Label("Start studying");
        startStudyingLabel.setLayoutX(413);
        startStudyingLabel.setLayoutY(19);

        // End of education date picker
        endOfEducationDatePicker = new DatePicker();
        endOfEducationDatePicker.setLayoutX(701);
        endOfEducationDatePicker.setLayoutY(15);
        endOfEducationDatePicker.setPrefSize(91, 25);

        // End of education label
        Label endOfEducationLabel = new Label("End of education");
        endOfEducationLabel.setLayoutX(605);
        endOfEducationLabel.setLayoutY(20);

        // Grade text field
        gradeTextField = new TextField();
        gradeTextField.setLayoutX(106);
        gradeTextField.setLayoutY(52);
        gradeTextField.setPrefSize(107, 25);

        // Grade label
        Label gradeLabel = new Label("Grade");
        gradeLabel.setLayoutX(68);
        gradeLabel.setLayoutY(57);
        gradeLabel.setFont(new Font(11));

        // Inform checkbox
        informCheckBox = new CheckBox("Inform");
        informCheckBox.setLayoutX(811);
        informCheckBox.setLayoutY(19);

        // Activities text area
        activitiesTextArea = new TextArea();
        activitiesTextArea.setLayoutX(221);
        activitiesTextArea.setLayoutY(50);
        activitiesTextArea.setPrefSize(212, 175);
        activitiesTextArea.setPromptText("Description of activities and associations");

        // Description text area
        descriptionTextArea = new TextArea();
        descriptionTextArea.setLayoutX(440);
        descriptionTextArea.setLayoutY(51);
        descriptionTextArea.setPrefSize(442, 174);
        descriptionTextArea.setPromptText("Description");

        // Add components to main AnchorPane
        mainAnchorPane.getChildren().addAll(nameOfTheSchoolTextField, nameOfTheSchoolLabel, fieldOfStudyTextField, fieldOfStudyLabel,
                startStudyingDatePicker, startStudyingLabel, endOfEducationDatePicker, endOfEducationLabel,
                gradeTextField, gradeLabel, informCheckBox, activitiesTextArea, descriptionTextArea);

        // Add top AnchorPane and main AnchorPane to the main VBox
        introductionVBox1.getChildren().addAll(topAnchorPane, mainAnchorPane);

        // Add main VBox to itself (EducationComponent)
        this.getChildren().add(introductionVBox1);

        try {
            URL url = new URL("http://localhost:8081/home/profile/education");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
            con.setRequestMethod("GET");
            if (con.getResponseCode() == 200) {
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                ObjectMapper objectMapper = new ObjectMapper();
                Education education = objectMapper.readValue(response.toString(), Education.class);
                if(education == null) {
                    System.out.println("null contact info");
                    return;
                }
                nameOfTheSchoolTextField.setText(education.getNameOfTheSchool());
                fieldOfStudyTextField.setText(education.getFieldOfStudy());
                startStudyingDatePicker.setValue(convertLongToLocalDate(education.getStartStudying()));
                endOfEducationDatePicker.setValue(convertLongToLocalDate(education.getEndOfEducation()));
                gradeTextField.setText(education.getGrade());
                activitiesTextArea.setText(education.getActivities());
                descriptionTextArea.setText(education.getDescription());
            } else {
                System.out.println(con.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to handle edit button click event
    private void handleEditEducationButton() {
        if (!editable) {
            setButtonToSaveState();
            setEditableFields(true);
        } else {
            try {
                URL url = new URL("http://localhost:8081/home/profile/education");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> education = new HashMap<>();
                education.put("nameOfTheSchool", nameOfTheSchoolTextField.getText());
                education.put("fieldOfStudy", fieldOfStudyTextField.getText());
                education.put("startStudying", String.valueOf(convertToMilliseconds(startStudyingDatePicker.getValue())));
                education.put("endOfEducation" , String.valueOf(convertToMilliseconds(endOfEducationDatePicker.getValue())));
                education.put("grade" , gradeTextField.getText());
                education.put("activities" , activitiesTextArea.getText());
                education.put("description" , descriptionTextArea.getText());
                con.getOutputStream().write(objectMapper.writeValueAsString(education).getBytes());
                if (con.getResponseCode() == 200) {
                    setButtonToEditState();
                    setEditableFields(false);
                } else {
                    System.out.println(con.getResponseCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    String getSelectedRadioButtonText(ToggleGroup toggleGroup) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText();
        } else {
            return "null";
        }
    }

    private long convertToMilliseconds(LocalDate localDate) {
        if(localDate == null) {
            return 0;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime();
    }

    // Method to set fields editable or non-editable
    private void setEditableFields(boolean editable) {
        nameOfTheSchoolTextField.setEditable(editable);
        fieldOfStudyTextField.setEditable(editable);
        startStudyingDatePicker.setDisable(!editable);
        endOfEducationDatePicker.setDisable(!editable);
        gradeTextField.setEditable(editable);
        informCheckBox.setDisable(!editable);
        activitiesTextArea.setEditable(editable);
        descriptionTextArea.setEditable(editable);
    }

    // Method to set button text to "Edit" state
    private void setButtonToEditState() {
        editEducationButton.setText("Edit");
        editable = false;
    }

    // Method to set button text to "Save" state
    private void setButtonToSaveState() {
        editEducationButton.setText("Save");
        editable = true;
    }

    private LocalDate convertLongToLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}