module com.example.pizzitas {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.example.pizzitas to javafx.fxml;
    exports com.example.pizzitas;
    exports com.example.pizzitas.Controllers;
    opens com.example.pizzitas.Controllers to javafx.fxml;

    // Asegúrate de incluir el paquete com.example.pizzitas.factory
    exports com.example.pizzitas.factory;
}
