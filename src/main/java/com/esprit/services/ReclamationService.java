package com.esprit.services;

import com.esprit.models.Reclamation;
import com.esprit.utils.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
public class ReclamationService implements IService<Reclamation> {

    Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Reclamation reclamation) {
        String req = "INSERT INTO `reclamation` (`id_user`, `dateR`, `description`, `etat`) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, reclamation.getIdUser());
            ps.setDate(2, Date.valueOf(reclamation.getDateR()));  // Corrected here
            ps.setString(3, reclamation.getDescription());
            ps.setString(4, reclamation.getEtat());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                reclamation.setId(rs.getInt(1));
            }
            System.out.println("Reclamation added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Reclamation reclamation) {
        String req = "UPDATE `reclamation` SET `id_user`=?, `dateR`=?, `description`=?, `etat`=? WHERE `id`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, reclamation.getIdUser());
            ps.setDate(2, Date.valueOf(reclamation.getDateR()));
            ps.setString(3, reclamation.getDescription());
            ps.setString(4, reclamation.getEtat());
            ps.setInt(5, reclamation.getId());

            int updatedRows = ps.executeUpdate();
            if (updatedRows > 0) {
                System.out.println("Reclamation updated successfully.");
            } else {
                System.out.println("No Reclamation found with ID: " + reclamation.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating Reclamation: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `reclamation` WHERE `id` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            int deletedRows = ps.executeUpdate();
            if (deletedRows > 0) {
                System.out.println("Reclamation deleted successfully.");
            } else {
                System.out.println("No Reclamation found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Reclamation: " + e.getMessage());
        }
    }

    @Override
    public Reclamation getOneById(int id) {
        String req = "SELECT * FROM `reclamation` WHERE `id` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idUser = rs.getInt("id_user");
                LocalDate dateR = rs.getDate("dateR").toLocalDate();
                String description = rs.getString("description");
                String etat = rs.getString("etat");
                return new Reclamation(id, idUser, dateR, description, etat);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Reclamation by ID: " + e.getMessage());
        }
        return null; // Return null if Reclamation not found
    }

    @Override
    public List<Reclamation> getAll() {
        List<Reclamation> reclamations = new ArrayList<>();
        String req = "SELECT * FROM `reclamation`";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                int idUser = rs.getInt("id_user");
                LocalDate dateR = rs.getDate("dateR").toLocalDate();
                String description = rs.getString("description");
                String etat = rs.getString("etat");
                Reclamation reclamation = new Reclamation(id, idUser, dateR, description, etat);
                reclamations.add(reclamation);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Reclamations: " + e.getMessage());
        }
        return reclamations;
    }
}
