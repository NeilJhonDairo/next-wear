package com.example.nextwear;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import java.util.HashMap;
import java.util.Map;

public class ShopController {

    private static final double WINDOW_WIDTH = 1280;
    private static final double WINDOW_HEIGHT = 720;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox productSection;

    // Cart to store items
    private Map<String, CartItem> cart = new HashMap<>();
    private int cartItemCount = 0;

    public static class CartItem {
        private String productName;
        private String price;
        private String size;
        private int quantity;

        public CartItem(String productName, String price, String size, int quantity) {
            this.productName = productName;
            this.price = price;
            this.size = size;
            this.quantity = quantity;
        }

        // Getters
        public String getProductName() { return productName; }
        public String getPrice() { return price; }
        public String getSize() { return size; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }

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

    // PRODUCT MODAL WITH ADD TO CART FUNCTIONALITY
    @FXML
    private void showProductModal(MouseEvent event) {
        VBox clickedProduct = (VBox) event.getSource();
        showProductModalWithData(clickedProduct);
    }

    @FXML
    private void showProductModal(ActionEvent event) {
        Button button = (Button) event.getSource();
        VBox productCard = (VBox) button.getParent();
        showProductModalWithData(productCard);
    }

    private void showProductModalWithData(VBox productCard) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);

        String productName = ((Label) productCard.getChildren().get(1)).getText();
        String productPrice = ((Label) productCard.getChildren().get(2)).getText();
        Image productImage = ((ImageView) productCard.getChildren().get(0)).getImage();

        ImageView imageView = new ImageView(productImage);
        imageView.setFitWidth(350);
        imageView.setPreserveRatio(true);

        Label nameLabel = new Label(productName);
        nameLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label priceLabel = new Label(productPrice);
        priceLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #3b82f6;");

        // Size ComboBox
        ComboBox<String> sizeBox = new ComboBox<>();
        sizeBox.getItems().addAll("Small", "Medium", "Large", "XL");
        sizeBox.setPromptText("Select Size");
        sizeBox.setPrefWidth(200);
        sizeBox.setStyle("-fx-font-size: 18px; -fx-padding: 10;");

        // Quantity Spinner
        Label quantityLabel = new Label("Quantity:");
        quantityLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        Spinner<Integer> quantitySpinner = new Spinner<>(1, 10, 1);
        quantitySpinner.setStyle("-fx-font-size: 16px; -fx-pref-width: 100;");

        // Add to Cart Button
        Button addBtn = new Button("Add to Cart");
        addBtn.setStyle(
                "-fx-background-color: #2979FF; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 15 40; " +
                        "-fx-font-size: 18px; " +
                        "-fx-background-radius: 10;"
        );
        addBtn.setOnAction(e -> {
            if (sizeBox.getValue() == null) {
                showAlert("Error", "Please select a size before adding to cart.");
                return;
            }

            String size = sizeBox.getValue();
            int quantity = quantitySpinner.getValue();
            addToCart(productName, productPrice, size, quantity);
            updateCartButton();
            modal.close();
            showSuccessAlert("Success", quantity + " " + productName + "(s) added to cart!");
        });

