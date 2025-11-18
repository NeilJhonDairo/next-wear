package com.example.nextwear;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/nextwear/login-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());


        primaryStage.setMaximized(true);

        primaryStage.setScene(scene);
        primaryStage.setTitle("NEXT WEAR");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}