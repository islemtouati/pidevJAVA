package com.example.ghada;

import com.example.ghada.Model.Livraison;
import com.example.ghada.Model.Panier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class CommandeController {
    @FXML
    private Label id_addresse;

    @FXML
    private Label id_nom;

    @FXML
    private Label id_pays;

    @FXML
    private Label id_prenom;

    @FXML
    private Label id_prixt;

    @FXML
    private Label id_tel;

    @FXML
    private GridPane panierGridPane;

    public void initialize() {
        loadPanierDetails();
        loadLivraisonDetails();
    }

    private void loadPanierDetails() {
        List<Panier> panierItems = DataStore.getInstance().getPanierItems();
        int row = 0;
        for (Panier item : panierItems) {
            panierGridPane.add(new Label(item.getProduit().getNom()), 0, row);
            panierGridPane.add(new Label(String.valueOf(item.getQuantite())), 1, row);
            panierGridPane.add(new Label(String.format("%.2f", item.getPrixTotal())), 2, row);
            row++;  // Incrémente la ligne pour le prochain article
        }
    }

    private void loadLivraisonDetails() {
        Livraison livraison = DataStore.getInstance().getLivraisonDetails();
        nomLabel.setText(livraison.getNom());
        adresseLabel.setText(livraison.getAdresse());
        // Ajoutez plus de champs si nécessaire
    }
    @FXML
    void command(ActionEvent event) {
        try {

            Parent commandView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ghada/CommandeInterface.fxml")));
            Scene scene = new Scene(commandView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void panier(ActionEvent event) {
        try {

            Parent panierView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ghada/PanierInterface.fxml")));
            Scene scene = new Scene(panierView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void product(ActionEvent event) {
        try {

            Parent productView = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/ghada/ProduitInterface.fxml")));
            Scene scene = new Scene(productView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
