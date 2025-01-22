package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InLinkedApplication extends Application {
    public static Stage stage;
    public static String token;

    @Override
    public void start(Stage stage) {
        InLinkedApplication.stage = stage;
        stage.setTitle("InLinked");
        try (BufferedReader reader = new BufferedReader(new FileReader("client/src/main/resources/com/client/token.txt"))) {
            String line = reader.readLine();
            if (line == null) {
                stage.setScene(new Scene(
                        (new FXMLLoader(InLinkedApplication.class.getResource("login.fxml"))).load()));
            } else {
                token = line;
                stage.setScene(new Scene(
                        (new FXMLLoader(InLinkedApplication.class.getResource("home.fxml"))).load()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        stage.show();
    }

    public static void changeScene(SceneName sceneName) throws IOException {
        switch (sceneName) {
            case signUP -> stage.setScene(new Scene(
                    (new FXMLLoader(InLinkedApplication.class.getResource("signUp.fxml"))).load()));
            case login -> {
                stage.setScene(new Scene(
                        (new FXMLLoader(InLinkedApplication.class.getResource("login.fxml"))).load()));
            }
            case home -> stage.setScene(new Scene(
                    (new FXMLLoader(InLinkedApplication.class.getResource("home.fxml"))).load()));
        }
    }

    public static void main(String[] args) {
        launch();
    }

}