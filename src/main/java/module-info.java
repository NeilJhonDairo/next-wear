module com.example.nextwear {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.nextwear to javafx.fxml;
    exports com.example.nextwear;
}
