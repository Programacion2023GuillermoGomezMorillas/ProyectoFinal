package org.example.proyectofinaljava.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.proyectofinaljava.db.GeneroDAO;
import org.example.proyectofinaljava.db.LibroDAO;
import org.example.proyectofinaljava.model.Libro;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;


public class LibrosController implements Initializable {
    private ObservableList<Libro> listaLibros;
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
    private Button btQuitarFiltro;

    @FXML
    private Button btVolver;

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

    /**
     * Metodo para cuando se haga click en la tabla
     * En este caso rellenará ciertos campos de texto con los valores de la tabla
     *
     * @param event
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
     * Este metodo se ejecutará cuando se abra la ventana
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generoDAO = GeneroDAO.getConnection();
        libroDAO = LibroDAO.getConnection();
        actualizarTvLibros();
        //Metemos en los combobox
        actualizarCbBuscar();
        actualizarCbGenero();
        //Asociamos la lista a la tabla
        tvLibros.setItems(listaLibros);
        tvLibros.refresh();

    }

    /**
     * Actualiza la tabla para que se visualicen los libros en la tabla
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
     * Método para borrar un libro
     *
     * @param event
     */
    @FXML
    void onClickBorrar(MouseEvent event) {
        if (tvLibros.getSelectionModel().isEmpty()) {
            alertaDeError("Selecciona un libro que quieras eliminar");
        } else {
            try {
                libroDAO.deleteLibroByIsbn(tfIsbnModif.getText());
                limpiarDatosModif();
                actualizarTvLibros();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Método para buscar un libro
     *
     * @param event
     */
    @FXML
    void onClickBuscar(MouseEvent event) {
        //buscamos el libro seleccionado

        if (tfBuscar.getText().isEmpty()) {
            alertaDeError("Introduce una busqueda");
        } else {
            try {
                ObservableList<Libro> libros = FXCollections.observableArrayList(libroDAO.getAllLibros());
                if (cbBuscar.getValue() == null) {
                    alertaDeError("Seleccione un método de busqueda");
                } else {
                    if (cbBuscar.getValue().equals("GENERO")) {
                        libros = FXCollections.observableArrayList(libroDAO.getLibroByGenero(tfBuscar.getText()));
                    } else if (cbBuscar.getValue().equals("ISBN")) {
                        libros = FXCollections.observableArrayList(libroDAO.getLibroByIsbn(tfBuscar.getText()));
                    } else if (cbBuscar.getValue().equals("TITULO")) {
                        libros = FXCollections.observableArrayList(libroDAO.getLibroByTitulo(tfBuscar.getText()));
                    }
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
     * Método para insertar un libro
     *
     * @param event
     */
    @FXML
    void onClickInsertar(MouseEvent event) {
        Libro libro = null;

        if (comprobarDatos()) {
            libro = new Libro(
                    tfIsbnModif.getText(),
                    tfTituloModif.getText(),
                    tfAutorModif.getText(),
                    tfAnoModif.getText(),
                    cbGeneroModif.getValue()
            );
        }

        if (libro != null) {
            if (listaLibros.contains(libro)) {
                alertaDeError("Ya existe un libro con ese ISBN");
            } else {
                try {
                    libroDAO.insertLibro(libro);
                    actualizarTvLibros();
                    limpiarDatosModif();
                } catch (SQLException e) {
                    System.out.println("Error al guardar: " + libro);
                }
            }
        }
    }

    /**
     * Método para modificar un libro
     *
     * @param event
     */
    @FXML
    void onClickModificar(MouseEvent event) {
        Libro libro = null;

        if (comprobarDatos()) {
            libro = new Libro(
                    tfIsbnModif.getText(),
                    tfTituloModif.getText(),
                    tfAutorModif.getText(),
                    tfAnoModif.getText(),
                    cbGeneroModif.getValue()

            );
        }

        if (libro != null) {
            try {
                libroDAO.updateLibro(libro);
                actualizarTvLibros();
                limpiarDatosModif();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    /**
     * Metodo para quitar los filtros puestos en Buscar Libro
     *
     * @param event
     */
    @FXML
    void onClickQuitarFiltro(MouseEvent event) {
        actualizarTvLibros();
    }

    /**
     * Método para volver a la anterior ventana
     *
     * @param event
     */
    @FXML
    void onClickVolver(MouseEvent event) {
        Stage stage = (Stage) btVolver.getScene().getWindow();
        stage.close();
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

    public void actualizarCbBuscar() {
        try {
            List<String> listaGeneros = generoDAO.getAllGeneros();
            //Añadimos el valor al comboBox
            cbBuscar.getItems().add("GENERO");
            cbBuscar.getItems().add("TITULO");
            cbBuscar.getItems().add("ISBN");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @return Devuelve true en el caso de que todas las comprobaciondes sean correctas
     */
    public boolean comprobarDatos() {
        boolean bool = true;
        if (!tfIsbnModif.getText().matches("^[0-9]{13}$")) {
            alertaDeError("El ISBN es incorrecto o esta vacio");
            tfIsbnModif.requestFocus();
            bool = false;
        }
        else if (tfTituloModif.getText().isEmpty()) {
            alertaDeError("El título no puede ser un campo vacio");
            tfTituloModif.requestFocus();
            bool = false;
        } else if (!tfAnoModif.getText().matches("^[0-9]{1,4}$")) {
            alertaDeError("El año no es correcto o esta vacio ");
            tfAnoModif.requestFocus();
            bool = false;
        } else if (tfAutorModif.getText().isEmpty()) {
            alertaDeError("El autor no puede ser un campo vacio");
            tfAutorModif.requestFocus();
            bool = false;
        }
        return bool;
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

}



