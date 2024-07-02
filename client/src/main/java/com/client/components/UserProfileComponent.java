package com.client.components;

import com.client.LinkedinApplication;
import com.client.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class UserProfileComponent extends AnchorPane {
    User user;
    String viewerUsername;
    VBox contentVBox;
    VBox introductionVBox;
    Label introductionLabel;
    VBox educationVBox;
    Label educationLabel;
    VBox skillsVBox;
    Label skillsLabel;
    ImageView profileImageView;
    Label usernameLabel;
    Label nameLabel;
    Button followButton;

    public UserProfileComponent(User user) {
        this.user = user;
        this.setPrefSize(900, 720);

        contentVBox = new VBox();
        contentVBox.setLayoutY(80);

        introductionVBox = new VBox();
        introductionVBox.setPrefSize(100, 200);
        introductionLabel = new Label("Introduction");
        introductionLabel.setAlignment(Pos.CENTER);
        introductionLabel.setPrefSize(926, 44);
        introductionLabel.setBackground(new Background(new BackgroundFill(Color.web("#0598ff"), CornerRadii.EMPTY, Insets.EMPTY)));
        introductionLabel.setTextFill(Color.WHITE);
        introductionLabel.setFont(new Font("MRT_Sima", 28));
        introductionLabel.setPadding(new Insets(0, 0, 0, 10));
        introductionVBox.getChildren().add(introductionLabel);

        educationVBox = new VBox();
        educationVBox.setPrefSize(900, 200);
        educationLabel = new Label("Education");
        educationLabel.setAlignment(Pos.CENTER);
        educationLabel.setPrefSize(902, 44);
        educationLabel.setBackground(new Background(new BackgroundFill(Color.web("#0598ff"), CornerRadii.EMPTY, Insets.EMPTY)));
        educationLabel.setTextFill(Color.WHITE);
        educationLabel.setFont(new Font("MRT_Sima", 28));
        educationLabel.setPadding(new Insets(0, 0, 0, 10));
        educationVBox.getChildren().add(educationLabel);

        skillsVBox = new VBox();
        skillsVBox.setPrefSize(900, 230);
        skillsLabel = new Label("Skills");
        skillsLabel.setAlignment(Pos.CENTER);
        skillsLabel.setPrefSize(903, 44);
        skillsLabel.setBackground(new Background(new BackgroundFill(Color.web("#0598ff"), CornerRadii.EMPTY, Insets.EMPTY)));
        skillsLabel.setTextFill(Color.WHITE);
        skillsLabel.setFont(new Font("MRT_Sima", 28));
        skillsLabel.setPadding(new Insets(0, 0, 0, 10));
        skillsVBox.getChildren().add(skillsLabel);

        contentVBox.getChildren().addAll(introductionVBox, educationVBox, skillsVBox);

        if (user.getProfilePath() == null)
            profileImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/user.png").toUri().toString()));
        else {
            profileImageView = new ImageView(new Image(Path.of(user.getProfilePath()).toUri().toString()));
        }
        profileImageView.setFitHeight(60);
        profileImageView.setFitWidth(55);
        profileImageView.setLayoutX(16);
        profileImageView.setLayoutY(18);
        profileImageView.setPickOnBounds(true);
        profileImageView.setPreserveRatio(true);

        usernameLabel = new Label("username");
        usernameLabel.setLayoutX(82);
        usernameLabel.setLayoutY(13);
        usernameLabel.setFont(new Font(22));

        nameLabel = new Label("name");
        nameLabel.setLayoutX(82);
        nameLabel.setLayoutY(39);
        nameLabel.setFont(new Font(22));

        followButton = new Button();
        try {
            URL url = new URL("http://localhost:8081/home/followers");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", "Bearer " + LinkedinApplication.token);
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
                List<User> users = objectMapper.readValue(response.toString(), new TypeReference<List<User>>() {
                });
                boolean check = true;
                for (User user1 : users) {
                    if (user.getUsername().equals(user1.getUsername())) {
                        followButton.setText("unfollow");
                        check = false;
                        break;
                    }
                }
                if (check) {
                    followButton.setText("follow");
                }
            }
            else {
                followButton.setText("server error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        followButton.setLayoutX(722);
        followButton.setLayoutY(25);
        followButton.setPrefHeight(32.0);
        followButton.setPrefWidth(120);
        followButton.setStyle("-fx-background-color: #0598ff;");
        followButton.setTextFill(javafx.scene.paint.Color.WHITE);
        followButton.setFont(new Font(20));
        followButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(followButton.getText().equals("follow")) {
                    try {
                        URL url = new URL("http://localhost:8081/home/follow/" + user.getUsername());
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestProperty("Authorization", "Bearer " + LinkedinApplication.token);
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
                            if(response.toString().equals("followed")) {
                                followButton.setText("unfollow");
                            }
                            else {
                                followButton.setText(response.toString());
                            }
                        }
                        else {
                            followButton.setText("server error");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if(followButton.getText().equals("unfollow")) {
                    try {
                        URL url = new URL("http://localhost:8081/home/unfollow/" + user.getUsername());
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestProperty("Authorization", "Bearer " + LinkedinApplication.token);
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
                            if(response.toString().equals("unfollowed")) {
                                followButton.setText("follow");
                            }
                            else {
                                followButton.setText(response.toString());
                            }
                        }
                        else {
                            followButton.setText("server error");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        this.getChildren().addAll(profileImageView, usernameLabel, nameLabel, followButton, contentVBox);
    }

}