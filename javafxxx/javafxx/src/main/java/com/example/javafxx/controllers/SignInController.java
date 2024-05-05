package com.example.javafxx.controllers;

import com.example.javafxx.services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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

    @FXML
    private Button button_reset_password;

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
    @FXML
    private void handleResetPassword(ActionEvent event) {
        try {
            // Load the reset password FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxx/resetPassword.fxml"));
            Parent root = loader.load();

            // Create a new stage for the reset password scene
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Reset Password");
            stage.show();

            // Close the current sign-in stage
            ((Stage) button_reset_password.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
