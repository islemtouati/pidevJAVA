package com.LibraMarket.libra.services;

import com.LibraMarket.libra.models.catego;
import com.LibraMarket.libra.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceCatego implements CRUD<catego> {
    private Connection cnx;

    public ServiceCatego() {
        cnx = DBConnection.getInstance().getCnx();
    }
    @Override
    public void insertOne(catego catego) throws SQLException {
        String req = "INSERT INTO `category`(`titre`) VALUES " +
                "('"+catego.getTitre()+"')";
        Statement st = cnx.createStatement();
        st.executeUpdate(req);
        System.out.println("catefory Added !");
    }


    @Override
    public void updateOne(catego catego) throws SQLException {
        String sql = "UPDATE category SET titre=? WHERE id=?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setString(1, catego.getTitre());
            preparedStatement.setInt(2, catego.getId());
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void deleteOne(int id) throws SQLException {
        String sql = "DELETE FROM category WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public List<catego> selectAll() throws SQLException {
        List<catego> categoListList = new ArrayList<>();
        String req = "SELECT * FROM `category` ";
        Statement st = cnx.createStatement();
        ResultSet result = st.executeQuery(req);
        while (result.next()) {
            catego ch = new catego();
                ch.setId(result.getInt("id"));
                ch.setTitre(result.getString("titre"));


            categoListList.add(ch);
        }
        return categoListList;
    }

    @Override
    public List<catego> selectListDerou() throws SQLException {
        List<catego> categoListList = new ArrayList<>();
        String req = "SELECT * FROM `category` ";
        Statement st = cnx.createStatement();
        ResultSet result = st.executeQuery(req);
        while (result.next()) {
            catego ch = new catego();
            ch.setId(result.getInt("id"));
            ch.setTitre(result.getString("titre"));

            categoListList.add(ch);
        }
        return categoListList;

    }
}
