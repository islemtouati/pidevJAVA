package com.example.ghada.Service;


import com.example.ghada.Model.Command;
import com.example.ghada.Model.IService;

import com.example.ghada.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CommandService implements IService<Command> {
    private Connection cnx;
    private PreparedStatement prs;
    public CommandService(){
        cnx= DataSource.getInstance().getConnection();
    }
    public void add(Command C) {
        String requete = "insert into Commande( nom_utilisateur,prenom_utilisateur,adresse_utilisateur,prix_total,Tel_utilis,pays_utilis,mode_p) values(?,?,?,?,?,?,?) ";
        try {
            prs = cnx.prepareStatement(requete);
            prs.setString(1, C.getNom_utilisateur());
            prs.setString(2, C.getPrenom_utilisateur());
            prs.setString(3, C.getAdresse_utilisateur());
            prs.setInt(4, C.getPrix_total());
            prs.setInt(5,C.getTel_utilis());
            prs.setString(6,C.getPays_utilis());
            prs.setString(7, C.getMode_p());
            prs.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Command commande, int id) {
        String requete="update commande set  nom_utilisateur=?,prenom_utilisateur=?,adresse_utilisateur=?,prix_total=?,Tel_utilis=?,pays_utilis=?,mode_p=? where id_command=?";
        {try {
            prs = cnx.prepareStatement(requete);

            prs.setString(1, commande.getNom_utilisateur());
            prs.setString(2, commande.getPrenom_utilisateur());
            prs.setString(3, commande.getAdresse_utilisateur());
            prs.setInt(4, commande.getPrix_total());
            prs.setInt(5,commande.getTel_utilis());
            prs.setString(6,commande.getPays_utilis());
            prs.setString(7, commande.getMode_p());
            this.prs.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        }
    }

    public void delete(int id)   {
        String requete="delete from commande where id_command=?";

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
    public List<Command> getAll() {
        String requete="Select * from commande";
        List<Command> list=new ArrayList<>();
        try {
            ResultSet rs=prs.executeQuery(requete);
            while (rs.next()) {
                list.add(new Command(
                        rs.getString("nom_utilistaeur"),
                        rs.getString("prenom_utilisateur"),
                        rs.getString("Addresse_utilisateur"),
                        rs.getInt("prix_totale"),
                        rs.getInt("tel_utilis"),
                        rs.getString("pays_utilis"),
                        rs.getString("mose_p")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
    public Command getById(int id) {
        return null;
    }

}
