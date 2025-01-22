package com.client;

import com.client.components.*;
import com.client.models.Post;
import com.client.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class HomeController {

    @FXML
    private VBox homeVbox;

    public void initialize() {
        homeVbox.setAlignment(Pos.CENTER);
        addRefreshButton().setOnAction(this::homeButtonPressed);
        setPosts();
    }

    void clean() {
        homeVbox.getChildren().clear();
    }

    Button addRefreshButton() {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(900, 40);

        Button button = new Button();
        button.setLayoutY(-1);
        button.setPrefSize(900, 40);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            }
        });

        ImageView imageView = new ImageView(new Image(Path.of("client/src/main/resources/com/client/pictures/refresh.png").toUri().toString()));
        imageView.setFitHeight(34);
        imageView.setFitWidth(38);
        imageView.setLayoutX(431);
        imageView.setLayoutY(4);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        root.getChildren().addAll(button, imageView);
        homeVbox.getChildren().add(root);
        return button;
    }

    @FXML
    void homeButtonPressed(ActionEvent event) {
        clean();
        addRefreshButton().setOnAction(this::homeButtonPressed);
        setPosts();
    }

    @FXML
    void jobsButtonPressed(ActionEvent event) {

    }

    @FXML
    void meButtonPressed(ActionEvent event) {
        clean();
        addRefreshButton().setOnAction(this::meButtonPressed);
        try {
            homeVbox.getChildren().add(new MyProfileComponent());
        } catch (IOException e) {
            e.printStackTrace();
            homeVbox.getChildren().add(new Label("server error"));
        }

    }

    @FXML
    void messageButtonPressed(ActionEvent event) {
        clean();
        try {
            homeVbox.getChildren().add(new MessageComponent());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void myNetworkButtonPressed(ActionEvent event) {
        clean();
        SearchBarComponent searchBarComponent = new SearchBarComponent();
        searchBarComponent.searchTextfield.setPromptText("search post");
        searchBarComponent.searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                while (homeVbox.getChildren().size() > 2) {
                    homeVbox.getChildren().remove(2);
                }
                if (searchBarComponent.searchTextfield.getText().isEmpty()) {
                    searchBarComponent.searchTextfield.setText("please write your search term");
                }
                try {
                    URL url = new URL("http://localhost:8081/home/search/post/" + searchBarComponent.searchTextfield.getText());
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
                            List<Post> posts = objectMapper.readValue(response.toString(), new TypeReference<List<Post>>() {
                            });
                            for (Post post : posts) {
                                if (post.getMediaPath() == null || post.getMediaPath().isEmpty()) {
                                    TextPostComponent textPostComponent = new TextPostComponent(post);
                                    homeVbox.getChildren().add(textPostComponent);
                                } else {
                                    MediaPostComponent mediaPostComponent = new MediaPostComponent(post);
                                    homeVbox.getChildren().add(mediaPostComponent);
                                }
                            }
                            break;
                        case 401:
                            homeVbox.getChildren().add(new Label("your token is expired! please log out and log in again."));
                            InLinkedApplication.changeScene(SceneName.login);
                            break;
                        default:
                            System.out.println(con.getResponseCode());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    clean();
                    homeVbox.getChildren().add(new Label("connection failed!"));
                }
            }
        });
        homeVbox.getChildren().add(searchBarComponent);
    }

    @FXML
    void newPostButtonPressed(ActionEvent event) {
        clean();
        homeVbox.getChildren().add(new NewPostComponent());
    }

    @FXML
    void notifButtonPressed(ActionEvent event) {
        clean();
        homeVbox.getChildren().add(new NotificationComponent());
    }

    @FXML
    void searchButtonPressed(ActionEvent event) {
        clean();
        addRefreshButton().setOnAction(this::searchButtonPressed);
        SearchBarComponent searchBarComponent = new SearchBarComponent();
        searchBarComponent.searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                while (homeVbox.getChildren().size() > 2) {
                    homeVbox.getChildren().remove(2);
                }
                if (searchBarComponent.searchTextfield.getText().isEmpty()) {
                    searchBarComponent.searchTextfield.setText("please write your search term");
                }
                try {
                    URL url = new URL("http://localhost:8081/home/search/" + searchBarComponent.searchTextfield.getText());
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
                            List<User> users = objectMapper.readValue(response.toString(), new TypeReference<List<User>>() {
                            });
                            for (User user : users) {
                                SearchResultComponent searchResultComponent = new SearchResultComponent(user);
                                searchResultComponent.userButton.setOnAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent actionEvent) {
                                        clean();
                                        homeVbox.getChildren().add(new UserProfileComponent(user));
                                    }
                                });
                                homeVbox.getChildren().add(searchResultComponent);
                            }
                            break;
                        case 401:
                            homeVbox.getChildren().add(new Label("your token is expired! please log out and log in again."));
                            break;
                        default:
                            System.out.println(con.getResponseCode());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    homeVbox.getChildren().add(new Label("connection failed!"));
                }
            }
        });
        homeVbox.getChildren().add(searchBarComponent);

    }

    private void setPosts() {
        try {
            URL url = new URL("http://localhost:8081/home");
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
                    List<Post> posts = objectMapper.readValue(response.toString(), new TypeReference<List<Post>>() {
                    });
                    for (Post post : posts) {
                        if (post.getMediaPath() == null || post.getMediaPath().isEmpty()) {
                            TextPostComponent textPostComponent = new TextPostComponent(post);
                            homeVbox.getChildren().add(textPostComponent);
                        } else {
                            MediaPostComponent mediaPostComponent = new MediaPostComponent(post);
                            homeVbox.getChildren().add(mediaPostComponent);
                        }
                    }
                    break;
                case 401:
                    homeVbox.getChildren().add(new Label("your token is expired! please log out and log in again."));
                    InLinkedApplication.changeScene(SceneName.login);
                    break;
                default:
                    System.out.println(con.getResponseCode());

            }
        } catch (Exception e) {
            e.printStackTrace();
            clean();
            homeVbox.getChildren().add(new Label("connection failed!"));
        }
    }

    public VBox getHomeVbox() {
        return homeVbox;
    }
}


