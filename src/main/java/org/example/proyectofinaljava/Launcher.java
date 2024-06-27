package org.example.proyectofinaljava;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase Launcher que inicia la aplicación JavaFX.
 */
public class Launcher extends Application {

    /**
     * Método start que inicia la aplicación JavaFX.
     *
     * @param stage Stage principal de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML de la interfaz gráfica
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("prestamos-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Configurar la ventana principal
        stage.setTitle("Gestion de biblioteca"); // Título de la ventana
        stage.setScene(scene); // Establecer la escena en la ventana
        stage.show(); // Mostrar la ventana
    }

    /**
     * Método main que inicia la aplicación.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        launch(); // Lanzar la aplicación JavaFX
    }
}
