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
        // loadPlaceholderAccounts(); // removed — start empty
        renderAccounts();
    }

    private void renderAccounts() {
        accountListContainer.getChildren().clear();

        // --- EMPTY STATE ---
        if (accounts.isEmpty()) {
            VBox emptyState = new VBox(12);
            emptyState.setAlignment(Pos.CENTER);
            emptyState.setPadding(new Insets(80, 0, 80, 0));

            Label icon = new Label("🏦");
            icon.setStyle("-fx-font-size: 48px;");

            Label title = new Label("No accounts yet");
            title.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #1a1a2e;");

            Label subtitle = new Label("Click \"add account +\" to get started.");
            subtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #888888;");

            emptyState.getChildren().addAll(icon, title, subtitle);
            accountListContainer.getChildren().add(emptyState);
            return;
        }

        for (int i = 0; i < accounts.size(); i++) {
            accountListContainer.getChildren().add(createAccountCard(accounts.get(i), i));
        }
    }

    private HBox createAccountCard(AccountStored account, int index) {
        HBox card = new HBox(16);
        card.setAlignment(Pos.CENTER_LEFT);
        card.getStyleClass().add("account-card");
        card.setPadding(new Insets(18, 20, 18, 20));

        StackPane iconCircle = new StackPane();
        iconCircle.setMinSize(52, 52);
        iconCircle.getStyleClass().add("icon-circle");
        Label iconLabel = new Label("⌂");
        iconLabel.getStyleClass().add("icon-label");
        iconCircle.getChildren().add(iconLabel);

        Label nameLabel = new Label(account.getAccountName());
        nameLabel.getStyleClass().add("account-name-label");
        nameLabel.setMinWidth(140);

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
        HBox.setHgrow(spacer, Priority.ALWAYS);

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

        Button editBtn = new Button("✏");
        editBtn.getStyleClass().add("action-btn-edit");
        editBtn.setOnAction(e -> handleEdit(index));

        Button deleteBtn = new Button("🗑");
        deleteBtn.getStyleClass().add("action-btn-delete");
        deleteBtn.setOnAction(e -> handleDelete(index));

        card.getChildren().addAll(iconCircle, nameLabel, badgeOrBtn, spacer, balanceBox, editBtn, deleteBtn);
        return card;
    }

    @FXML
    private void handleAddAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/csit228capstone/screens/EditAccountDialog.fxml")
            );
            VBox root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("New Account");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            EditAccountController controller = loader.getController(); // FIXED: was NewAccountController
            controller.setAccount(new AccountStored("", "PHP", 0.00, false)); // blank account for add mode

            stage.showAndWait();

            if (controller.isSaveClicked()) {
                AccountStored newAccount = controller.getAccount(); // FIXED: was controller.getResult()
                if (newAccount != null && !newAccount.getAccountName().isEmpty()) {
                    accounts.add(newAccount);
                    renderAccounts();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleSetDefault(int index) {
        for (AccountStored acc : accounts) acc.setDefault(false);
        accounts.get(index).setDefault(true);
        renderAccounts();
    }

    private void handleEdit(int index) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/csit228capstone/screens/EditAccountDialog.fxml")
            );
            VBox root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Edit Account");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            EditAccountController controller = loader.getController();
            controller.setAccount(accounts.get(index));

            stage.showAndWait();

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
}