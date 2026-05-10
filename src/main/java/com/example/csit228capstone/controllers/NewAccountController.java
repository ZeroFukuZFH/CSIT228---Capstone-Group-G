package com.example.csit228capstone.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewAccountController extends BaseFormHandler {
    @FXML
    TextField balanceField;
    @FXML
    public void initialize(){
        String currency = ""; // CHANGE TO DATABASE FETCH
        balanceField.setPromptText(currency + " " + 0.00);

    }
    @Override
    public void handleSave(ActionEvent event) {
        super.handleSave(event);
        // ADD DATABSE POST
    }
}
