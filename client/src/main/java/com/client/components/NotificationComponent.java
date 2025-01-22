package com.client.components;

import com.client.InLinkedApplication;
import com.client.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class NotificationComponent extends AnchorPane {
    VBox connectRequestVBox;
    VBox requestToConnectVBox;

    public NotificationComponent() {
        this.setPrefSize(880, 650.0);

        ScrollPane scrollPane1 = new ScrollPane();
        scrollPane1.setLayoutY(44.0);
        scrollPane1.setPrefSize(880, 281.0);
        connectRequestVBox = new VBox();
        connectRequestVBox.setPrefSize(844.0, 274.0);
        scrollPane1.setContent(connectRequestVBox);

        try {
            URL url = new URL("http://localhost:8081/home/connect/myRequest");
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
                List<User> users = objectMapper.readValue(response.toString(), new TypeReference<List<User>>() {
                });
                for (User user : users) {
                    MyRequestComponent myRequestComponent = new MyRequestComponent(user);
                    myRequestComponent.deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                URL url = new URL("http://localhost:8081/home/connect/deleteRequest/" + user.getUsername());
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                                con.setRequestMethod("GET");
                                if (con.getResponseCode() == 200) {
                                    connectRequestVBox.getChildren().remove(myRequestComponent);
                                } else {
                                    myRequestComponent.deleteButton.setText("server error");
                                    System.out.println(con.getResponseCode());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                myRequestComponent.deleteButton.setText("server error");
                            }
                        }
                    });
                    connectRequestVBox.getChildren().add(myRequestComponent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setLayoutY(367.0);
        scrollPane2.setPrefSize(880, 282.0);
        requestToConnectVBox = new VBox();
        requestToConnectVBox.setPrefSize(847.0, 279.0);
        scrollPane2.setContent(requestToConnectVBox);

        try {
            URL url = new URL("http://localhost:8081/home/connect/otherRequest");
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
                List<User> users = objectMapper.readValue(response.toString(), new TypeReference<List<User>>() {
                });
                for (User user : users) {
                    OtherRequestComponent otherRequestComponent = new OtherRequestComponent(user);
                    otherRequestComponent.acceptButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            try {
                                URL url = new URL("http://localhost:8081/home/connect/accept/" + user.getUsername());
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                                con.setRequestMethod("GET");
                                if (con.getResponseCode() == 200) {
                                    requestToConnectVBox.getChildren().remove(otherRequestComponent);
                                } else {
                                    otherRequestComponent.acceptButton.setText("server error");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                otherRequestComponent.acceptButton.setText("server error");
                            }
                        }
                    });
                    requestToConnectVBox.getChildren().add(otherRequestComponent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Label label1 = new Label("My Connect Requests");
        label1.setAlignment(javafx.geometry.Pos.CENTER);
        label1.setPrefSize(880, 44.0);
        label1.setStyle("-fx-background-color: #0598ff;");
        label1.setTextFill(javafx.scene.paint.Color.WHITE);
        label1.setPadding(new Insets(0, 0, 0, 10.0));
        label1.setFont(Font.font("MRT_Sima", 28.0));

        Label label2 = new Label("Other Requests to Connect");
        label2.setAlignment(javafx.geometry.Pos.CENTER);
        label2.setLayoutY(325.0);
        label2.setPrefSize(880, 44.0);
        label2.setStyle("-fx-background-color: #0598ff;");
        label2.setTextFill(javafx.scene.paint.Color.WHITE);
        label2.setPadding(new Insets(0, 0, 0, 10.0));
        label2.setFont(Font.font("MRT_Sima", 28.0));

        this.getChildren().addAll(scrollPane1, scrollPane2, label1, label2);
    }
}