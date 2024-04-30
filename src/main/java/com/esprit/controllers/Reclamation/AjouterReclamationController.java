package com.esprit.controllers.Reclamation;

import com.esprit.models.Reclamation;
import com.esprit.services.ReclamationService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import java.sql.Date;
import java.time.LocalDate;

public class AjouterReclamationController {

    @FXML
    private TextField tfDescription;
    @FXML
    private DatePicker dpDate;

    private ReclamationService reclamationService = new ReclamationService();

    @FXML
    private void handleAjouterReclamation() {
        try {

            String description = tfDescription.getText();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Please enter a description.");
            }
            if (description.length() > 15) {
                throw new IllegalArgumentException("Description should not exceed 15 characters.");
            }
            // Validation de la date
            LocalDate selectedDate = dpDate.getValue();
            LocalDate currentDate = LocalDate.now();
            if (!selectedDate.equals(currentDate)) {
                throw new IllegalArgumentException("You can only select today's date.");
            }
            Reclamation newReclamation = new Reclamation();
            newReclamation.setIdUser(1); // User ID is set by default to 1
            newReclamation.setDateR(dpDate.getValue()); // Setting the date from DatePicker
            newReclamation.setDescription(tfDescription.getText());
            newReclamation.setEtat("non traité"); // Default state

            reclamationService.ajouter(newReclamation);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reclamation Added");
            alert.setHeaderText(null);
            alert.setContentText("Reclamation has been added successfully!");
            alert.showAndWait();

            // Optionally, clear the form or navigate away
            tfDescription.clear();
            dpDate.setValue(null);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Add Reclamation");
            alert.setContentText("There was an error: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
