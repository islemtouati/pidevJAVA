package com.example.javafxx.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static DB instance ;
    private static final String URL ="jdbc:mysql://127.0.0.1:3306/libraaaaa";
    private static final String USERNAME="root";
    private static final String PASSWORD ="";


    Connection cnx ;

    private DB(){

        try {
            cnx = DriverManager.getConnection(URL,USERNAME,PASSWORD);

            System.out.println("Connected ...");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("____not connected____ ");

        }

    }

    public static DB getInstance(){
        if (instance == null)
            instance = new DB();

        return instance;
    }

    public Connection getCnx(){
        return cnx;
    }


}
