package com.example.csit228capstone.controllers.cashflow;

import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.services.DashboardService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.*;

public class CashflowController {

    @FXML
    private BarChart<String, Number> cashflowBarChart;

    @FXML
    private PieChart cashflowPieChart;

    @FXML
    private VBox cashflowListView;


    // do dli jd ko sure ani nga code, gapalaban rako ai lmao
    private XYChart.Series<String, Number> seriesIncome;
    private XYChart.Series<String, Number> seriesExpense;

    private List<Transaction> transactions;
    private DashboardService dashboardService;

    @FXML
    public void initialize() {
        this.dashboardService = new DashboardService();

        this.seriesIncome = new XYChart.Series<>();
        this.seriesIncome.setName("INCOME");

        this.seriesExpense = new XYChart.Series<>();
        this.seriesExpense.setName("EXPENSES");

        renderList();
        renderChart();
        renderPieChart();
    }

    public void renderChart() {
        this.seriesIncome.getData().clear();
        this.seriesExpense.getData().clear();
        this.cashflowBarChart.getData().clear();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");

        Map<String, Double> incomeMap = new TreeMap<>();
        Map<String, Double> expenseMap = new TreeMap<>();

        for (Transaction tx : this.transactions) {
            String txDate = simpleDateFormat.format(tx.getTransactionDate());
            txDate = txDate.replace("\"", "");

            if (tx.getTransactionType() == TransactionType.INCOME) {
                incomeMap.put(txDate, incomeMap.getOrDefault(txDate, 0.0) + tx.getAmount());
            } else if (tx.getTransactionType() == TransactionType.EXPENSE) {
                expenseMap.put(txDate, expenseMap.getOrDefault(txDate, 0.0) + tx.getAmount());
            }
        }

        Set<String> allDates = new TreeSet<>();
        allDates.addAll(incomeMap.keySet());
        allDates.addAll(expenseMap.keySet());

        for (String dateStr : allDates) {
            Double incomeAmt = incomeMap.getOrDefault(dateStr, 0.0);
            Double expenseAmt = expenseMap.getOrDefault(dateStr, 0.0);

            this.seriesIncome.getData().add(new XYChart.Data<>(dateStr, incomeAmt));
            this.seriesExpense.getData().add(new XYChart.Data<>(dateStr, expenseAmt));
        }

        this.cashflowBarChart.getData().addAll(this.seriesIncome, this.seriesExpense);
    }

    public void renderList() {
        this.cashflowListView.getChildren().clear();
        this.transactions = dashboardService.getAllTransactions();

        for (Transaction tx : transactions) {
            if (tx.getTransactionType() == TransactionType.INCOME || tx.getTransactionType() == TransactionType.EXPENSE) {
                cashflowListView.getChildren().add(transactionItem(tx));
            }
        }
    }

    private void renderPieChart() {
        double totalIncome = 0.0;
        double totalExpense = 0.0;

        for (Transaction tx : this.transactions) {
            if (tx.getTransactionType() == TransactionType.INCOME) {
                totalIncome += tx.getAmount();
            } else if (tx.getTransactionType() == TransactionType.EXPENSE) {
                totalExpense += tx.getAmount();
            }
        }

        // Pie chart
        String incomeLabel = String.format("Income: ₱%,.2f", totalIncome);
        String expenseLabel = String.format("Expenses: ₱%,.2f", totalExpense);

        PieChart.Data sliceIncome = new PieChart.Data(incomeLabel, totalIncome);
        PieChart.Data sliceExpense = new PieChart.Data(expenseLabel, totalExpense);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(sliceIncome, sliceExpense);
        this.cashflowPieChart.setData(pieChartData);


        this.cashflowPieChart.setLabelsVisible(true);
    }

    public HBox transactionItem(Transaction transaction) {
        HBox row = new HBox(10);
        int sharedWidth = 120;
        row.setPadding(new Insets(10));
        row.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");

        String cleanedTitle = transaction.getTransactionTitle().replace("\"", "");
        Label titleLabel = new Label(cleanedTitle);
        titleLabel.setStyle("-fx-text-fill: black;");
        titleLabel.setPrefWidth(sharedWidth);

        String cleanedDesc = transaction.getDescription().replace("\"", "");
        Label descLabel = new Label(cleanedDesc);
        descLabel.setStyle("-fx-text-fill: black;");
        descLabel.setPrefWidth(sharedWidth);

        Label typeLabel = new Label(transaction.getTransactionType().toString());
        typeLabel.setStyle("-fx-text-fill: black;");
        typeLabel.setPrefWidth(sharedWidth);

        //  local formatting of table rows
        String formattedAmount = String.format("₱%,.2f", transaction.getAmount());
        Label amountLabel = new Label(formattedAmount);

        if (transaction.getTransactionType() == TransactionType.INCOME) {
            amountLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else {
            amountLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
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