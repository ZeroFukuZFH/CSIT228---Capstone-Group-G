package com.example.csit228capstone.controllers.expenses;

import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.services.DashboardService;
import com.example.csit228capstone.services.ExpenseService;
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
    private DatePicker datePicker;

    @FXML
    private BarChart<String, Number> expensesBarChart;
    private XYChart.Series series;
    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private VBox expensesListView;

    private String category;
    private String search;

    List<Transaction> expenses;

    DashboardService dashboardService;
    @FXML
    public void initialize() {
        this.dashboardService = new DashboardService();
        this.datePicker.setValue(LocalDate.now());
        this.series = new XYChart.Series<>();
        this.series.setName("EXPENSES");
        // TODO: Setup category combo box items

        // TODO: Setup category combo box listener


        renderList();
        renderChart();
        search();
        filter();

    }

    @FXML
    private void onDayButtonClick() {
        // TODO: Implement day view logic
    }

    @FXML
    private void onWeekButtonClick() {
        // TODO: Implement week view logic
    }

    @FXML
    private void onMonthButtonClick() {
        // TODO: Implement month view logic
    }

    @FXML
    private void onYearButtonClick() {
        // TODO: Implement year view logic
    }

    private void filter(){
        this.categoryComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.category = newValue;
            renderList();
        });
    }

    private void search(){
        this.searchTextField.textProperty().addListener((observable ,oldVal,newVal ) -> {
            this.search = newVal;
            renderList();
        });
    }

    public void renderChart(){
        this.series.getData().clear();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy");
        for (Transaction expense : this.expenses){
            String expenseDate = simpleDateFormat.format(expense.getTransactionDate());
            this.series.getData().add(new XYChart.Data<>(expenseDate,expense.getAmount()));
        }
        this.expensesBarChart.getData().add(this.series);

    }

    public void renderList(){
        this.expenses = dashboardService.getAllTransactions();
        for (Transaction expense : expenses){
            if (expense.getTransactionType() == TransactionType.EXPENSE){
                expensesListView.getChildren().add(transactionItem(expense));
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
        amountLabel.setStyle("-fx-text-fill: black;");
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