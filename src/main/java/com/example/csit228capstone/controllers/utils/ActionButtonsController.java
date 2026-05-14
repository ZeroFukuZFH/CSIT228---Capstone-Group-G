package com.example.csit228capstone.controllers.utils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ActionButtonsController {

    private void openWindow(String title, String route) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(route));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTransfer() {
        System.out.println("Transfer button clicked");
        openWindow("New Transfer", "/com/example/csit228capstone/screens/NewTransfer.fxml");
    }

    @FXML
    private void handleNewTransaction() {
        System.out.println("New Transaction button clicked");
        openWindow("New Transaction", "/com/example/csit228capstone/screens/NewTransaction.fxml");
    }
}