package org.example.proyectofinaljava.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.proyectofinaljava.db.GeneroDAO;
import org.example.proyectofinaljava.db.LibroDAO;
import org.example.proyectofinaljava.model.Libro;
import org.example.proyectofinaljava.model.Prestamo;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Esta clase es para seleccionar un libro a la hora de elegir un nuevo préstamo.
 */
public class NuevoPrestamoSeleccionarLibroController implements Initializable {

    private ObservableList<Libro> listaLibros;
    private LibroDAO libroDAO;
    private GeneroDAO generoDAO;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btMostrarTodos;

    @FXML
    private Button btNuevoLibro;

    @FXML
    private Button btRefrescar;

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
     * Método que se ejecuta al hacer clic en el botón de buscar.
     *
     * @param event El evento de ratón asociado al clic.
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
     * Método que se ejecuta al hacer clic en el botón de mostrar todos.
     *
     * @param event El evento de ratón asociado al clic.
     */
    @FXML
    void onClickMostrarTodos(MouseEvent event) {
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
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tvLibros.setItems(listaLibros);
        tvLibros.refresh();
    }

    /**
     * Método que se ejecuta al hacer clic en el botón de refrescar.
     *
     * @param event El evento de ratón asociado al clic.
     */
    @FXML
    void onClickRefrescar(MouseEvent event) {
        actualizarTvLibros();
    }

    /**
     * Método que se ejecuta al hacer clic en la tabla de libros.
     *
     * @param event El evento de ratón asociado al clic.
     */
    @FXML
    void onClickTvLibros(MouseEvent event) {
        // Evento para que cuando el usuario haga doble click en algún libro de la tabla se guarden los valores para después llevar a cabo el préstamo
        tvLibros.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                //cuando el usuario acepte, llamamos a la acción definida en la ventana principal y salimos
                Libro libro = tvLibros.getSelectionModel().getSelectedItem();
                if (onGetLibro != null && (libro.getEstado().matches("Disponible"))) {
                    onGetLibro.obtenLibro(tvLibros.getSelectionModel().getSelectedItem());
                    //cerramos la ventana
                    Stage stage = (Stage) tvLibros.getScene().getWindow();
                    stage.close();
                } else {
                    alertaDeError("Seleccione un libro disponible");
                }
            }
        });
    }

    /**
     * Cierra la ventana actual.
     *
     * @param event El evento de ratón asociado al clic.
     */
    @FXML
    void onClickVolver(MouseEvent event) {
        this.onGetLibro = null;
        Stage stage = (Stage) btVolver.getScene().getWindow();
        stage.close();
    }

    /**
     * Al iniciar la ventana se ejecutará el siguiente método.
     *
     * @param url            La ubicación utilizada para resolver el objeto raíz, o nulo si no se ha especificado.
     * @param resourceBundle Los recursos utilizados para localizar el objeto raíz, o nulo si no se ha especificado.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generoDAO = GeneroDAO.getConnection();
        libroDAO = LibroDAO.getConnection();
        this.onGetLibro = null;
        actualizarTvLibros();
        //Metemos en los combobox
        actualizarCbBuscar();

        //Asociamos la lista a la tabla
        tvLibros.setItems(listaLibros);
        tvLibros.refresh();
    }

    /**
     * Añade los valores al ComboBox para buscar por ellos.
     */
    public void actualizarCbBuscar() {
        try {
            List<String> listaGeneros = generoDAO.getAllGeneros();
            //Añadimos el valor al ComboBox
            cbBuscar.getItems().add("GENERO");
            cbBuscar.getItems().add("TITULO");
            cbBuscar.getItems().add("ISBN");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Actualiza la TableView de libros con los libros disponibles.
     */
    public void actualizarTvLibros() {
        try {
            listaLibros = FXCollections.observableArrayList(libroDAO.getAllLibrosDisponibles());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //Con esto se asocia la lista a la tabla
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
     * Muestra un mensaje de error en una alerta.
     *
     * @param mensaje El mensaje a mostrar en la alerta de error.
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
     * Esta interfaz nos permite asignar la acción cuando volvamos de llamar a la ventana secundaria.
     */
    public interface OnGetLibro {
        void obtenLibro(Libro libro);
    }

    //la instancia a la que llamaremos cuando el usuario pulse a aceptar
    private NuevoPrestamoSeleccionarLibroController.OnGetLibro onGetLibro;

    /**
     * Nos permitirá asignar la lambda en la principal con la acción que realizaremos.
     *
     * @param onGetLibro La acción a realizar al obtener el libro.
     */
    public void setOnGetLibro(NuevoPrestamoSeleccionarLibroController.OnGetLibro onGetLibro) {
        this.onGetLibro = onGetLibro;
    }
}
