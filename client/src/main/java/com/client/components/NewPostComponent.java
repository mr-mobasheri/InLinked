package com.client.components;

import com.client.LinkedinApplication;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;


public class NewPostComponent extends AnchorPane {

    String mediaPath;
    TextArea captionTextArea;
    Button postButton;
    Button addMediaButton;
    StackPane viewStackPane;
    ImageView imageView;
    MediaView mediaView;

    public NewPostComponent() {
        this.setMaxHeight(Double.NEGATIVE_INFINITY);
        this.setMaxWidth(Double.NEGATIVE_INFINITY);
        this.setMinHeight(Double.NEGATIVE_INFINITY);
        this.setMinWidth(Double.NEGATIVE_INFINITY);
        this.setPrefHeight(630.0);
        this.setPrefWidth(700.0);
        this.setPadding(new Insets(5, 5, 5, 5));

        captionTextArea = new TextArea();
        captionTextArea.setEditable(true);
        captionTextArea.setLayoutX(10.0);
        captionTextArea.setLayoutY(62.0);
        captionTextArea.setPrefHeight(200.0);
        captionTextArea.setPrefWidth(680.0);
        captionTextArea.setPromptText("Write here");
        captionTextArea.setWrapText(true);
        captionTextArea.setFont(new Font(18.0));

        postButton = new Button("Post");
        postButton.setLayoutX(598.0);
        postButton.setLayoutY(14.0);
        postButton.setPrefHeight(32.0);
        postButton.setPrefWidth(91.0);
        postButton.setStyle("-fx-background-color: #0598ff;");
        postButton.setTextFill(javafx.scene.paint.Color.WHITE);
        postButton.setFont(new Font(14.0));
        postButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (captionTextArea.getText().isEmpty()) {
                    captionTextArea.setText("please Write something!");
                }
            }
        });

        addMediaButton = new Button("add Media");
        addMediaButton.setLayoutX(594.0);
        addMediaButton.setLayoutY(274.0);
        addMediaButton.setPrefHeight(32.0);
        addMediaButton.setPrefWidth(91.0);
        addMediaButton.setStyle("-fx-background-color: #0598ff;");
        addMediaButton.setTextFill(javafx.scene.paint.Color.WHITE);
        addMediaButton.setFont(new Font(14.0));
        addMediaButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Media File");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"),
                        new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.flv", "*.m4v")
                );
                File file = fileChooser.showOpenDialog(LinkedinApplication.stage);
                if (file != null) {
                    mediaPath = file.toURI().toString();
                    Media media = new Media(mediaPath);
                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    mediaView.setMediaPlayer(mediaPlayer);
                    mediaPlayer.play();
                }
            }
        });

        viewStackPane = new StackPane();
        viewStackPane.setLayoutX(149.0);
        viewStackPane.setLayoutY(315.0);
        viewStackPane.setPrefHeight(300.0);
        viewStackPane.setPrefWidth(400.0);
        viewStackPane.setStyle("-fx-border-color: Black;");

        imageView = new ImageView();
        imageView.setFitHeight(300.0);
        imageView.setFitWidth(400.0);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        mediaView = new MediaView();
        mediaView.setFitHeight(300.0);
        mediaView.setFitWidth(400.0);

        viewStackPane.getChildren().addAll(imageView, mediaView);

        this.getChildren().addAll(captionTextArea, postButton, addMediaButton, viewStackPane);

    }

}