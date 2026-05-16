package com.example.csit228capstone.services;

import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserService extends BaseService{
    public boolean login(String firstname ,String lastname, String password){

        String sql = "SELECT * FROM user WHERE first_name=? AND last_name =? AND password=?";
        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1,firstname);
            pstmt.setString(2,lastname);
            pstmt.setString(3,password);
            ResultSet resultSet = pstmt.executeQuery();
            boolean status = resultSet.next();
            if (status){
                int id = resultSet.getInt("user_id");
                setDefault(id);
            }
            return status;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public void register(String firstname,String lastname,String password){
        String sql = "INSERT INTO user (first_name,last_name,password) VALUES (?,?,?)";
        try(PreparedStatement pstmt = super.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1,firstname);
            pstmt.setString(2,lastname);
            pstmt.setString(3,password);

            int affectedRows = pstmt.executeUpdate();

            if (!(affectedRows > 0)) return;

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                setDefault(id);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setDefault(int currentUserId) throws SQLException {
        Session.getInstance().setAttribute("id",String.valueOf(currentUserId));
        Session.getInstance().setAttribute("default_account_id",String.valueOf(createDefaultAccount()));
        Session.getInstance().setAttribute("default_category_id",String.valueOf(createDefaultCategory()));
    }


    private int createDefaultCategory() throws SQLException {
        int id = Integer.parseInt(Session.getInstance().getAttribute("id"));

        // First, check if category already exists
        String checkSql = "SELECT category_id FROM category WHERE user_id = ? LIMIT 1";
        try (PreparedStatement checkStmt = super.connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, id);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Category already exists, return existing ID
                    int existingId = rs.getInt("category_id");
                    System.out.println("Default category already exists for user: " + id + " with ID: " + existingId);
                    return existingId;
                }
            }
        }

        // If we get here, no category exists, so create it
        String sql = "INSERT INTO category (category_name, user_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "DEFAULT");
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

            // Get the generated category ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    System.out.println("Created default category for user: " + id + " with ID: " + newId);
                    return newId;
                } else {
                    throw new SQLException("Creating category failed, no ID obtained.");
                }
            }
        }
    }

    private int createDefaultAccount() throws SQLException {
        int id = Integer.parseInt(Session.getInstance().getAttribute("id"));

        // Check if account already exists and get its ID
        String checkSql = "SELECT account_id FROM account WHERE user_id = ? LIMIT 1";
        try (PreparedStatement checkStmt = super.connection.prepareStatement(checkSql)) {
            checkStmt.setInt(1, id);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next()) {
                    // Account already exists, return existing ID
                    int existingId = rs.getInt("account_id");
                    System.out.println("Account already exists for user: " + id + " with ID: " + existingId);
                    return existingId;
                }
            }
        }

        // Create new account
        String sql = "INSERT INTO account (user_id, account_name, current_balance) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, "DEFAULT ACCOUNT");
            pstmt.setDouble(3, 0.00);
            pstmt.executeUpdate();

            // Get the generated account ID
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int newId = generatedKeys.getInt(1);
                    System.out.println("Created default account for user: " + id + " with ID: " + newId);
                    return newId;
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }
        }
    }
}
