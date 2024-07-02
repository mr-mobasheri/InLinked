package com.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class SignUpController {

    @FXML
    private Label confirmPassLabel;

    @FXML
    private PasswordField confirmPassTextfield;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextfield;

    @FXML
    private Label firstnameLabel;

    @FXML
    private TextField firstnameTextfield;

    @FXML
    private Label lastnameLabel;

    @FXML
    private TextField lastnameTextfield;

    @FXML
    private Label passLabel;

    @FXML
    private PasswordField passTextfield;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label wrongLabel;

    @FXML
    private TextField usernameTextfield;

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

        usernameTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.isEmpty()) {
                    usernameLabel.setText("");
                }
            }
        });

        firstnameTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.isEmpty()) {
                    firstnameLabel.setText("");
                }
            }
        });

        lastnameTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.isEmpty()) {
                    lastnameLabel.setText("");
                }
            }
        });

        passTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.isEmpty() && !isValidPass(t1)) {
                    passLabel.setText("at least 8 char, one cap, one small and one digit");
                }
                else {
                    passLabel.setText("");
                }
            }
        });

        confirmPassTextfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(!t1.isEmpty()) {
                    confirmPassLabel.setText("");
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
    void signInButtonPressed(ActionEvent event) {
        clean();
        try {
            LinkedinApplication.changeScene(SceneName.login);
        } catch (IOException e) {
            e.printStackTrace();
            wrongLabel.setText("internal error");
        }
    }

    private void clean() {
        emailLabel.setText("");
        usernameLabel.setText("");
        firstnameLabel.setText("");
        lastnameLabel.setText("");
        passLabel.setText("");
        confirmPassLabel.setText("");
        wrongLabel.setText("");
    }

    private boolean check() {
        boolean check = true;
        if (!isValidEmailAddress(emailTextfield.getText())) {
            emailLabel.setText("Email is invalid");
            check = false;
        }
        if(emailTextfield.getText().isEmpty()) {
            emailLabel.setText("enter your email");
            check = false;
        }
        if(usernameTextfield.getText().isEmpty()) {
            usernameLabel.setText("enter your username");
            check = false;
        }
        else {
            for (char c : usernameTextfield.getText().toCharArray()) {
                if(!Character.isAlphabetic(c)) {
                    usernameLabel.setText("username can only contain letters");
                    break;
                }
            }
        }
        if(firstnameTextfield.getText().isEmpty()) {
            firstnameLabel.setText("enter your firstname");
            check = false;
        }
        if(lastnameTextfield.getText().isEmpty()) {
            lastnameLabel.setText("enter your last name");
            check = false;
        }
        if(passTextfield.getText().isEmpty()) {
            passLabel.setText("enter your password");
            check = false;
        }
        if(confirmPassTextfield.getText().isEmpty()) {
            confirmPassLabel.setText("repeat your password");
            check = false;
        }
        else if(!confirmPassTextfield.getText().equals(passTextfield.getText())) {
            confirmPassLabel.setText("Passwords are not equal");
            check = false;
        }
        return check;
    }

    public static boolean isValidPass(String pass) {
        boolean cap = false, small = false, digit = false;
        for (char c: pass.toCharArray()) {
            if (Character.isLowerCase(c))
                small = true;
            if (Character.isUpperCase(c))
                cap = true;
            if(Character.isDigit(c))
                digit = true;
        }
        return cap && small && digit && pass.length() >= 8;
    }

    @FXML
    void signUpButtonPressed(ActionEvent event) {
        if(!check()) {
            return;
        }
        try {
            URL url = new URL("http://localhost:8081/auth/register");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> credentials = new HashMap<>();
            credentials.put("email", emailTextfield.getText());
            credentials.put("username", usernameTextfield.getText());
            credentials.put("firstname", firstnameTextfield.getText());
            credentials.put("lastname", lastnameTextfield.getText());
            credentials.put("password", passTextfield.getText());
            con.getOutputStream().write(objectMapper.writeValueAsString(credentials).getBytes());
            if (con.getResponseCode() == 406) {
                usernameLabel.setText("username is already taken");
            } else if (con.getResponseCode() == 409) {
                emailLabel.setText("this email is already registered");
            } else if(con.getResponseCode() == 200){
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                }
                LinkedinApplication.token = response.toString();
                clean();
                LinkedinApplication.changeScene(SceneName.home);
            }
            else {
                wrongLabel.setText("server error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            wrongLabel.setText("connection failed");
        }
    }

}
