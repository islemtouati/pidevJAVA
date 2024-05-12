package com.LibraMarket.libra.services;

import com.LibraMarket.libra.models.Product;
import com.LibraMarket.libra.models.catego;
import com.LibraMarket.libra.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.sql.DriverManager.getConnection;

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
    public List<Product> searchByTitre(String titre) throws SQLException {
        List<Product> oeuvres = new ArrayList<>();
        String query = "SELECT * FROM product WHERE titre LIKE ?";

        try (Connection connection = DBConnection.getInstance().getCnx();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,  titre + "%"); // Search for titre containing the given string

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Create Product objects from the result set and add them to the list
                    Product oeuvre = new Product(
                            resultSet.getInt("id"),
                            resultSet.getString("titre"),
                            resultSet.getDouble("prix"),
                            resultSet.getString("img"),
                            resultSet.getString("description"),
                            resultSet.getInt("category_id")
                    );
                    oeuvres.add(oeuvre);
                }
            }
        }

        return oeuvres;
    }

    public List<Product> getAll() {
        String requete = "Select * from product";
        List<Product> list = new ArrayList<>();
        try (PreparedStatement prs = cnx.prepareStatement(requete)) {
            ResultSet rs = prs.executeQuery(requete);
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getDouble("prix"),
                        rs.getString("img"),
                        rs.getString("description"),
                        rs.getInt("category_id")));




            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;

    }
    public List<Product> rechercherProduit(String searchTerm) throws SQLException {
        List<Product> searchResults = new ArrayList<>();
        String query = "SELECT id, titre, description, img, prix FROM product WHERE titre LIKE ? OR description LIKE ? OR prix LIKE ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            String searchPattern = "%" + searchTerm + "%";
            preparedStatement.setString(1, searchPattern);
            preparedStatement.setString(2, searchPattern);
            preparedStatement.setString(3, searchPattern);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt("id"));
                    product.setTitre(resultSet.getString("titre"));
                    product.setDescription(resultSet.getString("description"));
                    product.setImg(resultSet.getString("img")); // Set the image URL
                    product.setPrix(resultSet.getDouble("prix")); // Set the date
                    searchResults.add(product);
                }
            }
        }
        return searchResults;
    }
}
