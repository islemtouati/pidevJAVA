module com.LibraMarket.libra {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires itextpdf;
    requires org.apache.pdfbox;
    requires kaptcha;
    requires java.desktop;


    exports com.LibraMarket.libra.controllers;
    exports com.LibraMarket.libra.controllers.frontProd;

    opens com.LibraMarket.libra.controllers to javafx.fxml;
    opens com.LibraMarket.libra.controllers.frontProd to javafx.fxml;

    opens com.LibraMarket.libra.models;
    exports com.LibraMarket.libra.test;
    opens com.LibraMarket.libra.test to javafx.fxml;


}
