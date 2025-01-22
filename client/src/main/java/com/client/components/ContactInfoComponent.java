package com.client.components;

import com.client.InLinkedApplication;
import com.client.models.ContactInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ContactInfoComponent extends VBox {

    private TextField emailTextField;
    private TextField phoneNumberTextField;
    private ToggleGroup phoneTypeToggleGroup;
    private RadioButton cellularPhoneRadioButton;
    private RadioButton homePhoneRadioButton;
    private RadioButton workPhoneRadioButton;
    private TextField addressTextField;
    private TextField userProfileLinkTextField;
    private TextField instantCommunicationTextField;
    private ComboBox<String> birthMonthComboBox;
    private ComboBox<Integer> birthdayComboBox;
    private final List<String> months = Arrays.asList(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    );

    private ToggleGroup birthdayDisplayPolicyToggleGroup;
    private RadioButton justMeRadioButton;
    private RadioButton myConnectionsRadioButton;
    private RadioButton myCommunicationNetworkRadioButton;
    private RadioButton allUsersRadioButton;

    private Button editButton; // Edit button instance
    private boolean editable = false; // Flag to track edit mode

    public ContactInfoComponent() {
        initializeUI();
    }

    private void initializeUI() {
        this.setPrefSize(890, 286);
        this.setStyle("-fx-border-color: black; -fx-border-insets: 3; -fx-border-width: 2;");
        this.setPadding(new Insets(10));

        // Header Pane
        AnchorPane headerPane = new AnchorPane();
        headerPane.setPrefSize(890, 41);

        Label headerLabel = new Label("Contact Information");
        headerLabel.setPrefSize(758, 44);
        headerLabel.setStyle("-fx-background-color: #0598ff;");
        headerLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        headerLabel.setFont(new Font("MRT_Sima", 28));
        headerLabel.setPadding(new Insets(0, 0, 0, 10));
        AnchorPane.setLeftAnchor(headerLabel, 0.0);
        AnchorPane.setRightAnchor(headerLabel, 132.0);

        editButton = new Button("Edit");
        editButton.setPrefSize(126, 42);
        editButton.setStyle("-fx-background-color: #0598ff;");
        editButton.setTextFill(javafx.scene.paint.Color.WHITE);
        editButton.setFont(new Font(20));
        editButton.setOnAction(event -> handleEditButtonPressed());
        AnchorPane.setRightAnchor(editButton, 0.0);

        headerPane.getChildren().addAll(headerLabel, editButton);

        // Content Pane
        AnchorPane contentPane = new AnchorPane();
        contentPane.setPrefSize(889, 238);

        emailTextField = new TextField();
        emailTextField.setLayoutX(258);
        emailTextField.setLayoutY(29);
        emailTextField.setPrefSize(127, 25);

        Label emailLabel = new Label("Email:");
        emailLabel.setLayoutX(223);
        emailLabel.setLayoutY(33);
        emailLabel.setFont(new Font(11));

        phoneNumberTextField = new TextField();
        phoneNumberTextField.setLayoutX(575);
        phoneNumberTextField.setLayoutY(27);
        phoneNumberTextField.setPrefSize(150, 25);

        instantCommunicationTextField = new TextField();
        instantCommunicationTextField.setLayoutX(130);
        instantCommunicationTextField.setLayoutY(160);
        instantCommunicationTextField.setPrefSize(150, 25);

        Label instantCommunicationLabel = new Label("Instant Communication");
        instantCommunicationLabel.setLayoutX(2);
        instantCommunicationLabel.setLayoutY(165);
        instantCommunicationLabel.setFont(new Font(11));

        Label userProfileLinkLabel = new Label("User profile link");
        userProfileLinkLabel.setLayoutX(11);
        userProfileLinkLabel.setLayoutY(32);
        userProfileLinkLabel.setFont(new Font(11));
        contentPane.getChildren().add(userProfileLinkLabel);

        userProfileLinkTextField = new TextField();
        userProfileLinkTextField.setLayoutX(103);
        userProfileLinkTextField.setLayoutY(31);
        userProfileLinkTextField.setPrefSize(90, 25);
        userProfileLinkTextField.isDisable();
        userProfileLinkTextField.setEditable(false);

        Label phoneNumberLabel = new Label("Phone Number:");
        phoneNumberLabel.setLayoutX(479);
        phoneNumberLabel.setLayoutY(33);
        phoneNumberLabel.setFont(new Font(11));

        phoneTypeToggleGroup = new ToggleGroup();

        cellularPhoneRadioButton = new RadioButton("Cellular Phone");
        cellularPhoneRadioButton.setLayoutX(737);
        cellularPhoneRadioButton.setLayoutY(9);
        cellularPhoneRadioButton.setToggleGroup(phoneTypeToggleGroup);

        homePhoneRadioButton = new RadioButton("Home Phone");
        homePhoneRadioButton.setLayoutX(737);
        homePhoneRadioButton.setLayoutY(32);
        homePhoneRadioButton.setToggleGroup(phoneTypeToggleGroup);

        workPhoneRadioButton = new RadioButton("Work Phone");
        workPhoneRadioButton.setLayoutX(737);
        workPhoneRadioButton.setLayoutY(55);
        workPhoneRadioButton.setToggleGroup(phoneTypeToggleGroup);

        addressTextField = new TextField();
        addressTextField.setLayoutX(72);
        addressTextField.setLayoutY(73);
        addressTextField.setPrefSize(564, 25);

        Label addressLabel = new Label("Address:");
        addressLabel.setLayoutX(23);
        addressLabel.setLayoutY(77);
        addressLabel.setFont(new Font(11));

        birthMonthComboBox = new ComboBox<>();
        birthMonthComboBox.setLayoutX(98);
        birthMonthComboBox.setLayoutY(123);
        birthMonthComboBox.setPrefWidth(150);
        birthMonthComboBox.setItems(FXCollections.observableArrayList(months));
        birthMonthComboBox.setValue("April");
        birthMonthComboBox.setOnAction(event -> handleMonthChange());

        Label birthMonthLabel = new Label("Birth Month:");
        birthMonthLabel.setLayoutX(31);
        birthMonthLabel.setLayoutY(127);
        birthMonthLabel.setFont(new Font(11));

        birthdayComboBox = new ComboBox<>();
        birthdayComboBox.setLayoutX(337);
        birthdayComboBox.setLayoutY(123);
        birthdayComboBox.setPrefWidth(150);
        birthdayComboBox.setValue(1);

        Label birthdayLabel = new Label("Birthday:");
        birthdayLabel.setLayoutX(277);
        birthdayLabel.setLayoutY(127);
        birthdayLabel.setFont(new Font(11));

        birthdayDisplayPolicyToggleGroup = new ToggleGroup();

        justMeRadioButton = new RadioButton("Just me");
        justMeRadioButton.setLayoutX(665);
        justMeRadioButton.setLayoutY(90);
        justMeRadioButton.setToggleGroup(birthdayDisplayPolicyToggleGroup);

        myConnectionsRadioButton = new RadioButton("My connections");
        myConnectionsRadioButton.setLayoutX(665);
        myConnectionsRadioButton.setLayoutY(115);
        myConnectionsRadioButton.setToggleGroup(birthdayDisplayPolicyToggleGroup);

        Label birthdayDisplayPolicyLable = new Label("Birthday Display Policy");
        birthdayDisplayPolicyLable.setLayoutX(550);
        birthdayDisplayPolicyLable.setLayoutY(127);
        birthdayDisplayPolicyLable.setFont(new Font(11));


        myCommunicationNetworkRadioButton = new RadioButton("My communication network");
        myCommunicationNetworkRadioButton.setLayoutX(665);
        myCommunicationNetworkRadioButton.setLayoutY(139);
        myCommunicationNetworkRadioButton.setToggleGroup(birthdayDisplayPolicyToggleGroup);

        allUsersRadioButton = new RadioButton("All users");
        allUsersRadioButton.setLayoutX(665);
        allUsersRadioButton.setLayoutY(163);
        allUsersRadioButton.setToggleGroup(birthdayDisplayPolicyToggleGroup);

        contentPane.getChildren().addAll(
                emailTextField, emailLabel, phoneNumberTextField, phoneNumberLabel,
                cellularPhoneRadioButton, homePhoneRadioButton, workPhoneRadioButton,
                addressTextField, userProfileLinkTextField, addressLabel, birthMonthComboBox, birthMonthLabel,
                birthdayComboBox, birthdayLabel, birthdayDisplayPolicyLable,
                justMeRadioButton, myConnectionsRadioButton,
                myCommunicationNetworkRadioButton, allUsersRadioButton, instantCommunicationTextField, instantCommunicationLabel
        );

        AnchorPane.setTopAnchor(contentPane, 41.0);

        this.getChildren().addAll(headerPane, contentPane);
        setEditable(false); // Initial state: non-editable

        try {
            URL url = new URL("http://localhost:8081/home/profile/contactInfo");
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
                ContactInfo contactInfo = objectMapper.readValue(response.toString(), ContactInfo.class);
                if(contactInfo == null) {
                    System.out.println("null contact info");
                    return;
                }
                emailTextField.setText(contactInfo.getEmail());
                phoneNumberTextField.setText(contactInfo.getPhoneNumber());
                addressTextField.setText(contactInfo.getAddress());
                instantCommunicationTextField.setText(contactInfo.getInstantCommunication());
                if (contactInfo.getBirthday() != null) {
                    birthdayComboBox.setValue(Integer.parseInt(contactInfo.getBirthday()));
                }
                if (contactInfo.getPhoneType() != null) {
                    for (Toggle toggle : phoneTypeToggleGroup.getToggles()) {
                        if (((RadioButton) toggle).getText().equals(contactInfo.getPhoneType())) {
                            phoneTypeToggleGroup.selectToggle(toggle);
                        }
                    }
                }
            } else {
                System.out.println(con.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setEditable(boolean editable) {
        emailTextField.setEditable(editable);
        phoneNumberTextField.setEditable(editable);
        addressTextField.setEditable(editable);
        birthMonthComboBox.setDisable(!editable);
        birthdayComboBox.setDisable(!editable);
        justMeRadioButton.setDisable(!editable);
        myConnectionsRadioButton.setDisable(!editable);
        myCommunicationNetworkRadioButton.setDisable(!editable);
        allUsersRadioButton.setDisable(!editable);
        instantCommunicationTextField.setEditable(editable);
        cellularPhoneRadioButton.setDisable(!editable);
        homePhoneRadioButton.setDisable(!editable);
        workPhoneRadioButton.setDisable(!editable);
    }

    private void handleEditButtonPressed() {
        if (editable) {
            // Switch to Save mode
            try {
                URL url = new URL("http://localhost:8081/home/profile/contactInfo");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> contactInformation = new HashMap<>();
                contactInformation.put("email", emailTextField.getText());
                contactInformation.put("phoneNumber", phoneNumberTextField.getText());
                contactInformation.put("phoneType", getSelectedRadioButtonText(phoneTypeToggleGroup));
                contactInformation.put("address", addressTextField.getText());
                contactInformation.put("birthday", String.valueOf(birthdayComboBox.getValue()));
                contactInformation.put("birthMonth", birthMonthComboBox.getValue());
                contactInformation.put("birthdayDisplayPolicy", getSelectedRadioButtonText(birthdayDisplayPolicyToggleGroup));
                contactInformation.put("instantCommunication", instantCommunicationTextField.getText());
                con.getOutputStream().write(objectMapper.writeValueAsString(contactInformation).getBytes());
                if (con.getResponseCode() == 200) {
                    editButton.setText("Edit");
                    setEditable(false);
                } else {
                    System.out.println(con.getResponseCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Switch to Edit mode
            editButton.setText("Save");
            setEditable(true);
        }
        editable = !editable; // Toggle editable flag
    }

    String getSelectedRadioButtonText(ToggleGroup toggleGroup) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            return selectedRadioButton.getText();
        } else {
            return "null";
        }
    }

    private void handleMonthChange() {
        String selectedMonth = birthMonthComboBox.getValue();
        if (selectedMonth != null) {
            int daysInMonth = getDaysInMonth(selectedMonth);
            birthdayComboBox.setItems(FXCollections.observableArrayList(
                    IntStream.rangeClosed(1, daysInMonth).boxed().toArray(Integer[]::new)
            ));
        }
    }

    private int getDaysInMonth(String month) {
        switch (month) {
            case "January":
            case "March":
            case "May":
            case "July":
            case "August":
            case "October":
            case "December":
                return 31;
            case "April":
            case "June":
            case "September":
            case "November":
                return 30;
            case "February":
                return 28; // You can modify this for handling leap years
            default:
                return 30;
        }
    }

    // Methods to set and get values from the component

    public RadioButton getJustMeRadioButton() {
        return justMeRadioButton;
    }

    public RadioButton getMyConnectionsRadioButton() {
        return myConnectionsRadioButton;
    }

    public RadioButton getMyCommunicationNetworkRadioButton() {
        return myCommunicationNetworkRadioButton;
    }

    public RadioButton getAllUsersRadioButton() {
        return allUsersRadioButton;
    }
}