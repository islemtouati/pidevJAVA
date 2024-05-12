package com.LibraMarket.libra.controllers;

import com.LibraMarket.libra.models.Product;
import com.LibraMarket.libra.services.ServiceProduct;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductfrontControlleur {
    ServiceProduct taskSer = new ServiceProduct();
    private ObservableList<Product> productList;


    @FXML
    private PieChart PieChart;

    @FXML
    private TableView<Product> ts_view; // Use the model class here

    @FXML
    private Button pdf;

    @FXML
    private TableColumn<Product, Double> ts_Prix;

    @FXML
    private TableColumn<Product, Integer> ts_catego;

    @FXML
    private TableColumn<Product, String> ts_description;

    @FXML
    private TableColumn<Product, String> ts_img;
    @FXML

    private TextField searchField;


    @FXML
    private TableColumn<Product, String> ts_titre;


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    private void showAlertSuc(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    private void showAlertWar(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public void add_Task(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/add_product.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();

        // Get the controller
        Add_Product controller = fxmlLoader.getController();

        // Set the stage
        Stage stage = new Stage();
        controller.setStage(stage); // Pass the stage to the controller
        stage.setScene(new Scene(root1));
        stage.show();
    }

    public void affichertasks() {
        try {
            productList = FXCollections.observableArrayList(taskSer.selectAll());
            ts_view.setItems(productList);
        } catch (SQLException e) {
            showAlert("Erreur", "erreur lors du load du tabview");
        }
    }


    private void updateTask(Product task) {
        try {
            taskSer.updateOne(task); // Update the modified task using the service
            showAlertSuc("Success ", "Task updated!");
            affichertasks(); // Refresh the table after updating the task
        } catch (SQLException e) {
            showAlert("Error", "Error updating the task: " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    public List<Integer> countTasksByStatus(List<Product> tasksList) {
        List<Integer> taskCounts = new ArrayList<>(3); // Initialize with three elements for each status

        int doneCount = 0;
        int inProgressCount = 0;
        int toDoCount = 0;

        for (Product task : tasksList) {
            int status = task.getCategory_id();
            if (status == 2) {
                doneCount++;
            } else if (status == 1) {
                inProgressCount++;
            } else if (status == 3) {
                toDoCount++;
            }
        }

        taskCounts.add(doneCount);
        taskCounts.add(inProgressCount);
        taskCounts.add(toDoCount);

        return taskCounts;
    }

    @FXML
    void initialize() throws SQLException {

        // Column bindings
        ts_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        ts_Prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        ts_img.setCellValueFactory(new PropertyValueFactory<>("img"));

        ts_img.setCellFactory(column -> new ImageTableCell());
        ts_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        ts_catego.setCellValueFactory(new PropertyValueFactory<>("category_id"));

        // Enable editing for each cell
        ts_catego.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ts_titre.setCellFactory(TextFieldTableCell.forTableColumn());
        ts_Prix.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        ts_img.setCellFactory(TextFieldTableCell.forTableColumn());
        ts_description.setCellFactory(TextFieldTableCell.forTableColumn());

        // Refresh the TableView
        affichertasks();
        updatePieChartData();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            handleSearch(newValue.trim()); // Appeler la méthode de recherche avec le nouveau texte
        });
    }

    private void handleSearch(String keyword) {
        if (!keyword.isEmpty()) {
            String keywordLower = keyword.toLowerCase();
            List<Product> filteredList = productList.stream()
                    .filter(product -> product.getTitre().toLowerCase().contains(keywordLower))
                    .collect(Collectors.toList());
            ts_view.setItems(FXCollections.observableArrayList(filteredList));
        } else {
            ts_view.setItems(productList);
        }
    }




    // Method to handle edit commit events
    private void handleEditCommit(TableColumn.CellEditEvent<Product, ?> event, String propertyName) {
        Product task = event.getRowValue();
        switch (propertyName) {
            case "titre":
                task.setTitre(event.getNewValue().toString());
                break;
            case "prix":
                task.setPrix(Double.parseDouble(event.getNewValue().toString()));
                break;
            case "description":
                task.setDescription(event.getNewValue().toString());
                break;
        }
        try {
            taskSer.updateField(task.getId(), propertyName, event.getNewValue());
            showAlertSuc("Success ", "Field updated!");
        } catch (SQLException e) {
            showAlert("Error", "Error updating the field: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void refrech(ActionEvent event) {
        affichertasks(); // Refresh the TableView
    }

    private void updatePieChartData() {
        try {
            // Step 1: Retrieve the list of tasks
            List<Product> tasksList = taskSer.selectAll();

            // Step 2: Get the counts for tasks in different statuses
            List<Integer> taskCounts = countTasksByStatus(tasksList);
            // Calculate total tasks count
            int totalTasks = taskCounts.stream().mapToInt(Integer::intValue).sum(); // Declare and initialize totalTasks
            // Step 3: Update PieChart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Romance", taskCounts.get(2)), // Index 2 for Todo count
                    new PieChart.Data("Horror", taskCounts.get(1)), // Index 1 for In Progress count
                    new PieChart.Data("#", taskCounts.get(0)) // Index 0 for Complete count
            );

            PieChart.setData(pieChartData);
            PieChart.setStartAngle(90);

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Error updating PieChart data: " + e.getMessage());
        }
    }
}
