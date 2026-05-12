package com.example.csit228capstone.data;

import java.util.Date;

public class Transaction {
    private String transactionTitle;
    private Date transactionDate;
    private String description;
    private TransactionType transactionType;
    private double amount;

    public String getTransactionTitle() {
        return transactionTitle;
    }

    public void setTransactionTitle(String transactionTitle) {
        this.transactionTitle = transactionTitle;
    }

    public  Transaction(Date transactionDate, String description, TransactionType transactionType, double amount){
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
        this.transactionType = transactionType;
    }

    public  Transaction(Date transactionDate, String description, TransactionType transactionType){
        this.amount = 0.00;
        this.transactionDate = transactionDate;
        this.description = description;
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
