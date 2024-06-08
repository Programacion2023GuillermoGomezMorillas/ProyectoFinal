package org.example.proyectofinaljava.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.proyectofinaljava.model.*;

public class SeleccionarLibroController {

    @FXML
    private Button btBuscar;

    @FXML
    private Button btModificar;

    @FXML
    private ComboBox<String> cbBuscar;

    @FXML
    private ComboBox<String> cbGeneroModif;

    @FXML
    private TableColumn<Libro, String> tcAno;

    @FXML
    private TableColumn<Libro, String> tcAutor;

    @FXML
    private TableColumn<Libro, String> tcGenero;

    @FXML
    private TableColumn<Libro, String> tcIsbn;

    @FXML
    private TableColumn<Libro, String> tcTitulo;

    @FXML
    private TextField tfAnoModif;

    @FXML
    private TextField tfAutorModif;

    @FXML
    private TextField tfBuscar;

    @FXML
    private TextField tfIsbnModif;

    @FXML
    private TextField tfTituloModif;

    @FXML
    private TableView<Libro> tvLibros;

    @FXML
    void onClickBuscar(MouseEvent event) {

    }

    @FXML
    void onClickModificar(MouseEvent event) {

    }

    @FXML
    void onClickTvLibros(MouseEvent event) {
        Libro libro = tvLibros.getSelectionModel().getSelectedItem();
        //si hay un libro seleccionado mostramos los datos
        if (libro != null) {
            tfIsbnModif.setText(libro.getIsbn());
            tfTituloModif.setText(libro.getTitulo());
            tfAnoModif.setText(libro.getAnio());
            tfAutorModif.setText(libro.getAutor());
            cbGeneroModif.setValue(libro.getGenero());
        }
    }
    }


