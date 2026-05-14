package com.example.csit228capstone.controllers.categories;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CategoriesController {

    @FXML
    private VBox categoryListContainer;

    // ─── Public helpers used by row controllers ──────────────────────────────────
    public void addCategoryRow(String icon, String title, String[] subCategories) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/csit228capstone/screens/CategoryRow.fxml"));
            Node row = loader.load();

            CategoryRowController ctrl = loader.getController();
            ctrl.setData(icon, title, this);

            for (String sub : subCategories) {
                ctrl.addSubCategoryRow(sub);
            }

            categoryListContainer.getChildren().add(row);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void removeCategoryRow(Node row) {
        categoryListContainer.getChildren().remove(row);
    }

    // ─── FXML Handlers ───────────────────────────────────────────────────────────

    @FXML
    private void handleAddCategory() {
        openCategoryDialog(false, null, null);
    }

    // ─── Dialog helpers ──────────────────────────────────────────────────────────

    public void openCategoryDialog(boolean isSubCategory,
                                   CategoryRowController parentCtrl,
                                   String[] existingData) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/com/example/csit228capstone/screens/NewCategoryDialog.fxml"));
            Parent root = loader.load();

            NewCategoryDialogController dialogCtrl = loader.getController();
            dialogCtrl.configure(isSubCategory, existingData, result -> {
                // result[0] = title, result[1] = description,
                // result[2] = color hex, result[3] = icon
                if (existingData != null) {
                    // editing — delegate back to the row that opened the dialog
                    // (handled inside CategoryRowController / SubCategoryRowController)
                } else if (isSubCategory && parentCtrl != null) {
                    parentCtrl.addSubCategoryRow(result[0]);
                } else {
                    addCategoryRow(result[3], result[0], new String[]{});
                }
            });

            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.setTitle(isSubCategory ? "New Sub Category" : "New Category");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}