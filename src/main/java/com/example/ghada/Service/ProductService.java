package com.example.ghada.Service;


import com.example.ghada.Model.Commande;
import com.example.ghada.Model.IService;
import com.example.ghada.Model.Produit;
import com.example.ghada.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


    public class ProduitService implements IService<Produit> {
        private Connection cnx;
        private PreparedStatement prs;
        public ProduitService(){
            cnx= DataSource.getInstance().getConnection();
        }
        public void add(Produit Pr) {
            String requete = "insert into Produit(id,category_id,titre,prix,description,img) values(?,?,?,?,?) ";
            try {
                prs = cnx.prepareStatement(requete);
                prs.setInt(1, Pr.getId());
                prs.setInt(1, Pr.getCategory_id());
                prs.setString(2, Pr.getTitre());
                prs.setDouble(3, Pr.getPrix());
                prs.setString(4, Pr.getDescription());
                prs.setString(5,Pr.getImg());
                prs.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        public void update(Produit produit, int id) {
            String requete="update produit set category_id=?,titre=?,prix=?,description=?,img=? where id_produit=?";
            {try {
                prs = cnx.prepareStatement(requete);

                prs.setInt(1, produit.getCategory_id());
                prs.setString(2, produit.getTitre());
                prs.setDouble(3, produit.getPrix());
                prs.setString(4, produit.getDescription());
                prs.setString(5,produit.getImg());
                this.prs.executeUpdate();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            }
        }



        public void delete(int id)   {
            String requete="delete from produit where id_produit=?";

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
            System.out.println("Suppression produit effectue");
        }



        public List<Produit> getAll() {
            String requete="Select * from produit";
            List<Produit> list=new ArrayList<>();
            try {
                ResultSet rs=prs.executeQuery(requete);
                while (rs.next()) {
                    list.add(new Produit(
                            rs.getInt("id"),
                            rs.getInt("category_id"),
                            rs.getString("titre"),
                            rs.getDouble("prix"),
                            rs.getString("description"),
                            rs.getString("img")));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;

        }
        public Produit getById(int id) {
            return null;
        }


    }



