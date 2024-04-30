package com.esprit.controllers.Reclamation;

import com.esprit.models.Reclamation;
import com.esprit.services.ReclamationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AfficherReclamationController {

    @FXML
    private TilePane reclamationsTilePane;
    @FXML
    private VBox detailsPane;  // Assuming this is the VBox containing all detail components
    @FXML
    private TextField tfId, tfUserId, tfEtat;
    @FXML
    private DatePicker dpDate;
    @FXML
    private TextArea taDescription;

    private ReclamationService reclamationService = new ReclamationService();
    @FXML
    private StackPane contentPane;

    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    @FXML
    public void initialize() {
        detailsPane.setVisible(false);  // Initially hide the details pane
        loadReclamations();
    }

    private void loadReclamations() {
        reclamationsTilePane.getChildren().clear();  // Clear existing cards
        for (Reclamation reclamation : reclamationService.getAll()) {
            VBox card = new VBox(10);
            card.getChildren().add(new Label("ID: " + reclamation.getId()));
            card.getChildren().add(new Label("User ID: " + reclamation.getIdUser()));
            card.getChildren().add(new Label("Date: " + reclamation.getDateR().toString()));
            card.getChildren().add(new Label("Description: " + reclamation.getDescription()));
            card.setOnMouseClicked(event -> {
                populateDetails(reclamation);
                detailsPane.setVisible(true);  // Show details only when a card is clicked
            });
            card.getStyleClass().add("reclamation-card");
            reclamationsTilePane.getChildren().add(card);
        }
    }

    private void populateDetails(Reclamation reclamation) {
        tfId.setText(String.valueOf(reclamation.getId()));
        tfUserId.setText(String.valueOf(reclamation.getIdUser()));
        dpDate.setValue(reclamation.getDateR());
        taDescription.setText(reclamation.getDescription());
        tfEtat.setText(reclamation.getEtat());
        tfEtat.setEditable(false);  // Make "etat" read-only
    }

    @FXML
    private void updateReclamation() {
        try {
            Reclamation reclamation = new Reclamation(
                    Integer.parseInt(tfId.getText()),
                    Integer.parseInt(tfUserId.getText()),
                    dpDate.getValue(),
                    taDescription.getText(),
                    tfEtat.getText());

            reclamationService.modifier(reclamation);
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Reclamation updated successfully!");
            infoAlert.showAndWait();
            loadReclamations(); // Refresh the list
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error updating reclamation: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void deleteReclamation() {
        try {
            int id = Integer.parseInt(tfId.getText());
            reclamationService.supprimer(id);
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION, "Reclamation deleted successfully!");
            infoAlert.showAndWait();
            loadReclamations(); // Refresh the list
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Error deleting reclamation: " + e.getMessage());
            errorAlert.showAndWait();
        }
    }
    @FXML
    public void addReclamation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reclamation/AjouterReclamation.fxml"));
            Node ajouterReponseView = loader.load();
            contentPane.getChildren().setAll(ajouterReponseView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
