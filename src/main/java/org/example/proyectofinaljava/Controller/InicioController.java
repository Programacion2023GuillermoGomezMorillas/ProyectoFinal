package org.example.proyectofinaljava.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.proyectofinaljava.HelloApplication;
import org.example.proyectofinaljava.Launcher;

import java.io.IOException;

public class InicioController {

    @FXML
    void onClickLibros(ActionEvent event) {
        //es necesario el control de excepciones
        try {
            //Cargamos la ventana de la gestion de los libros
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("libros-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Gestion de biblioteca");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void onClickPrestamos(ActionEvent event) {
        try {
            //Cargamos la ventana de la gestion de los Prestamos
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("prestamos-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Gestion de socios");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void onClickSocios(ActionEvent event) {
        try {
            //Cargamos la ventana de la gestion de los socios
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("socios-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("Gestion de socios");
            stage.setScene(scene);
            stage.showAndWait();
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

}