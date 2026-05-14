package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CategoryService extends Database {
    public CategoryService(){
        super();
    }
    public HashMap<String,String> loadCategories() {

        String sql = "SELECT category_icon, category_name FROM category WHERE user_id=?";
        HashMap<String,String> categories = new HashMap<>();
        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            int id = Integer.parseInt(Session.getInstance().getAttribute("id"));
            pstmt.setInt(1,id);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                String icon = resultSet.getString("category_icon");
                String name = resultSet.getString("category_name");
                categories.put(icon,name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return categories;
    }
}