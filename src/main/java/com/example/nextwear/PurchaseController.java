package com.example.nextwear;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.util.*;

public class PurchaseController {

    // Store purchase history
    private static List<PurchaseOrder> purchaseHistory = new ArrayList<>();

    @FXML
    private VBox purchaseItemsContainer;

    @FXML
    public void initialize() {
        System.out.println("✅ PurchaseController initialized");

        // Update stats based on actual purchase history
        updatePurchaseStats();
        loadPurchaseHistory();
    }

    // ADD THIS METHOD TO UPDATE STATS DYNAMICALLY
    private void updatePurchaseStats() {
        int totalOrders = purchaseHistory.size();
        double totalSpent = purchaseHistory.stream().mapToDouble(PurchaseOrder::getTotalPrice).sum();
        long deliveredCount = purchaseHistory.stream()
                .filter(order -> "delivered".equalsIgnoreCase(order.getStatus()))
                .count();

        // You'll need to add fx:id for these labels in FXML to update them dynamically
        System.out.println("📊 Stats - Orders: " + totalOrders + ", Spent: ₱" + totalSpent + ", Delivered: " + deliveredCount);
    }

    @FXML
    private void goHome(ActionEvent event) {
        navigateTo("homepage-view.fxml", event);
    }

    @FXML
    private void goShop(ActionEvent event) {
        navigateTo("shop-view.fxml", event);
    }

    @FXML
    private void goPurchase(ActionEvent event) {
        System.out.println("Already in My Purchase");
        loadPurchaseHistory();
    }

