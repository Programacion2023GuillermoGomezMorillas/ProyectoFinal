module org.example.proyectofinaljava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.proyectofinaljava to javafx.fxml;
    exports org.example.proyectofinaljava;
}