package com.example.csit228capstone.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.IOException;

public class CategoryRowController {

    @FXML private Label categoryIcon;
    @FXML private Label categoryTitle;
    @FXML private VBox subCategoryContainer;

    /** The root VBox of this row (used for removal from parent list). */
    @FXML private VBox rootVBox;

    private CategoriesController parentController;

    // ─── Setup ───────────────────────────────────────────────────────────────────

    /**
     * Populates the row with data and stores a reference to the main controller.
     */
    public void setData(String icon, String title, CategoriesController parentController) {
        this.parentController = parentController;
        categoryIcon.setText(icon);
        categoryTitle.setText(title);
    }

    // ─── Sub-category helpers ────────────────────────────────────────────────────

    /**
     * Programmatically adds a sub-category row inside this parent row.
     */
    public void addSubCategoryRow(String title) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/csit228capstone/screens/SubCategoryRow.fxml"));
            Node row = loader.load();

            SubCategoryRowController ctrl = loader.getController();
            ctrl.setData(title, this);

            subCategoryContainer.getChildren().add(row);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeSubCategoryRow(Node row) {
        subCategoryContainer.getChildren().remove(row);
    }

    // ─── FXML Handlers ───────────────────────────────────────────────────────────

    @FXML
    private void handleAddSubCategory() {
        if (parentController != null) {
            parentController.openCategoryDialog(true, this, null);
        }
    }

    @FXML
    private void handleEdit() {
        if (parentController != null) {
            String[] existing = {
                    categoryTitle.getText(),
                    "",                        // description — populate from model if available
                    "#1e5631",                 // color
                    categoryIcon.getText()
            };
            parentController.openCategoryDialog(false, null, existing);
        }
    }

    @FXML
    private void handleDelete() {
        if (parentController != null) {
            parentController.removeCategoryRow(rootVBox);
        }
    }
}