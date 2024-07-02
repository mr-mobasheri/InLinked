package com.client.components;

import com.client.models.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

import java.nio.file.Path;

public class SearchResultComponent extends AnchorPane {
    ImageView profileImageView;
    Label usernameLabel;
    Label nameLabel;
    //    Label followingLabel;
    public Button userButton;

    public SearchResultComponent(User user) {
        this.setPrefSize(900, 70);
        this.setStyle("-fx-border-color: blue; -fx-border-width: 3;");
        if (user.getProfilePath() == null)
            profileImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/user.png").toUri().toString()));
        else {
            profileImageView = new ImageView(new Image(Path.of(user.getProfilePath()).toUri().toString()));
        }
        profileImageView.setFitHeight(60);
        profileImageView.setFitWidth(55);
        profileImageView.setLayoutX(115);
        profileImageView.setLayoutY(10);
        profileImageView.setPickOnBounds(true);
        profileImageView.setPreserveRatio(true);

        usernameLabel = new Label(user.getUsername());
        usernameLabel.setLayoutX(181);
        usernameLabel.setLayoutY(5);
        usernameLabel.setFont(new Font(22));

        nameLabel = new Label(user.getFirstName());
        nameLabel.setLayoutX(181);
        nameLabel.setLayoutY(31);
        nameLabel.setFont(new Font(22));

//        followingLabel = new Label("Following");
//        followingLabel.setLayoutX(731);
//        followingLabel.setLayoutY(16);
//        followingLabel.setFont(new Font(20));

        userButton = new Button();
        userButton.setLayoutY(1);
        userButton.setMnemonicParsing(false);
        userButton.setPrefSize(900, 70);
        userButton.setStyle("-fx-opacity: 0;");

        this.getChildren().addAll(profileImageView, usernameLabel, nameLabel, userButton);
    }
}