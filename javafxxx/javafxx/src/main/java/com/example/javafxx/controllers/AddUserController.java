package com.example.javafxx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import com.example.javafxx.entities.Utilisateur;
import com.example.javafxx.services.UserService;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AddUserController implements Initializable {

    @FXML
    private ChoiceBox<String> choice_role;

    private List<String> roles = FXCollections.observableArrayList("admin", "user");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the ChoiceBox with the roles
        choice_role.setItems((ObservableList<String>) roles);
    }

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtAdresse;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtMdp;


    private UserService userService = new UserService();
    private GestionUserController mainController;

    public void setMainController(GestionUserController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        String email = txtEmail.getText();
        String adresse = txtAdresse.getText();
        String tel = txtTel.getText();
        String mdp = txtMdp.getText();
        String role = choice_role.getValue(); // Get selected role from ChoiceBox

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || adresse.isEmpty() || tel.isEmpty() || role == null || mdp.isEmpty()) {
            displayError("Veuillez remplir tous les champs!");
            // Handle empty fields or role not selected
            System.out.println("Please fill in all fields");
            return;
        }
        if (!isValidEmail(email)) {
            displayError("Format d'email incorrect!");
            System.out.println("Invalid email format");
            return;
        }
        if (!isValidPhoneNumber(tel)) {
            displayError("Le numéro de téléphone doit comporter 8 chiffres!");
            return;
        }
        if (!isValidPassword(mdp)) {
            displayError("mot de passe format : au moins une lettre majuscule, un chiffre, un caractère spécial, et être d'au moins 8 caractères.");
            return;
        }

        // Create a new user object with the input data
        Utilisateur newUser = new Utilisateur(nom, prenom, email, adresse, Integer.parseInt(tel), role, mdp);

        // Call the addUser method in your UserService to add the new user
        userService.addUser(event, txtNom.getText(), txtPrenom.getText(), txtEmail.getText(), txtAdresse.getText(), Integer.valueOf(txtTel.getText()), choice_role.getValue(), txtMdp.getText());

        // Close the AddUser view
       // closeAddUserView(event);

        userService.changeScene(event, "gestionUser.fxml", "Gestion User");

    }

    @FXML
    private void handleCancel(ActionEvent event) {
        userService.changeScene(event, "gestionUser.fxml", "Gestion User");
    }


    //controll saisie
    private void displayError(String message) {
        System.out.println(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
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
    private boolean isValidPassword(String password) {
        // Vérifiez si le mot de passe contient au moins une lettre majuscule, un chiffre, un caractère spécial, et est d'au moins 8 caractères
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }
}
