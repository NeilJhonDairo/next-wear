package com.example.nextwear;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

public class SignupController {

    private static final double WINDOW_WIDTH = 1280;
    private static final double WINDOW_HEIGHT = 720;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void onSignup(ActionEvent event) {
        if (validateSignup()) {
            // Add your signup logic here (database save, etc.)
            showSuccessAlert("Sign Up Successful", "Your account has been created successfully!");

            // Redirect to login page after successful signup
            try {
                goToLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onBackToLogin(ActionEvent event) throws IOException {
        goToLogin(event);
    }

    private boolean validateSignup() {
        if (fullNameField.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter your full name.");
            return false;
        }

        if (emailField.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter your email.");
            return false;
        }

        if (passwordField.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter your password.");
            return false;
        }

        if (confirmPasswordField.getText().isEmpty()) {
            showAlert("Validation Error", "Please confirm your password.");
            return false;
        }

        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            showAlert("Validation Error", "Passwords do not match.");
            return false;
        }

        if (passwordField.getText().length() < 6) {
            showAlert("Validation Error", "Password must be at least 6 characters long.");
            return false;
        }

        return true;
    }

    private void goToLogin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/nextwear/login-view.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        // Add CSS
        scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}