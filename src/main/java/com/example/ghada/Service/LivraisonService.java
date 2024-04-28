package com.example.ghada.Service;

import com.example.ghada.Model.IService;
import com.example.ghada.Model.Livraison;
import com.example.ghada.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivraisonService implements IService<Livraison> {
    private Connection cnx;
    private PreparedStatement prs;
    public LivraisonService(){
        cnx= DataSource.getInstance().getConnection();
    }
    public void add(Livraison l) {
        String requete = "insert into livraison(adresse,tel,postal_code,country,city,firstname_client,lastname_client) values(?,?,?,?,?,?,?) ";
        try {
            prs = cnx.prepareStatement(requete);
            prs.setString(1, l.getAdresse());
            prs.setInt(2, l.getTel());
            prs.setInt(3, l.getPostal_code());
            prs.setString(4, l.getCountry());
            prs.setString(5, l.getCity());
            prs.setString(6, l.getFirstname_client());
            prs.setString(7, l.getLastname_client());

            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Livraison livraison, int id) {
        String requete="update livraison set adresse=?,tel=?,postal_code?,country=?,city=?,firstname_client=?,lastname_client=?,address=? where id_livraison=?";
        {try {
            prs = cnx.prepareStatement(requete);
            prs.setString(1, livraison.getAdresse());
            prs.setInt(2, livraison.getTel());
            prs.setInt(3, livraison.getPostal_code());
            prs.setString(4, livraison.getCountry());
            prs.setString(5, livraison.getCity());
            prs.setString(6, livraison.getFirstname_client());
            prs.setString(7, livraison.getLastname_client());


            this.prs.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
    }

    public void delete(int id)   {
        String requete="delete from livraison where id_livraison=?";

        try {
            prs=cnx.prepareStatement(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prs.setInt(1,id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Suppression Livraison effectue");
    }
    public List<Livraison> getAll() {
        String requete="Select * from livraison";
        List<Livraison> list=new ArrayList<>();
        try {
            ResultSet rs=prs.executeQuery(requete);
            while (rs.next()) {
                list.add(new Livraison(
                        rs.getString("adresse"),
                        rs.getInt("Tel"),
                        rs.getInt("postal_code"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("firstname_client"),
                        rs.getString("lastname_client")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
    public Livraison getById(int id) {
        return null;
    }
}
