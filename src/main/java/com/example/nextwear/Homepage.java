package com.example.nextwear;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Homepage extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/homepage-view.fxml"));
        System.out.println(getClass().getResource("/homepage-view.fxml")); // 👈 Add this line
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("NextWear App");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
