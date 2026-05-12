package com.example.csit228capstone.data;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String transactionTitle;
    private Date transactionDate;
    private String description;
    private TransactionType transactionType;
    private double amount;

    public  Transaction(String transactionTitle, String description, TransactionType transactionType, double amount){
        this.transactionTitle = transactionTitle;
        this.amount = amount;
        this.transactionDate = new Date();
        this.description = description;
        this.transactionType = transactionType;
    }

    public  Transaction(String transactionTitle, String description,Date transactionDate, TransactionType transactionType, double amount){
        this.transactionTitle = transactionTitle;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
        this.transactionType = transactionType;
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

    @Override
    public String toString() {
        return String.format("Transaction[Title: %s, Date: %s, Description: %s, Type: %s, Amount: $%.2f]",
                transactionTitle,
                transactionDate,
                description,
                transactionType,
                amount
        );
    }
}
