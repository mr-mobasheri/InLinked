package com.client.components;

import com.client.InLinkedApplication;
import com.client.models.Comment;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MediaPostComponent extends StackPane {
    Post post;
    boolean isLiked;
    User viewer;
    List<Comment> comments = new ArrayList<>();
    AnchorPane mainAnchorpane;
    ImageView profileImageView;
    Label usernameLabel;
    Label nameLabel;
    StackPane viewStackPane;
    ImageView imageView;
    MediaPlayerComponent mediaPlayerComponent;
    Button commentButton;
    Button likeButton;
    Button likedByButton;
    ImageView commentImageView;
    ImageView likeImageView;
    TextArea captionTextArea;

    public MediaPostComponent(Post post) throws IOException {
        this.post = post;
        URL url = new URL("http://localhost:8081/home/user/" + getUsernameFromJwt(InLinkedApplication.token));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
        con.setRequestMethod("GET");
        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        if (con.getResponseCode() == 200) {
            viewer = objectMapper.readValue(response.toString(), User.class);
        } else {
            System.out.println(con.getResponseCode());
            throw new IOException();
        }

        mainAnchorpane = new AnchorPane();
        mainAnchorpane.setPrefSize(500, 600);
        mainAnchorpane.setPadding(new Insets(5));
        if (post.getSender().getProfilePath() == null)
            profileImageView = new ImageView(new Image(Path.of("client/src/main/resources/com/client/pictures/user.png").toUri().toString()));
        else {
            profileImageView = new ImageView(new Image(Path.of(post.getSender().getProfilePath()).toUri().toString()));
        }
        profileImageView.setFitHeight(37);
        profileImageView.setFitWidth(43);
        profileImageView.setLayoutX(14);
        profileImageView.setLayoutY(11);
        profileImageView.setPreserveRatio(true);
        profileImageView.setClip(new Circle(16, 16, 45));

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

        try {
            Path filePath = Paths.get(new URI(post.getMediaPath()));
            String mimeType = Files.probeContentType(filePath);
            if (mimeType != null) {
                if (mimeType.startsWith("image/")) {
                    imageView.setImage(new Image(post.getMediaPath()));
                    viewStackPane.getChildren().add(imageView);
                } else if (mimeType.startsWith("video/")) {
                    mediaPlayerComponent = new MediaPlayerComponent(new MediaPlayer(new Media(post.getMediaPath())));
                    viewStackPane.getChildren().add(mediaPlayerComponent);
                } else {
                    System.out.println("The file is neither an image nor a video.");
                    imageView.setImage(new Image(Path.of("client/src/main/resources/com/client/pictures/cantOpenFile.png").toUri().toString()));
                }
            } else {
                System.out.println("MIME type could not be determined.");
                imageView.setImage(new Image(Path.of("client/src/main/resources/com/client/pictures/cantOpenFile.png").toUri().toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            imageView.setImage(new Image(Path.of("client/src/main/resources/com/client/pictures/cantOpenFile.png").toUri().toString()));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            imageView.setImage(new Image(Path.of("client/src/main/resources/com/client/pictures/cantOpenFile.png").toUri().toString()));
        }

        likeButton = new Button();
        likeButton.setLayoutX(14);
        likeButton.setLayoutY(453);
        likeButton.setPrefSize(27, 26);
        likeButton.setStyle("-fx-background-color: w;");
        for (String username : post.getLikerUsername()) {
            if (username.equals(viewer.getUsername())) {
                likeImageView = new ImageView(new Image(Path.of("client/src/main/resources/com/client/pictures/like.png").toUri().toString()));
                isLiked = true;
            }
        }
        if (!isLiked) {
            likeImageView = new ImageView(new Image(Path.of("client/src/main/resources/com/client/pictures/unlike.png").toUri().toString()));
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
                        con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                        con.setRequestMethod("GET");
                        if (con.getResponseCode() != 200) {
                            System.out.println(con.getResponseCode());
                        } else {
                            isLiked = false;
                            post.getLikerUsername().remove(viewer.getUsername());
                            likeImageView.setImage(new Image(Path.of("client/src/main/resources/com/client/pictures/unlike.png").toUri().toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        URL url = new URL("http://localhost:8081/home/post/like/" + post.getId());
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                        con.setRequestMethod("GET");
                        if (con.getResponseCode() != 200) {
                            System.out.println(con.getResponseCode());
                        } else {
                            likeImageView.setImage(new Image(Path.of("client/src/main/resources/com/client/pictures/like.png").toUri().toString()));
                            isLiked = true;
                            post.getLikerUsername().add(viewer.getUsername());
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
                    URL url = new URL("http://localhost:8081/home/user");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
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
        commentImageView = new ImageView(new Image(Path.of("client/src/main/resources/com/client/pictures/comment.png").toUri().toString()));
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
                        if (textArea.getText().isEmpty()) {
                            textArea.setText("please write something");
                            textArea.selectAll();
                        } else {
                            try {
                                URL url = new URL("http://localhost:8081/home/post/comment/" + post.getId());
                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                                con.setRequestMethod("POST");
                                con.setDoOutput(true);
                                ObjectMapper objectMapper = new ObjectMapper();
                                con.getOutputStream().write(objectMapper.writeValueAsString(textArea.getText()).getBytes());
                                if (con.getResponseCode() != 200) {
                                    System.out.println(con.getResponseCode());
                                } else {
                                   comments.add(new Comment(textArea.getText(), viewer.getUsername(), viewer.getProfilePath()));
                                    AnchorPane commentRoot = new AnchorPane();
                                    commentRoot.setPrefSize(500.0, 100.0);
                                    commentRoot.setStyle("-fx-border-color: black; -fx-border-width: 3;");

                                    ImageView profileImageView;
                                    if (viewer.getProfilePath() == null)
                                        profileImageView = new ImageView(new Image(Path.of("client/src/main/resources/com/client/pictures/user.png").toUri().toString()));
                                    else {
                                        profileImageView = new ImageView(new Image(Path.of(viewer.getProfilePath()).toUri().toString()));
                                    }
                                    profileImageView.setFitHeight(60.0);
                                    profileImageView.setFitWidth(55.0);
                                    profileImageView.setLayoutX(14.0);
                                    profileImageView.setLayoutY(8.0);
                                    profileImageView.setPickOnBounds(true);
                                    profileImageView.setPreserveRatio(true);

                                    Label usernameLabel = new Label(viewer.getUsername());
                                    usernameLabel.setLayoutX(74.0);
                                    usernameLabel.setLayoutY(6.0);
                                    usernameLabel.setFont(new Font(15.0));

                                    Label commentLabel = new Label(textArea.getText());
                                    commentLabel.setAlignment(javafx.geometry.Pos.TOP_LEFT);
                                    commentLabel.setLayoutX(74.0);
                                    commentLabel.setLayoutY(28.0);
                                    commentLabel.setPrefSize(420.0, 62.0);
                                    commentLabel.setTextAlignment(TextAlignment.LEFT);
                                    commentLabel.setWrapText(true);
                                    commentLabel.setFont(new Font(18.0));

                                    commentRoot.getChildren().addAll(profileImageView, usernameLabel, commentLabel);
                                    vBox.getChildren().add(commentRoot);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                root.getChildren().addAll(backButton, commentsLabel, scrollPane, textArea, sendButton);
                try {
                    URL url = new URL("http://localhost:8081/home/post/comment/" + post.getId());
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                    con.setRequestMethod("GET");
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
                        List<Comment> comments1 = objectMapper.readValue(response.toString(), new TypeReference<List<Comment>>() {
                        });
                        comments.clear();
                        comments.addAll(comments1);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (Comment comment : comments) {
                    AnchorPane commentRoot = new AnchorPane();
                    commentRoot.setPrefSize(500.0, 100.0);
                    commentRoot.setStyle("-fx-border-color: black; -fx-border-width: 3;");
                    ImageView profileImageView;
                    if (comment.getSenderProfilePath() == null)
                        profileImageView = new ImageView(new Image(Path.of("client/src/main/resources/com/client/pictures/user.png").toUri().toString()));
                    else {
                        profileImageView = new ImageView(new Image(Path.of(comment.getSenderProfilePath()).toUri().toString()));
                    }
                    profileImageView.setFitHeight(60.0);
                    profileImageView.setFitWidth(55.0);
                    profileImageView.setLayoutX(14.0);
                    profileImageView.setLayoutY(8.0);
                    profileImageView.setPickOnBounds(true);
                    profileImageView.setPreserveRatio(true);

                    Label usernameLabel = new Label(comment.getSenderUsername());
                    usernameLabel.setLayoutX(74.0);
                    usernameLabel.setLayoutY(6.0);
                    usernameLabel.setFont(new Font(15.0));

                    Label commentLabel = new Label(comment.getText());
                    commentLabel.setAlignment(javafx.geometry.Pos.TOP_LEFT);
                    commentLabel.setLayoutX(74.0);
                    commentLabel.setLayoutY(28.0);
                    commentLabel.setPrefSize(420.0, 62.0);
                    commentLabel.setTextAlignment(TextAlignment.LEFT);
                    commentLabel.setWrapText(true);
                    commentLabel.setFont(new Font(18.0));

                    commentRoot.getChildren().addAll(profileImageView, usernameLabel, commentLabel);
                    vBox.getChildren().add(commentRoot);
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