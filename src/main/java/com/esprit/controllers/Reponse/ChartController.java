package com.esprit.controllers.Reponse;

import com.esprit.models.Reclamation;
import com.esprit.services.ReclamationService;
import com.esprit.services.ServiceChart;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import com.esprit.services.ServiceChart.*;
import javafx.scene.chart.PieChart;

import java.util.List;

public class ChartController {
    @FXML
    private PieChart piecharte;

    @FXML
    void initialize() {
        // Initialisation du PieChart
        piecharte.setTitle("Réclamations par État");

        // Appel de la fonction pour récupérer les réclamations triées par état
        ReclamationService reclamationService = new ReclamationService();
        List<Reclamation> reclamations = ServiceChart.getReclamationsOrderByEtat();

        // Création des sections du PieChart
        for (Reclamation reclamation : reclamations) {
            PieChart.Data data = new PieChart.Data(reclamation.getEtat(), 1);
            piecharte.getData().add(data);
        }
    }

}
