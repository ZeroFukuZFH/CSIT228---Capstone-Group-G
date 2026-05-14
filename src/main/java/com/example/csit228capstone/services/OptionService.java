package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.session.Session;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OptionService extends Database {
    private int defaultCategoryId;
    private int defaultAccountId;
    public OptionService(){
        super();

        this.defaultAccountId = Integer.parseInt(Session.getInstance().getAttribute("id")); // CHANGE TO ACCOUNT ID

        PreparedStatement pstmt;
        String sql = "SELECT category_id FROM category WHERE user_id=?";
        try {
            pstmt = super.connection.prepareStatement(sql);
            pstmt.setInt(1,this.defaultAccountId);
            ResultSet resultSet = pstmt.executeQuery();
            Boolean result = resultSet.next();
            this.defaultCategoryId = resultSet.getInt("category_id");

            if (result) return;

            sql = "INSERT INTO category (category_name,user_id) VALUES (?,?)";
            pstmt = super.connection.prepareStatement(sql);
            pstmt.setString(1,"DEFAULT");
            pstmt.setInt(2,this.defaultAccountId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void importCsv(List<Transaction> transactions) {
        String sql = "INSERT INTO transaction (account_id, category_id, transaction_type, amount, description, transaction_date, transaction_title) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            for (Transaction t : transactions) {
                pstmt.setInt(1, this.defaultAccountId);
                pstmt.setInt(2, this.defaultCategoryId);
                switch (t.getTransactionType()) {
                    case INCOME -> pstmt.setString(3, "INCOME");
                    case EXPENSE -> pstmt.setString(3, "EXPENSE");
                }
                pstmt.setBigDecimal(4, BigDecimal.valueOf(t.getAmount()));
                pstmt.setString(5, t.getDescription());
                java.util.Date utilDate = t.getTransactionDate();
                pstmt.setDate(6, new java.sql.Date(utilDate.getTime()));
                pstmt.setString(7, t.getTransactionTitle());
                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to import " + transactions.size() + " transactions", e);
        }
    }
}
