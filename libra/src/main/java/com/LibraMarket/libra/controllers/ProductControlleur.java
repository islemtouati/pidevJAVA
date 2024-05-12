package com.LibraMarket.libra.controllers;

import com.LibraMarket.libra.models.Product;
import com.LibraMarket.libra.services.ServiceCatego;
import com.LibraMarket.libra.services.ServiceProduct;
import com.LibraMarket.libra.utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.chart.PieChart;
public class ProductControlleur {
    ServiceProduct taskSer = new ServiceProduct();

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
    private TableColumn<Product, String> ts_titre;

    @FXML
    private TableColumn<Product, Void> ts_view_action;



    /*public ObservableList<Product> getOeuvre() {
        ObservableList<Product> oeuvreList = FXCollections.observableArrayList();
        String query = "SELECT oeuvre.*, categorie.nom_cat FROM oeuvre JOIN categorie ON oeuvre.id_cat = categorie.id_cat";
        ServiceProduct so = new ServiceProduct();
        ServiceCatego sc = new ServiceCatego();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            con = DBConnection.getInstance().getCnx();
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {

                Product oeuvre = new Product();
                oeuvre.setId(rs.getInt("id_oeuvre"));
                oeuvre.setTitre(rs.getString("titre"));
                oeuvre.setDescription(rs.getString("description"));
                oeuvre.setPrix(rs.getFloat("prix"));
                oeuvre.setCategory_id(rs.getInt("id_cat"));
                // Obtenez la catégorie à partir de son ID
                System.out.println("ss");

                //Categorie categorie = sc.getCategorieById(categorieId);

                // Assurez-vous que la catégorie n'est pas null avant de définir son nom
                //oeuvre.setNomCategorie(categorie.getNom_cat());

                oeuvreList.add(oeuvre);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération des oeuvres.", ex);
        } finally {
            // Fermeture des ressources
            try {


                if (rs != null) rs.close();
                System.out.println("88");
                if (st != null) st.close();
                System.out.println("99");
                if (con != null) con.close();
                System.out.println("00");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("vv");

        return oeuvreList;
    }*/
    @FXML


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




    public void affichertasks(){
        try {
            ts_view.getItems().setAll(taskSer.selectAll());
        }
        catch (SQLException e){
            showAlert("Erreur","erreur lors du load du tabview");
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
    /*@FXML
    public void showOeuvres1(){
        ObservableList<Product> list = ();
        tvOeuvre.setItems(list);
        tvOeuvre.setItems(list);
        coltitre.setCellValueFactory(new PropertyValueFactory<String,Product>("titre"));
        coldescription.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("description"));
        colprix.setCellValueFactory(new PropertyValueFactory<Float,Oeuvre>("prix"));
        coldate.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("date"));
        colstatus.setCellValueFactory(new PropertyValueFactory<String,Oeuvre>("Status"));
        ServiceCategorie sc = new ServiceCategorie();
        colNomCat.setCellValueFactory(new PropertyValueFactory<Integer,Oeuvre>("id_cat"));
        FilteredList<Oeuvre> filteredData = new FilteredList<>(list, b-> true);
        searchField.textProperty().addListener((observable , oldValue, newValue) -> {
            filteredData.setPredicate(Oeuvre -> {
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if(Oeuvre.getTitre().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }
                else if(Oeuvre.getStatus().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }
                else if (String.valueOf(Oeuvre.getId_oeuvre()).toLowerCase().contains(searchKeyword.toLowerCase())) {                    return true;


                }else
                    return false;
            });
        });
        SortedList<Oeuvre> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvOeuvre.comparatorProperty());
        tvOeuvre.setItems(sortedData);
    }*/

    @FXML
    void initialize() throws SQLException {
        // Column bindings



        ts_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        ts_Prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        ts_img.setCellValueFactory(new PropertyValueFactory<>("img"));
        ts_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        ts_catego.setCellValueFactory(new PropertyValueFactory<>("category_id"));


        // Enable editing for each cell
        ts_catego.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        ts_titre.setCellFactory(TextFieldTableCell.forTableColumn());
        ts_Prix.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        ts_img.setCellFactory(TextFieldTableCell.forTableColumn());
        ts_description.setCellFactory(TextFieldTableCell.forTableColumn());


        // Handle edit commit events
        ts_titre.setOnEditCommit(event -> handleEditCommit(event, "titre"));
        ts_Prix.setOnEditCommit(event -> handleEditCommit(event, "prix"));
        ts_description.setOnEditCommit(event -> handleEditCommit(event, "description"));


        // Update button action
        ts_view_action.setCellFactory(param -> new TableCell<>() {
        //    private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Product task = getTableView().getItems().get(getIndex());

                    if (task != null) { // Check if an item is selected
                        int var = task.getId();
                        if (var != 0) {
                            try {
                                taskSer.deleteOne(var);
                                affichertasks();
                                showAlertSuc("Success ", "Tache supprimer !");
                            } catch (SQLException e) {
                                showAlert("Erreur", "Erreur lors de la suppression de la tache !");
                            }
                        } else {
                            showAlertWar("Aucune sélection", "Aucun tache sélectionné");
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonBox = new HBox(5);
                    buttonBox.getChildren().addAll(/*updateButton, */deleteButton);
                    setGraphic(buttonBox);
                }
            }
        });

        // Refresh the TableView
        affichertasks();
        updatePieChartData();
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
    @FXML
    private void handleGeneratePDF(ActionEvent event) {
        // Instancier la classe pdf
        pdf pdfGenerator = new pdf();

        // Obtenez le chemin où le fichier PDF doit être enregistré
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save PDF");
        fileChooser.setInitialFileName("product.pdf");
        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();

            // Appelez la méthode pour générer le PDF en utilisant la classe pdf
            pdfGenerator.generatePDF(ts_view, filePath);
            System.out.println("PDF generated successfully.");
        } else {
            System.out.println("PDF generation canceled.");
        }
    }

}
