package com.example.csit228capstone.data;

import java.util.Date;

public class Account {
    private int accountId;
    private String accountName;
    private Double currentBalance;
    private Date createdAt;

    public Account(int accountId, String accountName, Double currentBalance){
        this.accountName = accountName;
        this.accountId = accountId;
        this.currentBalance = currentBalance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return accountName;
    }
}
