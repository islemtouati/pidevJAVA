module com.webandit.libra {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    exports com.webandit.libra.controllers;

    opens com.webandit.libra.controllers to javafx.fxml;

    opens com.webandit.libra.models;
    exports com.webandit.libra.test;
    opens com.webandit.libra.test to javafx.fxml;


}