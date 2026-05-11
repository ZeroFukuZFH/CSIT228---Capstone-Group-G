package com.example.csit228capstone.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class NewTransactionController extends BaseFormHandler {
    @FXML
    private TextField titleField;

    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<String> accountBox;

    @FXML
    private ComboBox<String> categoryBox;

    @FXML
    public void initialize(){
        String[] accounts = {}; // REPLACE LATER WITH DATABASE FETCH
        String[] category = {}; // REPLACE LATER WITH DATABASE FETCH
        accountBox.getItems().addAll(accounts);
        categoryBox.getItems().addAll(category);
    }

    @Override
    public void handleSave(ActionEvent event) {
        super.handleSave(event);
        // ADD DATABSE POST
    }
}
