package org.example.proyectofinaljava.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.proyectofinaljava.db.GeneroDAO;
import org.example.proyectofinaljava.db.LibroDAO;
import org.example.proyectofinaljava.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SeleccionarLibroController implements Initializable {
    private ObservableList<Libro> listaLibros;
    private LibroDAO libroDAO;
    private GeneroDAO generoDAO;


    @FXML
    private Button btBuscar;

    @FXML
    private Button btSelecLibro;

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
        if (tfBuscar.getText().isEmpty()) {
            alertaDeError("Introduce una busqueda");
        } else {
            try {
                ObservableList<Libro> libros;

                if (cbBuscar.getValue().equals("GENERO")) {
                    libros = FXCollections.observableArrayList(libroDAO.getLibroByGenero(tfBuscar.getText()));


                } else if (cbBuscar.getValue().matches("ISBN")) {
                    libros = FXCollections.observableArrayList(libroDAO.getLibroByIsbn(tfBuscar.getText()));
                } else {
                    libros = FXCollections.observableArrayList(libroDAO.getLibroByTitulo(tfBuscar.getText()));


                }

                //Asociamos la lista a la tabla
                tvLibros.setItems(libros);
                tvLibros.refresh();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * para modificar un libro
     * @param event
     */
    /*
    @FXML
    void onClickModificar(MouseEvent event) {
        Libro libro;
        libro = new Libro(
                tfIsbnModif.getText(),
                tfTituloModif.getText(),
                tfAutorModif.getText(),
                tfAnoModif.getText(),
                cbGeneroModif.getValue()
        );
        //cuando el usuario acepte, llamamos a la acción definida en la ventana principal y salimos
        if (onGetLibro != null) {
            if (tvLibros.getSelectionModel().getSelectedItem() != null) {
                onGetLibro.obtenLibro(tvLibros.getSelectionModel().getSelectedItem());

                //cerramos la ventana
                Stage stage = (Stage) tvLibros.getScene().getWindow();
                stage.close();
            } else {
                alertaDeError("Escoge un libro");
            }
        }
    }

     */
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



    /**
     * Al iniciar la ventana se ejecutará el siguiente método
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generoDAO = GeneroDAO.getConnection();
        libroDAO = LibroDAO.getConnection();
        actualizarTvLibros();
        //Metemos en los combobox
        actualizarCbGenero();
        actualizarCbBuscar();

        //Asociamos la lista a la tabla
        tvLibros.setItems(listaLibros);
        tvLibros.refresh();
        tfIsbnModif.setDisable(true);
        tfIsbnModif.setDisable(true);
        tfTituloModif.setDisable(true);
        tfAutorModif.setDisable(true);
        tfAnoModif.setDisable(true);
        cbGeneroModif.setDisable(true);
    }

    /**
     * Añaidos al comboBox los tres valores para buscar sobre ellos
     */
    public void actualizarCbBuscar() {
        try {
            List<String> listaGeneros = generoDAO.getAllGeneros();
            //Añadimos el valor al comboBox
            cbBuscar.getItems().add("GENERO");
            cbBuscar.getItems().add("TITULO");
            cbBuscar.getItems().add("IBSN");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Metodo para actualizar el ComboBox que se encarga de listar los generos
     */
    public void actualizarCbGenero() {
        try {
            List<String> listaGeneros = generoDAO.getAllGeneros();
            for (String genero : listaGeneros) {
                //Añadimos el valor al comboBox
                cbGeneroModif.getItems().add(genero);
            }
            //seleccionamos el primero
            cbGeneroModif.setValue(listaGeneros.get(0));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Actualiza la tabla para que se visualicen los datos
     */
    public void actualizarTvLibros() {
        try {
            listaLibros = FXCollections.observableArrayList(libroDAO.getAllLibros());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //Con esto se asocia la lista a la tabla
        tcIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tcAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tcAno.setCellValueFactory(new PropertyValueFactory<>("anio"));
        tcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tvLibros.setItems(listaLibros);
        tvLibros.refresh();

    }

    /**
     * @param mensaje Muestra el mensaje en forma de error
     */
    private void alertaDeError(String mensaje) {
        //creamos la alerta de tipo Error
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        //Mostramos el mensaje por pantalla
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Metodo para limpiar los campos
     */
    public void limpiarDatosModif() {
        tfIsbnModif.setText("");
        tfTituloModif.setText("");
        tfAnoModif.setText("");
        tfAutorModif.setText("");
        cbGeneroModif.setValue("");
    }

    @FXML
    void onClickSeleccionar(MouseEvent event) {
        //cuando el usuario acepte, llamamos a la acción definida en la ventana principal y salimos
        if (onGetLibro != null) {
            onGetLibro.obtenLibro(tvLibros.getSelectionModel().getSelectedItem());
            Libro libro;
            libro = new Libro(
                    tfIsbnModif.getText(),
                    tfTituloModif.getText(),
                    tfAutorModif.getText(),
                    tfAnoModif.getText(),
                    cbGeneroModif.getValue(),
                    cbGeneroModif.getValue()
            );
        //cerramos la ventana
        Stage stage = (Stage) tvLibros.getScene().getWindow();
        stage.close();
        } else {
            alertaDeError("Seleccione un libro");
        }

    }

    //esta interface nos permite asignar la acción cuando volvamos de llamar a la ventana secundaria
    public interface OnGetLibro {
        void obtenLibro(Libro libro);
    }

    //la instancia  a la que llamaremos cuando el usuario pulse a aceptar
    private OnGetLibro onGetLibro;

    //nos permitirá asignar la lambda en la principal con la acción que realizaremos
    public void setOnGetLibro(OnGetLibro onGetLibro) {
        this.onGetLibro = onGetLibro;
    }
}


