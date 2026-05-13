package com.example.csit228capstone.controllers.categories;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SubCategoryRowController {

    @FXML private Label subCategoryTitle;
    @FXML private HBox rootHBox;

    private CategoryRowController parentRowController;

    // ─── Setup ───────────────────────────────────────────────────────────────────

    public void setData(String title, CategoryRowController parentRowController) {
        this.parentRowController = parentRowController;
        subCategoryTitle.setText(title);
    }

    // ─── FXML Handlers ───────────────────────────────────────────────────────────

    @FXML
    private void handleEdit() {
        // Editing a sub-category: open dialog pre-filled with current title
        // For now, just print to console; wire up to CategoriesController as needed
        System.out.println("Edit sub-category: " + subCategoryTitle.getText());
    }

    @FXML
    private void handleDelete() {
        if (parentRowController != null) {
            parentRowController.removeSubCategoryRow(rootHBox);
        }
    }
}
