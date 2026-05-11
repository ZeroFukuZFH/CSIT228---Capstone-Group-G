package com.example.csit228capstone.controllers;

import com.example.csit228capstone.Database.Database;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    public static int currentUserId;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event)
            throws IOException {

        String username =
                usernameField.getText();

        String password =
                passwordField.getText();

        if (
                username.isEmpty() ||
                        password.isEmpty()
        ) {

            System.out.println(
                    "Please enter username and password."
            );

            return;
        }

        try {

            Connection conn =
                    Database
                            .getInstance()
                            .getConnection();

            String sql =
                    "SELECT * FROM users " +
                            "WHERE username = ? AND password = ?";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs =
                    pst.executeQuery();

            if (rs.next()) {

                // STORE LOGGED IN USER
                currentUserId =
                        rs.getInt("user_id");

                System.out.println(
                        "Login Successful!"
                );

                Parent dashboardRoot =
                        FXMLLoader.load(
                                getClass().getResource(
                                        "/com/example/csit228capstone/screens/Dashboard.fxml"
                                )
                        );

                Stage stage =
                        (Stage) ((Node) event.getSource())
                                .getScene()
                                .getWindow();

                stage.setScene(new Scene(dashboardRoot));
                stage.setTitle("Smart Save - Dashboard");
                stage.show();

            } else {

                System.out.println(
                        "Account not found. Please sign up first."
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSignUpClick(ActionEvent event)
            throws IOException {

        Parent root =
                FXMLLoader.load(
                        getClass().getResource(
                                "/com/example/csit228capstone/screens/Registration.fxml"
                        )
                );

        Stage stage =
                (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Create Account");
        stage.show();
    }
}