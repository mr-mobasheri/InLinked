package com.client.components;

import com.client.models.Post;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;

import java.nio.file.Path;

public class PostComponent extends AnchorPane {
    Post post;
    boolean isLiked;
    String viewerUsername;
    ImageView profileImageView;
    Label usernameLabel;
    Label nameLabel;
    StackPane viewStackPane;
    ImageView imageView;
    MediaView mediaView;
    Button commentButton;
    Button likeButton;
    ImageView commentImageView;
    ImageView likeImageView;
    TextArea captionTextArea;

    public PostComponent(Post post) {
        this.post = post;
        this.setPrefSize(500, 600);
        this.setPadding(new Insets(5));
        if (post.getSender().getProfilePath() == null)
            profileImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/user.png").toUri().toString()));
        else {
            profileImageView = new ImageView(new Image(Path.of(post.getSender().getProfilePath()).toUri().toString()));
        }
        profileImageView.setFitHeight(37);
        profileImageView.setFitWidth(43);
        profileImageView.setLayoutX(14);
        profileImageView.setLayoutY(11);
        profileImageView.setPreserveRatio(true);

        usernameLabel = new Label("username");
        usernameLabel.setFont(new Font(16));
        usernameLabel.setLayoutX(62);
        usernameLabel.setLayoutY(7);

        nameLabel = new Label("name");
        nameLabel.setFont(new Font(16));
        nameLabel.setLayoutX(62);
        nameLabel.setLayoutY(23);

        viewStackPane = new StackPane();
        viewStackPane.setLayoutX(10);
        viewStackPane.setLayoutY(51);
        viewStackPane.setPrefSize(480, 400);
        viewStackPane.setStyle("-fx-border-color: Black;");

        imageView = new ImageView();
        imageView.setFitHeight(400);
        imageView.setFitWidth(480);
        imageView.setPreserveRatio(true);

        mediaView = new MediaView();
        mediaView.setFitHeight(400);
        mediaView.setFitWidth(480);

        viewStackPane.getChildren().addAll(imageView, mediaView);

        likeButton = new Button();
        likeButton.setLayoutX(14);
        likeButton.setLayoutY(453);
        likeButton.setPrefSize(27, 26);
        likeButton.setStyle("-fx-background-color: w;");
        if (post.getLikerUsername().contains(viewerUsername)) {
            likeImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/like.png").toUri().toString()));
            isLiked = true;
        } else {
            likeImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/unlike.png").toUri().toString()));
            isLiked = false;
        }
        likeImageView.setFitHeight(38);
        likeImageView.setFitWidth(32);
        likeImageView.setPreserveRatio(true);
        likeButton.setGraphic(likeImageView);
        likeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (isLiked) {
                    likeImageView.setImage(new Image(Path.of("src/main/resources/com/client/pictures/unlike.png").toUri().toString()));
                    post.getLikerUsername().remove(viewerUsername);
                } else {
                    likeImageView.setImage(new Image(Path.of("src/main/resources/com/client/pictures/like.png").toUri().toString()));
                    post.getLikerUsername().add(viewerUsername);
                }
            }
        });

        commentButton = new Button();
        commentButton.setLayoutX(63);
        commentButton.setLayoutY(451);
        commentButton.setPrefSize(27, 26);
        commentButton.setStyle("-fx-background-color: w;");
        commentImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/comment.png").toUri().toString()));
        commentImageView.setFitHeight(38);
        commentImageView.setFitWidth(32);
        commentImageView.setPreserveRatio(true);
        commentButton.setGraphic(commentImageView);
        commentButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

        captionTextArea = new TextArea();
        captionTextArea.setEditable(false);
        captionTextArea.setLayoutX(10);
        captionTextArea.setLayoutY(487);
        captionTextArea.setPrefSize(480, 105);
        captionTextArea.setPromptText("Caption");
        captionTextArea.setWrapText(true);
        this.getChildren().addAll(profileImageView, usernameLabel, nameLabel, viewStackPane, likeButton, commentButton, captionTextArea);
        initialize();
    }

    private void initialize() {
        usernameLabel.setText(post.getSender().getUsername());
        nameLabel.setText(post.getSender().getFirstName());
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}