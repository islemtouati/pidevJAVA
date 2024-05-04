package com.LibraMarket.libra.models;

import javafx.collections.ObservableList;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class GeneratePdf {

    public static void generatePDF(ObservableList<Product> oeuvres, File file) {
        // Définir les marges et les espacements
        float margin = 50;
        float startX = 50;
        float startY = 750;
        float lineSpacing = 20;

        // Créer un nouveau document PDF
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Créer un flux de contenu pour écrire dans le document PDF
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Ajouter le titre
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText("Liste des Oeuvres");



                // Afficher les informations des oeuvres dans le document PDF
                for (Product oeuvre : oeuvres) {
                    startY -= lineSpacing;
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.showText("Titre: " + oeuvre.getTitre());
                    contentStream.newLineAtOffset(0, -lineSpacing);
                    contentStream.showText("Description: " + oeuvre.getDescription());
                    contentStream.newLineAtOffset(0, -lineSpacing);
                    contentStream.showText("Prix: " + oeuvre.getPrix());
                    contentStream.newLineAtOffset(0, -lineSpacing);


                    contentStream.showText("--------------------------------------");
                    contentStream.newLineAtOffset(0, -lineSpacing);
                }
            }

            // Sauvegarder le document PDF
            document.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}