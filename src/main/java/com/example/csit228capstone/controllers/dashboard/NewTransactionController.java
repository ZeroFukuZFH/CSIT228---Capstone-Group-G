package com.example.csit228capstone.controllers.dashboard;

import com.example.csit228capstone.data.Account;
import com.example.csit228capstone.data.Category;
import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.services.AccountService;
import com.example.csit228capstone.services.CategoryService;
import com.example.csit228capstone.services.TransactionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewTransactionController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<Account> accountBox;

    @FXML
    private ComboBox<Category> categoryBox;

    @FXML
    private ComboBox<TransactionType> typeBox;

    private TransactionService transactionService;

    @FXML
    public void initialize(){

        this.transactionService = new TransactionService();

        typeBox.getItems().addAll(TransactionType.EXPENSE,TransactionType.INCOME);
        CategoryService categoryService = new CategoryService();
        categoryBox.getItems().addAll(categoryService.getAllCategories());
        AccountService accountService = new AccountService();
        accountBox.getItems().addAll(accountService.getAllAccounts());

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
        try {
            // Get selected category
            Category selectedCategory = categoryBox.getValue();
            if (selectedCategory == null) {
                showAlert("Error", "Please select a category");
                return;
            }
            int categoryId = selectedCategory.getId();

            Account selectedAcccount = accountBox.getValue();
            if (selectedAcccount == null){
                showAlert("Error", "Please select an Account");
                return;
            }
            int accountId = selectedAcccount.getAccountId();

            // Get selected transaction type
            TransactionType selectedType = typeBox.getValue();
            if (selectedType == null) {
                showAlert("Error", "Please select transaction type");
                return;
            }
            TransactionType transactionType = selectedType;

            // Get title and description
            String title = titleField.getText();
            if (title == null || title.trim().isEmpty()) {
                showAlert("Error", "Please enter a title");
                return;
            }

            String description = descriptionField.getText();

            // Get amount
            double amount;
            try {
                amount = Double.parseDouble(amountField.getText());
                if (amount <= 0) {
                    showAlert("Error", "Amount must be greater than 0");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid amount");
                return;
            }



            // Create and save transaction
            Transaction transaction = new Transaction(
                    accountId,
                    categoryId,
                    title,
                    description,
                    transactionType,
                    amount
            );

            transactionService.addTransaction(transaction);

            clearForm();

            showAlert("Success", "Transaction saved successfully");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Failed to save transaction: " + e.getMessage());
        }
    }

    private void clearForm() {
        titleField.clear();
        descriptionField.clear();
        amountField.clear();
        categoryBox.setValue(null);
        typeBox.setValue(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}