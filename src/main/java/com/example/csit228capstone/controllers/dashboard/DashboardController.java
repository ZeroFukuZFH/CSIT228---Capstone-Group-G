package com.example.csit228capstone.controllers.dashboard;

import com.example.csit228capstone.data.Account;
import com.example.csit228capstone.data.Category;
import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.services.AccountService;
import com.example.csit228capstone.services.CategoryService;
import com.example.csit228capstone.services.TransactionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class DashboardController {

    @FXML
    Label totalExpenses;
    @FXML
    Label totalIncome;
    @FXML
    Label incomeCurrency;
    @FXML
    Label expenseCurrency;
    @FXML
    Label currentDate;
    @FXML
    ScrollPane scrollPane;
    @FXML
    VBox contentBox;
    @FXML
    ComboBox<Account> accountComboBox;
    @FXML
    ComboBox<Category> categoriesComboBox;
    @FXML
    TextField searchTextField;
    String search;
    Category category;
    Account account;
    List<Transaction> transactions;
    List<Category> categories;
    List<Account> accounts;
    CategoryService categoryService;
    TransactionService transactionService;
    AccountService accountService;

    @FXML
    public void initialize(){
        this.transactionService = new TransactionService();
        this.categoryService = new CategoryService();
        this.accountService = new AccountService();

        this.accounts = this.accountService.getAllAccounts();
        this.categories = this.categoryService.getAllCategories();

        this.currentDate.setText(new Date().toString());
        this.incomeCurrency.setText(transactionService.getCurrency());
        this.expenseCurrency.setText(transactionService.getCurrency());

        this.contentBox.setPadding(new Insets(10));
        this.contentBox.setSpacing(10);

        this.accountComboBox.getItems().addAll(accounts);
        this.categoriesComboBox.getItems().addAll(categories);

        this.accountComboBox.getItems().add(0, null);
        this.accountComboBox.setValue(null);
        this.categoriesComboBox.getItems().add(0, null);
        this.categoriesComboBox.setValue(null);

        loadTransactions();
        filter();
        renderList();
    }

    private void loadTransactions() {
        this.transactions = this.transactionService.getAllTransactions();
        System.out.println("Loaded " + transactions.size() + " transactions");

        if (transactions.isEmpty()) {
            Label noDataLabel = new Label("No transactions found");
            noDataLabel.setStyle("-fx-text-fill: #666; -fx-font-size: 14px;");
            contentBox.getChildren().add(noDataLabel);
        }

        this.totalExpenses.setText(getTotalExpenses().toString());
        this.totalIncome.setText(getTotalIncome().toString());
    }

    @FXML
    private void onAddTransactionClick(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csit228capstone/screens/NewTransactionDialogue.fxml"));
        try {
            Parent root = loader.load();
            Stage smallStage = new Stage();
            smallStage.setTitle("Add New Transaction");
            smallStage.setWidth(400);
            smallStage.setHeight(500);
            smallStage.setResizable(false);

            Scene scene = new Scene(root);
            smallStage.setScene(scene);
            smallStage.showAndWait();

            renderList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void filter(){
        this.accountComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.account = newValue;
            renderList();
        });

        this.categoriesComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.category = newValue;
            renderList();
        });

        this.searchTextField.textProperty().addListener((observable ,oldVal,newVal ) -> {
            this.search = newVal;
            renderList();
        });
    }

    public void renderList(){
        this.transactions = this.transactionService.getAllTransactions();
        this.contentBox.getChildren().clear();

        for (Transaction t : transactions) {
            boolean matchesSearch = true;
            boolean matchesCategory = true;
            boolean matchesAccount = true;

            if (search != null && !search.isEmpty()) {
                matchesSearch = t.getTransactionTitle().toLowerCase().contains(search.toLowerCase());
            }

            if (account != null) {
                matchesAccount = t.getAccountId() == account.getAccountId();
            }

            if (category != null) {
                matchesCategory = t.getCategoryId() == category.getId();
            }

            if (matchesSearch && matchesCategory && matchesAccount) {
                this.contentBox.getChildren().add(transactionItem(t));
            }
        }
    }
    public HBox transactionItem(Transaction transaction){
        HBox row = new HBox(10);
        row.setPadding(new Insets(10));
        row.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");
        row.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(transaction.getTransactionTitle());
        titleLabel.setStyle("-fx-text-fill: black;");
        titleLabel.setPrefWidth(150);

        Label descLabel = new Label(transaction.getDescription());
        descLabel.setStyle("-fx-text-fill: black;");
        descLabel.setPrefWidth(200);

        Label typeLabel = new Label(transaction.getTransactionType().toString());
        typeLabel.setStyle("-fx-text-fill: black;");
        typeLabel.setPrefWidth(100);

        Label amountLabel = new Label(String.format("$%.2f", transaction.getAmount()));
        if (transaction.getTransactionType() == TransactionType.EXPENSE) {
            amountLabel.setStyle("-fx-text-fill: #dc3545; -fx-font-weight: bold;");
        } else {
            amountLabel.setStyle("-fx-text-fill: #28a745; -fx-font-weight: bold;");
        }
        amountLabel.setPrefWidth(120);
        amountLabel.setAlignment(Pos.CENTER_RIGHT);

        Label dateLabel = new Label(transaction.getTransactionDate().toString());
        dateLabel.setStyle("-fx-text-fill: black;");
        dateLabel.setPrefWidth(120);

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-background-radius: 5; -fx-cursor: hand;");
        deleteButton.setOnAction(event -> {
            deleteTransaction(transaction);
        });

        row.getChildren().addAll(titleLabel, descLabel, typeLabel, amountLabel, dateLabel, deleteButton);
        row.setSpacing(10);
        return row;
    }

    private void deleteTransaction(Transaction transaction) {
        transactionService.deleteTransaction(transaction.getTransactionId());
        renderList();
    }

    private Double getTotalExpenses(){
        double total = 0.00;
        for(Transaction transaction : this.transactions){
            if(transaction.getTransactionType() == TransactionType.EXPENSE){
                total += transaction.getAmount();
            }
        }
        return total;
    }

    private Double getTotalIncome(){
        double total = 0.00;
        for(Transaction transaction : this.transactions){
            if(transaction.getTransactionType() == TransactionType.INCOME){
                total += transaction.getAmount();
            }
        }
        return total;
    }
}