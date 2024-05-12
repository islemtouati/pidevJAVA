package com.LibraMarket.libra.controllers.frontProd;

import com.LibraMarket.libra.models.Product;
import com.LibraMarket.libra.services.ServiceProduct;
import javafx.event.ActionEvent;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Label;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.sql.SQLException;
        import java.util.List;

public class CardProd {

    ServiceProduct ps = new ServiceProduct();

    @FXML
    private ImageView Post_img;

    @FXML
    private Label category;

    @FXML
    private Label description;

    @FXML
    private Label prix;

    @FXML
    private Label titre;


    private Product product;
    private GridPost frontcontroller;

    public void getData(Product product) {
        this.product = product;
        titre.setText(product.getTitre());
       category.setText(String.valueOf(product.getCategory_id()));
        description.setText(product.getDescription());
        prix.setText(String.valueOf(product.getPrix()));
        System.out.println(product.getImg());
        Image imageprofile = new Image(product.getImg());
        Post_img.setImage(imageprofile);



    }

    int id ;
    public void setId(int id) {
        this.id=id;

    }

    public void onDeleteButtonClickFront(ActionEvent actionEvent) {
        if (id != 0) {
            try {
                System.out.println(id+"ahawaaaaa");
                ps.deleteOne(id);
                frontcontroller.actualise(); // Accessing ServiceChantier methods via 'ps' instance
                // Refresh the table after deleting a chantier
            } catch (SQLException e) {

                throw new RuntimeException(e);
            }
        }
    }



    public void setControllerAct(GridPost frontpost) {
        this.frontcontroller =frontpost;
    }

    public void panier(ActionEvent actionEvent) {
        // ici todo panier
    }
}