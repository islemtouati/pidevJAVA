module com.LibraMarket.libra {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    //requires itextpdf;
    requires org.apache.pdfbox;


    exports com.LibraMarket.libra.controllers;

    opens com.LibraMarket.libra.controllers to javafx.fxml;

    opens com.LibraMarket.libra.models;
    exports com.LibraMarket.libra.test;
    opens com.LibraMarket.libra.test to javafx.fxml;


}