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

    private static final double WINDOW_WIDTH = 1280;
    private static final double WINDOW_HEIGHT = 720;

    @FXML
    private void onLogin(ActionEvent event) throws IOException {
        loadScene("/com/example/nextwear/homepage-view.fxml", event, true);
    }

    @FXML
    private void onForgotPassword(ActionEvent event) throws IOException {
        loadScene("/com/example/nextwear/forgot-password.fxml", event, false);
    }

    // Add this method for Sign Up
    @FXML
    private void onSignup(ActionEvent event) throws IOException {
        loadScene("/com/example/nextwear/sign-up-view.fxml", event, true);
    }

    private void loadScene(String fxmlPath, ActionEvent event, boolean fullscreen) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));

            //  Check if the file exists
            if (loader.getLocation() == null) {
                System.out.println("❌ File not found: " + fxmlPath);
                return;
            }

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

            // Add CSS
            scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());

            stage.setScene(scene);

            if (fullscreen) {
                stage.setMaximized(true);
            } else {
                stage.setMaximized(false);
                stage.centerOnScreen();
            }

            stage.show();
            System.out.println("✅ Successfully loaded: " + fxmlPath);

        } catch (IOException e) {
        }
    }
}