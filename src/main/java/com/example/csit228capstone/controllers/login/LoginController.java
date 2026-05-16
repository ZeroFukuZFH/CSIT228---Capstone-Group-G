package com.example.csit228capstone.controllers.login;

import com.example.csit228capstone.services.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField fnameField;
    @FXML
    private TextField lnameField;
    @FXML
    private PasswordField passwordField;

    private UserService userService;

    @FXML
    private void initialize(){
        this.userService = new UserService();
    }
    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String fname = fnameField.getText();
        String lname = lnameField.getText();
        String password = passwordField.getText();

        if (fname.isEmpty() || lname.isEmpty() || password.isEmpty()) {
            showError("Fields should not be empty");
            return;
        }

        boolean status = this.userService.login(fname,lname,password);

        if (status) {
            System.out.println("Login Success! Redirecting to Dashboard...");

            Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/com/example/csit228capstone/screens/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot));
            stage.setTitle("Smart Save - Dashboard");
            stage.show();
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
    private void onSignUpClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/csit228capstone/screens/Registration.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Create Account");
        stage.show();
    }
}