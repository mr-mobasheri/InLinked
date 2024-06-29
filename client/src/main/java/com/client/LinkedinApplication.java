package com.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LinkedinApplication extends Application {
    private static Stage stage;
    private static Scene login;
    private static Scene signUp;
    public static String token;


    @Override
    public void start(Stage stage) throws IOException {
        LinkedinApplication.stage = stage;
        setScene();
        stage.setTitle("Linkedin");
        stage.setScene(login);
        stage.show();
    }

    private void setScene() throws IOException {
        login = new Scene(
                (new FXMLLoader(LinkedinApplication.class.getResource("login.fxml"))).load());
        signUp = new Scene(
                (new FXMLLoader(LinkedinApplication.class.getResource("signUp.fxml"))).load());
    }

    public static void changeScene(SceneName sceneName) {
        switch (sceneName) {
            case signUP -> stage.setScene(signUp);
            case login -> stage.setScene(login);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}