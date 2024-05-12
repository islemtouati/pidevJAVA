package com.LibraMarket.libra.controllers;


import com.LibraMarket.libra.models.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class item {
    private Product oeuv;


    @FXML
    private Label categoryItem;

    @FXML
    private Label idItem;

    @FXML
    private ImageView imageItem;

    @FXML
    private Label prixItem;

    @FXML
    private Label titreItem;


    public void setData(Product oev) {
        this.oeuv = oev;
        idItem.setText(Integer.toString(oev.getId()));
        prixItem.setText(Double.toString(oev.getPrix()));
        titreItem.setText(oev.getTitre());
        categoryItem.setText(oev.getCategory().getTitre());



        String imageUrl = oev.getImg();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                Image image = new Image(imageUrl);
                imageItem.setImage(image);
            } catch (Exception e) {
                // Handle the exception (e.g., log it, show an error message)
                System.err.println("Error loading image: " + e.getMessage());
                e.printStackTrace();
                // You might want to set a default image here
                // Image image = new Image("default_image.png");
                // imageItem.setImage(image);
            }
        } else {
            // Si l'URL de l'image est null ou vide, vous pouvez définir une image par défaut ici
            // Image image = new Image("default_image.png");
            // imageItem.setImage(image);
        }
        System.out.println("Titre: " + oev.getTitre());
    }

}