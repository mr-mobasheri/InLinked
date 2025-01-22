package com.client.components;

import com.client.InLinkedApplication;
import com.client.models.MyInfo;
import com.client.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MyInfoComponent extends VBox {

    VBox introductionVBox;
    AnchorPane headerPane;
    Label headerLabel;
    Button editIntroductionButton;
    AnchorPane contentPane;
    TextField educationPlaceTextField;
    Label firstNameLabel;
    TextField lastNameTextField;
    Label lastNameLabel;
    TextField additionalNameTextField;
    Label additionalNameLabel;
    TextField headlineTextField;
    Label headlineLabel;
    TextField currentJobPositionTextField;
    Label currentJobPositionLabel;
    TextField firstNameTextField;
    TextField levelOfEducationTextField;
    Label educationPlaceLabel;
    Label levelOfEducationLabel;
    TextField countryTextField;
    TextField cityTextField;
    Label countryLabel;
    Label cityLabel;
    RadioButton lookingForANewJobRadioButton;
    ToggleGroup conditionToggleGroup;
    RadioButton recruitmentRadioButton;
    RadioButton providingServicesRadioButton;
    ComboBox<String> professionComboBox;
    Label professionLabel;
    TextField customProfessionTextField;
    Button addProfessionButton;

    private boolean isEditing = false;

    public MyInfoComponent() {
        setPrefHeight(228.0);
        setPrefWidth(890);

        introductionVBox = new VBox();
        introductionVBox.setPrefHeight(131.0);
        introductionVBox.setPrefWidth(899.0);
        introductionVBox.setStyle("-fx-border-color: black; -fx-border-insets: 3; -fx-border-width: 2;");

        headerPane = new AnchorPane();
        headerPane.setPrefHeight(34.0);
        headerPane.setPrefWidth(890.0);

        headerLabel = new Label("My Information");
        headerLabel.setPrefHeight(44.0);
        headerLabel.setPrefWidth(758.0);
        headerLabel.setStyle("-fx-background-color: #0598ff;");
        headerLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        headerLabel.setPadding(new Insets(0, 0, 0, 10));
        headerLabel.setFont(new Font("MRT_Sima", 28.0));
        AnchorPane.setLeftAnchor(headerLabel, 0.0);
        AnchorPane.setRightAnchor(headerLabel, 132.0);

        editIntroductionButton = new Button("Edit");
        editIntroductionButton.setPrefHeight(42.0);
        editIntroductionButton.setPrefWidth(126.0);
        editIntroductionButton.setStyle("-fx-background-color: #0598ff;");
        editIntroductionButton.setTextFill(javafx.scene.paint.Color.WHITE);
        editIntroductionButton.setFont(new Font(20.0));
        editIntroductionButton.setOnAction(event -> editIntroductionButtonPressed());
        AnchorPane.setRightAnchor(editIntroductionButton, 0.0);

        headerPane.getChildren().addAll(headerLabel, editIntroductionButton);

        contentPane = new AnchorPane();
        contentPane.setPrefHeight(168.0);
        contentPane.setPrefWidth(890.0);

        firstNameLabel = new Label("First Name");
        firstNameLabel.setLayoutX(40.0);
        firstNameLabel.setLayoutY(15.0);

        firstNameTextField = new TextField();
        firstNameTextField.setLayoutX(104.0);
        firstNameTextField.setLayoutY(11.0);
        firstNameTextField.setPrefHeight(25.0);
        firstNameTextField.setPrefWidth(107.0);

        lastNameLabel = new Label("Last Name");
        lastNameLabel.setLayoutX(35.0);
        lastNameLabel.setLayoutY(61.0);
        lastNameLabel.setFont(new Font(11.0));

        lastNameTextField = new TextField();
        lastNameTextField.setLayoutX(104.0);
        lastNameTextField.setLayoutY(56.0);
        lastNameTextField.setPrefHeight(25.0);
        lastNameTextField.setPrefWidth(107.0);

        additionalNameLabel = new Label("Additional Name");
        additionalNameLabel.setLayoutX(7.0);
        additionalNameLabel.setLayoutY(112.0);
        additionalNameTextField = new TextField();
        additionalNameTextField.setLayoutX(104.0);
        additionalNameTextField.setLayoutY(108.0);
        additionalNameTextField.setPrefHeight(25.0);
        additionalNameTextField.setPrefWidth(107.0);

        headlineLabel = new Label("Headline");
        headlineLabel.setLayoutX(258.0);
        headlineLabel.setLayoutY(15.0);

        headlineTextField = new TextField();
        headlineTextField.setLayoutX(315.0);
        headlineTextField.setLayoutY(11.0);
        headlineTextField.setPrefHeight(25.0);
        headlineTextField.setPrefWidth(145.0);

        currentJobPositionLabel = new Label("Current job position");
        currentJobPositionLabel.setLayoutX(223.0);
        currentJobPositionLabel.setLayoutY(61.0);
        currentJobPositionLabel.setFont(new Font(10.0));

        currentJobPositionTextField = new TextField();
        currentJobPositionTextField.setLayoutX(315.0);
        currentJobPositionTextField.setLayoutY(56.0);
        currentJobPositionTextField.setPrefHeight(25.0);
        currentJobPositionTextField.setPrefWidth(145.0);

        educationPlaceLabel = new Label("Education Place");
        educationPlaceLabel.setLayoutX(233.0);
        educationPlaceLabel.setLayoutY(112.0);
        educationPlaceLabel.setFont(new Font(10.0));

        educationPlaceTextField = new TextField();
        educationPlaceTextField.setLayoutX(315.0);
        educationPlaceTextField.setLayoutY(107.0);
        educationPlaceTextField.setPrefHeight(25.0);
        educationPlaceTextField.setPrefWidth(145.0);

        levelOfEducationLabel = new Label("Level of education");
        levelOfEducationLabel.setLayoutX(498.0);
        levelOfEducationLabel.setLayoutY(15.0);
        levelOfEducationLabel.setFont(new Font(10.0));

        levelOfEducationTextField = new TextField();
        levelOfEducationTextField.setLayoutX(583.0);
        levelOfEducationTextField.setLayoutY(11.0);
        levelOfEducationTextField.setPrefHeight(25.0);
        levelOfEducationTextField.setPrefWidth(107.0);

        countryLabel = new Label("Country");
        countryLabel.setLayoutX(532.0);
        countryLabel.setLayoutY(61.0);

        countryTextField = new TextField();
        countryTextField.setLayoutX(583.0);
        countryTextField.setLayoutY(57.0);
        countryTextField.setPrefHeight(25.0);
        countryTextField.setPrefWidth(107.0);

        cityLabel = new Label("City");
        cityLabel.setLayoutX(552.0);
        cityLabel.setLayoutY(111.0);

        cityTextField = new TextField();
        cityTextField.setLayoutX(583.0);
        cityTextField.setLayoutY(108.0);
        cityTextField.setPrefHeight(25.0);
        cityTextField.setPrefWidth(107.0);

        conditionToggleGroup = new ToggleGroup();

        lookingForANewJobRadioButton = new RadioButton("Looking for a new job");
        lookingForANewJobRadioButton.setLayoutX(741.0);
        lookingForANewJobRadioButton.setLayoutY(15.0);
        lookingForANewJobRadioButton.setToggleGroup(conditionToggleGroup);

        recruitmentRadioButton = new RadioButton("Recruitment");
        recruitmentRadioButton.setLayoutX(741.0);
        recruitmentRadioButton.setLayoutY(64.0);
        recruitmentRadioButton.setToggleGroup(conditionToggleGroup);

        providingServicesRadioButton = new RadioButton("Providing services");
        providingServicesRadioButton.setLayoutX(741.0);
        providingServicesRadioButton.setLayoutY(112.0);
        providingServicesRadioButton.setToggleGroup(conditionToggleGroup);

        professionLabel = new Label("Profession");
        professionLabel.setLayoutX(248.0);
        professionLabel.setLayoutY(149.0);

        professionComboBox = new ComboBox<>();
        professionComboBox.setLayoutX(313.0);
        professionComboBox.setLayoutY(143.0);
        professionComboBox.setPrefWidth(150.0);
        professionComboBox.getItems().addAll(
                "Developer",
                "Designer",
                "Manager",
                "Analyst",
                "Consultant"
        );
        professionComboBox.setValue("Developer");
        customProfessionTextField = new TextField();
        customProfessionTextField.setLayoutX(473.0);
        customProfessionTextField.setLayoutY(143.0);
        customProfessionTextField.setPrefWidth(150.0);

        addProfessionButton = new Button("Add");
        addProfessionButton.setLayoutX(633.0);
        addProfessionButton.setLayoutY(143.0);
        addProfessionButton.setOnAction(event -> addCustomProfession());

        contentPane.getChildren().addAll(
                firstNameLabel, firstNameTextField, lastNameLabel, lastNameTextField, additionalNameLabel, additionalNameTextField,
                headlineLabel, headlineTextField, currentJobPositionLabel, currentJobPositionTextField,
                educationPlaceLabel, educationPlaceTextField, levelOfEducationLabel, levelOfEducationTextField,
                countryLabel, countryTextField, cityLabel, cityTextField, lookingForANewJobRadioButton,
                recruitmentRadioButton, providingServicesRadioButton, professionLabel, professionComboBox,
                customProfessionTextField, addProfessionButton
        );

        introductionVBox.getChildren().addAll(headerPane, contentPane);
        getChildren().add(introductionVBox);

        setFieldsEditable(false);

        try {
            URL url = new URL("http://localhost:8081/home/profile/myInfo");
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
                MyInfo myInfo = objectMapper.readValue(response.toString(), MyInfo.class);
                if (myInfo != null) {
                    additionalNameTextField.setText(myInfo.getAdditionalName());
                    headlineTextField.setText(myInfo.getHeadline());
                    currentJobPositionTextField.setText(myInfo.getCurrentJobPosition());
                    levelOfEducationTextField.setText(myInfo.getLevelOfEducation());
                    educationPlaceTextField.setText(myInfo.getEducationPlace());
                    cityTextField.setText(myInfo.getCity());
                    if (myInfo.getProfession() != null) {
                        professionComboBox.setValue(myInfo.getProfession());
                    }
                    if (myInfo.getCondition() != null) {
                        for (Toggle toggle : conditionToggleGroup.getToggles()) {
                            if (((RadioButton) toggle).getText().equals(myInfo.getCondition())) {
                                conditionToggleGroup.selectToggle(toggle);
                            }
                        }
                    }
                }
            } else {
                System.out.println(con.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL("http://localhost:8081/home/profile");
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
                User user = objectMapper.readValue(response.toString(), User.class);
                firstNameTextField.setText(user.getFirstName());
                lastNameTextField.setText(user.getLastName());
                countryTextField.setText(user.getCountry());
            } else {
                System.out.println(con.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void editIntroductionButtonPressed() {
        if (!isEditing) {
            editIntroductionButton.setText("Save");
            setFieldsEditable(true);
            isEditing = true;
        } else {
            try {
                URL url = new URL("http://localhost:8081/home/profile/myInfo");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> introduction = new HashMap<>();
                introduction.put("firstName", firstNameTextField.getText());
                introduction.put("lastName", lastNameTextField.getText());
                introduction.put("additionalName", additionalNameTextField.getText());
                introduction.put("headline", headlineTextField.getText());
                introduction.put("currentJobPosition", currentJobPositionTextField.getText());
                introduction.put("levelOfEducation", levelOfEducationTextField.getText());
                introduction.put("educationPlace", educationPlaceTextField.getText());
                if (countryTextField.getText() != null) {
                    introduction.put("country", countryTextField.getText());
                } else {
                    introduction.put("country", "");
                }
                introduction.put("city", cityTextField.getText());
                introduction.put("profession", professionComboBox.getValue());
                introduction.put("condition", getSelectedRadioButtonText(conditionToggleGroup));
                con.getOutputStream().write(objectMapper.writeValueAsString(introduction).getBytes());
                if (con.getResponseCode() == 200) {
                    editIntroductionButton.setText("Edit");
                    setFieldsEditable(false);
                    isEditing = false;
                } else {
                    System.out.println(con.getResponseCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setFieldsEditable(boolean editable) {
        firstNameTextField.setEditable(editable);
        lastNameTextField.setEditable(editable);
        additionalNameTextField.setEditable(editable);
        headlineTextField.setEditable(editable);
        currentJobPositionTextField.setEditable(editable);
        educationPlaceTextField.setEditable(editable);
        levelOfEducationTextField.setEditable(editable);
        countryTextField.setEditable(editable);
        cityTextField.setEditable(editable);
        lookingForANewJobRadioButton.setDisable(!editable);
        recruitmentRadioButton.setDisable(!editable);
        providingServicesRadioButton.setDisable(!editable);
        professionComboBox.setDisable(!editable);
        customProfessionTextField.setEditable(editable); // Enable/disable custom profession field
        addProfessionButton.setDisable(!editable); // Enable/disable add button
    }

    private void addCustomProfession() {
        String customProfession = customProfessionTextField.getText().trim();
        if (!customProfession.isEmpty() && !professionComboBox.getItems().contains(customProfession)) {
            professionComboBox.getItems().add(customProfession);
            professionComboBox.setValue(customProfession); // Optionally set the value to the newly added item
            customProfessionTextField.clear(); // Clear the text field after adding
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
}