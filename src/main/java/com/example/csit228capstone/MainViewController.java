package com.example.csit228capstone;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainViewController {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML
    protected void onAddAccount(ActionEvent event) throws IOException {
        // convert to singleton later for easy screen traversal
        root = FXMLLoader.load(getClass().getResource("AddAccount.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
