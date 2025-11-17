package com.example.nextwear;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private void onLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/nextwear/homepage-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Create scene
        Scene scene = new Scene(root, 1280, 720);

        // ADD CSS PROPERLY HERE
        scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        stage.centerOnScreen();
    }






    @FXML
    private void onForgotPassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/nextwear/forgot-password.fxml"));

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 500, 400)); // ✔ centered small window
        stage.centerOnScreen();
        stage.show();
    }
}
