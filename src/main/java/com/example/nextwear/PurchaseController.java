package com.example.nextwear;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PurchaseController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
