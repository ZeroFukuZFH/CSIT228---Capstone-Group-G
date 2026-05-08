package com.example.csit228capstone.views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class AccountsController {

    // Transition balik sa Dashboard
    @FXML
    private void onBackToDashboard(ActionEvent event) {
        loadScene(event, "/com/example/csit228capstone/views/Dashboard.fxml", "Smart Save - Dashboard");
    }

    // Transition padung sa Login
    @FXML
    private void onLogoutClick(ActionEvent event) {
        loadScene(event, "/com/example/csit228capstone/views/Login.fxml", "Welcome Back");
    }

    // LOGIC PARA SA DESIGN (Based sa screenshot)

    @FXML
    private void onAddAccountClick(ActionEvent event) {
        // TODO: I-open ang modal o popup para sa pag-add og bag-ong account
        System.out.println("Opening Add Account Dialog...");
    }

    @FXML
    private void onEditAccount(ActionEvent event) {
        // TODO: Logic para sa pag-edit sa napili nga account (✎ button)
        System.out.println("Editing selected account...");
    }

    @FXML
    private void onDeleteAccount(ActionEvent event) {
        // TODO: Logic para sa pag-delete (🗑 button) - kasagaran naay Confirmation Alert
        System.out.println("Deleting selected account...");
    }

    @FXML
    private void onSetDefault(ActionEvent event) {
        // TODO: Logic para i-set ang account isip 'default' (set as default button)
        System.out.println("Account set as primary/default.");
    }

    // Navigational Helper
    private void loadScene(ActionEvent event, String fxmlPath, String title) {
        try {
            URL resource = getClass().getResource(fxmlPath);
            if (resource == null) {
                resource = getClass().getResource(fxmlPath.replace("/views/", "/"));
            }

            if (resource == null) {
                System.err.println("DEBUG ERROR: FXML Not Found: " + fxmlPath);
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