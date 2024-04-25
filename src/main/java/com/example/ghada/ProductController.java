package com.example.ghada;
import javafx.event.ActionEvent;
import com.example.ghada.Model.Livraison;
import com.example.ghada.Model.Panier;
import com.example.ghada.Model.Produit;
import com.example.ghada.Service.PanierService;
import com.example.ghada.Service.ProduitService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitController  {
    private final PanierService panierService = new PanierService();
    private final ProduitService produitService = new ProduitService();



    @FXML
    private Button Actualiser;
    @FXML
    private Button AjoutP;

    @FXML
    private Button AjoutP2;

    @FXML
    private Button AjoutP3;

    @FXML
    private Label id_descproduit1=new Label();

    @FXML
    private Label id_descproduit2;

    @FXML
    private ImageView id_imgproduit1;

    @FXML
    private ImageView id_imgproduit2;

    @FXML
    private Label id_nomproduit1=new Label();

    @FXML
    private Label id_nomproduit2;

    @FXML
    private Label id_prixproduit1=new Label();

    @FXML
    private Label id_prixproduit2;
    private void populateFields(Produit produit) {

        this.id_nomproduit1.setText(produit.getTitre());
        this.id_descproduit1.setText(produit.getDescription());
        this.id_prixproduit1.setText(Double.toString(produit.getPrix()));
    }
    @FXML
    void Actualiser(ActionEvent event) {
        configureLabels(); // Recharge et met à jour les labels avec de nouvelles données
    }

    private void configureLabels() {
        List<Produit> produits = loadProduit(); // Charge tous les produits
        if (!produits.isEmpty()) {
            Produit produit = produits.get(0); // Prend le premier produit pour l'exemple
            id_nomproduit1.setText("name: " + produit.getTitre());
            id_descproduit1.setText("Name: " + produit.getDescription());
            id_prixproduit1.setText("First Name: " + produit.getPrix());
        }

    }
    private void setImageToImageView(ImageView id_imgproduit1, String imagePath) {
        Image image = new Image(new File(imagePath).toURI().toString());
        id_imgproduit1.setImage(image);
    }




    private List<Produit> loadProduit() {

        // Your code to load personnel from database
        System.out.println("Chargement des personnels depuis la base de données...");
        List<Produit> produits = produitService.getAll(); // Adapt this line as per your actual service call

        return produits;
    }
    @FXML
    void AjoutPanier(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        Produit produitToAdd = null;

        // Sélectionnez le produit en fonction du bouton cliqué
        if (clickedButton == AjoutP) {
            produitToAdd = produitService.getById(1);
        } else if (clickedButton == AjoutP2) {
            produitToAdd = produitService.getById(2);
        } else if (clickedButton == AjoutP3) {
            produitToAdd = produitService.getById(4);
        }

        // Vérifiez si le produit à ajouter est valide
        if (produitToAdd != null) {
            try {
                // Créez un nouvel objet Panier en utilisant le constructeur approprié
                Panier panier = new Panier(produitToAdd.getId(), (int) produitToAdd.getPrix(), (int) produitToAdd.getPrix(), 1);
                panierService.add(panier);
                showAlert("Produit ajouté au panier !");
            } catch (Exception e) {
                showAlert("Erreur lors de la création du panier : " + e.getMessage());
            }
        } else {
            showAlert("Erreur lors de l'ajout du produit au panier : produit non trouvé.");
        }
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}









