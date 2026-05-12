package com.example.csit228capstone.data;

public class AccountStored {

    private String accountName;
    private String currency;
    private double balance;
    private boolean isDefault;

    public AccountStored(String accountName, String currency, double balance, boolean isDefault) {
        this.accountName = accountName;
        this.currency = currency;
        this.balance = balance;
        this.isDefault = isDefault;
    }

    public String getAccountName() { return accountName; }
    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public boolean isDefault() { return isDefault; }
    public void setDefault(boolean isDefault) { this.isDefault = isDefault; }
}