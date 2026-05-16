package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CashFlowService extends Database {

    public CashFlowService() {
        super();
    }

    // Fetch all income data grouped by date
    public Map<Date, Double> getAllIncome() {
        Map<Date, Double> incomeMap = new HashMap<>();
        String sql = """
                SELECT DATE(t.transaction_date) AS income_date, SUM(t.amount) AS total_amount
                FROM transaction t
                INNER JOIN account a ON t.account_id = a.account_id
                WHERE a.user_id = ?
                AND LOWER(t.transaction_type) = 'income'
                GROUP BY DATE(t.transaction_date)
                ORDER BY income_date ASC
                """;

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Date date = resultSet.getDate("income_date");
                    Double totalAmount = resultSet.getDouble("total_amount");

                    incomeMap.put(date, totalAmount != null ? totalAmount : 0.0);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all income: " + e.getMessage(), e);
        }
        return incomeMap;
    }

    // Fetch all expense data grouped by date
    public Map<Date, Double> getAllExpenses() {
        Map<Date, Double> expenseMap = new HashMap<>();
        String sql = """
                SELECT DATE(t.transaction_date) AS expense_date, SUM(t.amount) AS total_amount
                FROM transaction t
                INNER JOIN account a ON t.account_id = a.account_id
                WHERE a.user_id = ?
                AND LOWER(t.transaction_type) = 'expense'
                GROUP BY DATE(t.transaction_date)
                ORDER BY expense_date ASC
                """;

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    Date date = resultSet.getDate("expense_date");
                    Double totalAmount = resultSet.getDouble("total_amount");
                    // Default to 0.0 if database amount is null
                    expenseMap.put(date, totalAmount != null ? totalAmount : 0.0);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all expenses: " + e.getMessage(), e);
        }
        return expenseMap;
    }

    // Get the total sum of income
    public Double getTotalIncome() {
        String sql = """
                SELECT COALESCE(SUM(t.amount), 0.0) AS total_income
                FROM transaction t
                INNER JOIN account a ON t.account_id = a.account_id
                WHERE a.user_id = ?
                AND LOWER(t.transaction_type) = 'income'
                """;

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("total_income");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting total income: " + e.getMessage(), e);
        }
        return 0.00;
    }

    // Get the total sum of expenses
    public Double getTotalExpenses() {
        String sql = """
                SELECT COALESCE(SUM(t.amount), 0.0) AS total_expenses
                FROM transaction t
                INNER JOIN account a ON t.account_id = a.account_id
                WHERE a.user_id = ?
                AND LOWER(t.transaction_type) = 'expense'
                """;

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("total_expenses");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting total expenses: " + e.getMessage(), e);
        }
        return 0.00;
    }

    private int getCurrentUserId() {
        String id = Session.getInstance().getAttribute("id");
        if (id == null) {
            throw new IllegalStateException("No logged-in user found in session.");
        }
        return Integer.parseInt(id);
    }
}