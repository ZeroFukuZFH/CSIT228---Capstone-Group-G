package com.example.csit228capstone.controllers.accounts;

import com.example.csit228capstone.data.AccountStored;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {

    @FXML private VBox accountListContainer;
    @FXML private Button addAccountBtn;

    private List<AccountStored> accounts = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPlaceholderAccounts();
        renderAccounts();
    }

    // as if lng nga naay sud daan
    private void loadPlaceholderAccounts() {
        accounts.add(new AccountStored("Cash Wallet", "PHP", 5000.00, true));
        accounts.add(new AccountStored("Bank Account", "PHP", 12500.75, false));
        accounts.add(new AccountStored("GCash", "PHP", 800.00, false));
    }

    private void renderAccounts() {
        accountListContainer.getChildren().clear();
        for (int i = 0; i < accounts.size(); i++) {
            accountListContainer.getChildren().add(createAccountCard(accounts.get(i), i));
        }
    }

    private HBox createAccountCard(AccountStored account, int index) {
        // Root Card
        HBox card = new HBox(16);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("account-card");
        card.setPadding(new Insets(18, 20, 18, 20));

        // Icon Circle
        StackPane iconCircle = new StackPane();
        iconCircle.setMinSize(52, 52);
        iconCircle.getStyleClass().add("icon-circle");
        Label iconLabel = new Label("⌂");
        iconLabel.getStyleClass().add("icon-label");
        iconCircle.getChildren().add(iconLabel);

        // Account Name
        Label nameLabel = new Label(account.getAccountName());
        nameLabel.getStyleClass().add("account-name-label");
        nameLabel.setMinWidth(140);

        // Default Badge OR Set Default Button
        Region badgeOrBtn;
        if (account.isDefault()) {
            Label defaultBadge = new Label("default");
            defaultBadge.getStyleClass().add("default-badge");
            defaultBadge.setPadding(new Insets(4, 12, 4, 12));
            badgeOrBtn = defaultBadge;
        } else {
            Button setDefaultBtn = new Button("set as default");
            setDefaultBtn.getStyleClass().add("set-default-btn");
            setDefaultBtn.setOnAction(e -> handleSetDefault(index));
            badgeOrBtn = setDefaultBtn;
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        // Balance Section
        VBox balanceBox = new VBox(2);
        balanceBox.setAlignment(Pos.CENTER_RIGHT);
        balanceBox.setMinWidth(150);

        Label balanceTitle = new Label("Balance");
        balanceTitle.getStyleClass().add("balance-title");

        HBox currencyRow = new HBox(6);
        currencyRow.setAlignment(Pos.CENTER_RIGHT);
        Label currencyLabel = new Label(account.getCurrency());
        currencyLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #888;");
        Label balanceLabel = new Label(String.format("%.2f", account.getBalance()));
        balanceLabel.getStyleClass().add("balance-amount");
        currencyRow.getChildren().addAll(currencyLabel, balanceLabel);
        balanceBox.getChildren().addAll(balanceTitle, currencyRow);

        // Action Buttons
        Button editBtn = new Button("✏");
        editBtn.getStyleClass().add("action-btn-edit");
        editBtn.setOnAction(e -> handleEdit(index));

        Button deleteBtn = new Button("🗑");
        deleteBtn.getStyleClass().add("action-btn-delete");
        deleteBtn.setOnAction(e -> handleDelete(index));

        card.getChildren().addAll(iconCircle, nameLabel, badgeOrBtn, spacer, balanceBox, editBtn, deleteBtn);
        return card;
    }

    // HANDLERS

    @FXML private void handleAddAccount() {
        accounts.add(new AccountStored("New Account", "PHP", 0.00, false));
        renderAccounts();
    }

    private void handleSetDefault(int index) {
        for (AccountStored acc : accounts) acc.setDefault(false);
        accounts.get(index).setDefault(true);
        renderAccounts();
    }

    /**
     * Opens the Edit Modal and refreshes list if saved
     */
    private void handleEdit(int index) {
        try {
            // Load Edit FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csit228capstone/screens/EditAccountDialog.fxml"));
            VBox root = loader.load();

            // Setup Modal Window
            Stage stage = new Stage();
            stage.setTitle("Edit Account");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            // Pass data to Edit Controller
            EditAccountController controller = loader.getController();
            controller.setAccount(accounts.get(index));

            stage.showAndWait();

            // Refresh UI if save was clicked
            if (controller.isSaveClicked()) {
                renderAccounts();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(int index) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Account");
        confirm.setHeaderText("Delete \"" + accounts.get(index).getAccountName() + "\"?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            accounts.remove(index);
            renderAccounts();
        }
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}