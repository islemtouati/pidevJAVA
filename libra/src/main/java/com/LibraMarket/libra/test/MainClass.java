package com.LibraMarket.libra.test;

import com.LibraMarket.libra.models.catego;
import com.LibraMarket.libra.services.ServiceCatego;
import com.LibraMarket.libra.utils.DBConnection;

import java.sql.Date;
import java.sql.SQLException;

public class MainClass {

    public static void main(String[] args) {
        DBConnection cn1 = DBConnection.getInstance();

        // Convert the string date to a Date object
        Date date = Date.valueOf("2024-05-01");

        catego p = new catego("Ben Daoued");

        ServiceCatego sp = new ServiceCatego();

        try {
            sp.insertOne(p);
           // System.out.println(sp.selectAll());
        } catch (SQLException e) {
            System.err.println("Erreur: " + e.getMessage());
        }
    }
}
