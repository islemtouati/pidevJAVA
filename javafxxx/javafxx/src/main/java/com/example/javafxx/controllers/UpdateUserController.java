package com.example.javafxx.controllers;

import com.example.javafxx.entities.Utilisateur;
import com.example.javafxx.services.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class UpdateUserController {
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
    private ChoiceBox<String> choice_role;

    private Utilisateur userToUpdate;
    UserService userService = new UserService();
    private int userId;
    private GestionUserController mainController;



    public void initData(Utilisateur user, GestionUserController mainController) {
        this.userToUpdate = user;
        this.mainController = mainController; // Store the main controller reference
        txtNom.setText(user.getNom());
        txtPrenom.setText(user.getPrenom());
        txtEmail.setText(user.getEmail());
        txtAdresse.setText(user.getAdresse());
        txtTel.setText(String.valueOf(user.getTel()));
        // Populate the ChoiceBox with options
        choice_role.setItems(FXCollections.observableArrayList("admin", "user"));
        // Set the initial value of the ChoiceBox based on the user's role
        choice_role.setValue(user.getRole());
    }

    @FXML
    private void handleUpdate(ActionEvent event) {

            // Update user data based on the fields in the UI
        userToUpdate.setNom(txtNom.getText());
        userToUpdate.setPrenom(txtPrenom.getText());
        userToUpdate.setEmail(txtEmail.getText());
        userToUpdate.setAdresse(txtAdresse.getText());
        userToUpdate.setTel(Integer.parseInt(txtTel.getText()));
        userToUpdate.setRole(choice_role.getValue()); // Set the role from the ChoiceBox

        //controll saisie :
        String nom = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String email = txtEmail.getText().trim();
        String adresse = txtAdresse.getText().trim();
        String telText = txtTel.getText().trim();
        String role = choice_role.getValue();

        // Check if any of the fields are empty after trimming whitespace
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || adresse.isEmpty() || telText.isEmpty() || role == null) {
            displayError("Veuillez remplir tous les champs!");
            return;
        }
        // Check if the email is valid
        if (!isValidEmail(email)) {
            displayError("Format d'email incorrect!");
            return;
        }
        // Check if the phone number is valid
        if (!isValidPhoneNumber(telText)) {
            displayError("Le numéro de téléphone doit comporter 8 chiffres!");
            return;
        }

        int tel = Integer.parseInt(telText);
        // Update user data based on the validated input
        userToUpdate.setNom(nom);
        userToUpdate.setPrenom(prenom);
        userToUpdate.setEmail(email);
        userToUpdate.setAdresse(adresse);
        userToUpdate.setTel(tel);
        userToUpdate.setRole(role);

        // Call the updateUser method in your UserService or perform the update operation here
        userService.updateUser(userToUpdate);

        closeUpdateUserView(event);

        mainController.refreshUserTable();


    }

    @FXML
    private void handleExit(ActionEvent event) {

        closeUpdateUserView(event);
    }

    private void closeUpdateUserView(ActionEvent event) {
        // Get the stage (window) of the event source
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Close the stage
        stage.close();    }

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
}

