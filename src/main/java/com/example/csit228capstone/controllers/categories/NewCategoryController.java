package com.example.csit228capstone.controllers.categories;

import com.example.csit228capstone.services.CategoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


// Same structure as NewAccountController
public class NewCategoryController {

    @FXML
    private TextField categoryNameField; // category name input

 //cancel
    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow(event);
    }

    // save
    // Validates then saves category name to DB.
    @FXML
    private void handleSave(ActionEvent event) {
        String name = categoryNameField.getText().trim();

        if (name.isEmpty()) {
            showError("Category name cannot be empty.");
            return;
        }

        CategoryService categoryService = new CategoryService();
        categoryService.addNewCategory(name);

        closeWindow(event);
    }



    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}