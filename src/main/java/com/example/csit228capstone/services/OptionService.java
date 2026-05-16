package com.example.csit228capstone.services;

import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionService extends BaseService {

    public OptionService(){
        super();

    }
    public void importCsv(List<Transaction> transactions) {
        String sql = "INSERT INTO transaction (account_id, category_id, transaction_type, amount, description, transaction_date, transaction_title, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            // Start transaction
            super.connection.setAutoCommit(false);

            try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
                for (Transaction t : transactions) {
                    pstmt.setInt(1, super.getDefaultAccountId());
                    pstmt.setInt(2, super.getDefaultCategoryId());

                    switch (t.getTransactionType()) {
                        case EXPENSE -> pstmt.setString(3, "EXPENSE");
                        case INCOME -> pstmt.setString(3, "INCOME");
                    }

                    pstmt.setBigDecimal(4, BigDecimal.valueOf(t.getAmount()));
                    pstmt.setString(5, t.getDescription());
                    java.util.Date utilDate = t.getTransactionDate();
                    pstmt.setDate(6, new java.sql.Date(utilDate.getTime()));
                    pstmt.setString(7, t.getTransactionTitle());
                    pstmt.setInt(8, super.getCurrentUserId());
                    pstmt.addBatch();

                    // Update account balance based on transaction type
                    String updateBalanceSql;
                    if (t.getTransactionType() == TransactionType.INCOME) {
                        updateBalanceSql = "UPDATE account SET current_balance = current_balance + ? WHERE account_id = ?";
                    } else {
                        updateBalanceSql = "UPDATE account SET current_balance = current_balance - ? WHERE account_id = ?";
                    }

                    try (PreparedStatement balanceStmt = super.connection.prepareStatement(updateBalanceSql)) {
                        balanceStmt.setDouble(1, t.getAmount());
                        balanceStmt.setInt(2, super.getDefaultAccountId());
                        balanceStmt.executeUpdate();
                        System.out.println("Account balance updated for transaction type: " + t.getTransactionType());
                    }
                }

                pstmt.executeBatch();

                // Commit transaction
                super.connection.commit();
                System.out.println("Successfully imported " + transactions.size() + " transactions");

            } catch (SQLException e) {
                // Rollback if anything fails
                super.connection.rollback();
                System.err.println("SQL Error: " + e.getMessage());
                throw e;
            } finally {
                // Reset auto-commit
                super.connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    public List<Transaction> exportCsv(){
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT transaction_type, amount, description, transaction_date, transaction_title FROM transaction WHERE account_id=? AND category_id=? AND user_id=?";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, super.getDefaultAccountId());
            pstmt.setInt(2, super.getDefaultCategoryId());
            pstmt.setInt(3, super.getCurrentUserId());
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()){
                Transaction transaction = new Transaction(
                        getDefaultAccountId(),
                        getDefaultCategoryId(),
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
}