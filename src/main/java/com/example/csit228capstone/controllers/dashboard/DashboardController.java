package com.example.csit228capstone.controllers.dashboard;

import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.services.AccountService;
import com.example.csit228capstone.services.DashboardService;
import com.example.csit228capstone.services.OptionService;
import com.example.csit228capstone.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DateFormat;
import java.util.ArrayList;
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

    private void filter(){
        //TODO (IMPLEMENT LATER)
        // general idea : if category fk userid = userid then filter
    }

    private void search(){
        this.searchTextField.textProperty().addListener((observable ,oldVal,newVal ) -> {
            renderList(newVal);
        });
    }

    public void renderList(){
        this.contentBox.getChildren().clear();
        for (Transaction t : transactions) {
            this.contentBox.getChildren().add(transactionItem(t));
        }
    }

    public void renderList(String search){
        if(search.isEmpty()) {
            renderList();
            return;
        }
        this.contentBox.getChildren().clear();
        for (Transaction t : transactions) {
            if(t.getTransactionTitle().contains(search)){
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