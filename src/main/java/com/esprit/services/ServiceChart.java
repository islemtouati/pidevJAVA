package com.esprit.services;

import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.esprit.models.Reclamation;
public class ServiceChart {
    public static List<Reclamation> getReclamationsOrderByEtat() {
            List<Reclamation> reclamations = new ArrayList<>();
            Connection cnx = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                cnx = DataSource.getInstance().getConnection();

                String query = "SELECT * FROM reclamation ORDER BY etat";
                stmt = cnx.prepareStatement(query);
                rs = stmt.executeQuery();

                while (rs.next()) {
                    // Créer un objet Reclamation à partir des données de la base de données
                    Reclamation reclamation = new Reclamation();
                    reclamation.setId(rs.getInt("id"));
                    reclamation.setDescription(rs.getString("description"));
                    reclamation.setEtat(rs.getString("etat"));

                    // Ajouter la réclamation à la liste
                    reclamations.add(reclamation);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (cnx != null) {
                        cnx.close();
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            return reclamations;
        }


    }
