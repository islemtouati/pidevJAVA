package com.example.javafxx.controllers;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import com.example.javafxx.entities.Utilisateur;
import com.example.javafxx.services.UserService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionUserController implements Initializable {
    @FXML
    private TableView<Utilisateur> userTable;
    @FXML
    public Button exportPDF;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableColumn<Utilisateur, Button> colBan;
    @FXML
    private TableColumn<Utilisateur, Integer> colId;
    @FXML
    private TableColumn<Utilisateur, String> colNom;
    @FXML
    private TableColumn<Utilisateur, String> colPrenom;
    @FXML
    private TableColumn<Utilisateur, String> colEmail;
    @FXML
    private TableColumn<Utilisateur, String> colAdresse;
    @FXML
    private TableColumn<Utilisateur, Integer> colTel;
    @FXML
    private TableColumn<Utilisateur, String> colRole;
    @FXML
    private TableColumn<Utilisateur, Void> colUpdate;
    @FXML
    private TableColumn<Utilisateur, Button> colDelete;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;

    @FXML
    private Button  btn_logout;

    @FXML
    private Button  addUser;

    UserService userService = new UserService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Bind columns with Utilisateur properties
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()).asObject());
        colNom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNom()));
        colPrenom.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPrenom()));
        colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
        colAdresse.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAdresse()));
        colRole.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getRole()));
        colTel.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getTel()).asObject());

        // Set up cell factory for update button column
        colUpdate.setCellFactory(param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.setOnAction(event -> {
                    Utilisateur user = getTableView().getItems().get(getIndex());
                    openUpdateUserView(user);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        // Set up cell factory for delete button column
        colDelete.setCellFactory(cell -> new TableCell<Utilisateur, Button>() {
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(deleteButton);
                    setText(null);
                    deleteButton.setOnAction(event -> {
                        Utilisateur user = getTableView().getItems().get(getIndex());
                        handleDelete(user);
                    });
                }
            }
        });

        // Set up cell factory for ban button column
        colBan.setCellFactory(param -> new TableCell<>() {
            private final Button banButton = new Button("Ban");

            {
                banButton.setOnAction(event -> {
                    Utilisateur user = getTableView().getItems().get(getIndex());
                    handleBan(user);
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(banButton);
                    setText(null);
                }
            }
        });

        // Load users data into the table
        userTable.setItems(FXCollections.observableArrayList(userService.getAllUsers()));

        // Define action for search text field
        searchTextField.setOnKeyReleased(event -> handleSearch());

        // Define action for export PDF button
        exportPDF.setOnAction(this::handleExportPDF);

        // Define action for logout button
        btn_logout.setOnAction(event -> {
            userService.changeScene(event, "SignIn.fxml", "Log In");
        });

        // Define action for add user button
        addUser.setOnAction(event -> {
            userService.changeScene(event, "AddUser.fxml", "New User");
        });
    }


    private void handleUpdate(Utilisateur user) {

    }

    private void handleDelete(Utilisateur user) {
        // Implementation for deleting the user
        userService.deleteUser(user);
        userTable.getItems().remove(user);
    }

    public void refreshUserTable() {
        // Load users data into the table
        userTable.setItems(FXCollections.observableArrayList(userService.getAllUsers()));
    }

    private void openUpdateUserView(Utilisateur user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafxx/updateUser.fxml"));
            Parent root = loader.load();

            UpdateUserController controller = loader.getController();
            controller.initData(user, this);


            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update User");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleSearch() {
        String searchCriteria = searchTextField.getText();
        refreshTable(searchCriteria);
    }
    private void refreshTable(String searchCriteria) {
        // Clear the table
        userTable.getItems().clear();
        // Fetch users based on search criteria
        List<Utilisateur> users = userService.getAllUsers(searchCriteria);
        // Add fetched users to the table
        userTable.getItems().addAll(users);
    }

    @FXML
    private void handleExportPDF(ActionEvent event) {
        System.out.println("Export PDF button clicked");
        try {
            userService.exportUsersToPdf();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as per your application's requirements
        }
    }
    private void handleBan(Utilisateur user) {
        // Implementation for banning the user
        userService.banUser(user);
        refreshUserTable(); // Assuming you have a method to refresh the user table
    }
}






