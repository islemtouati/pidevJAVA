package com.example.javafxx.controllers;

import com.example.javafxx.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML
    private Button button_signin;
    @FXML
    private Button button_to_signup;
    @FXML
    private TextField tf_email;
    @FXML
    private  TextField tf_pwd;

    UserService us = new UserService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        button_signin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                us.signIn(event, tf_email.getText(), tf_pwd.getText());
            }
        });

        button_to_signup.setOnAction(event -> {
            UserService.changeScene(event,"SignUp.fxml","Sign up!");
        });

    }
}
