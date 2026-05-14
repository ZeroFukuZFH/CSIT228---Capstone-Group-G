package com.example.csit228capstone.controllers.login;

import com.example.csit228capstone.data.Transaction;
import com.example.csit228capstone.data.TransactionType;
import javafx.fxml.FXML;
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

    @FXML
    private void initialize() {
        String[] currency = {"USD", "PHP", "EUR", "GBP", "JPY"};
        defaultCurrencyComboBox.getItems().addAll(currency);
        defaultCurrencyComboBox.setValue(currency[0]);
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
                TransactionType transactionType = null;
                switch (columns[2].toUpperCase()){
                    case "INCOME":
                        transactionType = TransactionType.INCOME;
                        break;
                    case "EXPENSE":
                        transactionType = TransactionType.EXPENSE;
                        break;
                }

                double amount = Double.parseDouble(columns[3]);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date transactionDate = dateFormat.parse(columns[4]);

                Transaction transaction = new Transaction(title,description,transactionDate,transactionType,amount);
                System.out.println(transaction);
                importedTransactions.add(transaction);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleExport() {
        System.out.println("Export button clicked");

        List<Transaction> transactions = new ArrayList<>(); // REPLACE WITH DATA FETCH LATER
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
        // Handle default currency change
        System.out.println("Default currency button clicked");
        // TODO: Open dialog to select default currency
    }
}