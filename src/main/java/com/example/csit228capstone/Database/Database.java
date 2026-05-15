package com.example.csit228capstone.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    protected Connection connection;
    public Database() {
        try {
            String URL = "jdbc:mysql://localhost:3306/expense_tracker";
            String USERNAME = "root";
            String PASSWORD = "";
            this.connection = DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}