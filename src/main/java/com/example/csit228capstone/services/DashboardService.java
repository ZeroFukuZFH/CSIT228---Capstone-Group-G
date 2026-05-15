package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DashboardService extends Database {
    private final int defaultCategoryId;
    private final int defaultAccountId;
    private final int userId;
    public DashboardService(){
        super();

        try {
            Session.getInstance().setAttribute("default_account_id",String.valueOf(createDefaultAccount()));
            Session.getInstance().setAttribute("default_category_id",String.valueOf(createDefaultCategory()));

        } catch (SQLException e){
            System.out.println(e.getMessage());
        }

        this.defaultCategoryId = Integer.parseInt(Session.getInstance().getAttribute("default_category_id"));
        this.defaultAccountId = Integer.parseInt(Session.getInstance().getAttribute("default_account_id"));
        this.userId = Integer.parseInt(Session.getInstance().getAttribute("id"));

    }

    public String getCurrency(){
        String currecy = "PHP";
        return currecy;
    }

    public List<Transaction> getAllTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT transaction_type, amount, description, transaction_date, transaction_title FROM transaction WHERE account_id=? AND category_id=? AND user_id=?";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.defaultAccountId);
            pstmt.setInt(2, this.defaultCategoryId);
            pstmt.setInt(3, this.userId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction(
                        resultSet.getString("transaction_title"),
                        resultSet.getString("description"),
                        resultSet.getDate("transaction_date"),
                        TransactionType.valueOf(resultSet.getString("transaction_type")),
                        resultSet.getDouble("amount")
                );
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        }
        return transactions;
    }

    public Double getTotalExpenses(){
        Double expenses = 0.00;
        return expenses;
    }

    public Double getTotalBalance(){
        Double balance = 0.00;
        return balance;
    }

    public List<String> getAllCategories(){
        List<String> categories = new ArrayList<>();
        String sql = "SELECT category_name FROM category WHERE user_id = ?";

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                categories.add(rs.getString("category_name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    protected int createDefaultCategory() throws SQLException {
        int id = Integer.parseInt(Session.getInstance().getAttribute("id"));

        // First, check if category already exists
        String checkSql = "SELECT category_id FROM category WHERE user_id = ? AND category_name = 'DEFAULT' LIMIT 1";
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

    protected int createDefaultAccount() throws SQLException {
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
