package com.example.csit228capstone.controllers;

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
import java.util.function.Consumer;

public class NewCategoryDialogController implements Initializable {

    @FXML private Label dialogTitle;
    @FXML private Label dateLabel;
    @FXML private TextField titleField;
    @FXML private TextField descriptionField;
    @FXML private ColorPicker colorPicker;
    @FXML private StackPane iconPickerBox;
    @FXML private Label iconPreviewLabel;

    private Consumer<String[]> onSaveCallback;
    private boolean isSubCategory = false;

    // ─── Initializable ───────────────────────────────────────────────────────────

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set today's date
        dateLabel.setText(LocalDate.now()
                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));

        // Default color to red (matching the mockup)
        colorPicker.setValue(Color.RED);
    }

    // ─── Configuration ───────────────────────────────────────────────────────────

    /**
     * Configures the dialog before it is shown.
     *
     * @param isSubCategory  true  → "New Sub Category", false → "New Category"
     * @param existingData   non-null when editing; [title, description, colorHex, icon]
     * @param onSave         callback receiving [title, description, colorHex, icon]
     */
    public void configure(boolean isSubCategory,
                          String[] existingData,
                          Consumer<String[]> onSave) {
        this.isSubCategory = isSubCategory;
        this.onSaveCallback = onSave;

        dialogTitle.setText(isSubCategory ? "New Sub Category" : "New Category");

        if (existingData != null) {
            titleField.setText(existingData[0]);
            descriptionField.setText(existingData[1]);
            if (existingData[2] != null && !existingData[2].isEmpty()) {
                try {
                    colorPicker.setValue(Color.web(existingData[2]));
                } catch (IllegalArgumentException ignored) { }
            }
            if (existingData[3] != null) {
                iconPreviewLabel.setText(existingData[3]);
            }
        }
    }

    // ─── FXML Handlers ───────────────────────────────────────────────────────────

    @FXML
    private void handleIconPicker() {
        // TODO: open an emoji / icon chooser popup.
        // For now, cycle through a small demo set.
        String[] icons = {"🏠", "🍔", "🚗", "💊", "✈️", "📚", "🎮", "👔"};
        String current = iconPreviewLabel.getText();
        int idx = 0;
        for (int i = 0; i < icons.length; i++) {
            if (icons[i].equals(current)) { idx = (i + 1) % icons.length; break; }
        }
        iconPreviewLabel.setText(icons[idx]);
    }

    @FXML
    private void handleSave() {
        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            titleField.setStyle(titleField.getStyle()
                    + " -fx-border-color: #cc3333;");
            return;
        }

        String description = descriptionField.getText().trim();
        String colorHex = toHex(colorPicker.getValue());
        String icon = iconPreviewLabel.getText();

        if (onSaveCallback != null) {
            onSaveCallback.accept(new String[]{title, description, colorHex, icon});
        }

        closeDialog();
    }

    @FXML
    private void handleCancel() {
        closeDialog();
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────────

    private void closeDialog() {
        Stage stage = (Stage) dialogTitle.getScene().getWindow();
        stage.close();
    }

    /** Converts a JavaFX Color to a CSS hex string (#rrggbb). */
    private String toHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (color.getRed()   * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue()  * 255));
    }
}
