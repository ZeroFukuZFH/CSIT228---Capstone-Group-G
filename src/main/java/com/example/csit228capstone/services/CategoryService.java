package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CategoryService extends Database {
    public CategoryService(){
        super();
    }
    public void loadCategories() {

        try {
            String sql = "SELECT category_id, category_name FROM categories";
            PreparedStatement pst = super.connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("category_id");
                String name = rs.getString("category_name");
                System.out.println( id + " - " + name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}