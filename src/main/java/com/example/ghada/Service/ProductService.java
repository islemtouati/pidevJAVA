package com.example.ghada.Service;


import com.example.ghada.Model.IService;
import com.example.ghada.Model.Product;
import com.example.ghada.util.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


    public class ProductService implements IService<Product> {
        private Connection cnx;
        private PreparedStatement prs;
        public ProductService(){
            cnx= DataSource.getInstance().getConnection();
        }
        public void add(Product Pr) {
            String requete = "insert into product(id,category_id,titre,prix,description,img) values(?,?,?,?,?) ";
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
        public void update(Product product, int id) {
            String requete="update product set category_id=?,titre=?,prix=?,description=?,img=? where id_product=?";
            {try {
                prs = cnx.prepareStatement(requete);

                prs.setInt(1, product.getCategory_id());
                prs.setString(2, product.getTitre());
                prs.setDouble(3, product.getPrix());
                prs.setString(4, product.getDescription());
                prs.setString(5, product.getImg());
                this.prs.executeUpdate();
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
            }
        }

        public void delete(int id)   {
            String requete="delete from product where id_product=?";

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



        public List<Product> getAll() {
            String requete="Select * from product";
            List<Product> list=new ArrayList<>();
            try (PreparedStatement prs = cnx.prepareStatement(requete)){
                ResultSet rs=prs.executeQuery(requete);
                while (rs.next()) {
                    list.add(new Product(
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
        public Product getById(int id) {
            String requete = "SELECT * FROM product WHERE id = ?";
            try {
                prs = cnx.prepareStatement(requete);
                prs.setInt(1, id);
                ResultSet rs = prs.executeQuery();
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id"),
                            rs.getInt("category_id"),
                            rs.getString("titre"),
                            rs.getDouble("prix"),
                            rs.getString("description"),
                            rs.getString("img"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;

        }


    }



