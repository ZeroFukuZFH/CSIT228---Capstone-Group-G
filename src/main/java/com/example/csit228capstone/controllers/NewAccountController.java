package com.example.csit228capstone.controllers;

import com.example.csit228capstone.Database.Database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class NewAccountController
        extends BaseFormHandler {

    @FXML
    private TextField accountNameField;

    @FXML
    private TextField balanceField;

    @FXML
    public void initialize() {

        String currency = "₱";

        balanceField.setPromptText(
                currency + " " + 0.00
        );
    }

    @Override
    public void handleSave(ActionEvent event) {

        super.handleSave(event);

        try {

            String accountName =
                    accountNameField.getText();

            String balanceText =
                    balanceField.getText();

            // default balance
            double balance = 0;

            if (!balanceText.isEmpty()) {
                balance =
                        Double.parseDouble(balanceText);
            }

            Connection conn =
                    Database
                            .getInstance()
                            .getConnection();

            String sql =
                    "INSERT INTO accounts " +
                            "(user_id, account_name, current_balance) " +
                            "VALUES (?, ?, ?)";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setInt(
                    1,
                    LoginController.currentUserId
            );

            pst.setString(2, accountName);

            pst.setDouble(3, balance);

            pst.executeUpdate();

            System.out.println(
                    "Account Added Successfully!"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}