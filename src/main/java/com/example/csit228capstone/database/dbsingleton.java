package com.example.csit228capstone.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class dbsingleton {


    private static com.example.csit228capstone.database.dbsingleton instance;
    private Connection connection;

    private final String URL = "jdbc:mysql://localhost:3306/expense_tracker";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private dbsingleton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static synchronized com.example.csit228capstone.database.dbsingleton getInstance() {
        if (instance == null) {
            instance = new com.example.csit228capstone.database.dbsingleton();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}