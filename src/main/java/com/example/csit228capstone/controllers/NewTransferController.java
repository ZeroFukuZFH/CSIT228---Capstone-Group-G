package com.example.csit228capstone.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewTransferController extends BaseFormHandler {
    @FXML
    TextField fromField;
    @FXML
    TextField toField;
    @FXML
    TextField amountField;
    @Override
    public void handleSave(ActionEvent event) {
        super.handleSave(event);

        // ADD DATABSE POST
    }
}
