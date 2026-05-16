package com.example.csit228capstone.controllers.expenses;

import com.example.csit228capstone.data.Account;
import com.example.csit228capstone.data.Category;
import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.services.AccountService;
import com.example.csit228capstone.services.CategoryService;
import com.example.csit228capstone.services.TransactionService;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class ExpensesController {

    @FXML
    private BarChart<String, Number> expensesBarChart;
    private XYChart.Series series;
    @FXML
    private ComboBox<Category> categoriesComboBox;
    @FXML
    private ComboBox<Account> accountComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private VBox expensesListView;

    private Category category;
    private Account account;
    private String search;

    private List<Transaction> expenses;

    private CategoryService categoryService;
    private TransactionService transactionService;
    private AccountService accountService;
    @FXML
    public void initialize() {
        this.transactionService = new TransactionService();
        this.accountService = new AccountService();
        this.categoryService = new CategoryService();
        this.accountComboBox.getItems().addAll(accountService.getAllAccounts());
        this.categoriesComboBox.getItems().addAll(categoryService.getAllCategories());

        this.series = new XYChart.Series<>();
        this.series.setName("EXPENSES");

        renderList();
        renderChart();
        filter();
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


    public void renderChart(){
        this.series.getData().clear();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        for (Transaction expense : this.expenses){
            if (expense.getTransactionType() == TransactionType.EXPENSE){
                String expenseDate = simpleDateFormat.format(expense.getTransactionDate());
                this.series.getData().add(new XYChart.Data<>(expenseDate,expense.getAmount()));
            }
        }
        this.expensesBarChart.getData().add(this.series);
    }

    public void renderList(){
        this.expenses = this.transactionService.getAllTransactions();
        this.expensesListView.getChildren().clear();

        for (Transaction t : expenses) {
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
                if(t.getTransactionType() == TransactionType.EXPENSE){
                    this.expensesListView.getChildren().add(transactionItem(t));
                }
            }
        }
    }
    public HBox transactionItem(Transaction transaction){
        HBox row = new HBox(10);

        int sharedWidth = 120;
        row.setPadding(new Insets(10));
        row.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");

        Label titleLabel = new Label(transaction.getTransactionTitle());
        titleLabel.setStyle("-fx-text-fill: black;");
        titleLabel.setPrefWidth(sharedWidth);

        Label descLabel = new Label(transaction.getDescription());
        descLabel.setStyle("-fx-text-fill: black;");
        descLabel.setPrefWidth(sharedWidth);

        Label typeLabel = new Label(transaction.getTransactionType().toString());
        typeLabel.setStyle("-fx-text-fill: black;");
        typeLabel.setPrefWidth(sharedWidth);

        Label amountLabel = new Label(String.valueOf(transaction.getAmount()));
        amountLabel.setStyle("-fx-text-fill: red;");
        amountLabel.setPrefWidth(sharedWidth);
        amountLabel.setAlignment(Pos.CENTER_RIGHT);

        Label dateLabel = new Label(transaction.getTransactionDate().toString());
        dateLabel.setStyle("-fx-text-fill: black;");
        dateLabel.setPrefWidth(sharedWidth);

        row.getChildren().addAll(titleLabel, descLabel, typeLabel, amountLabel, dateLabel);
        row.setSpacing(10);
        return row;
    }
}