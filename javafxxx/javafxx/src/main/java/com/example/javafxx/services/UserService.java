package com.example.javafxx.services;

import com.example.javafxx.entities.Utilisateur;
import com.example.javafxx.utils.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    Connection cnx;

    public UserService() {
        cnx = DB.getInstance().getCnx();
    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title){
        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(UserService.class.getResource("/com/example/javafxx/" + fxmlFile));
            root = loader.load();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            root = FXMLLoader.load(UserService.class.getResource("/com/example/javafxx/" + fxmlFile));
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600,400));
        stage.show();

    }

    public void signUp(ActionEvent event, String nom, String prenom, String email,String adresse, Integer tel,String role, String password){
        PreparedStatement insert=null;
        PreparedStatement CheckUserExists=null;
        ResultSet resultSet=null;
        try {
            CheckUserExists= cnx.prepareStatement("SELECT * FROM utilisateur WHERE email = ?");
            CheckUserExists.setString(1, email);
            resultSet = CheckUserExists.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("utilisateur existe deja!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("utilisateur existe deja !");
                alert.show();
            } else {
                insert = cnx.prepareStatement("INSERT INTO utilisateur (nom, prenom, email, adresse, tel, role, password) VALUES (?,?,?,?,?,?,?)");
                insert.setString(1,nom);
                insert.setString(2,prenom);
                insert.setString(3,email);
                insert.setString(4,adresse);
                insert.setInt(5,tel);
                insert.setString(6,role);
                insert.setString(7,password);
                insert.executeUpdate();
                if ("admin".equals(role)) {
                    changeScene(event, "gestionUser.fxml", "Gestion des utilisateurs");
                } else {
                    changeScene(event, "hello-view.fxml", "Bienvenue!");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void signIn(ActionEvent event, String email, String pwd){
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            preparedStatement = cnx.prepareStatement("SELECT password, role FROM utilisateur WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.isBeforeFirst()){
                System.out.println("utilisateur introuvable!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("incorrect email !");
                alert.show();
            } else {
                while (resultSet.next()){
                    String getPassword = resultSet.getString("password");
                    String role = resultSet.getString("role");

                    if(getPassword.equals(pwd)){
                        if ("admin".equals(role)) {
                            changeScene(event, "gestionUser.fxml", "Gestion des utilisateurs");
                        } else {
                            changeScene(event, "hello-view.fxml", "Bienvenue!");
                        }
                    } else {
                        System.out.println("Mot de passe incorrect!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Mot de passe incorrect !");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Utilisateur> getAllUsers() {
        List<Utilisateur> users = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Prepare the SQL statement
            preparedStatement = cnx.prepareStatement("SELECT * FROM utilisateur");
            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and create Utilisateur objects
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setAdresse(resultSet.getString("adresse"));
                utilisateur.setTel(resultSet.getInt("tel"));
                utilisateur.setRole(resultSet.getString("role"));
                utilisateur.setPassword(resultSet.getString("password"));
                utilisateur.setIs_verified(resultSet.getBoolean("is_verified"));

                // Add the utilisateur to the list
                users.add(utilisateur);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception properly in your application
        } finally {
            // Close the resources
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return users;
    }

    public void updateUser(Utilisateur user) {
        PreparedStatement updateStatement = null;
        try {
            updateStatement = cnx.prepareStatement("UPDATE utilisateur SET nom=?, prenom=?, email=?, adresse=?, tel=?, role=? WHERE id=?");
            updateStatement.setString(1, user.getNom());
            updateStatement.setString(2, user.getPrenom());
            updateStatement.setString(3, user.getEmail());
            updateStatement.setString(4, user.getAdresse());
            updateStatement.setInt(5, user.getTel());
            updateStatement.setString(6, user.getRole());
            updateStatement.setInt(7, user.getId()); // Use email as identifier for the update
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            // Close resources if necessary
            try {
                if (updateStatement != null) {
                    updateStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle closing exception
            }
        }
    }

    public void deleteUser(Utilisateur user) {
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = cnx.prepareStatement("DELETE FROM utilisateur WHERE email=?");
            deleteStatement.setString(1, user.getEmail());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (deleteStatement != null) {
                try {
                    deleteStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addUser(ActionEvent event, String nom, String prenom, String email,String adresse, Integer tel,String role, String password) {
        PreparedStatement insert = null;
        try {
            insert = cnx.prepareStatement("INSERT INTO utilisateur (nom, prenom, email, adresse, tel, role, password) VALUES (?,?,?,?,?,?,?)");
            insert.setString(1,nom);
            insert.setString(2,prenom);
            insert.setString(3,email);
            insert.setString(4,adresse);
            insert.setInt(5,tel);
            insert.setString(6,role);
            insert.setString(7,password);
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
