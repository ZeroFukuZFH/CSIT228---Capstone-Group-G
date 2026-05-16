package com.example.csit228capstone.controllers.options;

import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import com.example.csit228capstone.services.OptionService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OptionsController {
    @FXML
    private Button importButton;

    @FXML
    private Button exportButton;

    @FXML
    private Button currencyButton;


    @FXML
    private ComboBox<String> defaultCurrencyComboBox;

    OptionService optionService;
    @FXML
    private void initialize() {
        String[] currency = {"USD", "PHP", "EUR", "GBP", "JPY"};
        defaultCurrencyComboBox.getItems().addAll(currency);
        this.optionService = new OptionService();
    }

    @FXML
    private void handleImport() {
        System.out.println("Import button clicked");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File file = fileChooser.showOpenDialog(null);

        if (file ==  null) return;

        String filePath = file.getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".csv")) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            List<Transaction> importedTransactions = new ArrayList<>(); // ARRAY LIST TO USE FOR UPDATING THE DATABASE

            boolean isFirstRow = true;
            while ((line = reader.readLine()) != null){
                if (isFirstRow) {
                    isFirstRow = false;
                    continue;
                }

                String[] columns = line.split(",");

                if(columns.length != 5) break;

                String title = columns[0];
                String description = columns[1];
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date transactionDate = dateFormat.parse(columns[2].replace("\"", "").trim());

                TransactionType transactionType = null;
                switch (columns[3].toUpperCase()){
                    case "INCOME":
                        transactionType = TransactionType.INCOME;
                        break;
                    case "EXPENSE":
                        transactionType = TransactionType.EXPENSE;
                        break;
                }

                double amount = Double.parseDouble(columns[4]);


                Transaction transaction = new Transaction(
                        optionService.getDefaultAccountId(),
                        optionService.getDefaultCategoryId(),
                        title,
                        description,
                        transactionDate,
                        transactionType,
                        amount
                );
                System.out.println(transaction);
                importedTransactions.add(transaction);

            }
            this.optionService.importCsv(importedTransactions);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleExport() {
        System.out.println("Export button clicked");

        List<Transaction> transactions = this.optionService.exportCsv();

        if(transactions.isEmpty()) return;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("save export to");
        fileChooser.setInitialFileName("transaction_history.csv");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if(file == null) return;

        String filePath = file.getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".csv")) {
            file = new File(filePath + ".csv");
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("transaction_title,description,transaction_date,transaction_type,amount\n");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


            for (Transaction transaction : transactions) {
                writer.write(String.format("\"%s\",\"%s\",\"%s\",%s,%.2f\n",
                        transaction.getTransactionTitle(),
                        transaction.getDescription(),
                        dateFormat.format(transaction.getTransactionDate()),
                        transaction.getTransactionType(),
                        transaction.getAmount()
                ));
            }

            System.out.println("File saved successfully at: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }

    @FXML
    private void handleDefaultCurrency() {
        String selectedCurrency = defaultCurrencyComboBox.getValue();

        if (selectedCurrency != null && !selectedCurrency.isEmpty()) {

            optionService.setCurrency(selectedCurrency);

            System.out.println("Default currency set to: " + selectedCurrency);
            showAlert(Alert.AlertType.INFORMATION, "Currency Updated", "Default currency has been set to: " + selectedCurrency);
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection",
                    "Please select a currency before saving.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}