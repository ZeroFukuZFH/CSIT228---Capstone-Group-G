package com.example.csit228capstone.controllers.categories;

import com.example.csit228capstone.services.CategoryService;
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


// Same structure sa AccountsController.
public class CategoriesController {

    @FXML
    private Button addCategoryBtn;

    @FXML
    private VBox categoryListContainer; // holds all rows

    private CategoryService categoryService;

    List<String> categories; // growable list nato


    @FXML
    public void initialize() {
        this.categoryService = new CategoryService();
        renderCategories();
    }

    // button

    @FXML
    private void handleAddCategory(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/example/csit228capstone/screens/NewCategoryDialogue.fxml"));
        try {
            Parent root = loader.load();

            Stage dialog = new Stage();
            dialog.setTitle("Add New Category");
            dialog.setWidth(400);
            dialog.setHeight(300);
            dialog.setResizable(false);
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

            renderCategories();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // AI
    // Fetches all category names from DB and rebuilds the list
    // Called on init, after add, after edit, and after delete.
    private void renderCategories() {
        this.categories = categoryService.getAllCategories();
        categoryListContainer.getChildren().clear();

        for (String categoryName : categories) {
            categoryListContainer.getChildren().add(buildCategoryRow(categoryName));
        }
    }


     // Row section
    private HBox buildCategoryRow(String categoryName) {
        HBox row = new HBox(10);
        row.setPadding(new Insets(10));
        row.setStyle("-fx-border-color: lightgray; -fx-border-width: 0 0 1 0;");
        row.setAlignment(Pos.CENTER_LEFT);


        Label nameLabel = new Label(categoryName);
        nameLabel.setStyle("-fx-text-fill: black;");
        nameLabel.setPrefWidth(200);


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
        editButton.setOnAction(event -> handleEditCategory(categoryName));


        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 5 15 5 15; -fx-background-radius: 5;");
        deleteButton.setOnAction(event -> handleDeleteCategory(categoryName));

        row.getChildren().addAll(nameLabel, spacer, editButton, deleteButton);
        return row;
    }


    // edit page
    private void handleEditCategory(String categoryName) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/example/csit228capstone/screens/EditCategoryDialogue.fxml"));
        try {
            Parent root = loader.load();

            EditCategoryController dialogController = loader.getController();
            dialogController.setCategory(categoryName);

            Stage dialog = new Stage();
            dialog.setTitle("Edit Category");
            dialog.setWidth(400);
            dialog.setHeight(300);
            dialog.setResizable(false);
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

            renderCategories(); // refresh list
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

   //delete
    private void handleDeleteCategory(String categoryName) {
        categoryService.deleteCategory(categoryName);
        renderCategories();
    }
}