module com.example.javafxx {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires itextpdf;
    requires java.mail;

    opens com.example.javafxx to javafx.fxml;
    exports com.example.javafxx.controllers;
    opens com.example.javafxx.controllers to javafx.fxml;
    exports com.example.javafxx.test;
    opens com.example.javafxx.entities;

}