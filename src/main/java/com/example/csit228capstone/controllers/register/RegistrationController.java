package com.example.csit228capstone.controllers.register;

import com.example.csit228capstone.services.data.UserService;
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

public class RegistrationController {
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
    private void handleRegistration(ActionEvent event) throws IOException {
        String fname = fnameField.getText();
        String lname = lnameField.getText();
        String password = passwordField.getText();

        if (fname.isEmpty() || lname.isEmpty() || password.isEmpty()){
            showError("Fields should not be empty");
        }

        this.userService.register(fname,lname,password);

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/csit228capstone/screens/Dashboard.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Dashboard");
        stage.show();
    }

    @FXML
    private void onLoginClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/csit228capstone/screens/Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}