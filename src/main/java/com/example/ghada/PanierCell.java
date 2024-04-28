package com.example.ghada;

import com.example.ghada.Model.Panier;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class PanierCell extends ListCell<Panier> {

    @Override
    protected void updateItem(Panier item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("com/example/ghada/PanierItem.fxml"));
                AnchorPane items = loader.load();
                ImageView img_panier = (ImageView) loader.getNamespace().get("imgProduct");
                Label titre_produit = (Label) loader.getNamespace().get("titleProduct");
                Label description_produit = (Label) loader.getNamespace().get("pdescriptionProduct");
                Label quantite_produit = (Label) loader.getNamespace().get("quantityProduct");
                Label prix_produit = (Label) loader.getNamespace().get("prixProduct");
                Label prix_t_produit1 = (Label) loader.getNamespace().get("prixtProduct");
                img_panier.setImage(new Image(item.getImg_product()));
                titre_produit.setText(item.getTitre_product());
                description_produit.setText(item.getDesc_product());
                prix_produit.setText(String.valueOf(item.getPrix_u()));
                prix_t_produit1.setText(String.valueOf(item.getPrix_t()));
                quantite_produit.setText(String.valueOf(item.getQuantite()));
                setGraphic(items);
            } catch (Exception e) {
                e.printStackTrace();
                setText("Erreur lors du chargement de l'article");
            }
        }
    }}

