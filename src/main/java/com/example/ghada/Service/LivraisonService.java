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
        String requete = "insert into livraison(adresse,tel,address,postal_code,country,city,status) values(?,?,?,?,?,?,?,?) ";
        try {
            prs = cnx.prepareStatement(requete);
            prs.setInt(2, l.getTel());
            prs.setInt(4, l.getPostal_code());
            prs.setString(5, l.getCountry());
            prs.setString(6, l.getCity());
            prs.setInt(1,l.getStatus());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Livraison livraison, int id) {
        String requete="update livraison set adresse=?,tel=?,address=?,postal_code?,country=?,city=? where id_livraison=?";
        {try {
            prs = cnx.prepareStatement(requete);

            prs.setInt(2, livraison.getTel());

            prs.setInt(4, livraison.getPostal_code());
            prs.setString(5, livraison.getCountry());
            prs.setString(6, livraison.getCity());
            prs.setInt(0,livraison.getStatus());
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
                        rs.getInt("tel"),
                        rs.getString("address"),

                        rs.getInt("postal_code"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getInt("status")));
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
