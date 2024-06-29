package com.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.*;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.text.TextAlignment;

public class LoginController {

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextfield;

    @FXML
    private Hyperlink joinNowHyperlink;

    @FXML
    private Button loginButton;

    @FXML
    private Label wrongLabel;

    @FXML
    private Label passLabel;

    @FXML
    private PasswordField passTextfield;

    public void initialize() {
        emailTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue,
                                String oldValue, String newValue) {
                if (!newValue.isEmpty() && !isValidEmailAddress(newValue)) {
                    emailLabel.setText("Email is invalid");
                } else {
                    emailLabel.setText("");
                }
            }
        });

        passTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.isEmpty()) {
                    passLabel.setText("");
                }
            }
        });
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Matcher m = (java.util.regex.Pattern.compile(ePattern)).matcher(email);
        return m.matches();
    }

    @FXML
    void joinNowHyperlinkPressed(ActionEvent event) {
        LinkedinApplication.changeScene(SceneName.signUP);
    }

    @FXML
    void loginButtonPressed(ActionEvent event) {
        if (emailTextfield.getText().isEmpty() || passTextfield.getText().isEmpty()) {
            if (emailTextfield.getText().isEmpty()) {
                emailLabel.setText("enter your email");
            }
            if (passTextfield.getText().isEmpty()) {
                passLabel.setText("enter your password");
            }
        } else if (!isValidEmailAddress(emailTextfield.getText())) {
            emailLabel.setText("Email is invalid");
        } else {
            try {
                URL url = new URL("http://localhost:8081/auth/login");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> credentials = new HashMap<>();
                credentials.put("email", emailTextfield.getText());
                credentials.put("password", passTextfield.getText());
                con.getOutputStream().write(objectMapper.writeValueAsString(credentials).getBytes());
                if(con.getResponseCode() == 401) {
                    wrongLabel.setText("email or password is incorrect");
                }
                else {
                    StringBuilder response = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(con.getInputStream(), "utf-8"))) {
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                    }
                    LinkedinApplication.token = response.toString();
                    wrongLabel.setText("signed in!");
                }
            }
            catch (Exception e) {
                wrongLabel.setText("connection failed");
            }
        }

    }

}
