package com.webandit.libra.services;

import com.webandit.libra.models.Product;
import com.webandit.libra.utils.DBConnection;

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
}
