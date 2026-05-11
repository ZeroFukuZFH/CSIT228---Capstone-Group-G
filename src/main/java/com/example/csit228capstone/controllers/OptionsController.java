package com.example.csit228capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class OptionsController {



    @FXML
    private Button importButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button currencyButton;


    @FXML
    private ComboBox<String> defaultCurrencyComboBox;

    @FXML
    private void initialize() {
        String[] currency = {"USD", "PHP", "EUR", "GBP", "JPY"};
        defaultCurrencyComboBox.getItems().addAll(currency);
        defaultCurrencyComboBox.setValue(currency[0]);
    }

    @FXML
    private void handleImport() {
        // Handle import CSV
        System.out.println("Import button clicked");
        // TODO: Open file chooser and import CSV data
    }

    @FXML
    private void handleExport() {
        // Handle export to CSV
        System.out.println("Export button clicked");
        // TODO: Save transactions to CSV file
    }

    @FXML
    private void handleDefaultCurrency() {
        // Handle default currency change
        System.out.println("Default currency button clicked");
        // TODO: Open dialog to select default currency
    }
}