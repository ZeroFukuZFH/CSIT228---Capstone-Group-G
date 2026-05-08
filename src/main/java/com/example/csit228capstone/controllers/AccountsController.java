package com.example.csit228capstone.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class AccountsController {

    @FXML
    private void onBackToDashboard(ActionEvent event) {
        loadScene(event, "/com/example/csit228capstone/screens/Dashboard.fxml", "Smart Save - Dashboard");
    }

    @FXML
    private void onLogoutClick(ActionEvent event) {
        loadScene(event, "/com/example/csit228capstone/screens/Login.fxml", "Welcome Back");
    }

    @FXML
    private void onNewTransactionClick(ActionEvent event) {
        System.out.println("Opening New Transaction Modal...");
        // TODO: I-load ang transaction screen o modal
    }

    @FXML
    private void onAddAccountClick(ActionEvent event) {
        System.out.println("Opening Add Account Dialog...");
    }

    @FXML
    private void onEditAccount(ActionEvent event) {
        System.out.println("Editing selected account...");
    }

    @FXML
    private void onDeleteAccount(ActionEvent event) {
        System.out.println("Deleting selected account...");
    }

    @FXML
    private void onSetDefault(MouseEvent event) {
        System.out.println("Account set as default via click.");
    }

    private void loadScene(ActionEvent event, String fxmlPath, String title) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                System.err.println("FATAL ERROR: FXML Not Found at " + fxmlPath);
                return;
            }

            Parent root = FXMLLoader.load(resource);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Load Scene Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}