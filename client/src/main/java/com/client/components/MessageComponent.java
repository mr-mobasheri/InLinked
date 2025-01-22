package com.client.components;

import com.client.InLinkedApplication;
import java.awt.Desktop;
import com.client.models.Message;
import com.client.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.converter.DefaultStringConverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public class MessageComponent extends AnchorPane {
    TextField searchTextField;
    VBox contactsVBos;
    TextField messageInputField;
    Button fileButton;
    Button sendButton;
    VBox messageContent;
    ImageView userImageView;
    Label nameLabel;
    HBox topBar;
    HBox messageInputArea;

    public MessageComponent() throws IOException {
        this.setPrefSize(900, 639);
        this.setStyle("-fx-background-color: #00ffff;");

        AnchorPane iconsPane = new AnchorPane();
        iconsPane.setPrefSize(80, 639);
        iconsPane.setStyle("-fx-background-color: #0028ff;");
        FontAwesomeIcon userIcon = new FontAwesomeIcon();
        userIcon.setFill(javafx.scene.paint.Color.WHITE);
        userIcon.setGlyphName("USER");
        userIcon.setSize("35");
        userIcon.setLayoutX(31.0);
        userIcon.setLayoutY(407.0);

        FontAwesomeIcon usersIcon = new FontAwesomeIcon();
        usersIcon.setFill(javafx.scene.paint.Color.WHITE);
        usersIcon.setGlyphName("USERS");
        usersIcon.setSize("35");
        usersIcon.setLayoutX(25.0);
        usersIcon.setLayoutY(281.0);

        iconsPane.getChildren().addAll(userIcon, usersIcon);

        AnchorPane searchPane = new AnchorPane();
        searchPane.setLayoutX(82.0);
        searchPane.setPrefSize(250, 639);
        searchPane.setStyle("-fx-background-color: #017bfe;");

        HBox searchBox = new HBox();
        searchBox.setLayoutX(8.0);
        searchBox.setLayoutY(14.0);
        searchBox.setPrefSize(221.0, 35.0);
        searchBox.setStyle("-fx-background-color: #001ffd; -fx-background-radius: 100px;");

        FontAwesomeIcon searchIcon = new FontAwesomeIcon();
        searchIcon.setGlyphName("SEARCH");
        searchIcon.setSize("20");
        HBox.setMargin(searchIcon, new Insets(6.0, 0, 0, 10.0));

        searchTextField = new TextField();
        searchTextField.setPrefSize(224.0, 25.0);
        searchTextField.setPromptText("Search");
        searchTextField.setStyle("-fx-background-color: transparent;");
        HBox.setMargin(searchTextField, new Insets(5.0, 0, 0, 5.0));

        searchBox.getChildren().addAll(searchIcon, searchTextField);
        searchPane.getChildren().add(searchBox);

        contactsVBos = new VBox();
        contactsVBos.setLayoutY(63);
        contactsVBos.setLayoutX(82);
        contactsVBos.setPrefSize(238, 639);

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
                Label label = new Label("   connects");
                label.setFont(new Font(20));
                contactsVBos.getChildren().add(label);
                for (User user : users) {
                    AnchorPane root = new AnchorPane();
                    root.setStyle("-fx-border-color: black; -fx-border-width: 2;");
                    root.setPrefSize(240.0, 60.0);

                    ImageView profileImageView;
                    if (user.getProfilePath() == null)
                        profileImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/user.png").toUri().toString()));
                    else {
                        profileImageView = new ImageView(new Image(Path.of(user.getProfilePath()).toUri().toString()));
                    }
                    profileImageView.setFitHeight(51.0);
                    profileImageView.setFitWidth(46.0);
                    profileImageView.setLayoutX(14.0);
                    profileImageView.setLayoutY(7.0);
                    profileImageView.setPickOnBounds(true);
                    profileImageView.setPreserveRatio(true);
                    profileImageView.setClip(new Circle(16, 16, 45));

                    Label usernameLabel = new Label(user.getUsername());
                    usernameLabel.setLayoutX(72.0);
                    usernameLabel.setLayoutY(1.0);
                    usernameLabel.setFont(new Font(22.0));

                    Label nameLabel1 = new Label(user.getFirstName());
                    nameLabel1.setLayoutX(72.0);
                    nameLabel1.setLayoutY(27.0);
                    nameLabel1.setFont(new Font(22.0));

                    root.getChildren().addAll(profileImageView, usernameLabel, nameLabel1);

                    root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            messageContent.getChildren().clear();
                            if (user.getProfilePath() == null)
                                userImageView.setImage(new Image(Path.of("src/main/resources/com/client/pictures/user.png").toUri().toString()));
                            else {
                                userImageView.setImage(new Image(Path.of(user.getProfilePath()).toUri().toString()));
                            }
                            nameLabel.setText(user.getFirstName());
                            topBar.setVisible(true);
                            messageInputArea.setVisible(true);

                            try {
                                URL url = new URL("http://localhost:8081/home/message/" + user.getUsername());
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
                                    List<Message> messages = objectMapper.readValue(response.toString(), new TypeReference<List<Message>>() {
                                    });
                                    if (messages.isEmpty()) {
                                        Label label1 = new Label("No message yet");
                                        label1.setFont(new Font(20));
                                        messageContent.getChildren().add(label1);
                                    } else {
                                        for (int i = 0; i < messages.size(); i++) {
                                            Message message = messages.get(i);
                                            if (message.getFilePath() == null) {
                                                if (message.getSender().equals(user.getUsername())) {
                                                    messageContent.getChildren().add(new OtherChatComponent(message.getText()));
                                                } else if (message.getReceiver().equals(user.getUsername())) {
                                                    messageContent.getChildren().add(new MyChatComponent(message.getText()));
                                                } else {
                                                    System.out.println("no sender or receiver");
                                                }
                                            } else {
                                                File file = new File(message.getFilePath());
                                                if (message.getSender().equals(user.getUsername())) {
                                                    AnchorPane root = new AnchorPane();
                                                    root.setMaxHeight(Double.NEGATIVE_INFINITY);
                                                    root.setMaxWidth(Double.NEGATIVE_INFINITY);
                                                    root.setMinHeight(Double.NEGATIVE_INFINITY);
                                                    root.setMinWidth(Double.NEGATIVE_INFINITY);
                                                    root.setPrefHeight(60.0);
                                                    root.setPrefWidth(555.0);
                                                    root.setStyle("-fx-background-color: #00ffff;");

                                                    Button button = new Button(file.getName());
                                                    button.setMnemonicParsing(false);
                                                    button.setPrefHeight(60.0);
                                                    button.setPrefWidth(200.0);
                                                    button.setStyle("-fx-background-color: black; -fx-background-radius: 20;");
                                                    button.setTextFill(javafx.scene.paint.Color.WHITE);
                                                    button.setFont(new Font(20.0));
                                                    button.setOnAction(new EventHandler<ActionEvent>() {
                                                        @Override
                                                        public void handle(ActionEvent actionEvent) {
                                                            if (Desktop.isDesktopSupported()) {
                                                                try {
                                                                    Desktop.getDesktop().open(file);
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            } else {
                                                                System.out.println("not supported");
                                                            }
                                                        }
                                                    });

                                                    root.getChildren().add(button);
                                                    messageContent.getChildren().add(root);
                                                } else if (message.getReceiver().equals(user.getUsername())) {
                                                    AnchorPane root = new AnchorPane();
                                                    root.setMaxHeight(Double.NEGATIVE_INFINITY);
                                                    root.setMaxWidth(Double.NEGATIVE_INFINITY);
                                                    root.setMinHeight(Double.NEGATIVE_INFINITY);
                                                    root.setMinWidth(Double.NEGATIVE_INFINITY);
                                                    root.setPrefHeight(60.0);
                                                    root.setPrefWidth(555.0);
                                                    root.setStyle("-fx-background-color: #00ffff;");

                                                    Button button = new Button(file.getName());
                                                    button.setLayoutX(355.0);
                                                    button.setMnemonicParsing(false);
                                                    button.setPrefHeight(60.0);
                                                    button.setPrefWidth(200.0);
                                                    button.setStyle("-fx-background-color: black; -fx-background-radius: 20;");
                                                    button.setTextFill(javafx.scene.paint.Color.WHITE);
                                                    button.setFont(new Font(20.0));
                                                    button.setOnAction(new EventHandler<ActionEvent>() {
                                                        @Override
                                                        public void handle(ActionEvent actionEvent) {
                                                            if (Desktop.isDesktopSupported()) {
                                                                try {
                                                                    Desktop.getDesktop().open(file);
                                                                } catch (IOException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            } else {
                                                                System.out.println("not supported");
                                                            }
                                                        }
                                                    });

                                                    root.getChildren().add(button);
                                                    messageContent.getChildren().add(root);
                                                } else {
                                                    System.out.println("no sender or receiver");
                                                }

                                            }
                                        }
                                    }
                                } else {
                                    System.out.println(con.getResponseCode());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            sendButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    if (messageInputField.getText().isEmpty()) {
                                        messageInputField.setText("write something...");
                                        messageInputField.selectAll();
                                        return;
                                    }
                                    try {
                                        URL url = new URL("http://localhost:8081/home/message/" + user.getUsername());
                                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                        con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                                        con.setRequestMethod("POST");
                                        con.setDoOutput(true);
                                        con.getOutputStream().write(messageInputField.getText().getBytes());
                                        if (con.getResponseCode() == 200) {
                                            if (messageContent.getChildren().size() == 1
                                                    && messageContent.getChildren().get(0) instanceof Label) {
                                                messageContent.getChildren().clear();
                                            }
                                            messageContent.getChildren().add(new MyChatComponent(messageInputField.getText()));
                                            messageInputField.setText("");
                                        } else {
                                            System.out.println("server error");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            fileButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    FileChooser fileChooser = new FileChooser();
                                    fileChooser.setTitle("Select a file");
                                    File file = fileChooser.showOpenDialog(InLinkedApplication.stage);
                                    if (file != null) {
                                        try {
                                            URL url = new URL("http://localhost:8081/home/message/file/" + user.getUsername());
                                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                            con.setRequestProperty("Authorization", "Bearer " + InLinkedApplication.token);
                                            con.setRequestMethod("POST");
                                            con.setDoOutput(true);
                                            con.getOutputStream().write(file.getPath().getBytes());
                                            if (con.getResponseCode() == 200) {
                                                if (messageContent.getChildren().size() == 1
                                                        && messageContent.getChildren().get(0) instanceof Label) {
                                                    messageContent.getChildren().clear();
                                                }
                                                AnchorPane root = new AnchorPane();
                                                root.setMaxHeight(Double.NEGATIVE_INFINITY);
                                                root.setMaxWidth(Double.NEGATIVE_INFINITY);
                                                root.setMinHeight(Double.NEGATIVE_INFINITY);
                                                root.setMinWidth(Double.NEGATIVE_INFINITY);
                                                root.setPrefHeight(60.0);
                                                root.setPrefWidth(555.0);
                                                root.setStyle("-fx-background-color: #00ffff;");

                                                Button button = new Button(file.getName());
                                                button.setLayoutX(355.0);
                                                button.setMnemonicParsing(false);
                                                button.setPrefHeight(60.0);
                                                button.setPrefWidth(200.0);
                                                button.setStyle("-fx-background-color: black; -fx-background-radius: 20;");
                                                button.setTextFill(javafx.scene.paint.Color.WHITE);
                                                button.setFont(new Font(20.0));
                                                button.setOnAction(new EventHandler<ActionEvent>() {
                                                    @Override
                                                    public void handle(ActionEvent actionEvent) {
                                                        if (Desktop.isDesktopSupported()) {
                                                            try {
                                                                Desktop.getDesktop().open(file);
                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            }
                                                        } else {
                                                            System.out.println("not supported");
                                                        }
                                                    }
                                                });

                                                root.getChildren().add(button);
                                                messageContent.getChildren().add(root);
                                            } else {
                                                System.out.println("server error");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    });

                    contactsVBos.getChildren().add(root);
                }
            } else {
                System.out.println(con.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        topBar = new HBox();
        topBar.setLayoutX(334.0);
        topBar.setPrefSize(563.0, 58.0);
        topBar.setStyle("-fx-background-color: #fdff01;");
        topBar.setVisible(false);

        userImageView = new ImageView();
        userImageView.setFitHeight(47.0);
        userImageView.setFitWidth(50);
        userImageView.setClip(new Circle(16, 16, 45));
        HBox.setMargin(userImageView, new Insets(5.0, 0, 0, 10.0));

        nameLabel = new Label();
        nameLabel.setPrefSize(209.0, 35.0);
        nameLabel.setFont(new Font(24.0));
        HBox.setMargin(nameLabel, new Insets(10.0));

        topBar.getChildren().addAll(userImageView, nameLabel);

        messageInputArea = new HBox();
        messageInputArea.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        messageInputArea.setLayoutX(334.0);
        messageInputArea.setLayoutY(593);
        messageInputArea.setPrefSize(563.0, 46.0);
        messageInputArea.setStyle("-fx-background-color: white;");
        messageInputArea.setVisible(false);

        messageInputField = new TextField();
        TextFormatter<String> formatter = new TextFormatter<>(new DefaultStringConverter(), null, change -> {
            if (change.getControlNewText().length() <= 1900) {
                return change;
            } else {
                return null;
            }
        });
        messageInputField.setTextFormatter(formatter);
        messageInputField.setPrefSize(482.0, 38.0);
        messageInputField.setPromptText("Type something to send ...");
        messageInputField.setStyle("-fx-background-color: transparent; -fx-border-color: blue;");

        fileButton = new Button();
        fileButton.setPrefSize(38.0, 26.0);
        fileButton.setStyle("-fx-background-color: white;");
        ImageView fileImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/file.png").toUri().toString()));
        fileImageView.setFitHeight(23.0);
        fileImageView.setFitWidth(21.0);
        fileButton.setGraphic(fileImageView);

        sendButton = new Button();
        sendButton.setPrefSize(38.0, 26.0);
        sendButton.setStyle("-fx-background-color: white;");
        ImageView sendImageView = new ImageView(new Image(Path.of("src/main/resources/com/client/pictures/send.png").toUri().toString()));
        sendImageView.setFitHeight(28.0);
        sendImageView.setFitWidth(30.0);
        sendButton.setGraphic(sendImageView);

        messageInputArea.getChildren().addAll(messageInputField, fileButton, sendButton);

        ScrollPane messageScrollPane = new ScrollPane();
        messageScrollPane.setLayoutX(334.0);
        messageScrollPane.setLayoutY(58.0);
        messageScrollPane.setPrefSize(563.0, 535);
        messageScrollPane.setFitToWidth(true);
        messageContent = new VBox();
        messageContent.setSpacing(10);
        messageContent.heightProperty().addListener(observable -> {
            messageScrollPane.setVvalue(1D);
        });
        messageContent.setPrefSize(558.0, 535);
        messageContent.setStyle("-fx-background-color: #00ffff;");
        messageScrollPane.setContent(messageContent);

        this.getChildren().addAll(iconsPane, searchPane, messageScrollPane, contactsVBos, topBar, messageInputArea);
    }

}