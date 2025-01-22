package com.client.components;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class OtherChatComponent extends AnchorPane {
    public OtherChatComponent(String messageText) {
        this.setMaxHeight(Double.NEGATIVE_INFINITY);
        this.setMaxWidth(Double.NEGATIVE_INFINITY);
        this.setMinHeight(Double.NEGATIVE_INFINITY);
        this.setMinWidth(Double.NEGATIVE_INFINITY);
        this.setPrefHeight(100.0);
        this.setPrefWidth(555.0);
        this.setStyle("-fx-background-color: #00ffff;");

        AnchorPane messagePane = new AnchorPane();
        messagePane.setMaxHeight(Double.NEGATIVE_INFINITY);
        messagePane.setMaxWidth(Double.NEGATIVE_INFINITY);
        messagePane.setMinHeight(Double.NEGATIVE_INFINITY);
        messagePane.setMinWidth(Double.NEGATIVE_INFINITY);
        messagePane.setPrefHeight(100.0);
        messagePane.setPrefWidth(450.0);
        messagePane.setStyle("-fx-background-color: #800080; -fx-background-radius: 30;");

        Label messageLabel = new Label(messageText);
        messageLabel.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        messageLabel.setLayoutX(19.0);
        messageLabel.setLayoutY(7.0);
        messageLabel.setPrefHeight(85.0);
        messageLabel.setPrefWidth(411.0);
        messageLabel.setWrapText(true);
        messageLabel.setFont(new Font(15.0));
        messageLabel.setTextFill(Color.WHITE);

        messagePane.getChildren().add(messageLabel);
        this.getChildren().add(messagePane);
    }
}