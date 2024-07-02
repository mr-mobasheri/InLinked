module com.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires fontawesomefx;
    requires javafx.media;

    opens com.client.models to com.fasterxml.jackson.databind;
    opens com.client to javafx.fxml;
    exports com.client;
    exports com.client.models;
    exports com.client.components;
}