package com.example.javafxx.controllers;

import com.example.javafxx.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private static Label welcom_label;
    @FXML
    private Button button_logout;

    UserService us = new UserService();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                us.changeScene(event,"SignIn.fxml","log In !");
            }
        });
    }
    public void setUserInformation(String nom){
        welcom_label.setText("Bienvenue "+nom+"!");
    }
}