package com.example.ghada;
import com.example.ghada.Model.Livraison;
import com.example.ghada.Service.LivraisonService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
public class LivraisonController {
    private final LivraisonService livraisonService = new LivraisonService();
    @FXML private TextField id_adresse, id_Tel, id_codepostal, id_nom, id_prenom, id_ville;
    @FXML private ChoiceBox<String> id_pays;
    @FXML
    public void initialize() {

        id_pays.setItems(FXCollections.observableArrayList(
                "Ariana", "Béja", "Ben Arous", "Bizerte", "Gabès", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kébili", "Kef", "Mahdia", "Manouba", "Médenine",
                "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana", "Sousse", "Tataouine",
                "Tozeur", "Tunis", "Zaghouan"
        ));
    }
    @FXML
    void handleAjouterLivraison(ActionEvent event) {
        StringBuilder errorMessage = new StringBuilder();
        boolean inputValid = true;
        if (id_adresse.getText().isEmpty()) {
            errorMessage.append("L'adresse ne peut pas être vide.\n");
            inputValid = false;
        }
        if (id_nom.getText().isEmpty()) {
            errorMessage.append("Le nom ne peut pas être vide.\n");
            inputValid = false;
        }
        if (id_prenom.getText().isEmpty() || !id_prenom.getText().matches("[a-zA-Zéèàêëïç\\s]+")) {
            errorMessage.append("Le prénom ne peut pas être vide et ne doit contenir que des lettres et des espaces.\n");
            inputValid = false;
        }
        if (id_ville.getText().isEmpty() || !id_ville.getText().matches("[a-zA-Zéèàêëïç\\s]+")) {
            errorMessage.append("La ville ne peut pas être vide et ne doit contenir que des lettres et des espaces.\n");
            inputValid = false;
        }
        if (!id_Tel.getText().matches("\\d{8}")) {
            errorMessage.append("Le numéro de téléphone doit contenir exactement 8 chiffres.\n");
            inputValid = false;
        }
        if (!id_codepostal.getText().matches("\\d{4}")) {
            errorMessage.append("Le code postal doit être numérique et contenir  4 chiffres.\n");
            inputValid = false;
        }
        if (!inputValid) {
            showAlert(errorMessage.toString());
            return;
        }
        if (id_pays.getValue() == null) {
            errorMessage.append("Vous devez sélectionner un pays.\n");
            inputValid = false;
        } if (!inputValid) {
            showAlert(errorMessage.toString());
            return;
        }
        Livraison newLivraison = new Livraison(
                id_adresse.getText(),
                Integer.parseInt(id_Tel.getText()),
                Integer.parseInt(id_codepostal.getText()),
                id_pays.getValue(),
                id_ville.getText(),
                id_nom.getText(),
                id_prenom.getText()
        );
        livraisonService.add(newLivraison);
        showAlert("Livraison ajoutée avec succès!");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ghada/CommandeInterface.fxml"));
        Parent commandeView = null;
        try {
            commandeView = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert("Erreur lors de l'ajout de la livraison ou lors du chargement de l'interface de commande.");
            throw new RuntimeException(ex);
        }
        Scene scene = new Scene(commandeView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Validation de Livraison");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void handleSupprimerLivraison(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ghada/ProduitInterface.fxml"));
            Parent productView = loader.load();
            Scene scene = new Scene(productView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur lors du chargement de l'interface produit.");
        }
    }
}







