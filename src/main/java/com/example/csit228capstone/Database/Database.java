package com.example.csit228capstone.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {

    private static Database instance;
    private Connection connection;

    private final String URL =
            "jdbc:mysql://localhost:3306/expense_tracker";

    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private Database() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );

            System.out.println("Database Connected!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Database getInstance() {

        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}