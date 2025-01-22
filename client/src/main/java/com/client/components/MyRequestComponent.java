package com.client.components;

import com.client.models.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.nio.file.Path;

public class MyRequestComponent extends AnchorPane {
    public Button deleteButton;

    public MyRequestComponent(User user) {
        this.setPrefSize(890, 70.0);
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

        Label usernameLabel = new Label(user.getUsername());
        usernameLabel.setLayoutX(84.0);
        usernameLabel.setLayoutY(6.0);
        usernameLabel.setFont(new Font(22.0));

        Label nameLabel = new Label(user.getFirstName());
        nameLabel.setLayoutX(84.0);
        nameLabel.setLayoutY(32.0);
        nameLabel.setFont(new Font(22.0));

        Label requestedLabel = new Label("requested");
        requestedLabel.setLayoutX(607.0);
        requestedLabel.setLayoutY(23.0);
        requestedLabel.setFont(new Font(20.0));

        deleteButton = new Button("delete");
        deleteButton.setLayoutX(719.0);
        deleteButton.setLayoutY(15.0);
        deleteButton.setPrefSize(95.0, 39.0);
        deleteButton.setStyle("-fx-background-color: #0598ff;");
        deleteButton.setTextFill(javafx.scene.paint.Color.WHITE);
        deleteButton.setFont(new Font(18.0));

        this.getChildren().addAll(profileImageView, usernameLabel, nameLabel, requestedLabel, deleteButton);
    }
}
