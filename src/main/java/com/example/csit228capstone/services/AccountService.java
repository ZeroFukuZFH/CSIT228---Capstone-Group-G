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

    }

    public void editAccount(String accountName, Double initalBalance){

    }

    public List<Account> getAllAccounts(){
        List<Account> accounts = new ArrayList<>();
        return accounts;
    }
}
