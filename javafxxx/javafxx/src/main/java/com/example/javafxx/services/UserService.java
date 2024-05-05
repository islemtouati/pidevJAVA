package com.example.javafxx.services;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
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
import java.util.concurrent.TimeUnit;

public class UserService {
    Connection cnx;
    private static Utilisateur connectedUser; // variable to hold the connected user

    public static Utilisateur getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(Utilisateur user) {
        connectedUser = user;
    }

    public UserService() {
        cnx = DB.getInstance().getCnx();
    }

    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
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
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }

    public void exportUsersToPdf() throws IOException {
        System.out.println("Exporting users to PDF...");
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("UserList.pdf"));
            document.open();
            List<Utilisateur> users = getAllUsers();
            for (Utilisateur user : users) {

                document.add(new Paragraph("Nom: " + user.getNom()));
                document.add(new Paragraph("Prenom: " + user.getPrenom()));
                document.add(new Paragraph("Adresse: " + user.getAdresse()));
                document.add(new Paragraph("Email: " + user.getEmail()));
                document.add(new Paragraph("Role: " + user.getRole()));
                document.add(new Paragraph("-----------------------------------"));
            }
            document.close();
            System.out.println("PDF Exported Successfully");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void banUser(Utilisateur user) {
        // Set is_banned to 1 immediately
        updateUserBannedStatus(user.getId(), 1);

        // Schedule a task to set is_banned back to 0 after 2 minutes
        scheduler.schedule(() -> updateUserBannedStatus(user.getId(), 0), 1, TimeUnit.MINUTES);
    }

    private void updateUserBannedStatus(int userId, int isBanned) {
        PreparedStatement preparedStatement = null;

        try {
            String query = "UPDATE utilisateur SET is_banned = ? WHERE id = ?";
            preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, isBanned);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void signUp(ActionEvent event, String nom, String prenom, String email, String adresse, Integer tel, String role, String password) {
        PreparedStatement insert = null;
        PreparedStatement CheckUserExists = null;
        ResultSet resultSet = null;
        try {
            CheckUserExists = cnx.prepareStatement("SELECT * FROM utilisateur WHERE email = ?");
            CheckUserExists.setString(1, email);
            resultSet = CheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("utilisateur existe deja!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("utilisateur existe deja !");
                alert.show();
            } else {
                insert = cnx.prepareStatement("INSERT INTO utilisateur (nom, prenom, email, adresse, tel, role, password) VALUES (?,?,?,?,?,?,?)");
                insert.setString(1, nom);
                insert.setString(2, prenom);
                insert.setString(3, email);
                insert.setString(4, adresse);
                insert.setInt(5, tel);
                insert.setString(6, role);
                insert.setString(7, password);
                insert.executeUpdate();

                changeScene(event, "SignIn.fxml", "SignIn!");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Utilisateur> getAllUsers(String searchCriteria) {
        List<Utilisateur> users = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Prepare the SQL statement with a WHERE clause for searching
            String query = "SELECT * FROM utilisateur WHERE nom LIKE ? OR prenom LIKE ? OR adresse LIKE ?";
            preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchCriteria + "%");
            preparedStatement.setString(2, "%" + searchCriteria + "%");
            preparedStatement.setString(3, "%" + searchCriteria + "%");

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Iterate through the result set and create Utilisateur objects
            while (resultSet.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(resultSet.getInt("id"));
                utilisateur.setNom(resultSet.getString("nom"));
                utilisateur.setPrenom(resultSet.getString("prenom"));
                utilisateur.setAdresse(resultSet.getString("adresse"));
                utilisateur.setEmail(resultSet.getString("email"));
                utilisateur.setRole(resultSet.getString("role"));
                utilisateur.setPassword(resultSet.getString("password"));

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

    public void signIn(ActionEvent event, String email, String pwd) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = cnx.prepareStatement("SELECT * FROM utilisateur WHERE email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Utilisateur introuvable!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Adresse e-mail incorrecte !");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String getPassword = resultSet.getString("password");
                    int isBanned = resultSet.getInt("is_banned");
                    String role = resultSet.getString("role");

                    if (getPassword.equals(pwd)) {
                        if (isBanned == 0 || resultSet.wasNull()) {
                            Utilisateur connectedUser = new Utilisateur();
                            connectedUser.setNom(resultSet.getString("nom"));
                            connectedUser.setPrenom(resultSet.getString("prenom"));
                            connectedUser.setEmail(resultSet.getString("email"));
                            connectedUser.setAdresse(resultSet.getString("adresse"));
                            connectedUser.setTel(resultSet.getInt("tel"));

                            // Set the connected user in UserService
                            setConnectedUser(connectedUser);

                            if ("admin".equals(role)) {
                                changeScene(event, "gestionUser.fxml", "Gestion des utilisateurs");
                            } else {
                                changeScene(event, "hello-view.fxml", "Bienvenue !");
                            }
                        } else {
                            System.out.println("Utilisateur banni !");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setContentText("Vous êtes banni ! Veuillez réessayer ultérieurement.");
                            alert.show();
                        }
                    } else {
                        System.out.println("Mot de passe incorrect !");
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
                utilisateur.setis_banned(resultSet.getInt("is_banned"));

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
        } finally {
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

    public void addUser(ActionEvent event, String nom, String prenom, String email, String adresse, Integer tel, String role, String password) {
        PreparedStatement insert = null;
        try {
            insert = cnx.prepareStatement("INSERT INTO utilisateur (nom, prenom, email, adresse, tel, role, password) VALUES (?,?,?,?,?,?,?)");
            insert.setString(1, nom);
            insert.setString(2, prenom);
            insert.setString(3, email);
            insert.setString(4, adresse);
            insert.setInt(5, tel);
            insert.setString(6, role);
            insert.setString(7, password);
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Utilisateur getUserByEmail(String toEmail) {
        String query = "SELECT * FROM utilisateur WHERE email = ?";
        Utilisateur user = null;

        try (Connection connection = DB.getInstance().getCnx();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, toEmail);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Retrieve user data from the result set
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String adresse = resultSet.getString("adresse");
                int tel = resultSet.getInt("tel");
                String role = resultSet.getString("role");
                String password = resultSet.getString("password");
                // Add more fields as needed

                // Create a Utilisateur object with retrieved data
                user = new Utilisateur(id, nom, prenom, adresse, toEmail, tel, role, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }

        return user;
    }
}
