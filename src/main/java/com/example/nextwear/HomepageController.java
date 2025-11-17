package com.example.nextwear;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class HomepageController {

    @FXML
    private void goHome(ActionEvent event) {
        try {
            // Reload home page to ensure fresh state
            Parent homeRoot = FXMLLoader.load(getClass().getResource("homepage-view.fxml"));
            Scene scene = new Scene(homeRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            System.out.println("✅ Navigated to Home");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goShop(ActionEvent event) {
        try {
            Parent shopRoot = FXMLLoader.load(getClass().getResource("shop-view.fxml"));
            Scene scene = new Scene(shopRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            System.out.println("✅ Navigated to Shop");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goPurchase(ActionEvent event) {
        try {
            Parent purchaseRoot = FXMLLoader.load(getClass().getResource("purchase-item-view.fxml"));
            Scene scene = new Scene(purchaseRoot);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            System.out.println("✅ Navigated to My Purchase");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        System.out.println("Logout pressed.");
        // Add logout logic here if needed
    }
}