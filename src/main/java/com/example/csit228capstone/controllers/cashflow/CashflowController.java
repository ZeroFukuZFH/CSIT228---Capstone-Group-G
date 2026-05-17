package com.example.csit228capstone.controllers.cashflow;

import com.example.csit228capstone.data.Category;
import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.services.CategoryService;
import com.example.csit228capstone.services.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.text.SimpleDateFormat;
import java.util.*;

public class CashflowController {

    @FXML
    private BarChart<String, Number> cashflowBarChart;

    @FXML
    private PieChart cashflowIncomePieChart;

    @FXML
    private PieChart cashflowExpensesPieChart;

    private XYChart.Series<String, Number> seriesIncome;
    private XYChart.Series<String, Number> seriesExpense;

    private List<Transaction> transactions;
    private List<Category> categories;

    private TransactionService transactionService;
    private CategoryService categoryService;

    private HashMap<Category, Double> incomeMap;
    private HashMap<Category, Double> expenseMap;

    @FXML
    public void initialize() {
        this.transactionService = new TransactionService();
        this.categoryService = new CategoryService();

        this.transactions = transactionService.getAllTransactions();
        this.categories = categoryService.getAllCategories();

        // Initialize HashMaps
        incomeMap = new HashMap<>();
        expenseMap = new HashMap<>();

        // Process transactions and group by category
        for (Transaction t : transactions) {
            for (Category c : categories) {
                if (t.getCategoryId() == c.getId()) {
                    if (t.getTransactionType() == TransactionType.INCOME) {
                        incomeMap.put(c, incomeMap.getOrDefault(c, 0.0) + t.getAmount());
                    } else if (t.getTransactionType() == TransactionType.EXPENSE) {
                        expenseMap.put(c, expenseMap.getOrDefault(c, 0.0) + t.getAmount());
                    }
                    break;
                }
            }
        }

        this.seriesIncome = new XYChart.Series<>();
        this.seriesIncome.setName("INCOME");

        this.seriesExpense = new XYChart.Series<>();
        this.seriesExpense.setName("EXPENSES");

        renderChart();
        renderPieChartExpenses();
        renderPieChartIncome();
    }

    public void renderChart() {
        if (transactions == null || transactions.isEmpty()) {
            return;
        }

        this.seriesIncome.getData().clear();
        this.seriesExpense.getData().clear();
        this.cashflowBarChart.getData().clear();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

        Map<String, Double> dailyIncome = new TreeMap<>();
        Map<String, Double> dailyExpense = new TreeMap<>();

        for (Transaction tx : this.transactions) {
            String txDate = simpleDateFormat.format(tx.getTransactionDate());
            txDate = txDate.replace("\"", "");
            if (tx.getTransactionType() == TransactionType.INCOME) {
                dailyIncome.put(txDate, dailyIncome.getOrDefault(txDate, 0.0) + tx.getAmount());
            } else if (tx.getTransactionType() == TransactionType.EXPENSE) {
                dailyExpense.put(txDate, dailyExpense.getOrDefault(txDate, 0.0) + tx.getAmount());
            }
        }

        Set<String> allDates = new TreeSet<>();
        allDates.addAll(dailyIncome.keySet());
        allDates.addAll(dailyExpense.keySet());

        for (String dateStr : allDates) {
            Double incomeAmt = dailyIncome.getOrDefault(dateStr, 0.0);
            Double expenseAmt = dailyExpense.getOrDefault(dateStr, 0.0);

            this.seriesIncome.getData().add(new XYChart.Data<>(dateStr, incomeAmt));
            this.seriesExpense.getData().add(new XYChart.Data<>(dateStr, expenseAmt));
        }

        this.cashflowBarChart.getData().addAll(this.seriesIncome, this.seriesExpense);
    }

    private void renderPieChartIncome() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if (incomeMap == null || incomeMap.isEmpty()) {
            pieChartData.add(new PieChart.Data("No Income Data", 1.0));
        } else {
            for (Map.Entry<Category, Double> entry : incomeMap.entrySet()) {
                if (entry.getValue() > 0) {
                    pieChartData.add(new PieChart.Data(entry.getKey().getName() + " - $" + String.format("%.2f", entry.getValue()), entry.getValue()));
                }
            }
        }

        this.cashflowIncomePieChart.setData(pieChartData);
        this.cashflowIncomePieChart.setLabelsVisible(true);
        this.cashflowIncomePieChart.setTitle("Income by Category");
    }

    private void renderPieChartExpenses() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        if (expenseMap == null || expenseMap.isEmpty()) {
            pieChartData.add(new PieChart.Data("No Expense Data", 1.0));
        } else {
            for (Map.Entry<Category, Double> entry : expenseMap.entrySet()) {
                if (entry.getValue() > 0) {
                    pieChartData.add(new PieChart.Data(entry.getKey().getName() + " - $" + String.format("%.2f", entry.getValue()), entry.getValue()));
                }
            }
        }

        this.cashflowExpensesPieChart.setData(pieChartData);
        this.cashflowExpensesPieChart.setLabelsVisible(true);
        this.cashflowExpensesPieChart.setTitle("Expenses by Category");
    }
}