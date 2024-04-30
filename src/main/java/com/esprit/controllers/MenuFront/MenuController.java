package com.esprit.controllers.MenuFront;


import com.esprit.controllers.Reclamation.AfficherReclamationController;
import com.esprit.controllers.Reponse.AfficherReponseController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

import java.io.IOException;


public class MenuController {

    @FXML
    private StackPane contentPane;


    private void loadContent(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Node view = loader.load();

            if (fxml.equals("/Reclamation/AfficherReclamation.fxml")) {
                AfficherReclamationController controller = loader.getController();
                controller.setMainContent(contentPane);
            }
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onReclamationClick(ActionEvent event) {
        loadContent("/Reclamation/AfficherReclamation.fxml");
    }

}