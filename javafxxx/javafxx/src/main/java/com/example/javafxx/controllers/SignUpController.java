package com.example.javafxx.controllers;

import com.example.javafxx.services.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class SignUpController implements Initializable {
    @FXML
    private Button button_signup;
    @FXML
    private Button button_to_login;
    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_prenom;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_adress;
    @FXML
    private TextField tf_tel;
    @FXML
    private ChoiceBox<String> tf_rolee;
    @FXML
    private TextField tf_pwd;

    UserService us = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add options to the ChoiceBox
        tf_rolee.setItems(FXCollections.observableArrayList("admin", "user"));

        // Set a default selection if needed
        tf_rolee.setValue("user");

        button_signup.setOnAction(this::handleSignUp);

        button_to_login.setOnAction(event -> us.changeScene(event, "SignIn.fxml", "logIn!"));
    }

    private void handleSignUp(ActionEvent event) {
        String errorMsg = validateInput();
        if (errorMsg == null) {
            us.signUp(event, tf_nom.getText(), tf_prenom.getText(), tf_email.getText(), tf_adress.getText(), Integer.valueOf(tf_tel.getText()), tf_rolee.getValue(), tf_pwd.getText());
        } else {
            displayError(errorMsg);
        }
    }

    //control saisie
    private String validateInput() {
        if (tf_nom.getText().trim().isEmpty() || tf_prenom.getText().trim().isEmpty() || tf_adress.getText().trim().isEmpty() || tf_pwd.getText().trim().isEmpty() || tf_rolee.getValue() == null) {
            return "Veuillez remplir tous les champs!";
        }

        if (!isValidEmail(tf_email.getText().trim())) {
            return "Format d'email incorrect!";
        }

        if (!isValidPhoneNumber(tf_tel.getText().trim())) {
            return "Le numéro de téléphone doit comporter 8 chiffres!";
        }
        if (!isValidPassword(tf_pwd.getText().trim())) {
            return "Mot de passe format : au moins une lettre majuscule, un chiffre, un caractère spécial, et être d'au moins 8 caractères.";
        }

        return null; // Validation successful
    }

    private boolean isValidEmail(String email) {
        // Email validation for presence of "@" and "."
        String emailPattern = "^.+@.+\\..+$";
        return Pattern.compile(emailPattern).matcher(email).matches();
    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        // Phone number validation (exactly 8 digits)
        return phoneNumber.matches("^\\d{8}$");
    }

    private void displayError(String message) {
        System.out.println(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    private boolean isValidPassword(String password) {
        // Vérifiez si le mot de passe contient au moins une lettre majuscule, un chiffre, un caractère spécial, et est d'au moins 8 caractères
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }
}
