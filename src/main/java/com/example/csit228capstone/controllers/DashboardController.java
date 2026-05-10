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

public class DashboardController {

    @FXML
    private void onAccountsClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Accounts.fxml", "Smart Save - Accounts");
    }

    @FXML
    private void onLogoutClick(ActionEvent event) {
        navigateTo(event, "/com/example/csit228capstone/screens/Login.fxml", "Welcome Back");
    }

    private void navigateTo(ActionEvent event, String path, String title) {
        try {
            URL resource = getClass().getResource(path);
            if (resource == null) {
                resource = getClass().getResource(path.replace("/controllers/", "/"));
            }

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
}