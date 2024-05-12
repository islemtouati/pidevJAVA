package com.LibraMarket.libra.controllers;

import com.LibraMarket.libra.models.Product;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.LibraMarket.libra.models.Product ;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class pdf {

    public void generatePDF(TableView<Product> tableView, String filePath) {
        Document document = new Document();
        try (OutputStream outputStream = new FileOutputStream(filePath)) {
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.add(new Paragraph("Mes Produits "));

            PdfPTable pdfPTable = new PdfPTable(tableView.getColumns().size());
            for (TableColumn<Product, ?> column : tableView.getColumns()) {
                pdfPTable.addCell(column.getText());
            }

            ObservableList<TableColumn<Product, ?>> columns = tableView.getColumns();
            ObservableList<Product> items = tableView.getItems();
            for (Product item : items) {
                for (TableColumn<Product, ?> column : columns) {
                    if (column.getCellData(item) != null) {
                        pdfPTable.addCell(column.getCellData(item).toString());
                    } else {
                        pdfPTable.addCell(""); // Ensure that null values are handled.
                    }
                }
            }

            document.add(pdfPTable);
            document.close(); // Explicitly close the document.
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    // Créer une cellule avec style
    private PdfPCell createCell(String content) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(new Phrase(content));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
}

