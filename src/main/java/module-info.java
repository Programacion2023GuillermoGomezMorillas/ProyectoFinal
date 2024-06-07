module org.example.proyectofinaljava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.proyectofinaljava to javafx.fxml;
    exports org.example.proyectofinaljava;
    opens org.example.proyectofinaljava.db to javafx.fxml;
    exports org.example.proyectofinaljava.db;
    opens org.example.proyectofinaljava.Controller to javafx.fxml;
    exports org.example.proyectofinaljava.Controller;
    opens org.example.proyectofinaljava.model to javafx.fxml;
    exports org.example.proyectofinaljava.model;
}