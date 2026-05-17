
package com.example.csit228capstone.controllers.accounts;

import com.example.csit228capstone.data.Account;
import com.example.csit228capstone.services.AccountService;
import com.example.csit228capstone.session.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class AccountsController {

    @FXML
    private Button addAccountBtn;

    @FXML
    private VBox accountListContainer;

    private AccountService accountService;

    List<Account> accounts;

    @FXML
    public void initialize() {
        this.accountService = new AccountService();
        renderAccounts();
    }

    @FXML
    private void handleAddAccount(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csit228capstone/screens/NewAccountDialogue.fxml"));
        try {
            Parent root = loader.load();


            Stage smallStage = new Stage();
            smallStage.setTitle("Add New Transaction");
            smallStage.setResizable(false);

            Scene scene = new Scene(root);
            smallStage.setScene(scene);
            smallStage.showAndWait();

            renderAccounts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void renderAccounts(){
        this.accounts = accountService.getAllAccounts();
        accountListContainer.getChildren().clear();
        for (Account account : accounts){
            accountListContainer.getChildren().add(accountsContainer(account));
        }
    }

    private HBox accountsContainer(Account account){
        HBox row = new HBox(10);

        int sharedWidth = 120;
        row.setPadding(new Insets(10));
        row.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");
        row.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(account.getAccountName());
        titleLabel.setStyle("-fx-text-fill: black;");
        titleLabel.setPrefWidth(sharedWidth);

        Label amountLabel = new Label(String.valueOf(account.getCurrentBalance()));
        amountLabel.setStyle("-fx-text-fill: black;");
        amountLabel.setPrefWidth(sharedWidth);
        amountLabel.setAlignment(Pos.CENTER_RIGHT);

        // Create Edit button
        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
        editButton.setOnAction(event -> handleEditAccount(account));

        // Create Delete button
        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
        deleteButton.setOnAction(event -> handleDeleteAccount(account));

        // Create spacer region to push buttons to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        row.getChildren().addAll(titleLabel, amountLabel, spacer, editButton, deleteButton);
        row.setSpacing(10);
        return row;
    }

    private void handleEditAccount(Account account) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/csit228capstone/screens/EditAccountDialogue.fxml"));
        try {
            Parent root = loader.load();

            EditAccountController dialogController = loader.getController();
            dialogController.setOldAccount(account.getAccountName());

            Stage smallStage = new Stage();
            smallStage.setTitle("Edit Existing Transaction");
            smallStage.setWidth(400);
            smallStage.setHeight(300);
            smallStage.setResizable(false);

            Scene scene = new Scene(root);
            smallStage.setScene(scene);
            smallStage.showAndWait();

            renderAccounts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleDeleteAccount(Account account) {
        accountService.deleteAccount(account.getAccountName());
        renderAccounts();
    }



}