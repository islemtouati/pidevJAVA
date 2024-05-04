package com.LibraMarket.libra.services;

import com.LibraMarket.libra.models.Product;
import com.LibraMarket.libra.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduct implements CRUD<Product> {
    private Connection cnx;

    public ServiceProduct() {
        cnx = DBConnection.getInstance().getCnx();
    }


    @Override
    public void insertOne(Product product) throws SQLException {
        String req = "INSERT INTO `product`(`category_id`, `titre`, `prix`, `img`, `description`) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(req)) {
            preparedStatement.setInt(1, product.getCategory().getId());
            preparedStatement.setString(2, product.getTitre());
            preparedStatement.setDouble(3, product.getPrix());
            preparedStatement.setString(4, product.getImg());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("Product Added !");
        }
    }

    @Override
    public void updateOne(Product product) throws SQLException {
        String sql = "UPDATE `product` SET  titre=?, prix=?, img=?, description=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getTitre());
            preparedStatement.setDouble(2, product.getPrix());
            preparedStatement.setString(3, product.getImg());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setInt(5, product.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteOne(int id) throws SQLException {
        String sql = "DELETE FROM product WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Product> selectAll() throws SQLException {
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Product product = new Product(

                        resultSet.getInt("id"),
                        resultSet.getString("titre"),
                        resultSet.getFloat("prix"),
                        resultSet.getString("img"),
                        resultSet.getString("description"),
                        // Assuming category_id is an int column in the database
                        resultSet.getInt("category_id")
                );
                productList.add(product);
            }
        }
        return productList;
    }

    @Override
    public List<Product> selectListDerou() throws SQLException {
        return null;
    }

    public void updateField(int id, String fieldName, Object value) throws SQLException {
        String sql = "UPDATE product SET " + fieldName + " = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setObject(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<Product> getAllOeuvres() {
        List<Product> oeuvres = new ArrayList<>();
        ServiceCatego sc=new ServiceCatego();
        // SQL query to select all Oeuvres
        String sql = "SELECT * FROM product";

        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();

            // Iterate over the result set and create Oeuvre objects
            while (resultSet.next()) {
                // Retrieve data from the result set
                String titre = resultSet.getString("titre");
                double prix = resultSet.getDouble("prix");
                String description = resultSet.getString("description");
                String img = resultSet.getString("img");
                int category_id = resultSet.getInt("category_id");
                //Categorie categorie=sc.getCategorieById(idcateg);


                // Create an Oeuvre object and add it to the list
                Product oeuvre = new Product(titre, prix ,description, img, category_id);
                oeuvres.add(oeuvre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to retrieve products from the database.");
        }

        return oeuvres;
    }


}
