package com.example.csit228capstone.controllers.accounts;

import com.example.csit228capstone.data.AccountStored;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditAccountController {

    @FXML private TextField nameField;
    @FXML private TextField balanceField;

    private AccountStored account;
    private boolean saveClicked = false;

    // Call this from the Main Controller to pass the data
    public void setAccount(AccountStored account) {
        this.account = account;
        nameField.setText(account.getAccountName());
        balanceField.setText(String.valueOf(account.getBalance()));
    }

    public boolean isSaveClicked() {
        return saveClicked;
    }

    @FXML
    private void handleSave() {
        // Update the object (Ready for DB)
        account.setAccountName(nameField.getText());
        account.setBalance(Double.parseDouble(balanceField.getText()));

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