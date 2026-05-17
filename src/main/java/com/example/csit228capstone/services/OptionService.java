package com.example.csit228capstone.services;

import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.*;
import java.util.Collections;

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



    public List<Transaction> exportCsv() {
        List<Transaction> transactions = Collections.synchronizedList(new ArrayList<>());
        String sql = "SELECT transaction_type, amount, description, transaction_date, transaction_title FROM transaction WHERE account_id=? AND category_id=? AND user_id=?";

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, super.getDefaultAccountId());
            pstmt.setInt(2, super.getDefaultCategoryId());
            pstmt.setInt(3, super.getCurrentUserId());

            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                final int accountId = super.getDefaultAccountId();
                final int categoryId = super.getDefaultCategoryId();
                final String title = resultSet.getString("transaction_title");
                final String description = resultSet.getString("description");
                final Date date = resultSet.getDate("transaction_date");
                final TransactionType type = TransactionType.valueOf(resultSet.getString("transaction_type"));
                final double amount = resultSet.getDouble("amount");

                CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                    Transaction transaction = new Transaction(accountId, categoryId, title, description, date, type, amount);
                    transactions.add(transaction);
                }, executor);

                futures.add(future);
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }

        return transactions;
    }
}