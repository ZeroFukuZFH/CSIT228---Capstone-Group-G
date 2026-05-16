package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryService extends Database {

    public CategoryService() {
        super();
    }


    // Inserts a new category name into the DB for the current user.
    public void addNewCategory(String categoryName) {
        String sql = "INSERT INTO category (category_name, user_id) VALUES (?, ?)";

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);
            pstmt.setInt(2, getCurrentUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Updates category name. Uses old name to find the row.
    public void editCategory(String oldCategoryName, String newCategoryName) {
        String sql = "UPDATE category SET category_name=? WHERE category_name=? AND user_id=?";

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, newCategoryName);
            pstmt.setString(2, oldCategoryName);
            pstmt.setInt(3, getCurrentUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Deletes a category by name. Checks user_id for security.
    public void deleteCategory(String categoryName) {
        String sql = "DELETE FROM category WHERE category_name=? AND user_id=?";

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);
            pstmt.setInt(2, getCurrentUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    // Returns all category names for the current user.
    public List<String> getAllCategories() {
        String sql = "SELECT category_name FROM category WHERE user_id=? ORDER BY created_at DESC";
        List<String> categories = new ArrayList<>();

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                categories.add(resultSet.getString("category_name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }


    // Gets the current logged-in user's ID from the session
    private int getCurrentUserId() {
        String id = Session.getInstance().getAttribute("id");
        if (id == null) {
            throw new IllegalStateException("No logged-in user found in session.");
        }
        return Integer.parseInt(id);
    }

    // Gets the default category ID from the session
    private int getDefaultCategoryId() {
        String id = Session.getInstance().getAttribute("default_category_id");
        if (id == null) {
            throw new IllegalStateException("No default category found in session.");
        }
        return Integer.parseInt(id);
    }
}