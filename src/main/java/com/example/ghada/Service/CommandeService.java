package com.example.ghada.Service;


import com.example.ghada.Model.Commande;
import com.example.ghada.Model.IService;

import com.example.ghada.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements IService<Commande> {
    private Connection cnx;
    private PreparedStatement prs;
    public CommandeService(){
        cnx= DataSource.getInstance().getConnection();
    }
    public void add(Commande C) {
        String requete = "insert into Commande(utilisateur_id,created_at,transporteur_name,transporteur_price,is_paid,method,reference,stipe_session_id,paypal_commande_id) values(?,?,?,?,?,?,?,?,?) ";
        try {
            prs = cnx.prepareStatement(requete);
            prs.setInt(2, C.getUtilisateur_id());
            prs.setString(4, C.getCreated_at());
            prs.setString(5, C.getTransporteur_name());
            prs.setInt(6, C.getTransporteur_price());
            prs.setInt(1,C.getIs_paid());
            prs.setString(4, C.getMethod());
            prs.setString(5, C.getReference());
            prs.setString(6, C.getStipe_session_id());
            prs.setString(1,C.getPaypal_commande_id());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Commande commande, int id) {
        String requete="update commande set utilisateur_id=?,created_at=?,transporteur_name=?,transporteur_price=?,is_paid=?,method=?,reference=?,stipe_session_id=?,paypal_commande_id=? where id_commande=?";
        {try {
            prs = cnx.prepareStatement(requete);

            prs.setInt(2, commande.getUtilisateur_id());
            prs.setString(4, commande.getCreated_at());
            prs.setString(5, commande.getTransporteur_name());
            prs.setInt(6, commande.getTransporteur_price());
            prs.setInt(1,commande.getIs_paid());
            prs.setString(4, commande.getMethod());
            prs.setString(5, commande.getReference());
            prs.setString(6, commande.getStipe_session_id());
            prs.setString(1,commande.getPaypal_commande_id());
            this.prs.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
    }

    public void delete(int id)   {
        String requete="delete from commande where id_commande=?";

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
    public List<Commande> getAll() {
        String requete="Select * from commande";
        List<Commande> list=new ArrayList<>();
        try {
            ResultSet rs=prs.executeQuery(requete);
            while (rs.next()) {
                list.add(new Commande(
                        rs.getInt("id"),
                        rs.getInt("utilisateur_id"),
                        rs.getString("created_at"),
                        rs.getString("transporteur_name"),
                        rs.getInt("transporteur_price"),
                        rs.getInt("is_paid"),
                        rs.getString("method"),
                        rs.getString("reference"),
                        rs.getString("stipe_session_id"),
                        rs.getString("paypal_commande_id")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
    public Commande getById(int id) {
        return null;
    }

}
