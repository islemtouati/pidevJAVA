package com.esprit.services;

import com.esprit.models.Reponse;
import com.esprit.utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseService implements IService<Reponse> {

    Connection cnx = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Reponse reponse) {
        try {
            cnx.setAutoCommit(false);  // Disable auto-commit

            // Insert the new response
            String insertResponse = "INSERT INTO `reponse` (`id`, `date_rep`, `description`) VALUES (?, ?, ?)";
            try (PreparedStatement ps = cnx.prepareStatement(insertResponse)) {
                ps.setInt(1, reponse.getId());
                ps.setDate(2, new java.sql.Date(reponse.getDateRep().getTime()));
                ps.setString(3, reponse.getDescription());
                ps.executeUpdate();
            }

            String updateReclamation = "UPDATE `reclamation` SET `etat` = 'traité' WHERE `id` = ?";
            try (PreparedStatement ps = cnx.prepareStatement(updateReclamation)) {
                ps.setInt(1, reponse.getId());
                int updateCount = ps.executeUpdate();
                if (updateCount == 0) {
                    throw new SQLException("Updating reclamation failed, no rows affected.");
                }
            }

            cnx.commit();
            System.out.println("Response added and reclamation updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding response and updating reclamation: " + e.getMessage());
            try {
                cnx.rollback();
                System.out.println("Transaction is being rolled back");
            } catch (SQLException excep) {
                System.err.println("Error during transaction rollback: " + excep.getMessage());
            }
        } finally {
            try {
                cnx.setAutoCommit(true);  // Restore auto-commit mode
            } catch (SQLException ex) {
                System.err.println("Error restoring auto-commit: " + ex.getMessage());
            }
        }
    }

    @Override
    public void modifier(Reponse reponse) {
        String req = "UPDATE `reponse` SET `date_rep`=?, `description`=? WHERE `idrep`=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDate(1, new java.sql.Date(reponse.getDateRep().getTime()));
            ps.setString(2, reponse.getDescription());
            ps.setInt(3, reponse.getIdrep());
            int updateCount = ps.executeUpdate();
            if (updateCount > 0) {
                System.out.println("Response updated successfully.");
            } else {
                System.out.println("No response found with ID: " + reponse.getIdrep());
            }
        } catch (SQLException e) {
            System.out.println("Error updating response: " + e.getMessage());
        }
    }

    @Override
    public void supprimer(int idrep) {
        String req = "DELETE FROM `reponse` WHERE `idrep` = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, idrep);
            int deleteCount = ps.executeUpdate();
            if (deleteCount > 0) {
                System.out.println("Response deleted successfully.");
            } else {
                System.out.println("No response found with ID: " + idrep);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting response: " + e.getMessage());
        }
    }

    @Override
    public Reponse getOneById(int idrep) {
        String req = "SELECT * FROM `reponse` WHERE `idrep` = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, idrep);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                Date dateRep = rs.getDate("date_rep");
                String description = rs.getString("description");
                Reponse reponse = new Reponse(idrep, id, dateRep, description);
                return reponse;
            }
        } catch (SQLException e) {
            System.err.println("Error fetching response by ID: " + e.getMessage());
        }
        return null; // Return null if no response is found
    }

    @Override
    public List<Reponse> getAll() {
        List<Reponse> reponses = new ArrayList<>();
        String req = "SELECT * FROM `reponse`";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                int idrep = rs.getInt("idrep");
                int id = rs.getInt("id");
                Date dateRep = rs.getDate("date_rep");
                String description = rs.getString("description");
                Reponse reponse = new Reponse(idrep, id, dateRep, description);
                reponses.add(reponse);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching responses: " + e.getMessage());
        }
        return reponses;
    }
}
