package com.esprit.controllers.Reponse;

import com.esprit.models.Reponse;
import com.esprit.services.ReponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class AfficherReponseController {

    @FXML
    private ListView<Reponse> reponsesListView;
    private ObservableList<Reponse> reponsesObservableList;

    @FXML
    private TextField tfReponseId;
    @FXML
    private TextField tfReclamationId;
    @FXML
    private TextField tfDateRep;
    @FXML
    private TextArea taDescription;

    @FXML
    private VBox detailsPane;

    private ReponseService reponseService;

    @FXML
    private StackPane contentPane;
    public void setMainContent(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    @FXML
    public void initialize() {
        reponseService = new ReponseService();

        reponsesObservableList = FXCollections.observableArrayList();
        reponsesListView.setItems(reponsesObservableList);
        refreshReponsesList();

        detailsPane.setVisible(false);

        reponsesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Reponse item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vBox = new VBox(
                            new Label("ID Réponse: " + item.getIdrep()),
                            new Label("ID Réclamation: " + item.getId()),
                            new Label("Date: " + item.getDateRep().toString()),
                            new Label("Description: " + item.getDescription())
                    );
                    vBox.setSpacing(4);
                    setGraphic(vBox);
                }
            }
        });

        reponsesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tfReponseId.setText(String.valueOf(newValue.getIdrep()));
                tfReclamationId.setText(String.valueOf(newValue.getId()));
                tfDateRep.setText(newValue.getDateRep().toString());
                taDescription.setText(newValue.getDescription());
                detailsPane.setVisible(true);
            }
        });
    }

    private void refreshReponsesList() {
        reponsesObservableList.setAll(reponseService.getAll());
    }

    @FXML
    private void updateReponse(ActionEvent event) {
        Reponse selected = reponsesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to update this response?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    selected.setDescription(taDescription.getText());
                    reponseService.modifier(selected);
                    refreshReponsesList();
                    detailsPane.setVisible(false);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Response updated successfully!");
                    successAlert.showAndWait();
                }
            });
        }
    }
    @FXML
    public void addReponse(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reponse/AjouterReponse.fxml"));
            Node ajouterReponseView = loader.load();
            contentPane.getChildren().setAll(ajouterReponseView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void deleteReponse(ActionEvent event) {
        Reponse selected = reponsesListView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this response?", ButtonType.YES, ButtonType.NO);
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    reponseService.supprimer(selected.getIdrep());
                    refreshReponsesList();
                    detailsPane.setVisible(false);
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Response deleted successfully!");
                    successAlert.showAndWait();
                }
            });
        }
    }
}
