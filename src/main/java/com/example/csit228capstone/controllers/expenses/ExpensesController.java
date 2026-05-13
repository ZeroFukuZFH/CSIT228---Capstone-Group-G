package com.example.csit228capstone.controllers.expenses;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ExpensesController implements Initializable {

    // ── Chart ──────────────────────────────────────────────────
    @FXML private BarChart<String, Number> expensesBarChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Button selectedDateBtn;

    // ── Period Toggle ───────────────────────────────────────────
    @FXML private ToggleGroup periodToggleGroup;
    @FXML private ToggleButton dayToggle;
    @FXML private ToggleButton monthToggle;
    @FXML private ToggleButton yearToggle;

    // ── Expenses List ───────────────────────────────────────────
    @FXML private Button sortBtn;
    @FXML private TextField searchField;
    @FXML private Button addBtn;
    @FXML private ScrollPane transactionScrollPane;
    @FXML private VBox transactionListVBox;

    // ── Pagination ──────────────────────────────────────────────
    @FXML private Button prevPageBtn;
    @FXML private Button nextPageBtn;
    @FXML private Label pageLabel;

    // ── State ───────────────────────────────────────────────────
    private int currentPage = 1;
    private int totalPages = 1;
    private String currentPeriod = "day";
    private String currentSort = "Date";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupChart();
        setupToggleListeners();
        setupSearchListener();
        updatePageLabel();
    }

    // ── Chart Setup ─────────────────────────────────────────────

    private void setupChart() {
        loadChartData("day");
        styleChart();
    }

    private void loadChartData(String period) {
        expensesBarChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        switch (period) {
            case "day":
                selectedDateBtn.setText("This Week");
                String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
                double[] dayValues = {110, 190, 150, 70, 60, 105, 115};
                for (int i = 0; i < days.length; i++) {
                    series.getData().add(new XYChart.Data<>(days[i], dayValues[i]));
                }
                break;

            case "month":
                selectedDateBtn.setText("This Year");
                String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
                double[] monthValues = {200, 150, 180, 130, 170, 90, 110, 160, 140, 120, 200, 175};
                for (int i = 0; i < months.length; i++) {
                    series.getData().add(new XYChart.Data<>(months[i], monthValues[i]));
                }
                break;

            case "year":
                selectedDateBtn.setText("Last 5 Years");
                String[] years = {"2021", "2022", "2023", "2024", "2025"};
                double[] yearValues = {1200, 1500, 1350, 1700, 1600};
                for (int i = 0; i < years.length; i++) {
                    series.getData().add(new XYChart.Data<>(years[i], yearValues[i]));
                }
                break;
        }

        expensesBarChart.getData().add(series);

        // Style bars after data is added
        for (XYChart.Data<String, Number> data : series.getData()) {
            if (data.getNode() != null) {
                data.getNode().setStyle("-fx-bar-fill: #7b6cf6;");
            }
        }

        series.getData().forEach(data ->
                data.getNode().setStyle("-fx-bar-fill: #7b6cf6; -fx-background-radius: 4 4 0 0;")
        );
    }

    private void styleChart() {
        expensesBarChart.setStyle("-fx-background-color: transparent;");
        expensesBarChart.lookup(".chart-plot-background")
                .setStyle("-fx-background-color: transparent;");
    }

    // ── Toggle Listeners ─────────────────────────────────────────

    private void setupToggleListeners() {
        periodToggleGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (newToggle == null) {
                oldToggle.setSelected(true); // prevent deselection
                return;
            }
            resetToggleStyles();
            String selected = ((ToggleButton) newToggle).getText();
            currentPeriod = selected;
            applyActiveToggleStyle((ToggleButton) newToggle);
            loadChartData(selected);
        });
    }

    private void resetToggleStyles() {
        dayToggle.setStyle(
                "-fx-background-color: white; -fx-text-fill: #333; -fx-font-size: 13px;" +
                        "-fx-padding: 6 18 6 18; -fx-background-radius: 6 0 0 6;" +
                        "-fx-border-radius: 6 0 0 6; -fx-border-color: #cccccc; -fx-cursor: hand;"
        );
        monthToggle.setStyle(
                "-fx-background-color: white; -fx-text-fill: #333; -fx-font-size: 13px;" +
                        "-fx-padding: 6 18 6 18; -fx-background-radius: 0; -fx-border-radius: 0;" +
                        "-fx-border-color: #cccccc; -fx-border-width: 0 1 0 1; -fx-cursor: hand;"
        );
        yearToggle.setStyle(
                "-fx-background-color: white; -fx-text-fill: #333; -fx-font-size: 13px;" +
                        "-fx-padding: 6 18 6 18; -fx-background-radius: 0 6 6 0;" +
                        "-fx-border-radius: 0 6 6 0; -fx-border-color: #cccccc; -fx-border-width: 1; -fx-cursor: hand;"
        );
    }

    private void applyActiveToggleStyle(ToggleButton btn) {
        String baseActive =
                "-fx-background-color: #2d6a4f; -fx-text-fill: white; -fx-font-size: 13px;" +
                        "-fx-padding: 6 18 6 18; -fx-cursor: hand;";
        if (btn == dayToggle) {
            btn.setStyle(baseActive + "-fx-background-radius: 6 0 0 6; -fx-border-radius: 6 0 0 6;");
        } else if (btn == monthToggle) {
            btn.setStyle(baseActive + "-fx-background-radius: 0; -fx-border-radius: 0;");
        } else {
            btn.setStyle(baseActive + "-fx-background-radius: 0 6 6 0; -fx-border-radius: 0 6 6 0;");
        }
    }

    // ── Search ───────────────────────────────────────────────────

    private void setupSearchListener() {
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            // TODO: filter transaction list based on newVal
            filterTransactions(newVal);
        });
    }

    private void filterTransactions(String query) {
        // Implement filtering logic when connected to a data source
        // e.g., reload transactionListVBox with filtered results
    }

    // ── Pagination Handlers ──────────────────────────────────────

    @FXML
    private void handlePrevPage() {
        if (currentPage > 1) {
            currentPage--;
            updatePageLabel();
            loadPage(currentPage);
        }
    }

    @FXML
    private void handleNextPage() {
        if (currentPage < totalPages) {
            currentPage++;
            updatePageLabel();
            loadPage(currentPage);
        }
    }

    private void updatePageLabel() {
        pageLabel.setText(currentPage + "/" + totalPages);
    }

    private void loadPage(int page) {
        // TODO: fetch and display the transactions for the given page
    }

    // ── Add Expense Handler ──────────────────────────────────────

    @FXML
    private void handleAddExpense() {
        // TODO: open Add Expense dialog / modal
    }

    // ── Public helpers for populating rows dynamically ───────────

    /**
     * Call this from outside (e.g., a service layer) to populate the list.
     * Each transaction should have: icon emoji, name, date, type ("expense"/"income"), amount string.
     */
    public void populateTransactions(java.util.List<TransactionRow> rows, int page, int pages) {
        transactionListVBox.getChildren().clear();
        currentPage = page;
        totalPages = pages;
        updatePageLabel();

        for (TransactionRow row : rows) {
            HBox rowBox = buildTransactionRow(row);
            transactionListVBox.getChildren().add(rowBox);
        }
    }

    private HBox buildTransactionRow(TransactionRow row) {
        HBox hbox = new HBox(15);
        hbox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hbox.setStyle(
                "-fx-background-color: #f9f9f9; -fx-border-color: #eeeeee;" +
                        "-fx-border-radius: 8; -fx-background-radius: 8;" +
                        "-fx-border-width: 1; -fx-padding: 10 15 10 15;"
        );

        Label icon = new Label(row.icon);
        icon.setStyle(
                "-fx-font-size: 20px; -fx-background-color: #eeeeee;" +
                        "-fx-background-radius: 50; -fx-padding: 6;"
        );

        Label name = new Label(row.name);
        name.setStyle("-fx-font-size: 13px; -fx-text-fill: #1a1a1a; -fx-font-weight: bold;");

        Label date = new Label(row.date);
        date.setStyle("-fx-font-size: 12px; -fx-text-fill: #888888;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        boolean isExpense = "expense".equalsIgnoreCase(row.type);
        Label typeBadge = new Label(row.type);
        typeBadge.setStyle(
                "-fx-background-color: " + (isExpense ? "#e74c3c" : "#2d6a4f") + ";" +
                        "-fx-text-fill: white; -fx-font-size: 11px; -fx-font-weight: bold;" +
                        "-fx-padding: 4 12 4 12; -fx-background-radius: 4;"
        );

        Label amount = new Label(row.amount);
        amount.setStyle(
                "-fx-font-size: 13px; -fx-font-weight: bold;" +
                        "-fx-text-fill: " + (isExpense ? "#e74c3c" : "#2d6a4f") + ";"
        );

        hbox.getChildren().addAll(icon, name, date, spacer, typeBadge, amount);
        return hbox;
    }

    // ── Inner DTO ────────────────────────────────────────────────

    public static class TransactionRow {
        public String icon;
        public String name;
        public String date;
        public String type;   // "expense" or "income"
        public String amount; // e.g., "-₱1,200.00"

        public TransactionRow(String icon, String name, String date, String type, String amount) {
            this.icon = icon;
            this.name = name;
            this.date = date;
            this.type = type;
            this.amount = amount;
        }
    }
}