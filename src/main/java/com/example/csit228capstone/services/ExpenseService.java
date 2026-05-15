package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExpenseService extends Database implements IExpenseService {
    public ExpenseService(){
        super();
    }

    public void addExpense(String title, String description, String accountName, String categoryName, Double amount){
        int accountId = getAccountId(accountName);
        int categoryId = getCategoryId(categoryName);

        String sql = """
                INSERT INTO transaction
                (account_id, category_id, transaction_type, amount, description, transaction_title)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, accountId);
            pstmt.setInt(2, categoryId);
            pstmt.setString(3, "EXPENSE");
            pstmt.setDouble(4, amount == null ? 0.00 : amount);
            pstmt.setString(5, description);
            pstmt.setString(6, title);
            pstmt.executeUpdate();

            updateAccountBalance(accountId, amount == null ? 0.00 : amount);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Date, Double> getExpenses(){
        Map<Date, Double> expenses = new HashMap<>();

        String sql = """
                SELECT DATE(t.transaction_date) AS expense_date, SUM(t.amount) AS total_amount
                FROM transaction t
                INNER JOIN account a ON t.account_id = a.account_id
                WHERE a.user_id = ?
                AND LOWER(t.transaction_type) = 'expense'
                GROUP BY DATE(t.transaction_date)
                ORDER BY expense_date DESC
                """;

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Date date = resultSet.getDate("expense_date");
                Double totalAmount = resultSet.getDouble("total_amount");
                expenses.put(date, totalAmount);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return expenses;
    }

    public Double getTotalExpenses(){
        String sql = """
                SELECT COALESCE(SUM(t.amount), 0) AS total_expenses
                FROM transaction t
                INNER JOIN account a ON t.account_id = a.account_id
                WHERE a.user_id = ?
                AND LOWER(t.transaction_type) = 'expense'
                """;

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("total_expenses");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0.00;
    }

    private void updateAccountBalance(int accountId, Double amount){
        String sql = "UPDATE account SET current_balance = current_balance - ? WHERE account_id=? AND user_id=?";

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setInt(2, accountId);
            pstmt.setInt(3, getCurrentUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int getAccountId(String accountName){
        String sql = "SELECT account_id FROM account WHERE account_name=? AND user_id=?";

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, accountName);
            pstmt.setInt(2, getCurrentUserId());
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("account_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalArgumentException("Account not found: " + accountName);
    }

    private int getCategoryId(String categoryName){
        String sql = "SELECT category_id FROM category WHERE category_name=? AND user_id=?";

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);
            pstmt.setInt(2, getCurrentUserId());
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("category_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalArgumentException("Category not found: " + categoryName);
    }

    private int getCurrentUserId() {
        String id = Session.getInstance().getAttribute("id");
        if (id == null) {
            throw new IllegalStateException("No logged-in user found in session.");
        }
        return Integer.parseInt(id);
    }
}