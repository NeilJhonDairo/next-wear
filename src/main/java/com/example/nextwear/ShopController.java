package com.example.nextwear;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ShopController {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox productSection;

    // SCROLL TO PRODUCTS
    @FXML
    private void scrollToProducts() {
        double targetV = productSection.getLayoutY() /
                (scrollPane.getContent().getBoundsInLocal().getHeight() - scrollPane.getViewportBounds().getHeight());

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(scrollPane.vvalueProperty(), targetV);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    // PRODUCT MODAL
    @FXML
    private void showProductModal(MouseEvent event) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);

        VBox clickedProduct = (VBox) event.getSource();

        String productName = ((Label) clickedProduct.getChildren().get(1)).getText();
        String productPrice = ((Label) clickedProduct.getChildren().get(2)).getText();
        Image productImage = ((ImageView) clickedProduct.getChildren().get(0)).getImage();

        ImageView imageView = new ImageView(productImage);
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);

        Label nameLabel = new Label(productName);
        nameLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label priceLabel = new Label(productPrice);
        priceLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #3b82f6;");

        //  ComboBox
        ComboBox<String> sizeBox = new ComboBox<>();
        sizeBox.getItems().addAll("Small", "Medium", "Large", "XL");
        sizeBox.setPromptText("Select Size");
        sizeBox.setPrefWidth(200);       // bigger width
        sizeBox.setStyle("-fx-font-size: 18px; -fx-padding: 10;"); // larger text

        // Add to Cart Button
        Button addBtn = new Button("Add to Cart");
        addBtn.setStyle(
                "-fx-background-color: #2979FF; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 15 40; " +   // bigger padding
                        "-fx-font-size: 18px; " +  // bigger text
                        "-fx-background-radius: 10;"
        );
        addBtn.setOnAction(e -> modal.close());

        //  Cancel Button
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: #1f1f1f; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 15 40; " +   // bigger padding
                        "-fx-font-size: 18px; " +  // bigger text
                        "-fx-background-radius: 10;"
        );
        cancelBtn.setOnAction(e -> modal.close());

        HBox buttonBox = new HBox(20, addBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(25, imageView, nameLabel, priceLabel, sizeBox, buttonBox);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #111111;");

        Scene scene = new Scene(layout, 500, 600);
        modal.setTitle(productName);
        modal.setScene(scene);
        modal.showAndWait();
    }
}