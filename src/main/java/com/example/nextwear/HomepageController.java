package com.example.nextwear;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class HomepageController {

    private static final double WINDOW_WIDTH = 1280;
    private static final double WINDOW_HEIGHT = 720;

    @FXML
    private void goHome(ActionEvent event) {
        try {
            Parent homeRoot = FXMLLoader.load(getClass().getResource("homepage-view.fxml"));
            Scene scene = new Scene(homeRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());

            stage.setScene(scene);
            stage.setMaximized(true);
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
            Scene scene = new Scene(shopRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());

            stage.setScene(scene);
            stage.setMaximized(true);
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
            Scene scene = new Scene(purchaseRoot, WINDOW_WIDTH, WINDOW_HEIGHT);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());

            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            System.out.println("✅ Navigated to My Purchase");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        try {
            System.out.println("Logout pressed - Returning to Login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/nextwear/login-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

            scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());

            stage.setScene(scene);
            stage.setMaximized(false);
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
        }
    }
}