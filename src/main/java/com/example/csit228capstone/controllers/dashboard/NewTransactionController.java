package com.example.csit228capstone.controllers.dashboard;

import com.example.csit228capstone.data.Account;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.services.AccountService;
import com.example.csit228capstone.services.CategoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTransactionController {
    @FXML
    private TextField titleField;

    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> accountBox;

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    private ComboBox<TransactionType> typeBox;

    @FXML
    public void initialize(){
        typeBox.getItems().addAll(TransactionType.EXPENSE,TransactionType.INCOME);
        CategoryService categoryService = new CategoryService();
        for (String category : categoryService.getAllCategories()){
            categoryBox.getItems().add(category);
        }
        AccountService accountService = new AccountService();
        for (Account account : accountService.getAllAccounts()){
            accountBox.getItems().add(account.getAccountName());
        }

    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    private void handleSave(ActionEvent event) {
        saveTransaction();
        closeWindow(event);
    }

    private void saveTransaction() {

    }
}