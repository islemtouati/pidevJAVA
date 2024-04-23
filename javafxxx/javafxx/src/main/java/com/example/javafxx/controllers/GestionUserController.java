package com.example.javafxx.controllers;

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
import java.util.Optional;
import java.util.ResourceBundle;

public class GestionUserController implements Initializable {
    @FXML
    private TableView<Utilisateur> userTable;
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
        // Load users data into the table
        userTable.setItems(FXCollections.observableArrayList(userService.getAllUsers()));


        // Define actions for update and delete buttons
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
                        // Handle delete button action
                        Utilisateur user = getTableView().getItems().get(getIndex());
                        handleDelete(user);
                    });
                }
            }
        });

        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userService.changeScene(event,"SignIn.fxml","log In !");
            }
        });

        addUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userService.changeScene(event,"AddUser.fxml","New user !");
            }
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


}