        // Cancel Button
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: #1f1f1f; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 15 40; " +
                        "-fx-font-size: 18px; " +
                        "-fx-background-radius: 10;"
        );
        cancelBtn.setOnAction(e -> modal.close());

        HBox buttonBox = new HBox(20, addBtn, cancelBtn);
        buttonBox.setAlignment(Pos.CENTER);

        VBox quantityBox = new VBox(5, quantityLabel, quantitySpinner);
        quantityBox.setAlignment(Pos.CENTER_LEFT);

        VBox layout = new VBox(20, imageView, nameLabel, priceLabel, sizeBox, quantityBox, buttonBox);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #111111;");

        Scene scene = new Scene(layout, 500, 650);
        modal.setTitle("Product Details - " + productName);
        modal.setScene(scene);
        modal.showAndWait();
    }

    // ADD TO CART METHOD
    private void addToCart(String productName, String price, String size, int quantity) {
        String key = productName + "_" + size;
        if (cart.containsKey(key)) {
            CartItem existingItem = cart.get(key);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            cart.put(key, new CartItem(productName, price, size, quantity));
        }
        cartItemCount += quantity;
    }

    // UPDATE CART BUTTON
    private void updateCartButton() {
        System.out.println("Cart updated! Total items: " + cartItemCount);
    }

    // SHOW CART WITH ADDRESS AND PAYMENT
    @FXML
    private void openCart(ActionEvent event) {
        if (cart.isEmpty()) {
            showAlert("Cart Empty", "Your cart is empty. Add some items first!");
            return;
        }

        Stage cartStage = new Stage();
        cartStage.initModality(Modality.APPLICATION_MODAL);
        cartStage.setTitle("Shopping Cart - Checkout");

        // CART ITEMS SECTION
        VBox cartItemsBox = new VBox(10);
        cartItemsBox.setPadding(new Insets(20));
        cartItemsBox.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 10;");

        double totalAmount = 0;

        for (CartItem item : cart.values()) {
            HBox itemBox = new HBox(15);
            itemBox.setAlignment(Pos.CENTER_LEFT);
            itemBox.setStyle("-fx-padding: 10; -fx-background-color: #252525; -fx-background-radius: 8;");

            Label itemInfo = new Label(item.getProductName() + " (" + item.getSize() + ") x" + item.getQuantity());
            itemInfo.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
            itemInfo.setPrefWidth(250);

            Label itemPrice = new Label(item.getPrice());
            itemPrice.setStyle("-fx-text-fill: #3b82f6; -fx-font-size: 14px;");

            // Remove button
            Button removeBtn = new Button("❌");
            removeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: red; -fx-cursor: hand;");
            removeBtn.setOnAction(e -> {
                cartItemCount -= item.getQuantity();
                cart.remove(item.getProductName() + "_" + item.getSize());
                cartStage.close();
                openCart(event);
            });

            itemBox.getChildren().addAll(itemInfo, itemPrice, removeBtn);
            cartItemsBox.getChildren().add(itemBox);

            // Calculate total
            String priceStr = item.getPrice().replace("₱", "").trim();
            totalAmount += Double.parseDouble(priceStr) * item.getQuantity();
        }

        Label totalLabel = new Label("Total: ₱" + String.format("%.2f", totalAmount));
        totalLabel.setStyle("-fx-text-fill: #3ad13a; -fx-font-size: 20px; -fx-font-weight: bold;");

        // ADDRESS SECTION
        VBox addressBox = new VBox(10);
        addressBox.setPadding(new Insets(20));
        addressBox.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 10;");

        Label addressTitle = new Label("📦 Shipping Address");
        addressTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");
        fullNameField.setStyle("-fx-pref-width: 300; -fx-font-size: 14px;");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        phoneField.setStyle("-fx-pref-width: 300; -fx-font-size: 14px;");

        TextArea addressArea = new TextArea();
        addressArea.setPromptText("Complete Address (Street, Barangay, City, Province)");
        addressArea.setPrefRowCount(3);
        addressArea.setStyle("-fx-pref-width: 300; -fx-font-size: 14px;");

        addressBox.getChildren().addAll(addressTitle, fullNameField, phoneField, addressArea);

        // PAYMENT SECTION
        VBox paymentBox = new VBox(10);
        paymentBox.setPadding(new Insets(20));
        paymentBox.setStyle("-fx-background-color: #1a1a1a; -fx-background-radius: 10;");

        Label paymentTitle = new Label("💳 Payment Method");
        paymentTitle.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        ToggleGroup paymentGroup = new ToggleGroup();

        RadioButton codRadio = new RadioButton("💰 Cash on Delivery (COD)");
        codRadio.setToggleGroup(paymentGroup);
        codRadio.setSelected(true);
        codRadio.setStyle("-fx-text-fill: white;");

        RadioButton gcashRadio = new RadioButton("📱 GCash");
        gcashRadio.setToggleGroup(paymentGroup);
        gcashRadio.setStyle("-fx-text-fill: white;");

        RadioButton cardRadio = new RadioButton("💳 Credit/Debit Card");
        cardRadio.setToggleGroup(paymentGroup);
        cardRadio.setStyle("-fx-text-fill: white;");

        paymentBox.getChildren().addAll(paymentTitle, codRadio, gcashRadio, cardRadio);

        // ACTION BUTTONS
        HBox actionButtons = new HBox(20);
        actionButtons.setAlignment(Pos.CENTER);

        Button checkoutBtn = new Button("✅ Place Order");
        checkoutBtn.setStyle(
                "-fx-background-color: #3ad13a; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 12 30; " +
                        "-fx-background-radius: 10;"
        );

        final double finalTotal = totalAmount;
        checkoutBtn.setOnAction(e -> {
            if (validateCheckout(fullNameField, phoneField, addressArea)) {
                processOrder(fullNameField.getText(), phoneField.getText(), addressArea.getText(),
                        getSelectedPayment(paymentGroup), finalTotal);
                cartStage.close();
            }
        });

        Button cancelBtn = new Button("❌ Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: #ff4757; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 12 30; " +
                        "-fx-background-radius: 10;"
        );
        cancelBtn.setOnAction(e -> cartStage.close());

        actionButtons.getChildren().addAll(cancelBtn, checkoutBtn);

        // MAIN LAYOUT
        VBox mainLayout = new VBox(20, cartItemsBox, totalLabel, addressBox, paymentBox, actionButtons);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: #0E0E0E;");

        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #0E0E0E;");

        Scene scene = new Scene(scrollPane, 600, 700);
        cartStage.setScene(scene);
        cartStage.showAndWait();
    }

    // VALIDATE CHECKOUT FORM
    private boolean validateCheckout(TextField name, TextField phone, TextArea address) {
        if (name.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter your full name.");
            return false;
        }
        if (phone.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter your phone number.");
            return false;
        }
        if (address.getText().isEmpty()) {
            showAlert("Validation Error", "Please enter your complete address.");
            return false;
        }
        return true;
    }

    // GET SELECTED PAYMENT METHOD
    private String getSelectedPayment(ToggleGroup group) {
        RadioButton selected = (RadioButton) group.getSelectedToggle();
        return selected != null ? selected.getText() : "Cash on Delivery";
    }

    // PROCESS ORDER METHOD
    private void processOrder(String name, String phone, String address, String payment, double total) {
        // Convert cart to simple map for PurchaseController
        Map<String, Object> cartItemsMap = new HashMap<>();
        for (Map.Entry<String, CartItem> entry : cart.entrySet()) {
            cartItemsMap.put(entry.getKey(), entry.getValue());
        }

        // Send order to purchase history
        PurchaseController.addOrderFromCart(name, phone, address, payment, total, cartItemsMap);

        // Clear cart
        cart.clear();
        cartItemCount = 0;
        updateCartButton();

        // Show success message
        showSuccessAlert("Order Placed!",
                "🎉 Thank you for your order!\n\n" +
                        "📦 Order Summary:\n" +
                        "• Customer: " + name + "\n" +
                        "• Phone: " + phone + "\n" +
                        "• Address: " + address + "\n" +
                        "• Payment: " + payment + "\n" +
                        "• Total: ₱" + String.format("%.2f", total) + "\n\n" +
                        "✅ Your order has been added to purchase history!\n" +
                        "Check 'My Purchase' page to view your orders.");
    }

    // NAVIGATION METHODS
    @FXML
    private void goHome(ActionEvent event) {
        System.out.println("🔄 Navigating to Home...");
        loadPage("homepage-view.fxml", event);
    }

    @FXML
    private void goShop(ActionEvent event) {
        System.out.println("Already in Shop");
    }

    @FXML
    private void goPurchase(ActionEvent event) {
        System.out.println("🔄 Navigating to My Purchase...");
        loadPage("purchase-item-view.fxml", event);
    }

    //Goes back to login page
    @FXML
    private void logout(ActionEvent event) {
        try {
            System.out.println("Logout pressed from Shop - Returning to Login");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/nextwear/login-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

            // Add CSS
            scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());

            stage.setScene(scene);
            stage.setMaximized(false); // Dili fullscreen ang login
            stage.centerOnScreen();
            stage.show();

        } catch (Exception e) {
            System.out.println("❌ Error during logout: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // NAVIGATION METHOD
    private void loadPage(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

            // Add CSS
            scene.getStylesheets().add(getClass().getResource("/com/example/nextwear/application.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
            System.out.println("✅ Successfully navigated to: " + fxmlFile);
        } catch (Exception e) {
            System.out.println("❌ Error navigating to " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ALERT METHODS
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