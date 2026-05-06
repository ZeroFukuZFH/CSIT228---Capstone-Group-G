package com.example.csit228capstone.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class RegistrationController {

    @FXML
    private TextField fullNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button continueButton;

    @FXML
    private void handleRegistration(ActionEvent event) throws IOException {
        if (fullNameField == null || usernameField == null ||
                passwordField == null || confirmPasswordField == null) {
            System.out.println("Error: FXML fields are not properly linked.");
            return;
        }

        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (password.isEmpty() || username.isEmpty()) {
            System.out.println("Please fill in the required fields.");
        } else if (password.equals(confirmPassword)) {
            System.out.println("Registration Success! Redirecting to Dashboard...");

            // Lahos sa Dashboard human og register
            Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/com/example/csit228capstone/views/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
            stage.setTitle("Smart Save - Dashboard");
            stage.show();
        } else {
            System.out.println("Passwords do not match!");
        }
    }

    @FXML
    private void onSignInClick(ActionEvent event) throws IOException {
        Parent loginRoot = FXMLLoader.load(getClass().getResource("/com/example/csit228capstone/views/Login.fxml"));
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(loginRoot));
        window.show();
    }
}