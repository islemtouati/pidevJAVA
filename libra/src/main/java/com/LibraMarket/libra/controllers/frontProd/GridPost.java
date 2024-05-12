package com.LibraMarket.libra.controllers.frontProd;



        import com.LibraMarket.libra.models.Product;
        import com.LibraMarket.libra.services.ServiceProduct;
        import javafx.collections.FXCollections;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;

        import javafx.geometry.Insets;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.scene.layout.AnchorPane;
        import javafx.scene.layout.GridPane;
        import javafx.stage.Stage;

        import java.io.IOException;
        import java.sql.SQLException;
        import java.time.ZoneId;
        import java.time.format.DateTimeFormatter;
        import java.util.List;
        import java.util.stream.Collectors;

public class GridPost{
    @FXML
    private GridPane grid;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchField;

    private final ServiceProduct ps = new ServiceProduct();

    @FXML
    void initialize() {
        try {
            actualise();
            // Ajouter un écouteur d'événements au champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    if (newValue.isEmpty()) {
                        // Si le champ de recherche est vide, afficher tous les articles
                        actualise();
                    } else {
                        // Sinon, actualiser la liste des articles en fonction du terme de recherche
                        actualiseWithSearch(newValue);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void actualise() throws SQLException {
        // Effacer les éléments actuels de la grille
        grid.getChildren().clear();

        List<Product> productList = ps.selectAll();
        if (productList.isEmpty()) {
            System.out.println("post est vide.");
            return;
        }

        int column = 0;
        int row = 3;

        grid.setHgap(50);

        for (Product product : productList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/front_product/prodcart.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardProd p = fxmlLoader.getController();
                if (p == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                p.getData(product);
                p.setId(product.getId());
                p.setControllerAct(this);


                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }












    private void actualiseWithSearch(String searchTerm) throws SQLException {
        grid.getChildren().clear();

        List<Product> productList = ps.rechercherProduit(searchTerm);
        System.out.println("searchraniala trabhek" + searchTerm+"posts."+productList);
        if (productList.isEmpty()) {
            System.out.println("Aucun post trouvé pour le terme de recherche : " + searchTerm);
            return;
        }

        int column = 0;
        int row = 3;
        grid.setHgap(50);

        for (Product product : productList) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/front_product/prodcart.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                CardProd c = fxmlLoader.getController();
                if (c == null) {
                    System.out.println("Le contrôleur de l'élément n'a pas été initialisé.");
                    continue;
                }

                c.getData(product);


                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
                if (column == 3) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }





}