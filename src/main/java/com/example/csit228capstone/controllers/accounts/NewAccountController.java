package com.example.csit228capstone.controllers.accounts;

import com.example.csit228capstone.services.data.AccountService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewAccountController {

    @FXML
    private TextField accountNameField;

    @FXML
    private TextField accountBalanceField;

    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow(event);
    }
    @FXML
    private void handleSave(ActionEvent event) {
        try {
            String name = accountNameField.getText();
            Double balance = Double.parseDouble(accountBalanceField.getText());

            if (name.isEmpty()) {
                showError("Account name cannot be empty");
                return;
            }

            if (balance < 0) {
                showError("Balance cannot be negative");
                return;
            }

            AccountService accountService = new AccountService();
            accountService.addAccount(name,balance);
            closeWindow(event);

        } catch (NumberFormatException e) {
            showError("Please enter a valid balance amount");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}