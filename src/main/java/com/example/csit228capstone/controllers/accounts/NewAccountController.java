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

public class NewAccountController implements Initializable {

    @FXML private Label dateLabel;
    @FXML private TextField nameField;
    @FXML private TextField balanceField;
    @FXML private ColorPicker colorPicker;
    @FXML private StackPane iconPickerBox;
    @FXML private Label iconPreviewLabel;

    private boolean saveClicked = false;
    private AccountStored result = null;
    private String selectedIcon = "💳"; // default icon

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Show today's date on the header badge
        dateLabel.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        // Default color matches the app's green theme
        colorPicker.setValue(Color.web("#2d5a3d"));
        // Show default icon
        iconPreviewLabel.setText(selectedIcon);
    }

    @FXML
    private void handleIconPicker() {
        // TODO: plug in your emoji picker popup here
        // When done: selectedIcon = chosenEmoji; iconPreviewLabel.setText(chosenEmoji);
        System.out.println("Icon picker clicked");
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

        // Build the result object
        // "PHP" is hardcoded for now — swap with a DB fetch later
        result = new AccountStored(name, "PHP", balance, false);

        // TODO: persist to DB here
        // AccountDAO dao = new AccountDAO();
        // dao.insert(result);

        saveClicked = true;
        closeStage();
    }

    @FXML
    private void handleCancel() {
        closeStage();
    }

    /** Called by AccountsController after showAndWait() */
    public boolean isSaveClicked() {
        return saveClicked;
    }

    /** Returns the new AccountStored built from the form */
    public AccountStored getResult() {
        return result;
    }

    private void closeStage() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }
}