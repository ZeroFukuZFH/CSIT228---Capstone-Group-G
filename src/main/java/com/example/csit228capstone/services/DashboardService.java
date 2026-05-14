package com.example.csit228capstone.services;

import com.example.csit228capstone.Database.Database;
import com.example.csit228capstone.data.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DashboardService extends Database {
    public DashboardService(){
        super();
    }

    public String getCurrency(){
        String currecy = "";
        return currecy;
    }

    public List<Transaction> getAllTransactions(){
        List<Transaction> transactions = new ArrayList<>();
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
}
