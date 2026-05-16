package com.example.csit228capstone.services.data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService extends BaseService{

    public List<String> getAllCategories() {
        String sql = "SELECT category_name FROM category WHERE user_id=? ORDER BY created_at DESC";
        List<String> categories = new ArrayList<>();

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
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

    public void addNewCategory(String categoryName){
        String sql = "INSERT INTO category (category_name, user_id) VALUES (?, ?)";

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);
            pstmt.setInt(2, getCurrentUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editCategory(String oldCategoryName, String newCategoryName){
        String sql = "UPDATE category SET category_name=? WHERE category_name=? AND user_id=?";

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, newCategoryName);
            pstmt.setString(2, oldCategoryName);
            pstmt.setInt(3, getCurrentUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCategory(String categoryName){
        String sql = "DELETE FROM category WHERE category_name=? AND user_id=?";

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);
            pstmt.setInt(2, getCurrentUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
