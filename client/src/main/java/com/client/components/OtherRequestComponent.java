package com.client.components;

import com.client.models.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.nio.file.Path;

public class OtherRequestComponent extends AnchorPane {
    public Button acceptButton;
    public Button rejectButton;

    public OtherRequestComponent(User user) {
        this.setMaxHeight(Double.NEGATIVE_INFINITY);
        this.setMaxWidth(Double.NEGATIVE_INFINITY);
        this.setMinHeight(Double.NEGATIVE_INFINITY);
        this.setMinWidth(Double.NEGATIVE_INFINITY);
        this.setPrefHeight(70.0);
        this.setPrefWidth(890);
        this.setStyle("-fx-border-color: black; -fx-border-width: 3;");

        ImageView profileImageView;
        if (user.getProfilePath() == null)
            profileImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/user.png").toUri().toString()));
        else {
            profileImageView = new ImageView(new Image(Path.of(user.getProfilePath()).toUri().toString()));
        }
        profileImageView.setFitHeight(60.0);
        profileImageView.setFitWidth(55.0);
        profileImageView.setLayoutX(21.0);
        profileImageView.setLayoutY(8.0);
        profileImageView.setPickOnBounds(true);
        profileImageView.setPreserveRatio(true);
        profileImageView.setClip(new Circle(16, 16, 45));

        Label usernameLabel = new Label(user.getUsername());
        usernameLabel.setLayoutX(84.0);
        usernameLabel.setLayoutY(6.0);
        usernameLabel.setFont(new Font(22.0));

        Label nameLabel = new Label(user.getFirstName());
        nameLabel.setLayoutX(84.0);
        nameLabel.setLayoutY(32.0);
        nameLabel.setFont(new Font(22.0));

        Label requestsLabel = new Label("requests to connect");
        requestsLabel.setLayoutX(421.0);
        requestsLabel.setLayoutY(20.0);
        requestsLabel.setFont(new Font(20.0));

        acceptButton = new Button("accept");
        acceptButton.setLayoutX(719.0);
        acceptButton.setLayoutY(15.0);
        acceptButton.setPrefHeight(39.0);
        acceptButton.setPrefWidth(95.0);
        acceptButton.setStyle("-fx-background-color: #0598ff;");
        acceptButton.setTextFill(javafx.scene.paint.Color.WHITE);
        acceptButton.setFont(new Font(18.0));

        rejectButton = new Button("reject");
        rejectButton.setLayoutX(611.0);
        rejectButton.setLayoutY(16.0);
        rejectButton.setPrefHeight(39.0);
        rejectButton.setPrefWidth(95.0);
        rejectButton.setStyle("-fx-background-color: #0598ff;");
        rejectButton.setTextFill(javafx.scene.paint.Color.WHITE);
        rejectButton.setFont(new Font(18.0));

        this.getChildren().addAll(profileImageView, usernameLabel, nameLabel, requestsLabel, acceptButton, rejectButton);
    }
}
