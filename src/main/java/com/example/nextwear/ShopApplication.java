package com.example.nextwear;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShopApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load FXML
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/com/example/nextwear/shop-view.fxml")
        );

        // Create scene
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);

        // Add CSS
        scene.getStylesheets().add(
                getClass().getResource("/application.css").toExternalForm()
        );

        // Configure stage
        stage.setTitle("NextWear Shop");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
