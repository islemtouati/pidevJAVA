package com.example.ghada;

import javafx.event.ActionEvent;
import com.example.ghada.Model.Panier;
import com.example.ghada.Model.Product;
import com.example.ghada.Service.PanierService;
import com.example.ghada.Service.ProductService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
public class ProductController {
    private final PanierService panierService = new PanierService();
    private final ProductService productService = new ProductService();
    @FXML private Spinner<Integer> id_quantite1, id_quantite2, id_quantite3, id_quantite4;
    @FXML private Label id_descproduit1, id_descproduit11, id_descproduit12, id_descproduit13;
    @FXML private ImageView id_imgproduit1, id_imgproduit11, id_imgproduit12, id_imgproduit13;
    @FXML private Label id_nomproduit1, id_nomproduit11, id_nomproduit12, id_nomproduit13;
    @FXML private Label id_prixproduit1, id_prixproduit11, id_prixproduit12, id_prixproduit13;
    public void initialize() {

        loadProducts();
    }
    private void loadProducts() {
        List<Product> products = productService.getAll();
        if (products.size() >= 4) {
            setupProductView(products.get(0), id_nomproduit1, id_descproduit1, id_prixproduit1, id_imgproduit1, id_quantite1);
            setupProductView(products.get(1), id_nomproduit11, id_descproduit11, id_prixproduit11, id_imgproduit11, id_quantite2);
            setupProductView(products.get(2), id_nomproduit12, id_descproduit12, id_prixproduit12, id_imgproduit12, id_quantite3);
            setupProductView(products.get(3), id_nomproduit13, id_descproduit13, id_prixproduit13, id_imgproduit13, id_quantite4);
        }
    }
    private void setupProductView(Product product, Label name, Label desc, Label price, ImageView img, Spinner<Integer> qtySpinner) {
        name.setText("Titre: " + product.getTitre());
        desc.setText("Description: " + product.getDescription());
        price.setText("Prix: " + product.getPrix());
        img.setImage(new Image(product.getImg(), true));
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        qtySpinner.setValueFactory(valueFactory);
        qtySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("Nouvelle quantité sélectionnée: " + newValue);
        });
    }
    @FXML
    void AjoutPanier(ActionEvent event) {

        try {
            try {
                Button clickedButton = (Button) event.getSource();
                String id = clickedButton.getId();
                int productId = Integer.parseInt(id.replaceAll("[^0-9]", ""));
                Product productToAdd = productService.getById(productId);

                if (productToAdd != null) {
                    String spinnerId = "#id_quantite" + productId;
                    Spinner<Integer> quantitySpinner = (Spinner<Integer>) clickedButton.getScene().lookup(spinnerId);

                    if (quantitySpinner != null) {
                        int quantity = quantitySpinner.getValue();
                        Panier panier = new Panier(123, 123, (int) productToAdd.getPrix(),(int) productToAdd.getPrix(), quantity, productToAdd.getTitre(), productToAdd.getDescription(), productToAdd.getImg());
                        panierService.add(panier);
                        showAlert("Produit ajouté au panier !");
                    } else {
                        showAlert("Erreur: Spinner non trouvé pour l'ID " + spinnerId);
                    }
                } else {
                    showAlert("Produit non trouvé.");
                }
            } catch (NumberFormatException e) {
                showAlert("Erreur lors de l'extraction de l'ID du produit: " + e.getMessage());
            } catch (Exception e) {
                showAlert("Une erreur inattendue est survenue: " + e.getMessage());
            }
            Parent panierView = FXMLLoader.load(getClass().getResource("/com/example/ghada/PanierInterface.fxml"));
            Scene scene = new Scene(panierView);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
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
