package com.client.components;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class SearchBarComponent extends AnchorPane {
    public TextField searchTextfield;
    public Button searchButton;

    public SearchBarComponent() {
        this.setMaxHeight(Double.NEGATIVE_INFINITY);
        this.setMaxWidth(Double.NEGATIVE_INFINITY);
        this.setMinHeight(Double.NEGATIVE_INFINITY);
        this.setMinWidth(Double.NEGATIVE_INFINITY);
        this.setPrefHeight(96.0);
        this.setPrefWidth(700.0);

        searchTextfield = new TextField();
        searchTextfield.setLayoutX(26.0);
        searchTextfield.setLayoutY(27.0);
        searchTextfield.setPrefHeight(40.0);
        searchTextfield.setPrefWidth(551.0);
        searchTextfield.setPromptText("Search username, email or name");
        searchTextfield.setFont(new Font(20.0));

        searchButton = new Button("Search");
        searchButton.setLayoutX(581.0);
        searchButton.setLayoutY(27.0);
        searchButton.setPrefHeight(40.0); // Corrected the prefHeight from 0.0 to 40.0 to match the TextField height
        searchButton.setPrefWidth(92.0);
        searchButton.setStyle("-fx-background-color: #0598ff;");
        searchButton.setTextFill(javafx.scene.paint.Color.WHITE);
        searchButton.setFont(new Font(20.0));

        this.getChildren().addAll(searchTextfield, searchButton);
    }
}