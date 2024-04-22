package com.example.ghada;

import com.example.ghada.Model.Livraison;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.ghada.Service.LivraisonService;

import java.util.List;


public class LivraisonController  {
    private final LivraisonService LivraisonService = new LivraisonService();
    @FXML
    private TableColumn<Livraison, String> city;

    @FXML
    private TableColumn<Livraison, String> country;

    @FXML
    private TableColumn<Livraison, Integer> firstname;

    @FXML
    private TableColumn<Livraison, Integer> id;

    @FXML
    private TableColumn<Livraison, Integer> idlivreur;

    @FXML
    private TableColumn<Livraison, Integer> lastname;

    @FXML
    private TableColumn<Livraison, Integer> postalcode;

    @FXML
    private TableColumn<Livraison, Integer> status;
    @FXML
    private TableColumn<Livraison, String> address;

    @FXML
    private TableColumn<Livraison, String> adresse;

    @FXML
    private TableView<Livraison> Livraisontable;

    @FXML
    private TableColumn<Livraison, Integer> tel;

    @FXML



    private void populateFields(Livraison livraison) {

    }
    private void configureTableView() {
        this.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.idlivreur.setCellValueFactory(new PropertyValueFactory<>("idlivreur"));
        this.tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        this.adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        this.firstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        this.lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        this.address.setCellValueFactory(new PropertyValueFactory<>("address"));
        this.postalcode.setCellValueFactory(new PropertyValueFactory<>("postalcode"));
        this.country.setCellValueFactory(new PropertyValueFactory<>("country"));
        this.city.setCellValueFactory(new PropertyValueFactory<>("city"));
        this.status.setCellValueFactory(new PropertyValueFactory<>("status"));

    }

    private void refreshTableView() {
        List <Livraison> livraison = this.LivraisonService.getAll();
        ObservableList<Livraison> livraisonObservableList = FXCollections.observableArrayList(livraison);
        this.Livraisontable.setItems(livraisonObservableList);
    }

    }




