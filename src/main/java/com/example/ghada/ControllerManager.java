package com.example.ghada;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ControllerManager {
    private static PanierController panierController;

    public static void setPanierController(PanierController controller) {

        panierController = controller;
    }


    public static PanierController getPanierController() {

        return panierController;
    }
}
