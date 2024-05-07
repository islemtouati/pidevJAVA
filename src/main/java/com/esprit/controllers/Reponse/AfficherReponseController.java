package com.esprit.controllers.Reponse;

import com.esprit.models.Reclamation;
import com.esprit.models.Reponse;
import com.esprit.services.ReclamationService;
import com.esprit.services.ReponseService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

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

    @FXML
    private Button chartButton;
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

    public void generatePDF(ActionEvent actionEvent) {
        /*Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream("Reclamations.pdf"));
            document.open();
            ReclamationService ReclamationService = new ReclamationService();
            List<Reclamation> reclamationsList = ReclamationService.getAll();
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(reclamationsList);
            for (Reclamation reclamation : reclamations) {
                document.add(new Paragraph("ID: " + reclamation.getId()));
                document.add(new Paragraph("User ID: " + reclamation.getIdUser()));
                document.add(new Paragraph("Date: " + reclamation.getDateR().toString()));
                document.add(new Paragraph("Description: " + reclamation.getDescription()));
                document.add(new Paragraph("Etat: " + reclamation.getEtat()));
                document.add(new Paragraph("----------------------------------"));
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Generated");
            alert.setHeaderText(null);
            alert.setContentText("PDF file 'Reclamations.pdf' has been generated successfully!");
            alert.showAndWait();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Generate PDF");
            alert.setContentText("There was an error: " + e.getMessage());
            alert.showAndWait();
        } finally {
            document.close();
        }*/

        Document document = new Document(PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(document, new FileOutputStream("Reclamations.pdf"));
            document.open();

            ReclamationService reclamationService = new ReclamationService();
            List<Reclamation> reclamationsList = reclamationService.getAll(); // Obtenez la liste de réclamations
            ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(reclamationsList); // Convertissez la liste en ObservableList

            PdfPTable table = new PdfPTable(5); // Créez un tableau avec 5 colonnes
            table.addCell("ID");
            table.addCell("User ID");
            table.addCell("Date");
            table.addCell("Description");
            table.addCell("État");

            for (Reclamation reclamation : reclamations) {
                table.addCell(String.valueOf(reclamation.getId()));
                table.addCell(String.valueOf(reclamation.getIdUser()));
                table.addCell(reclamation.getDateR().toString());
                table.addCell(reclamation.getDescription());
                table.addCell(reclamation.getEtat());
            }

            document.add(table);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Généré");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier PDF 'Reclamations.pdf' a été généré avec succès!");
            alert.showAndWait();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Échec de la Génération du PDF");
            alert.setContentText("Une erreur s'est produite: " + e.getMessage());
            alert.showAndWait();
        } finally {
            document.close();
        }



    }

    public void Navigatetochart(ActionEvent Event) {
        try {
            // Charger le fichier FXML de la page cible
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Reponse/chart.fxml"));
            Parent chartView = loader.load();

            // Obtenir la scène actuelle et définir la nouvelle vue comme son contenu
            Stage stage = (Stage) ((Node) Event.getSource()).getScene().getWindow();
            Scene scene = new Scene(chartView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
