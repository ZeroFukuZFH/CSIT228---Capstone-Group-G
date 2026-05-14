package com.example.csit228capstone.controllers.accounts;

import com.example.csit228capstone.data.AccountStored;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class EditAccountController implements Initializable {

    @FXML private Label dateLabel;
    @FXML private TextField nameField;
    @FXML private TextField balanceField;
    @FXML private ColorPicker colorPicker;
    @FXML private StackPane iconPickerBox;
    @FXML private Label iconPreviewLabel;

    private AccountStored account;
    private boolean saveClicked = false;


    //Akoa ra gi copy ang icon sa categories

    private static final String[] ICONS = {"🏠", "🍔", "🚗", "💊", "✈️", "📚", "🎮", "👔"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Read-only date — cannot be edited
        // Automatic na ang date
        dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        // Default color
        colorPicker.setValue(Color.web("#2d5a3d"));

        // Default icon
        iconPreviewLabel.setText(ICONS[0]);
    }

    public void setAccount(AccountStored account) {
        this.account = account;
        nameField.setText(account.getAccountName());
        balanceField.setText(account.getBalance() == 0.0 ? "" : String.valueOf(account.getBalance()));
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    public AccountStored getAccount() {
        return account;
    }

    @FXML
    private void handleIconPicker() {
        // Cycle through account-themed icons on each click
        String current = iconPreviewLabel.getText();
        int idx = 0;
        for (int i = 0; i < ICONS.length; i++) {
            if (ICONS[i].equals(current)) {
                idx = (i + 1) % ICONS.length;
                break;
            }
        }
        iconPreviewLabel.setText(ICONS[idx]);
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText().trim();
        String balText = balanceField.getText().trim();

        // Validate name
        if (name.isEmpty()) {
            nameField.setStyle(nameField.getStyle() + "; -fx-border-color: red;");
            return;
        }

        // Validate balance
        double balance = 0.0;
        if (!balText.isEmpty()) {
            try {
                balance = Double.parseDouble(balText);
            } catch (NumberFormatException e) {
                balanceField.setStyle(balanceField.getStyle() + "; -fx-border-color: red;");
                return;
            }
        }

        account.setAccountName(name);
        account.setBalance(balance);

        // TODO: Database.getInstance().updateAccount(account);

        saveClicked = true;
        closeStage();
    }

    @FXML
    private void handleCancel() {
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}