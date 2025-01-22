package com.client.components;

import com.client.InLinkedApplication;
import com.client.models.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.converter.DefaultStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class NewPostComponent extends AnchorPane {

    String mediaPath;
    TextArea captionTextArea;
    Button postButton;
    Button addMediaButton;
    StackPane viewStackPane;
    ImageView imageView;
    MediaPlayerComponent mediaPlayerComponent;
    Label responseLabel;

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
        TextFormatter<String> formatter = new TextFormatter<>(new DefaultStringConverter(), null, change -> {
            if (change.getControlNewText().length() <= 3000) {
                return change;
            } else {
                return null;
            }
        });
        captionTextArea.setTextFormatter(formatter);

        responseLabel = new Label();
        responseLabel.setLayoutX(5);
        responseLabel.setLayoutX(62);
        responseLabel.setFont(new Font(20));

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
                    captionTextArea.selectAll();
                } else {
                    Post post = new Post();
                    post.setText(captionTextArea.getText());
                    post.setMediaPath(mediaPath);
                    try {
                        URL url = new URL("http://localhost:8081/home/post");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        ObjectMapper objectMapper = new ObjectMapper();
                        con.getOutputStream().write(objectMapper.writeValueAsString(post).getBytes());
                        if (con.getResponseCode() == 200) {
                            responseLabel.setText("New post Saved");
                        } else {
                            System.out.println(con.getResponseCode());
                            responseLabel.setText("server error");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        responseLabel.setText("server error");
                    }
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
                fileChooser.setTitle("Select an Image or Video");
                File file = fileChooser.showOpenDialog(InLinkedApplication.stage);
                if (file != null) {
                    mediaPath = file.toURI().toString();
                    try {
                        String mimeType = Files.probeContentType(Path.of(file.toURI()));
                        if (mimeType != null) {
                            if (mimeType.startsWith("image/")) {
                                imageView.setImage(new Image(mediaPath));
                                if (mediaPlayerComponent != null) {
                                    mediaPlayerComponent.mediaPlayer.pause();
                                    mediaPlayerComponent.setVisible(false);
                                }
                            } else if (mimeType.startsWith("video/")) {
                                Media media = new Media(mediaPath);
                                MediaPlayer mediaPlayer = new MediaPlayer(media);
                                mediaPlayerComponent = new MediaPlayerComponent(mediaPlayer);
                                viewStackPane.getChildren().add(mediaPlayerComponent);
                                imageView.setImage(null);
                                mediaPlayerComponent.setVisible(true);
                            } else {
                                if (mediaPlayerComponent != null) {
                                    mediaPlayerComponent.mediaPlayer.pause();
                                    mediaPlayerComponent.setVisible(false);
                                }
                                imageView.setImage(new Image(Path.of("src/main/resources/com/client/pictures/cantOpenFile.png").toUri().toString()));
                            }
                        } else {
                            if (mediaPlayerComponent != null) {
                                mediaPlayerComponent.mediaPlayer.pause();
                                mediaPlayerComponent.setVisible(false);
                            }
                            imageView.setImage(new Image(Path.of("src/main/resources/com/client/pictures/cantOpenFile.png").toUri().toString()));
                        }
                    } catch (IOException e) {
                        if (mediaPlayerComponent != null) {
                            mediaPlayerComponent.mediaPlayer.pause();
                            mediaPlayerComponent.setVisible(false);
                        }
                        e.printStackTrace();
                        imageView.setImage(new Image(Path.of("src/main/resources/com/client/pictures/cantOpenFile.png").toUri().toString()));
                    }
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

        viewStackPane.getChildren().addAll(imageView);

        this.getChildren().addAll(responseLabel, captionTextArea, postButton, addMediaButton, viewStackPane);

    }

}