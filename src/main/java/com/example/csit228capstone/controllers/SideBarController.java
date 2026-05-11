package com.example.csit228capstone.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SideBarController {

    private void navigateTo(ActionEvent event, String path, String title) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                System.err.println("Error: Cannot find " + path);
                return;
            }

            Parent root = FXMLLoader.load(resource);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDashboardClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Dashboard.fxml", "Dashboard");
    }

    @FXML
    public void onAccountsClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Accounts.fxml", "Accounts");
    }

    @FXML
    public void onCategoriesClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Categories.fxml", "Categories");
    }

    @FXML
    public void onCashflowClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Cashflow.fxml", "Cashflow");
    }

    @FXML
    public void onExpensesClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Expenses.fxml", "Expenses");
    }

    @FXML
    public void onIncomeClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Income.fxml", "Income");
    }

    @FXML
    public void onOptionsClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Options.fxml", "Options");
    }

    @FXML
    public void onLogoutClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Login.fxml", "Login");
    }
}
