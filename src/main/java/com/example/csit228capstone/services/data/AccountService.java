package com.example.csit228capstone.services.data;

import com.example.csit228capstone.data.Account;
import com.example.csit228capstone.session.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccountService extends BaseService {
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

    public void editAccount(String oldAccountName, String newAccountName, Double newBalance){
        String sql = "UPDATE account SET account_name = ?, current_balance = ? WHERE user_id = ? AND account_name = ?";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, newAccountName);
            pstmt.setDouble(2, newBalance == null ? 0.00 : newBalance);
            pstmt.setInt(3, getCurrentUserId());
            pstmt.setString(4, oldAccountName);
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
    public void deleteAccount(String accountName) {
        String sql = "DELETE FROM account WHERE account_name = ? AND user_id = ?";
        try (PreparedStatement pstmt = super.connection.prepareStatement(sql)) {
            pstmt.setString(1, accountName);
            pstmt.setInt(2,getCurrentUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
