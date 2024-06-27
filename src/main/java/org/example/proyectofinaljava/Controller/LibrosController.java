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


/**
 * Controlador para la gestión de libros en la aplicación.
 */
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
    private TableColumn<Libro, String> tcEstado;

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
     * Método que se ejecuta al hacer clic en la tabla de libros.
     * Rellena los campos de texto con los valores del libro seleccionado en la tabla.
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickTvLibros(MouseEvent event) {
        Libro libro = tvLibros.getSelectionModel().getSelectedItem();
        // Si hay un libro seleccionado, muestra sus datos en los campos de texto correspondientes.
        if (libro != null) {
            tfIsbnModif.setText(libro.getIsbn());
            tfTituloModif.setText(libro.getTitulo());
            tfAnoModif.setText(libro.getAnio());
            tfAutorModif.setText(libro.getAutor());
            cbGeneroModif.setValue(libro.getGenero());
        }
    }

    /**
     * Método inicializador que se ejecuta al abrir la ventana.
     *
     * @param url            la URL base para la inicialización.
     * @param resourceBundle el ResourceBundle específico para el inicializador.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generoDAO = GeneroDAO.getConnection();
        libroDAO = LibroDAO.getConnection();
        actualizarTvLibros();
        // Actualiza los ComboBoxes
        actualizarCbBuscar();
        actualizarCbGenero();
        // Asocia la lista de libros a la tabla
        tvLibros.setItems(listaLibros);
        tvLibros.refresh();
    }

    /**
     * Actualiza la tabla de libros con los datos más recientes de la base de datos.
     */
    public void actualizarTvLibros() {
        try {
            listaLibros = FXCollections.observableArrayList(libroDAO.getAllLibros());
            libroDAO.updateLibroEstado();
            libroDAO.updateLibroEstadoDisponible();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        // Configura las columnas de la tabla con los atributos de la clase Libro
        tcIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tcAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tcAno.setCellValueFactory(new PropertyValueFactory<>("anio"));
        tcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tvLibros.setItems(listaLibros);
        tvLibros.refresh();
    }

    /**
     * Método para manejar el evento de clic en el botón "Borrar".
     *
     * @param event el evento de clic del ratón.
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
                alertaDeError("No se puede eliminar un libro prestado");
            }
        }
    }

    /**
     * Método para manejar el evento de clic en el botón "Buscar".
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickBuscar(MouseEvent event) {
        // Realiza la búsqueda de libros según el criterio seleccionado
        if (tfBuscar.getText().isEmpty()) {
            alertaDeError("Introduce un criterio de búsqueda");
        } else {
            try {
                ObservableList<Libro> libros = null;
                if (cbBuscar.getValue() == null) {
                    alertaDeError("Tiene que seleccionar una busqueda");
                } else {

                    if (cbBuscar.getValue() == null) {
                        alertaDeError("Selecciona un método de búsqueda");
                        return;
                    } else if (cbBuscar.getValue().equals("GENERO")) {
                        libros = FXCollections.observableArrayList(libroDAO.getLibroByGenero(tfBuscar.getText()));
                    } else if (cbBuscar.getValue().equals("ISBN")) {
                        libros = FXCollections.observableArrayList(libroDAO.getLibroByIsbn(tfBuscar.getText()));
                    } else if (cbBuscar.getValue().equals("TITULO")) {
                        libros = FXCollections.observableArrayList(libroDAO.getLibroByTitulo(tfBuscar.getText()));
                    }
                }
                // Actualiza la tabla con los resultados de la búsqueda
                if (libros.isEmpty()) {
                    alertaDeError("No existe esa busqueda");
                } else {
                    tvLibros.setItems(libros);
                    tvLibros.refresh();
                }
            } catch (SQLException e) {
                alertaDeError("Busqueda no encontrada");
            }
        }
    }

    /**
     * Método para manejar el evento de clic en el botón "Insertar".
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickInsertar(MouseEvent event) {
        Libro libro = crearLibroDesdeFormulario();

        if (libro != null) {
            if (listaLibros.contains(libro)) {
                alertaDeError("Ya existe un libro con ese ISBN");
            } else {
                    System.out.println(libro);
                try {
                    System.out.println(libro);
                    libroDAO.insertLibro(libro);
                    actualizarTvLibros();
                    limpiarDatosModif();

                } catch (SQLException e) {
                    System.out.println(libro);
                    alertaDeError("Error al guardar: " + libro.getTitulo());
                }
            }
        }
    }

    /**
     * Método para manejar el evento de clic en el botón "Modificar".
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickModificar(MouseEvent event) {
        Libro libro = crearLibroDesdeFormulario();
        if (libro != null) {
            try {
                libroDAO.updateLibro(libro);
                actualizarTvLibros();
                limpiarDatosModif();
            } catch (SQLException e) {
                alertaDeError("No se puede modificar un libro prestado");
            }
        }
    }

    /**
     * Método para manejar el evento de clic en el botón "Quitar Filtro".
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickQuitarFiltro(MouseEvent event) {
        // Actualiza la tabla mostrando todos los libros sin filtro
        actualizarTvLibros();
    }

    /**
     * Método para manejar el evento de clic en el botón "Volver".
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickVolver(MouseEvent event) {
        // Cierra la ventana actual y vuelve a la ventana anterior
        Stage stage = (Stage) btVolver.getScene().getWindow();
        stage.close();
    }

    /**
     * Método auxiliar para crear un objeto Libro con los datos ingresados en el formulario.
     *
     * @return el objeto Libro creado o null si los datos no son válidos.
     */
    private Libro crearLibroDesdeFormulario() {
        Libro libro = null;
        if (comprobarDatos()) {
            libro = new Libro(
                    tfIsbnModif.getText(),
                    tfTituloModif.getText(),
                    tfAutorModif.getText(),
                    tfAnoModif.getText(),
                    cbGeneroModif.getValue(),
                    "Disponible"
            );
        }
        return libro;
    }

    /**
     * Actualiza el ComboBox de géneros con los valores disponibles en la base de datos.
     */
    public void actualizarCbGenero() {
        try {
            List<String> listaGeneros = generoDAO.getAllGeneros();
            for (String genero : listaGeneros) {
                cbGeneroModif.getItems().add(genero);
            }
            if (!listaGeneros.isEmpty()) {
                cbGeneroModif.setValue(listaGeneros.get(0));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Actualiza el ComboBox de métodos de búsqueda.
     */
    public void actualizarCbBuscar() {
        // Añade los métodos de búsqueda al ComboBox
        cbBuscar.getItems().addAll("GENERO", "TITULO", "ISBN");
    }

    /**
     * Verifica si los datos ingresados en el formulario son válidos.
     *
     * @return true si los datos son válidos, false si no lo son.
     */
    public boolean comprobarDatos() {
        boolean result = true;
        if (!tfIsbnModif.getText().matches("^[0-9]{13}$")) {
            alertaDeError("El ISBN debe tener 13 dígitos numéricos");
            tfIsbnModif.requestFocus();
            result = false;
        }
        if (tfTituloModif.getText().isEmpty()) {
            alertaDeError("El título no puede estar vacío");
            tfTituloModif.requestFocus();
            result = false;
        }
        // Comprueba si el año no es un número válido
        if (!tfAnoModif.getText().matches("^[0-9]{1,4}$")) {
            alertaDeError("El año debe ser un número válido");
            tfAnoModif.requestFocus();
            result = false;
        }
        // Comprueba si el autor está vacío
        if (tfAutorModif.getText().isEmpty()) {
            alertaDeError("El autor no puede estar vacío");
            tfAutorModif.requestFocus();
            result = false;
        }
        return result;
    }

    /**
     * Muestra una alerta de error con el mensaje especificado.
     *
     * @param mensaje el mensaje de error a mostrar.
     */
    private void alertaDeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Limpia todos los campos de texto del formulario de modificación de libros.
     */
    public void limpiarDatosModif() {
        tfIsbnModif.setText("");
        tfTituloModif.setText("");
        tfAnoModif.setText("");
        tfAutorModif.setText("");
        cbGeneroModif.setValue("");
    }
}