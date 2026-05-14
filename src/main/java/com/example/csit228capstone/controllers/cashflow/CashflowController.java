package com.example.csit228capstone.controllers.cashflow;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.ArcType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CashflowController {

    @FXML private Button btnSelectedDate;
    @FXML private Button btnDay;
    @FXML private Button btnWeek;
    @FXML private Button btnMonth;
    @FXML private Button btnYear;

    @FXML private BarChart<String, Number> incomeExpenseChart;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;

    @FXML private StackPane incomeDonutPane;
    @FXML private Canvas incomeCanvas;
    @FXML private Label incomeTotalLabel;

    @FXML private StackPane expensesDonutPane;
    @FXML private Canvas expensesCanvas;
    @FXML private Label expensesTotalLabel;

    private String activePreset = "day";
    private String rangeStart   = null;
    private String rangeEnd     = null;

    private static final String STYLE_ACTIVE =
            "-fx-background-color: #2d5a27; -fx-text-fill: white; " +
                    "-fx-border-radius: 6; -fx-background-radius: 6; " +
                    "-fx-font-size: 13px; -fx-padding: 8 20 8 20; -fx-cursor: hand;";

    private static final String STYLE_INACTIVE =
            "-fx-background-color: white; -fx-border-color: #cccccc; " +
                    "-fx-border-radius: 6; -fx-background-radius: 6; " +
                    "-fx-font-size: 13px; -fx-padding: 8 20 8 20; -fx-cursor: hand;";

    @FXML
    public void initialize() {
        // Essential for the stacked look
        incomeExpenseChart.setBarGap(0);
        incomeExpenseChart.setCategoryGap(30);
        incomeExpenseChart.setAnimated(false); // Animations often cause the axis bug

        setActivePreset("day");
        refreshChart();
        drawDonut(incomeCanvas);
        drawDonut(expensesCanvas);
    }

    @FXML private void onDayClicked(ActionEvent event) { resetRange(); setActivePreset("day"); refreshChart(); }
    @FXML private void onWeekClicked(ActionEvent event) { resetRange(); setActivePreset("week"); refreshChart(); }
    @FXML private void onMonthClicked(ActionEvent event) { resetRange(); setActivePreset("month"); refreshChart(); }
    @FXML private void onYearClicked(ActionEvent event) { resetRange(); setActivePreset("year"); refreshChart(); }
    @FXML private void onSelectedDateClicked(ActionEvent event) { showRangePicker(); }

    private void setActivePreset(String preset) {
        activePreset = preset;
        btnDay.setStyle(STYLE_INACTIVE);
        btnWeek.setStyle(STYLE_INACTIVE);
        btnMonth.setStyle(STYLE_INACTIVE);
        btnYear.setStyle(STYLE_INACTIVE);
        switch (preset) {
            case "day":   btnDay.setStyle(STYLE_ACTIVE);   break;
            case "week":  btnWeek.setStyle(STYLE_ACTIVE);  break;
            case "month": btnMonth.setStyle(STYLE_ACTIVE); break;
            case "year":  btnYear.setStyle(STYLE_ACTIVE);  break;
        }
    }

    private void resetRange() {
        rangeStart = null;
        rangeEnd   = null;
        btnSelectedDate.setText("selected_date");
    }

    @SuppressWarnings("unchecked")
    private void refreshChart() {
        // Fix for Issue #1 & #2: Clear data AND categories to force a fresh axis redraw
        incomeExpenseChart.getData().clear();
        xAxis.getCategories().clear();

        List<String> labels = buildLabels();
        xAxis.setCategories(FXCollections.observableArrayList(labels));

        // Mock data
        List<Double> incomeValues = buildData(labels.size(), 42);
        List<Double> expenseValues = buildData(labels.size(), 99);

        // Fix for Issue #3: Stacked Look
        // In image_93e5f5.png, pink is on top.
        // To do this in a standard BarChart, Series 1 is the bottom, Series 2 sits on top.
        XYChart.Series<String, Number> expenseSeries = new XYChart.Series<>();
        XYChart.Series<String, Number> incomeSeries = new XYChart.Series<>();

        for (int i = 0; i < labels.size(); i++) {
            // Purple base
            expenseSeries.getData().add(new XYChart.Data<>(labels.get(i), expenseValues.get(i)));
            // Pink top (Combined height)
            incomeSeries.getData().add(new XYChart.Data<>(labels.get(i), incomeValues.get(i)));
        }

        // Add to chart (Order matters for stacking CSS)
        incomeExpenseChart.getData().addAll(expenseSeries, incomeSeries);

        // Apply Styles to match image_93e5f5.png
        incomeExpenseChart.applyCss();
        incomeExpenseChart.layout();

        // Purple = Expense (Bottom), Pink = Income (Top)
        applyBarColor(expenseSeries, "#8a7cf0"); // Purple
        applyBarColor(incomeSeries, "#ff9a90");  // Pink
    }

    private void applyBarColor(XYChart.Series<String, Number> series, String hex) {
        for (XYChart.Data<String, Number> d : series.getData()) {
            if (d.getNode() != null) {
                // Set the color and add a slight radius to match the reference image
                d.getNode().setStyle("-fx-bar-fill: " + hex + "; -fx-background-radius: 4 4 0 0;");
            }
        }
    }

    private List<String> buildLabels() {
        List<String> labels = new ArrayList<>();
        int thisYear = LocalDate.now().getYear();

        switch (activePreset) {
            case "day":
                String[] days = {"Mon","Tue","Wed","Thu","Fri","Sat","Sun"};
                int ds = 0, de = 6;
                if (rangeStart != null && rangeEnd != null) {
                    ds = indexOf(days, rangeStart); de = indexOf(days, rangeEnd);
                }
                for (int i = ds; i <= de; i++) labels.add(days[i]);
                break;
            case "week":
                int ws = 1, we = 4;
                if (rangeStart != null && rangeEnd != null) {
                    ws = parseWeekNum(rangeStart); we = parseWeekNum(rangeEnd);
                }
                for (int i = ws; i <= we; i++) labels.add("Week " + i);
                break;
            case "month":
                String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
                int ms = 0, me = 11;
                if (rangeStart != null && rangeEnd != null) {
                    ms = indexOf(months, rangeStart); me = indexOf(months, rangeEnd);
                }
                for (int i = ms; i <= me; i++) labels.add(months[i]);
                break;
            case "year":
                int ys = thisYear - 7, ye = thisYear; // Showing 8 years like the image
                if (rangeStart != null && rangeEnd != null) {
                    ys = Integer.parseInt(rangeStart); ye = Integer.parseInt(rangeEnd);
                }
                for (int y = ys; y <= ye; y++) labels.add(String.valueOf(y));
                break;
        }
        return labels;
    }

    private List<Double> buildData(int count, long seed) {
        List<Double> data = new ArrayList<>();
        Random rng = new Random(seed);
        for (int i = 0; i < count; i++) data.add(60 + rng.nextDouble() * 80);
        return data;
    }

    private void drawDonut(Canvas canvas) {
        double w = canvas.getWidth(); double h = canvas.getHeight();
        double cx = w / 2.0; double cy = h / 2.0;
        double outerR = Math.min(w, h) / 2.0 - 6;
        double innerR = outerR * 0.58;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.web("#1abc9c"));
        gc.fillArc(cx - outerR, cy - outerR, outerR * 2, outerR * 2, 90, 270, ArcType.ROUND);
        gc.setFill(Color.web("#f1c40f"));
        gc.fillArc(cx - outerR, cy - outerR, outerR * 2, outerR * 2, 360, 90, ArcType.ROUND);
        gc.setFill(Color.WHITE);
        gc.fillOval(cx - innerR, cy - innerR, innerR * 2, innerR * 2);
    }

    private void showRangePicker() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Select Range");

        ComboBox<String> startBox = new ComboBox<>();
        ComboBox<String> endBox   = new ComboBox<>();
        List<String> options = getRangeOptions();
        startBox.getItems().addAll(options);
        endBox.getItems().addAll(options);

        Button ok = new Button("Apply");
        ok.setOnAction(e -> {
            if (startBox.getValue() != null && endBox.getValue() != null) {
                rangeStart = startBox.getValue();
                rangeEnd = endBox.getValue();
                btnSelectedDate.setText(rangeStart + " - " + rangeEnd);
                refreshChart();
            }
            dialog.close();
        });

        VBox root = new VBox(10, new Label("From:"), startBox, new Label("To:"), endBox, ok);
        root.setPadding(new Insets(20));
        dialog.setScene(new Scene(root));
        dialog.showAndWait();
    }

    private List<String> getRangeOptions() {
        List<String> opts = new ArrayList<>();
        int thisYear = LocalDate.now().getYear();
        switch (activePreset) {
            case "day": opts.addAll(Arrays.asList("Mon","Tue","Wed","Thu","Fri","Sat","Sun")); break;
            case "week": opts.addAll(Arrays.asList("Week 1","Week 2","Week 3","Week 4")); break;
            case "month": opts.addAll(Arrays.asList("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")); break;
            case "year": for (int y = thisYear - 10; y <= thisYear; y++) opts.add(String.valueOf(y)); break;
        }
        return opts;
    }

    private int indexOf(String[] arr, String val) {
        for (int i = 0; i < arr.length; i++) if (arr[i].equalsIgnoreCase(val)) return i;
        return 0;
    }

    private int parseWeekNum(String w) {
        try { return Integer.parseInt(w.replaceAll("[^0-9]", "")); } catch (Exception e) { return 1; }
    }
}