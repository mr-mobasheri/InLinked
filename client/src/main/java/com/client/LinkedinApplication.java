package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LinkedinApplication extends Application {
    public static Stage stage;
    public static String token;

    @Override
    public void start(Stage stage) throws IOException {
        LinkedinApplication.stage = stage;
        stage.setTitle("Linkedin");
        stage.setScene(new Scene(
                (new FXMLLoader(LinkedinApplication.class.getResource("login.fxml"))).load()));
        stage.setResizable(false);
        stage.show();
    }

    public static void changeScene(SceneName sceneName) throws IOException {
        switch (sceneName) {
            case signUP -> stage.setScene(new Scene(
                    (new FXMLLoader(LinkedinApplication.class.getResource("signUp.fxml"))).load()));
            case login -> stage.setScene(new Scene(
                    (new FXMLLoader(LinkedinApplication.class.getResource("login.fxml"))).load()));
            case home -> stage.setScene(new Scene(
                    (new FXMLLoader(LinkedinApplication.class.getResource("home.fxml"))).load()));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}