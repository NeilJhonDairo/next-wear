package com.example.nextwear;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void onLogin() {
        System.out.println("Email: " + emailField.getText());
        System.out.println("Password: " + passwordField.getText());
    }
}
