package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryService extends Database {
    public CategoryService(){
        super();
    }

    public void addNewCategory(String categoryName){

    }

    public void editCategory(String newCategoryName){

    }

    public List<String> getAllCategories() {

        String sql = "SELECT category_name FROM category WHERE user_id=?";
        List<String> categories = new ArrayList<>();
        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            int id = Integer.parseInt(Session.getInstance().getAttribute("id"));
            pstmt.setInt(1,id);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("category_name");
                categories.add(name);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }
}