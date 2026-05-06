package org.CSIT228CAPSTONE;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class MainViewAccount extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AccountsView.fxml")));
        primaryStage.setTitle("Accounts Window");

        try {
            Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Gemini_Generated_Image_b15vkdb15v.png")));
            primaryStage.getIcons().add(logo);
        } catch (NullPointerException e) {
            System.err.println("Failed to load window icon. Verify the file name in resources.");
        }

        Scene scene = new Scene(root, 950, 650);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style.css")).toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}