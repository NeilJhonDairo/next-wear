package com.example.nextwear;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomepageApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/homepage-view.fxml"));
        Scene scene = new Scene(loader.load(), 1280, 720);

        // Load CSS
        scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());

        // Set up stage
        primaryStage.setTitle("NextWear App");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
