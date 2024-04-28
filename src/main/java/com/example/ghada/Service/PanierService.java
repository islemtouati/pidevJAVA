package com.example.ghada.Service;

import com.example.ghada.Model.IService;
import com.example.ghada.Model.Panier;
import com.example.ghada.util.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PanierService implements IService<Panier> {

    private final Connection cnx;
    private PreparedStatement prs;

    public PanierService() {
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void add(Panier P) {
        String requete = "INSERT INTO Panier(id_c_id, prix_u, prix_t, quantite, titre_product, desc_product, img_product) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            prs = cnx.prepareStatement(requete);
            prs.setInt(1, P.getId_c_id());
            prs.setInt(2, P.getPrix_u());
            prs.setInt(3, P.getPrix_t());
            prs.setInt(4, P.getQuantite());
            prs.setString(5, P.getTitre_product());
            prs.setString(6, P.getDesc_product());
            prs.setString(7, P.getImg_product());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Panier panier, int id) {
        String requete = "UPDATE Panier SET id_c_id=?, prix_u=?, prix_t=?, quantite=?, titre_product=?, desc_product=?, img_product=? WHERE id=?";
        try {
            prs = cnx.prepareStatement(requete);
            prs.setInt(1, panier.getId_c_id());
            prs.setInt(2, panier.getPrix_u());
            prs.setInt(3, panier.getPrix_u() * panier.getQuantite());
            prs.setInt(4, panier.getQuantite());
            prs.setString(5, panier.getTitre_product());
            prs.setString(6, panier.getDesc_product());
            prs.setString(7, panier.getImg_product());
            prs.setInt(8, id);
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String requete = "DELETE FROM Panier WHERE id=?";
        try (PreparedStatement statement = cnx.prepareStatement(requete)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting panier failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting panier: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Panier> getAll() {
        String requete = "SELECT * FROM Panier";
        List<Panier> list = new ArrayList<>();
        try (PreparedStatement prs = cnx.prepareStatement(requete)) {
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                list.add(new Panier(
                        rs.getInt("id"),
                        rs.getInt("id_c_id"),
                        rs.getInt("prix_u"),
                        rs.getInt("prix_t"),
                        rs.getInt("quantite"),
                        rs.getString("titre_product"),
                        rs.getString("desc_product"),
                        rs.getString("img_product")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Panier getById(int id) {
        String query = "SELECT * FROM Panier WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Panier(
                        rs.getInt("id"),
                        rs.getInt("id_c_id"),
                        rs.getInt("prix_u"),
                        rs.getInt("prix_t"),
                        rs.getInt("quantite"),
                        rs.getString("titre_product"),
                        rs.getString("desc_product"),
                        rs.getString("img_product"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching panier by ID: " + e.getMessage(), e);
        }
        return null;
    }

    public ObservableList<Panier> getAllPanierItems() {
        return FXCollections.observableArrayList(getAll());
    }

    public int calculerPrixTotalParId(int id) {
        String query = "SELECT * FROM Panier WHERE id = ?";
        try (PreparedStatement stmt = cnx.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int prixU = rs.getInt("prix_u");
                int quantite = rs.getInt("quantite");
                return prixU * quantite;  // Calcule le prix total pour cet ID spécifique.
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching panier by ID: " + e.getMessage(), e);
        }
        return 0;  // Retourne 0 si l'article n'est pas trouvé.
    }

    public int calculerPrixTotalPanier() {
        List<Panier> tousLesPaniers = getAll();
        int prixTotalPanier = 0;
        for (Panier panier : tousLesPaniers) {
            prixTotalPanier += panier.getPrix_u() * panier.getQuantite();
        }
        return prixTotalPanier;
    }
}
