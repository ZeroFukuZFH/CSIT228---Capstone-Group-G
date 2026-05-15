package com.example.csit228capstone.controllers.dashboard;

import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.services.DashboardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class DashboardController {
    DashboardService service;
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
    ComboBox<String> categoriesComboBox;
    @FXML
    TextField searchTextField;


    String search;
    String category;


    List<Transaction> transactions;

    @FXML
    public void initialize(){
        this.service = new DashboardService();
        this.currentDate.setText(new Date().toString());
        this.incomeCurrency.setText(service.getCurrency());
        this.expenseCurrency.setText(service.getCurrency());
        this.totalExpenses.setText(service.getTotalExpenses().toString());
        this.totalIncome.setText(service.getTotalBalance().toString());

        this.contentBox.setPadding(new Insets(10));
        this.contentBox.setSpacing(10);

        this.categoriesComboBox.getItems().addAll(service.getAllCategories());

        this.transactions = service.getAllTransactions();

        renderList();
        search();
        filter();
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
            smallStage.show();

            renderList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void filter(){
        this.categoriesComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
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

    public void renderList(){
        this.contentBox.getChildren().clear();

        for (Transaction t : transactions) {
            boolean matchesSearch = true;
            boolean matchesCategory = true;

            // Check search filter
            if (search != null && !search.isEmpty()) {
                matchesSearch = t.getTransactionTitle().toLowerCase().contains(search.toLowerCase());
            }

            // Check category filter
            if (category != null && !category.isEmpty() && !category.equals("All")) {
                matchesCategory = true; // change to match ID
            }

            if (matchesSearch && matchesCategory) {
                this.contentBox.getChildren().add(transactionItem(t));
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