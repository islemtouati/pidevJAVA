package com.webandit.libra.controllers;

import com.webandit.libra.models.catego;
import com.webandit.libra.services.ServiceCatego;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Project {
    ServiceCatego ps = new ServiceCatego();

    @FXML
    private DatePicker ch_date;

    @FXML
    private Button ch_delete;



    @FXML
    private TextField ch_nom;



    @FXML
    private Button ch_reset;

    @FXML
    private Button ch_save;

    @FXML
    private Button ch_update;

    @FXML
    private TableView<catego> ch_view;


    @FXML
    private TableColumn<catego, String> ch_view_nom;



    // Utility method to show alert dialog
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
    // Method to perform validation for the catego name
    private boolean isValidChantierName(String name) {
        return Pattern.matches("[a-zA-Z]{5,20}", name);
    }




    // Method to display error message
    private void displayError(TextField field, String errorMessage) {
        field.getStyleClass().add("error-field");
        showAlert("Erreur de validation", errorMessage);
    }

    // Method to remove error style
    private void removeErrorStyle(TextField field) {
        field.getStyleClass().remove("error-field");
    }

    @FXML
    void addchantier(ActionEvent event) {
        try {


            if (!isValidChantierName(ch_nom.getText())) {
                displayError(ch_nom, "Le nom du chantier doit contenir uniquement des lettres et avoir une longueur entre 5 et 20 caractères !");
                return;
            } else {
                removeErrorStyle(ch_nom);
            }



            catego ch = new catego(ch_nom.getText());

            ps.insertOne(ch);
            afficherChantier();
        } catch (SQLException | NumberFormatException e) {
            showAlert("Erreur de saisie", "Erreur dans la saisie des données !");
        }
    }

    @FXML
    void deleteChantier(ActionEvent event) {
        catego selectedChantier = ch_view.getSelectionModel().getSelectedItem();
        if (selectedChantier != null) {
            try {
                ps.deleteOne(selectedChantier.getId()); // Accessing ServiceChantier methods via 'ps' instance
                afficherChantier(); // Refresh the table after deleting a chantier
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression du chantier!");
            }
        } else {
            showAlert("Aucune sélection", "Aucun chantier sélectionné");
        }
    }

    public void afficherChantier() {
        try {
            ch_view.getItems().setAll(ps.selectAll()); // Accessing ServiceChantier methods via 'ps' instance
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des chantiers!");
        }
    }

    @FXML
    void initialize() {
        ch_view_nom.setCellValueFactory(new PropertyValueFactory<>("titre"));
        afficherChantier(); //refrech el tableview
    }


    @FXML
    void resetFields(ActionEvent event) {
        ch_nom.clear();

    }
    @FXML
    void selectChantier(ActionEvent event) {
        catego selectedChantier = ch_view.getSelectionModel().getSelectedItem();
        if (selectedChantier != null) {
            ch_nom.setText(selectedChantier.getTitre());

        } else {
            showAlert("Aucune sélection", "Aucun chantier sélectionné");
        }
    }

    @FXML
    void updateChantier(ActionEvent event) {
        catego selectedChantier = ch_view.getSelectionModel().getSelectedItem();
        if (selectedChantier != null) {
            try {
                // Update the selected chantier object with the modified data
                selectedChantier.setTitre(ch_nom.getText());


                // Call the updateOne function from the service to update the database
                ps.updateOne(selectedChantier);

                // Refresh the table view after updating
                afficherChantier();
            } catch (SQLException | NumberFormatException e) {
                showAlert("Erreur", "Erreur lors de la mise à jour du chantier!");
            }
        } else {
            showAlert("Aucune sélection", "Aucun chantier sélectionné");
        }
    }
    @FXML
    private StackPane contentArea;
    public void Tasks(ActionEvent actionEvent)throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/fxml/Tasks.fxml"));
        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(fxml);
    }

}
