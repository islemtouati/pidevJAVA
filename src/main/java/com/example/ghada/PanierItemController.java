package com.example.ghada;

import com.example.ghada.Model.Panier;
import com.example.ghada.Service.PanierService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
public class PanierItemController {
    @FXML private Label description_produit;
    @FXML private ImageView img_panier;
    @FXML private Label prix_produit;
    @FXML private Label quantite_produit;
    @FXML private Label titre_produit;
    @FXML private Spinner<Integer> quantite_update;
    @FXML private Label prix_t_produit1;
    @FXML private CheckBox checkbox;
    private Panier panierItem;
    private PanierService panierService;
    private Timeline debounceTimer = new Timeline();
    public void setPanierService(PanierService service) {
        this.panierService = service;
    }
    private PanierController panierController; // Reference to the main controller

    public void setPanierController(PanierController controller) {
        this.panierController = controller;
    }
    @FXML
    private void selectionproduit(ActionEvent event) {
        CheckBox source = (CheckBox) event.getSource();
        Panier item = (Panier) source.getUserData();
        if (source.isSelected()) {
            PanierController.SelectionManager.selectedItems.add(item);
        } else {
            PanierController.SelectionManager.selectedItems.remove(item);
        }
    }
    private void updateTotal() {
        if (prix_t_produit1 != null) {
            int totalproduit = panierService.calculerPrixTotalParId(panierItem.getId());
            prix_t_produit1.setText("Total: " + totalproduit + " DT");
        } else {
            System.out.println("prix_t_panier is not initialized");
        }

    }
    public void initialize() {
        checkbox.setOnAction(this::selectionproduit);

    }
    private void updateTotalPrice() {

    }


    public void setPanierItem(Panier item) {
        this.panierItem = item;
        setupSpinner();
        updateUI();
        checkbox.setSelected(PanierController.SelectionManager.selectedItems.contains(item));
        checkbox.setUserData(item);
    }
    private void setupSpinner() {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, panierItem.getQuantite());
        quantite_update.setValueFactory(valueFactory);
        quantite_update.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.equals(oldValue)) {
                debounceTimer.stop();
                debounceTimer.getKeyFrames().clear();
                debounceTimer.getKeyFrames().add(new KeyFrame(Duration.millis(300), ae -> {
                    panierItem.setQuantite(newValue);
                    prix_produit.setText(String.valueOf(panierItem.getPrix_u() * newValue));
                    updateTotal();
                    updateTotalPrice();
                    updateUI();

                    panierService.update(panierItem, panierItem.getId());
                    if (panierController != null) {
                        panierController.updateTotalPrice();
                    }
                }));
                debounceTimer.playFromStart();
            }
        });
    }



    private void updateUI() {
        if (panierItem != null) {
            try {
                if (panierItem.getImg_product() != null && !panierItem.getImg_product().isEmpty()) {
                    Image image = new Image(panierItem.getImg_product(), true);
                    img_panier.setImage(image);
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid URL: " + panierItem.getImg_product() + ", error: " + e.getMessage());
                img_panier.setImage(new Image("/path/to/default/image.png"));
            }

            titre_produit.setText(panierItem.getTitre_product());
            description_produit.setText(panierItem.getDesc_product());
            prix_produit.setText(String.valueOf(panierItem.getPrix_u()));
            quantite_produit.setText(String.valueOf(panierItem.getQuantite()));
            prix_t_produit1.setText(String.valueOf(panierItem.getPrix_t()));
        }
        updateTotal();
        updateTotalPrice();

    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification de Quantité");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}