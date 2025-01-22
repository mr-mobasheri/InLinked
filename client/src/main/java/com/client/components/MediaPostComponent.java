package com.client.components;

import com.client.LinkedinApplication;
import com.client.models.Post;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.client.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class MediaPostComponent extends StackPane {
    Post post;
    boolean isLiked;
    String viewerUsername;
    AnchorPane mainAnchorpane;
    ImageView profileImageView;
    Label usernameLabel;
    Label nameLabel;
    StackPane viewStackPane;
    ImageView imageView;
    MediaView mediaView;
    Button commentButton;
    Button likeButton;
    Button likedByButton;
    ImageView commentImageView;
    ImageView likeImageView;
    TextArea captionTextArea;

    public MediaPostComponent(Post post) {
        this.post = post;
        viewerUsername = getUsernameFromJwt(LinkedinApplication.token);

        mainAnchorpane = new AnchorPane();
        mainAnchorpane.setPrefSize(500, 600);
        mainAnchorpane.setPadding(new Insets(5));
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

        usernameLabel = new Label(post.getSender().getUsername());
        usernameLabel.setFont(new Font(16));
        usernameLabel.setLayoutX(62);
        usernameLabel.setLayoutY(7);

        nameLabel = new Label(post.getSender().getFirstName());
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
        for (String username : post.getLikerUsername()) {
            if (username.equals(viewerUsername)) {
                likeImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/like.png").toUri().toString()));
                isLiked = true;
            }
        }
        if (!isLiked) {
            likeImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/unlike.png").toUri().toString()));
        }
        likeImageView.setFitHeight(38);
        likeImageView.setFitWidth(32);
        likeImageView.setPreserveRatio(true);
        likeButton.setGraphic(likeImageView);
        likeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (isLiked) {
                    try {
                        URL url = new URL("http://localhost:8081/home/post/unlike/" + post.getId());
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestProperty("Authorization", "Bearer " + LinkedinApplication.token);
                        con.setRequestMethod("GET");
                        if (con.getResponseCode() != 200) {
                            System.out.println(con.getResponseCode());
                        } else {
                            isLiked = false;
                            post.getLikerUsername().remove(viewerUsername);
                            likeImageView.setImage(new Image(Path.of("src/main/resources/com/client/pictures/unlike.png").toUri().toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        URL url = new URL("http://localhost:8081/home/post/like/" + post.getId());
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestProperty("Authorization", "Bearer " + LinkedinApplication.token);
                        con.setRequestMethod("GET");
                        if (con.getResponseCode() != 200) {
                            System.out.println(con.getResponseCode());
                        } else {
                            likeImageView.setImage(new Image(Path.of("src/main/resources/com/client/pictures/like.png").toUri().toString()));
                            isLiked = true;
                            post.getLikerUsername().add(viewerUsername);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        likedByButton = new Button("liked By");
        likedByButton.setLayoutX(60);
        likedByButton.setLayoutY(456);
        likedByButton.setPrefSize(60, 26);
        likedByButton.setStyle("-fx-background-color: blue;");
        likedByButton.setTextFill(Color.WHITE);
        likedByButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                mainAnchorpane.setVisible(false);
                AnchorPane root = new AnchorPane();
                root.setPrefSize(500.0, 600.0);
                root.setStyle("-fx-border-color: black;");

                Button backButton = new Button("<");
                backButton.setLayoutX(14.0);
                backButton.setLayoutY(14.0);
                backButton.setPrefSize(30.0, 32.0);
                backButton.setStyle("-fx-background-color: #0598ff;");
                backButton.setTextFill(javafx.scene.paint.Color.WHITE);
                backButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        MediaPostComponent.super.getChildren().remove(1);
                        mainAnchorpane.setVisible(true);
                    }
                });

                Label likedByLabel = new Label("Liked by:");
                likedByLabel.setLayoutX(201.0);
                likedByLabel.setLayoutY(15.0);
                likedByLabel.setFont(new Font(20.0));

                VBox vBox = new VBox();
                vBox.setPrefSize(446.0, 500.0);

                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setLayoutX(25.0);
                scrollPane.setLayoutY(76.0);
                scrollPane.setFitToWidth(true);
                scrollPane.setPrefSize(450.0, 504.0);
                scrollPane.setStyle("-fx-border-color: black;");
                scrollPane.setContent(vBox);

                root.getChildren().addAll(backButton, likedByLabel, scrollPane);
                root.setPadding(new Insets(5.0));
                MediaPostComponent.super.getChildren().add(root);

                try {
                    URL url = new URL("http://localhost:8081/home/user/" + post.getId());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Authorization", "Bearer " + LinkedinApplication.token);
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    ObjectMapper objectMapper = new ObjectMapper();
                    con.getOutputStream().write(objectMapper.writeValueAsString(post.getLikerUsername()).getBytes());
                    if (con.getResponseCode() != 200) {
                        System.out.println(con.getResponseCode());
                    } else {
                        StringBuilder response = new StringBuilder();
                        try (BufferedReader br = new BufferedReader(
                                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                            String responseLine = null;
                            while ((responseLine = br.readLine()) != null) {
                                response.append(responseLine.trim());
                            }
                        }
                        List<User> users = objectMapper.readValue(response.toString(), new TypeReference<List<User>>() {
                        });
                        for (User user : users) {
                            SearchResultComponent searchResultComponent = new SearchResultComponent(user);
                            searchResultComponent.setStyle("-fx-border-width: 3;");
                            searchResultComponent.profileImageView.setLayoutX(12);
                            searchResultComponent.usernameLabel.setLayoutX(70);
                            searchResultComponent.nameLabel.setLayoutX(70);
                            vBox.getChildren().add(searchResultComponent);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        commentButton = new Button();
        commentButton.setLayoutX(120);
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
                mainAnchorpane.setVisible(false);
                AnchorPane root = new AnchorPane();
                root.setPrefSize(500.0, 600.0);
                root.setPadding(new Insets(5.0));

                Button backButton = new Button("<");
                AnchorPane.setLeftAnchor(backButton, 14.0);
                AnchorPane.setTopAnchor(backButton, 14.0);
                backButton.setPrefSize(30.0, 32.0);
                backButton.setStyle("-fx-background-color: #0598ff;");
                backButton.setTextFill(javafx.scene.paint.Color.WHITE);
                backButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        MediaPostComponent.super.getChildren().remove(1);
                        mainAnchorpane.setVisible(true);
                    }
                });

                Label commentsLabel = new Label("comments");
                AnchorPane.setLeftAnchor(commentsLabel, 201.0);
                AnchorPane.setTopAnchor(commentsLabel, 15.0);
                commentsLabel.setFont(new Font(20.0));

                VBox vBox = new VBox();
                ScrollPane scrollPane = new ScrollPane();
                AnchorPane.setLeftAnchor(scrollPane, 25.0);
                AnchorPane.setTopAnchor(scrollPane, 61.0);
                scrollPane.setPrefSize(450.0, 478.0);
                scrollPane.setStyle("-fx-border-color: black;");
                scrollPane.setContent(vBox);

                TextArea textArea = new TextArea();
                AnchorPane.setLeftAnchor(textArea, 29.0);
                AnchorPane.setTopAnchor(textArea, 545.0);
                textArea.setPrefSize(388.0, 24.0);
                textArea.setPromptText("write ...");
                textArea.setWrapText(true);

                Button sendButton = new Button("send");
                AnchorPane.setLeftAnchor(sendButton, 423.0);
                AnchorPane.setTopAnchor(sendButton, 548.0);
                sendButton.setPrefSize(52.0, 32.0);
                sendButton.setStyle("-fx-background-color: #0598ff;");
                sendButton.setTextFill(javafx.scene.paint.Color.WHITE);
                sendButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(textArea.getText().isEmpty()) {
                            textArea.setText("please write something");
                            textArea.selectAll();
                        }
                        else {
                            try {
                                URL url = new URL("http://localhost:8081/home/post/comment/" + post.getId());
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setRequestProperty("Authorization", "Bearer " + LinkedinApplication.token);
                                con.setRequestMethod("POST");
                                con.setDoOutput(true);
                                ObjectMapper objectMapper = new ObjectMapper();
                                con.getOutputStream().write(objectMapper.writeValueAsString(textArea.getText()).getBytes());
                                if (con.getResponseCode() != 200) {
                                    System.out.println(con.getResponseCode());
                                } else {
                                    post.getComments().add(textArea.getText());
                                    Label label = new Label(">>" + textArea.getText());
                                    label.setFont(new Font(20));
                                    vBox.getChildren().add(label);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                root.getChildren().addAll(backButton, commentsLabel, scrollPane, textArea, sendButton);

                for (String comment : post.getComments()) {
                    Label label = new Label(">>" + comment);
                    label.setFont(new Font(20));
                    vBox.getChildren().add(label);
                }
                MediaPostComponent.super.getChildren().add(root);
            }
        });

        captionTextArea = new TextArea();
        captionTextArea.setText(post.getText());
        captionTextArea.setFont(new Font(18));
        captionTextArea.setEditable(false);
        captionTextArea.setLayoutX(10);
        captionTextArea.setLayoutY(487);
        captionTextArea.setPrefSize(480, 105);
        captionTextArea.setPromptText("Caption");
        captionTextArea.setWrapText(true);
        mainAnchorpane.getChildren().addAll(likedByButton, profileImageView, usernameLabel, nameLabel, viewStackPane, likeButton, commentButton, captionTextArea);

        this.getChildren().add(mainAnchorpane);

    }


    private String getUsernameFromJwt(String token) {
        Algorithm algorithm = Algorithm.HMAC256("my-secret-key");
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        return decodedJWT.getClaim("sub").asString();
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

}