package com.example.csit228capstone.controllers.categories;

import com.example.csit228capstone.services.CategoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


// Same structure as EditAccountController
public class EditCategoryController {

    @FXML
    private TextField categoryNameField; //  existing name

    private String oldCategoryName; //  to find the correct row in DB


    //data
    public void setCategory(String categoryName) {
        this.oldCategoryName = categoryName;
        categoryNameField.setText(categoryName);
    }


    @FXML
    private void handleCancel(ActionEvent event) {
        closeWindow(event);
    }


    //  updates the category name in DB
    @FXML
    private void handleSave(ActionEvent event) {
        String newName = categoryNameField.getText().trim();

        if (newName.isEmpty()) {
            showError("Category name cannot be empty.");
            return;
        }

        CategoryService categoryService = new CategoryService();
        categoryService.editCategory(oldCategoryName, newName);

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