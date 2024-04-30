package com.esprit.controllers.Reponse;

import com.esprit.models.Reclamation;
import com.esprit.models.Reponse;
import com.esprit.services.ReclamationService;
import com.esprit.services.ReponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class AjouterReponseController {

    @FXML
    private TableView<Reclamation> reclamationTableView;

    private ReclamationService reclamationService = new ReclamationService();
    private ReponseService reponseService = new ReponseService();

    @FXML
    public void initialize() {
        setupReclamationTable();
        loadReclamations();
    }

    private void setupReclamationTable() {
        TableColumn<Reclamation, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Reclamation, Integer> userIdColumn = new TableColumn<>("User ID");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));

        TableColumn<Reclamation, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("dateR"));

        TableColumn<Reclamation, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Reclamation, String> stateColumn = new TableColumn<>("État");
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));

        TableColumn<Reclamation, Void> actionColumn = new TableColumn<>("Action");
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Répondre");

            {
                btn.setOnAction((ActionEvent event) -> {
                    Reclamation data = getTableView().getItems().get(getIndex());
                    handleRespond(data);
                });
                btn.getStyleClass().add("respond-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    Reclamation reclamation = (Reclamation) getTableRow().getItem();
                    btn.setDisable(reclamation.getEtat().equals("traité"));
                    setGraphic(new HBox(10, btn));
                }
            }
        });

        reclamationTableView.getColumns().addAll(idColumn, userIdColumn, dateColumn, descriptionColumn, stateColumn, actionColumn);
    }



    private void handleRespond(Reclamation reclamation) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Respond to Reclamation");
        dialog.setHeaderText("Respond to Reclamation ID: " + reclamation.getId());
        dialog.setContentText("Enter your response:");

        dialog.showAndWait().ifPresent(responseText -> {
            if (!responseText.trim().isEmpty()) {

                // Create response instance
                Reponse newResponse = new Reponse();
                newResponse.setId(reclamation.getId());  // Set the reclamation ID
                newResponse.setDateRep(new java.util.Date());  // Current date as java.util.Date, convert if necessary
                newResponse.setDescription(responseText);  // Set the response description

                // Save response via service
                reponseService.ajouter(newResponse);

                // Optionally, update UI or notify user
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Response successfully added!");
                successAlert.showAndWait();

                loadReclamations();
            }
        });
    }


    private void loadReclamations() {
        ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(reclamationService.getAll());
        reclamationTableView.setItems(reclamations);
    }
}
