package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.data.Account;
import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountService extends Database {
    private int defaultId;
    public AccountService() {
        super();
    }

    public void addAccount(String accountName, Double initialBalance){
        String sql = "INSERT INTO account (user_id, account_name, current_balance) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
            pstmt.setString(2, accountName);
            pstmt.setDouble(3, initialBalance == null ? 0.00 : initialBalance);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editAccount(String accountName, Double initialBalance){
        String sql = "UPDATE account SET current_balance=? WHERE user_id=? AND account_name=?";
        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setDouble(1, initialBalance == null ? 0.00 : initialBalance);
            pstmt.setInt(2, getCurrentUserId());
            pstmt.setString(3, accountName);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_name, current_balance FROM account WHERE user_id=? ORDER BY created_at DESC";

        try(PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setInt(1, getCurrentUserId());
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()){
                Account account = new Account();
                account.setAccountName(resultSet.getString("account_name"));
                account.setCurrentBalance(resultSet.getDouble("current_balance"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return accounts;
    }

    private int getCurrentUserId() {
        String id = Session.getInstance().getAttribute("id");
        if (id == null) {
            throw new IllegalStateException("No logged-in user found in session.");
        }
        return Integer.parseInt(id);
    }
}
