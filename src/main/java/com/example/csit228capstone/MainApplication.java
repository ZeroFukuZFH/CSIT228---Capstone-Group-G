package com.example.csit228capstone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/csit228capstone/screens/Accounts.fxml"));


        if (fxmlLoader.getLocation() == null) {
            System.err.println("Error: Dashboard.fxml not found! Check your path.");
            return;
        }

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}