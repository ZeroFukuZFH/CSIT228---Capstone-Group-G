package com.example.csit228capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;

public class CashFlowController {

    @FXML private BarChart<String, Number> barChart;
    @FXML private PieChart incomePie;
    @FXML private PieChart expensePie;
    @FXML private ImageView logoImage;

    @FXML
    public void initialize() {
        setupBarChart();
        setupPieCharts();
    }

    private void setupBarChart() {
        XYChart.Series<String, Number> income = new XYChart.Series<>();
        income.setName("Income");
        income.getData().add(new XYChart.Data<>("2012", 120));
        income.getData().add(new XYChart.Data<>("2013", 100));
        income.getData().add(new XYChart.Data<>("2014", 140));

        XYChart.Series<String, Number> expense = new XYChart.Series<>();
        expense.setName("Expense");
        expense.getData().add(new XYChart.Data<>("2012", 45));
        expense.getData().add(new XYChart.Data<>("2013", 80));
        expense.getData().add(new XYChart.Data<>("2014", 70));

        barChart.getData().addAll(income, expense);
    }

    private void setupPieCharts() {
        incomePie.getData().add(new PieChart.Data("Salary", 70));
        incomePie.getData().add(new PieChart.Data("Freelance", 30));

        expensePie.getData().add(new PieChart.Data("Food", 40));
        expensePie.getData().add(new PieChart.Data("Rent", 60));
    }

    @FXML
    private void handleNewTransaction() {
        System.out.println("Opening New Transaction...");
    }

    @FXML
    private void handleLogoClick() {
        System.out.println("Logo clicked! Returning to Dashboard...");
    }
}