package com.example.ghada;
import com.example.ghada.Model.Panier;
import com.example.ghada.Service.PanierService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class PanierController {
    @FXML
    private Label prix_t_panier;
    private final PanierService panierService = new PanierService();
    @FXML private GridPane listViewPanier;
    private void populateGrid(ObservableList<Panier> items) {
        listViewPanier.getChildren().clear();
        listViewPanier.getChildren().clear();
        int row = 0, column = 0;
        for (Panier panier : items) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ghada/PanierItem.fxml"));
                AnchorPane pane = loader.load();
                PanierItemController controller = loader.getController();
                controller.setPanierService(panierService);
                controller.setPanierItem(panier);
                listViewPanier.add(pane, column, row);
                if (++column == 1) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                System.err.println("Error loading pane: " + e.getMessage());
            }
        }
    }
    public static class SelectionManager {
        public static List<Panier> selectedItems = new ArrayList<>();
    }
    public void initialize() {

        loadPanierData();
        updateTotalPrice();
    }
    public void updateTotalPrice() {
        if (prix_t_panier != null) {
            int total = panierService.calculerPrixTotalPanier();
            prix_t_panier.setText("Total: " + total + " DT");
        } else {
            System.out.println("prix_t_panier is not initialized");
        }
    }





    @FXML
    void update_panier(ActionEvent event) {
        try {
            Parent productView = FXMLLoader.load(getClass().getResource("/com/example/ghada/ProduitInterface.fxml"));
            Scene scene = new Scene(productView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateTotalPrice();
    }

    @FXML
    void AjoutCommande(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/ghada/LivraisonInterface.fxml"));
            Parent livraisonView = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(livraisonView));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert1("Erreur lors du chargement de l'interface de livraison.");
        }
        updateTotalPrice();
    }
    @FXML
    void supp_panier(ActionEvent event) {
        if (!SelectionManager.selectedItems.isEmpty()) {
            for (Panier selectedPanier : SelectionManager.selectedItems) {
                panierService.delete(selectedPanier.getId());
                showAlert("Vous avez supprimé l'article '" + selectedPanier.getTitre_product() + "' du panier.");
            }
            SelectionManager.selectedItems.clear();
            loadPanierData();  // Recharger les données après suppression
            updateTotalPrice();  // Mettre à jour le prix total après suppression
        } else {
            showAlert("Aucun produit sélectionné pour suppression.");
        }
    }



    private void loadPanierData() {
        ObservableList<Panier> items = panierService.getAllPanierItems();
        populateGrid(items);
        updateTotalPrice();

    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notification de Panier");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert1(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}