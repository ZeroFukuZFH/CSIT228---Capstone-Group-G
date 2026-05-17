package com.example.csit228capstone.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private int transactionId;
    private int categoryId;
    private int accountId;

    private String transactionTitle;
    private Date transactionDate;
    private String description;
    private TransactionType transactionType;
    private double amount;

    public Transaction(int accountId,int categoryId, String transactionTitle, String description, TransactionType transactionType, double amount) {
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.transactionTitle = transactionTitle;
        this.amount = amount;
        this.transactionDate = new Date();
        this.description = description;
        this.transactionType = transactionType;
    }

    public Transaction(int accountId,int categoryId, String transactionTitle, String description, Date transactionDate, TransactionType transactionType, double amount) {
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.transactionTitle = transactionTitle;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
        this.transactionType = transactionType;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTransactionTitle() {
        return transactionTitle;
    }

    public void setTransactionTitle(String transactionTitle) {
        this.transactionTitle = transactionTitle;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return String.format("Transaction[ID: %d, Title: %s, Date: %s, Description: %s, Type: %s, Amount: $%.2f]",
                categoryId,
                transactionTitle,
                transactionDate,
                description,
                transactionType,
                amount
        );
    }
}