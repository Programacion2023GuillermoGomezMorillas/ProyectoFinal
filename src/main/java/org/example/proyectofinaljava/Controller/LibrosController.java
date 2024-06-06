package org.example.proyectofinaljava.Controller;

import javafx.fxml.Initializable;
import org.example.proyectofinaljava.db.GeneroDAO;
import org.example.proyectofinaljava.db.LibroDAO;
import org.example.proyectofinaljava.model.Libro;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;


public class LibrosController implements Initializable {
    private LibroDAO libroDAO;
    private GeneroDAO generoDAO;
    @FXML
    private Button btBorrar;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btInsertar;

    @FXML
    private Button btModificar;

    @FXML
    private ComboBox<String> cbBuscar;

    @FXML
    private ComboBox<String> cbGenero;

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
    private TextField tfAno;

    @FXML
    private TextField tfAnoModif;

    @FXML
    private TextField tfAutor;

    @FXML
    private TextField tfAutorModif;

    @FXML
    private TextField tfBuscar;

    @FXML
    private TextField tfIsbn;

    @FXML
    private TextField tfIsbnBor;

    @FXML
    private TextField tfIsbnModif;

    @FXML
    private TextField tfTitulo;

    @FXML
    private TextField tfTituloModif;
    @FXML
    private TableView<Libro> tvLibros;




    /**
     *
     * @param event para borrar un libro
     */
    @FXML
    void onClickBorrar(MouseEvent event) {

    }

    /**
     *
     * @param event para buscar un libro
     */
    @FXML
    void onClickBuscar(MouseEvent event) {
        //buscamos el alumno seleccionado
        Libro libro=tvLibros.getSelectionModel().getSelectedItem();
        //si hay un alumno seleccionado mostramos los datos
        if(libro!=null){
            tfIsbn.setText(libro.getISBN());
            tfTitulo.setText(libro.getTitulo());
            tfAno.setPrefColumnCount(libro.getAnioPublicacion());
            tfAutor.setText(libro.getAutor());
            cbGenero.setValue(libro.getGenero());
        }
    }


    /**
     *
     * @param event para insertar un libro
     */
    @FXML
    void onClickInsertar(MouseEvent event) {

    }

    /**
     *
     * @param event para modificar un libro
     */
    @FXML
    void onClickModificar(MouseEvent event) {

    }

}
