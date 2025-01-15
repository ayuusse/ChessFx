module org.example.chess {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.media;

    opens org.example.chess to javafx.fxml;
    exports org.example.chess;
}