package com.esprit.controllers.Menu;


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

            if (fxml.equals("/Reponse/AfficherReponse.fxml")) {
                AfficherReponseController controller = loader.getController();
                controller.setMainContent(contentPane);
            }
            contentPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onReponseClick(ActionEvent event) {
        loadContent("/Reponse/AfficherReponse.fxml");
    }

}