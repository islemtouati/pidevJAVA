package com.webandit.libra.controllers;


import com.webandit.libra.models.Product;
import com.webandit.libra.models.catego;
import com.webandit.libra.services.ServiceCatego;
import com.webandit.libra.services.ServiceProduct;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;


public class add_tasks {

    ServiceProduct pro = new ServiceProduct();
    ServiceCatego cat = new ServiceCatego();
    private Stage stage; // Declare Stage

    @FXML
    private Button Ajouter;

    @FXML
    private Button Back;

    @FXML
    private Button Parcourir;

    @FXML
    private ChoiceBox<catego> categorieChoiceBox;

    @FXML
    private TextField descriptionField;

    @FXML
    private ImageView image;

    @FXML
    private TextField imgField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField titreField;
    private String xamppFolderPath="c:/xampp/xampp/htdocs/img/";

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void parcourirImage(ActionEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Choisi une image");
        Stage stage = new Stage();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG","*.jpg"),
                new FileChooser.ExtensionFilter("JPEG","*.jpeg"),
                new FileChooser.ExtensionFilter("PNG","*.png")
        );
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            Path source = file.toPath();
            String fileName = file.getName();
            Path destination = Paths.get(xamppFolderPath + fileName);
            String imgURL=xamppFolderPath+fileName;
            try {
                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                imgField.setText(imgURL);
                Image image1= new Image("file:" +imgURL);
                image.setImage(image1);


            } catch (IOException ex) {
                System.out.println("Could not get the image");
                ex.printStackTrace();
            }
        } else {
            System.out.println("No file selected");
        }

    }
    public void ajouterProduct(ActionEvent event) {
        try {
            // Validate titre
            String titre = titreField.getText();
            if (titre.isEmpty() || titre.length() > 15) {
                showAlert("Erreur de saisie", "Le titre doit être non vide et avoir au maximum 15 caractères.");
                return;
            }

            // Validate description
            String description = descriptionField.getText();
            if (description.length() < 5 || description.length() > 50) {
                showAlert("Erreur de saisie", "La description doit avoir au moins 5 caractères et au maximum 50 caractères.");
                return;
            }

            // Validate prix
            String prixText = prixField.getText();
            if (prixText.isEmpty()) {
                showAlert("Erreur de saisie", "Le prix ne peut pas être vide.");
                return;
            }
            try {
                Double.parseDouble(prixText);
            } catch (NumberFormatException e) {
                showAlert("Erreur de saisie", "Le prix doit être un nombre décimal.");
                return;
            }

            // Validate category selection
            catego selectedChantier = categorieChoiceBox.getValue();
            if (selectedChantier == null) {
                showAlert("Erreur de saisie", "Veuillez sélectionner une catégorie.");
                return;
            }

            // If all validations pass, create and insert the product
            Product prod = new Product(selectedChantier, titre, Float.parseFloat(prixText), imgField.getText(), description);
            pro.insertOne(prod);
            stage.close();
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de l'ajout du produit: " + e.getMessage());
        }
    }

    public void Backto(ActionEvent event) {

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void initialize() {
        try {
            // Load Chantiers into the ChoiceBox
            categorieChoiceBox.setItems(FXCollections.observableArrayList(cat.selectListDerou()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to perform validation

}
