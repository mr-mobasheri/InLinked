package com.client.components;

import com.client.InLinkedApplication;
import com.client.SceneName;
import com.client.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class MyProfileComponent extends AnchorPane {
    User me;
    ScrollPane scrollPane;
    VBox mainVBox;
    ImageView profileImageView;
    Label usernameLabel;
    Label nameLabel;
    Button logoutButton;
    Button followingButton;
    Button followerButton;
    Button connectsButton;

    public MyProfileComponent() throws IOException {
        me = getMyProfile();
        if (me == null) {
            throw new IOException();
        }

        this.setPrefSize(900, 720);

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setLayoutY(81);
        scrollPane.setPrefSize(900, 642);

        mainVBox = new VBox();
        scrollPane.setContent(mainVBox);

        mainVBox.getChildren().addAll(
                new MyInfoComponent(),
                new ContactInfoComponent(),
                new EducationComponent(),
                new SkillsComponent(),
                new JobComponent(),
                new OrgComponent()
        );

        if (me.getProfilePath() == null)
            profileImageView = new ImageView(new Image(Path.of("client/src/main/resources/com/client/pictures/user.png").toUri().toString()));
        else {
            profileImageView = new ImageView(new Image(Path.of(me.getProfilePath()).toUri().toString()));
        }
        profileImageView.setFitHeight(60);
        profileImageView.setFitWidth(55);
        profileImageView.setLayoutX(16);
        profileImageView.setLayoutY(18);
        profileImageView.setPickOnBounds(true);
        profileImageView.setPreserveRatio(true);
        profileImageView.setClip(new Circle(16, 16, 45));
        profileImageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Select an Image");
                File file = fileChooser.showOpenDialog(InLinkedApplication.stage);
                if (file != null) {
                    try {
                        URL url = new URL("http://localhost:8081/home/profile/image");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                        con.setRequestMethod("POST");
                        con.setDoOutput(true);
                        con.getOutputStream().write(file.getPath().getBytes());
                        if (con.getResponseCode() == 200) {
                            profileImageView.setImage(new Image(file.toURI().toString()));
                        } else {
                            System.out.println(con.getResponseCode());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        usernameLabel = new Label(me.getFirstName());
        usernameLabel.setLayoutX(82);
        usernameLabel.setLayoutY(13);
        usernameLabel.setFont(new Font(22));

        nameLabel = new Label(me.getLastName());
        nameLabel.setLayoutX(82);
        nameLabel.setLayoutY(39);
        nameLabel.setFont(new Font(22));

        logoutButton = createButton("log out", 788, 23, 91, 32);
        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                InLinkedApplication.token = null;
                try {
                    try (FileWriter writer = new FileWriter("client/src/main/resources/com/client/token.txt", false);) {
                    }
                    ;
                    InLinkedApplication.changeScene(SceneName.login);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        followingButton = createButtonWithGraphic("following", 645, 23, 118, 34,
                "src/main/resources/com/client/pictures/following.png");
        followingButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    URL url = new URL("http://localhost:8081/home/followings");
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
                        mainVBox.getChildren().clear();
                        Label label = new Label("   Followings");
                        label.setFont(new Font(20));
                        mainVBox.getChildren().add(label);
                        for (User user : users) {
                            SearchResultComponent searchResultComponent = new SearchResultComponent(user);
                            mainVBox.getChildren().add(searchResultComponent);
                        }
                    } else {
                        System.out.println(con.getResponseCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        followerButton = createButtonWithGraphic("follower", 519, 23, 118, 34,
                "src/main/resources/com/client/pictures/follow.png");
        followerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    URL url = new URL("http://localhost:8081/home/followers");
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
                        mainVBox.getChildren().clear();
                        Label label = new Label("   Followers");
                        label.setFont(new Font(20));
                        mainVBox.getChildren().add(label);
                        for (User user : users) {
                            SearchResultComponent searchResultComponent = new SearchResultComponent(user);
                            mainVBox.getChildren().add(searchResultComponent);
                        }
                    } else {
                        System.out.println(con.getResponseCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        connectsButton = createButtonWithGraphic("connects", 404, 23, 107, 34,
                "src/main/resources/com/client/pictures/connect.png");
        connectsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    URL url = new URL("http://localhost:8081/home/connect");
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
                        mainVBox.getChildren().clear();
                        Label label = new Label("   connects");
                        label.setFont(new Font(20));
                        mainVBox.getChildren().add(label);
                        for (User user : users) {
                            SearchResultComponent searchResultComponent = new SearchResultComponent(user);
                            mainVBox.getChildren().add(searchResultComponent);
                        }
                    } else {
                        System.out.println(con.getResponseCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        this.getChildren().addAll(scrollPane, profileImageView, usernameLabel, nameLabel, logoutButton, followingButton, followerButton, connectsButton);

    }

    private VBox createSection(String title, String buttonId, String buttonText) {
        VBox sectionVBox = new VBox();
        sectionVBox.setPrefSize(850, 200);
        sectionVBox.setStyle("-fx-border-color: black; -fx-border-insets: 3; -fx-border-width: 2;");

        AnchorPane headerPane = new AnchorPane();
        headerPane.setPrefSize(890, 44);

        Label headerLabel = new Label(title);
        headerLabel.setAlignment(Pos.CENTER);
        headerLabel.setPrefSize(758, 44);
        headerLabel.setStyle("-fx-background-color: #0598ff;");
        headerLabel.setTextFill(Color.WHITE);
        headerLabel.setFont(Font.font("MRT_Sima", 20));
        headerLabel.setPadding(new Insets(10, 0, 0, 10));

        Button editButton = new Button(buttonText);
        editButton.setLayoutX(763);
        editButton.setPrefSize(126, 42);
        editButton.setStyle("-fx-background-color: #0598ff;");
        editButton.setTextFill(Color.WHITE);
        editButton.setFont(new Font(20));
        editButton.setId(buttonId);

        headerPane.getChildren().addAll(headerLabel, editButton);
        sectionVBox.getChildren().add(headerPane);

        return sectionVBox;
    }

    private Button createButton(String text, double layoutX, double layoutY, double width, double height) {
        Button button = new Button(text);
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
        button.setPrefSize(width, height);
        button.setStyle("-fx-background-color: #0598ff;");
        button.setTextFill(Color.WHITE);
        button.setFont(new Font(15));
        return button;
    }

    private Button createButtonWithGraphic(String text, double layoutX, double layoutY, double width, double height, String imagePath) {
        Button button = createButton(text, layoutX, layoutY, width, height);
        ImageView graphic = new ImageView(new Image(Path.of(imagePath).toUri().toString()));
        graphic.setFitHeight(24);
        graphic.setFitWidth(23);
        graphic.setPreserveRatio(true);
        button.setGraphic(graphic);
        return button;
    }

    private User getMyProfile() {
        try {
            URL url = new URL("http://localhost:8081/home/profile");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
            con.setRequestMethod("GET");
            switch (con.getResponseCode()) {
                case 200:
                    StringBuilder response = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                        String responseLine = null;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                    }
                    ObjectMapper objectMapper = new ObjectMapper();
                    return objectMapper.readValue(response.toString(), User.class);
                default:
                    System.out.println(con.getResponseCode());
                    return null;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

