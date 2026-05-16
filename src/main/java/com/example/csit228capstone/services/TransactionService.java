package com.example.csit228capstone.services;

import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionService extends BaseService{
    public List<Transaction> getAllTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT account_id,category_id,transaction_type, amount, description, transaction_date, transaction_title FROM transaction WHERE user_id=?";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {

            pstmt.setInt(1, super.getCurrentUserId());
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                Transaction transaction = new Transaction(
                        resultSet.getInt("account_id"),
                        resultSet.getInt("category_id"),
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

    public void addTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (account_id, category_id, user_id, transaction_type, amount, description, transaction_date, transaction_title) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, transaction.getAccountId());
            pstmt.setInt(2, transaction.getCategoryId());
            pstmt.setInt(3, super.getCurrentUserId());
            pstmt.setString(4, transaction.getTransactionType().toString());
            pstmt.setDouble(5, transaction.getAmount());
            pstmt.setString(6, transaction.getDescription());
            pstmt.setDate(7, new Date(transaction.getTransactionDate().getTime()));
            pstmt.setString(8, transaction.getTransactionTitle());

            pstmt.executeUpdate();

            String updateBalanceSql = "";
            switch(transaction.getTransactionType()){
                case EXPENSE -> updateBalanceSql = "UPDATE account SET current_balance = current_balance + ? WHERE account_id = ?";
                case INCOME -> updateBalanceSql = "UPDATE account SET current_balance = current_balance - ? WHERE account_id = ?";
            }

            try (PreparedStatement balanceStmt = super.connection.prepareStatement(updateBalanceSql)) {
                balanceStmt.setDouble(1, transaction.getAmount());
                balanceStmt.setInt(2, transaction.getAccountId());
                balanceStmt.executeUpdate();
                System.out.println("Account balance updated for transaction type: " + transaction.getTransactionType());
            }
        } catch (SQLException e) {
            System.err.println("Error adding transaction: " + e.getMessage());
        }
    }

    public void deleteTransaction(int transactionId) {
        String sql = "DELETE FROM transaction WHERE transaction_id=? AND user_id=?";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            pstmt.setInt(2, super.getCurrentUserId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Transaction deleted successfully");
            } else {
                System.out.println("No transaction found with ID: " + transactionId);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting transaction: " + e.getMessage());
        }
    }
}