    @FXML
    private void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be redirected to the login page.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            showInfoAlert("Logged Out", "You have been successfully logged out!");
            goHome(event);
        }
    }

    @FXML
    private void closePage(ActionEvent event) {
        navigateTo("homepage-view.fxml", event);
    }

    @FXML
    private void viewPurchaseDetails(ActionEvent event) {
        showInfoAlert("Order Details",
                "📦 Order Details:\n\n" +
                        "• Product: Rise Above T-Shirt\n" +
                        "• Price: ₱150\n" +
                        "• Size: Large\n" +
                        "• Status: ✅ Delivered\n" +
                        "• Order Date: September 25, 2025");
    }

    @FXML
    private void refreshPurchases(ActionEvent event) {
        loadPurchaseHistory();
        showInfoAlert("Refreshed", "Your purchase history has been updated!");
    }

    @FXML
    private void contactSupport(ActionEvent event) {
        showInfoAlert("Customer Support",
                "📞 Contact Support:\n\n" +
                        "• Email: support@nextwear.com\n" +
                        "• Phone: +63 912 345 6789");
    }

    // LOAD PURCHASE HISTORY
    private void loadPurchaseHistory() {
        if (purchaseItemsContainer == null) {
            System.out.println("❌ purchaseItemsContainer is null - check FXML fx:id");
            return;
        }

        purchaseItemsContainer.getChildren().clear();

        if (purchaseHistory.isEmpty()) {
            Label emptyLabel = new Label("No purchases yet. Start shopping!");
            emptyLabel.setStyle("-fx-text-fill: #B0B0B0; -fx-font-size: 18px; -fx-padding: 40;");
            purchaseItemsContainer.getChildren().add(emptyLabel);
            return;
        }

        for (PurchaseOrder order : purchaseHistory) {
            VBox purchaseCard = createPurchaseCard(order);
            purchaseItemsContainer.getChildren().add(purchaseCard);
        }
    }

    // CREATE PURCHASE CARD
    private VBox createPurchaseCard(PurchaseOrder order) {
        VBox card = new VBox(15);
        card.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 20; -fx-background-radius: 15;");
        card.setOnMouseClicked(e -> showOrderDetails(order));

        HBox contentBox = new HBox(20);
        contentBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Product Image
        ImageView imageView = new ImageView(order.getProductImage());
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);

        // Product Info
        VBox infoBox = new VBox(8);
        infoBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        infoBox.setPrefWidth(400);

        Label productName = new Label(order.getProductName());
        productName.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");

        Label productDetails = new Label("Size: " + order.getSize() + " • Qty: " + order.getQuantity());
        productDetails.setStyle("-fx-font-size: 14px; -fx-text-fill: #cfcfcf;");

        Label price = new Label("₱ " + String.format("%.2f", order.getTotalPrice()));
        price.setStyle("-fx-font-size: 20px; -fx-text-fill: #1fb7ff; -fx-font-weight: bold;");

        infoBox.getChildren().addAll(productName, productDetails, price);

        // Order Status
        VBox statusBox = new VBox(5);
        statusBox.setAlignment(javafx.geometry.Pos.CENTER_RIGHT);

        Label status = new Label(order.getStatus());
        status.setStyle("-fx-font-size: 16px; -fx-text-fill: " + getStatusColor(order.getStatus()) +
                "; -fx-font-weight: bold; -fx-padding: 5 15; -fx-background-color: " +
                getStatusBackgroundColor(order.getStatus()) + "; -fx-background-radius: 20;");

        Label orderDate = new Label("Ordered: " + order.getOrderDate());
        orderDate.setStyle("-fx-font-size: 12px; -fx-text-fill: #cfcfcf;");

        Label deliveryDate = new Label("Delivered: " + order.getDeliveryDate());
        deliveryDate.setStyle("-fx-font-size: 12px; -fx-text-fill: #cfcfcf;");

        statusBox.getChildren().addAll(status, orderDate, deliveryDate);

        contentBox.getChildren().addAll(imageView, infoBox, statusBox);
        card.getChildren().add(contentBox);

        return card;
    }

    // GET STATUS COLOR
    private String getStatusColor(String status) {
        switch (status.toLowerCase()) {
            case "delivered": return "#3ad13a";
            case "shipped": return "#2979FF";
            case "processing": return "#ffa500";
            case "cancelled": return "#ff4757";
            default: return "#cfcfcf";
        }
    }

    // GET STATUS BACKGROUND COLOR
    private String getStatusBackgroundColor(String status) {
        switch (status.toLowerCase()) {
            case "delivered": return "rgba(58, 209, 58, 0.2)";
            case "shipped": return "rgba(41, 121, 255, 0.2)";
            case "processing": return "rgba(255, 165, 0, 0.2)";
            case "cancelled": return "rgba(255, 71, 87, 0.2)";
            default: return "rgba(207, 207, 207, 0.2)";
        }
    }

    // SHOW ORDER DETAILS
    private void showOrderDetails(PurchaseOrder order) {
        String details =
                "📦 Order #" + order.getOrderId() + "\n\n" +
                        "• Product: " + order.getProductName() + "\n" +
                        "• Size: " + order.getSize() + "\n" +
                        "• Quantity: " + order.getQuantity() + "\n" +
                        "• Price: ₱" + String.format("%.2f", order.getTotalPrice()) + "\n" +
                        "• Status: " + order.getStatus() + "\n" +
                        "• Order Date: " + order.getOrderDate() + "\n" +
                        "• Delivery Date: " + order.getDeliveryDate() + "\n\n" +
                        "📍 Delivery Address:\n" + order.getDeliveryAddress() + "\n\n" +
                        "📞 Contact: " + order.getCustomerPhone();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Details");
        alert.setHeaderText("Order Information");
        alert.setContentText(details);
        alert.getDialogPane().setPrefSize(400, 400);
        alert.showAndWait();
    }

    // UNIVERSAL NAVIGATION METHOD
    private void navigateTo(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            System.out.println("✅ Navigated to: " + fxmlFile);
        } catch (Exception e) {
            System.out.println("❌ Error navigating to " + fxmlFile + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ FIXED: STATIC METHOD TO ADD ORDERS FROM CART
    public static void addOrderFromCart(String customerName, String phone, String address,
                                        String paymentMethod, double total, Map<String, Object> cartItems) {

        // Generate unique order ID
        String orderId = "ORD" + System.currentTimeMillis();

        // Create purchase order for each cart item
        for (Map.Entry<String, Object> entry : cartItems.entrySet()) {
            if (entry.getValue() instanceof ShopController.CartItem) {
                ShopController.CartItem cartItem = (ShopController.CartItem) entry.getValue();

                // ✅ FIXED: Calculate price properly
                double itemTotal = 0;
                try {
                    String priceStr = cartItem.getPrice().replace("₱", "").trim();
                    itemTotal = Double.parseDouble(priceStr) * cartItem.getQuantity();
                } catch (NumberFormatException e) {
                    itemTotal = 150.0 * cartItem.getQuantity(); // Default price if parsing fails
                }

                PurchaseOrder order = new PurchaseOrder(
                        orderId,
                        cartItem.getProductName(),
                        getProductImage(cartItem.getProductName()),
                        cartItem.getSize(),
                        cartItem.getQuantity(),
                        itemTotal,
                        "Delivered",
                        getCurrentDate(),
                        getDeliveryDate(),
                        address,
                        phone
                );

                // ✅ FIXED: Add the order object to history
                purchaseHistory.add(0, order); // Add to beginning (most recent first)
            }
        }

        System.out.println("✅ Added " + cartItems.size() + " items to purchase history");
    }

    // HELPER METHODS
    private static String getCurrentDate() {
        return java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }

    private static String getDeliveryDate() {
        return java.time.LocalDate.now().plusDays(3)
                .format(java.time.format.DateTimeFormatter.ofPattern("MMM dd, yyyy"));
    }

    private static Image getProductImage(String productName) {
        try {
            // Map product names to image files
            String imageName = productName.toLowerCase().replace(" ", "");
            if (imageName.contains("riseabove")) {
                imageName = "Riseabove";
            } else if (imageName.contains("visionleaks")) {
                imageName = "VisionLeaks";
            } else if (imageName.contains("toyota") || imageName.contains("ae86")) {
                imageName = "ToyotaAE86";
            } else if (imageName.contains("nobound")) {
                imageName = "Nobound";
            } else {
                imageName = "Riseabove"; // Default image
            }

            String imagePath = "/com/example/nextwear/NextWearShopimage/" + imageName + ".png";
            System.out.println("Loading image: " + imagePath);
            return new Image(PurchaseController.class.getResourceAsStream(imagePath));
        } catch (Exception e) {
            System.out.println("❌ Error loading image for: " + productName);
            // Return default image if specific image not found
            try {
                return new Image(PurchaseController.class.getResourceAsStream(
                        "/com/example/nextwear/NextWearShopimage/Riseabove.png"));
            } catch (Exception ex) {
                System.out.println("❌ Even default image failed to load");
                return null;
            }
        }
    }

    private void showInfoAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // PURCHASE ORDER CLASS
    public static class PurchaseOrder {
        private String orderId;
        private String productName;
        private Image productImage;
        private String size;
        private int quantity;
        private double totalPrice;
        private String status;
        private String orderDate;
        private String deliveryDate;
        private String deliveryAddress;
        private String customerPhone;

        public PurchaseOrder(String orderId, String productName, Image productImage, String size,
                             int quantity, double totalPrice, String status, String orderDate,
                             String deliveryDate, String deliveryAddress, String customerPhone) {
            this.orderId = orderId;
            this.productName = productName;
            this.productImage = productImage;
            this.size = size;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
            this.status = status;
            this.orderDate = orderDate;
            this.deliveryDate = deliveryDate;
            this.deliveryAddress = deliveryAddress;
            this.customerPhone = customerPhone;
        }

        // Getters
        public String getOrderId() { return orderId; }
        public String getProductName() { return productName; }
        public Image getProductImage() { return productImage; }
        public String getSize() { return size; }
        public int getQuantity() { return quantity; }
        public double getTotalPrice() { return totalPrice; }
        public String getStatus() { return status; }
        public String getOrderDate() { return orderDate; }
        public String getDeliveryDate() { return deliveryDate; }
        public String getDeliveryAddress() { return deliveryAddress; }
        public String getCustomerPhone() { return customerPhone; }
    }
}