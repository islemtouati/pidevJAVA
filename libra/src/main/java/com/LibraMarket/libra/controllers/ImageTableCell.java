package com.LibraMarket.libra.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableCell;
import com.LibraMarket.libra.models.Product;

import java.net.URL;

public class ImageTableCell extends TableCell<Product, String> {
    private final ImageView imageView = new ImageView();

    @Override
    protected void updateItem(String imagePath, boolean empty) {
        super.updateItem(imagePath, empty);

        if (empty || imagePath == null) {
            setGraphic(null);
        } else {
            try {
                Image image = new Image("file:///C:/xampp/htdocs/img/" + imagePath);
                imageView.setImage(image);
                imageView.setFitWidth(50);
                imageView.setPreserveRatio(true);
                setGraphic(imageView);
            } catch (Exception e) {
                // Gérer l'exception, par exemple afficher un message d'erreur
                System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

}
