module com.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires fontawesomefx;


    opens com.client to javafx.fxml;
    exports com.client;
}