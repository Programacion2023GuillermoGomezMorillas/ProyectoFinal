package org.example.proyectofinaljava.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.proyectofinaljava.Launcher;
import org.example.proyectofinaljava.db.LibroDAO;
import org.example.proyectofinaljava.db.PrestamoDAO;
import org.example.proyectofinaljava.db.SocioDAO;
import org.example.proyectofinaljava.model.Libro;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import org.example.proyectofinaljava.model.Prestamo;
import org.example.proyectofinaljava.model.Socio;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para la gestión de préstamos en la biblioteca.
 */
public class PrestamosController implements Initializable {
    private ObservableList<Prestamo> listaPrestamos;
    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;
    private SocioDAO socioDAO;
    private Libro libroPrest;
    private Socio socioPrest;
    private long cont;

    @FXML
    private Button BtNuevoPrest;
    @FXML
    private Button btDevolver;
    @FXML
    private Button btBuscar;
    @FXML
    private Button btDelete;
    @FXML
    private Button btNuevoLibro;
    @FXML
    private Button btNuevoSocio;
    @FXML
    private Button btRefrescar;
    @FXML
    private ComboBox<String> cbBuscar;
    @FXML
    private ComboBox<String> cbEstado;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private TableColumn<Prestamo, String> tcEstado;
    @FXML
    private TableColumn<Prestamo, String> tcNomSocio;
    @FXML
    private TableColumn<Prestamo, String> tcFechaInicio;
    @FXML
    private TableColumn<Prestamo, String> tcFechaFin;
    @FXML
    private TableColumn<Prestamo, String> tcNumRef;
    @FXML
    private TableColumn<Prestamo, String> tcTitulo;
    @FXML
    private TextField tfNumRefBor;
    @FXML
    private TableView<Prestamo> tvPrestamos;

    /**
     * Inicializa el controlador, configurando DAOs y actualizando la tabla de préstamos.
     *
     * @param url            la URL para inicializar el controlador.
     * @param resourceBundle el ResourceBundle para inicializar el controlador.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socioDAO = SocioDAO.getConnection();
        libroDAO = LibroDAO.getConnection();
        prestamoDAO = PrestamoDAO.getConnection();
        actualizartvPrestamos();
        actualizarCbEstado();

        tvPrestamos.setItems(listaPrestamos);
        tvPrestamos.refresh();
    }

    /**
     * Maneja el evento de hacer clic en el botón de borrar préstamo.
     *
     * @param event el evento del clic del ratón.
     */
    @FXML
    void OnClickDelete(MouseEvent event) {
        if (comprobarDatos()) {
            try {
                prestamoDAO.deletePrestamoByReferencia(tfNumRefBor.getText());
                actualizartvPrestamos();
            } catch (SQLException e) {
                alertaDeError("No se ha podido borrar ese préstamo");
            }
        } else {
            alertaDeError("Debe ser un número de referencia válido");
        }
    }

    /**
     * Maneja el evento de hacer clic en el botón de devolver un libro.
     *
     * @param event el evento del clic del ratón.
     */
    @FXML
    void onClickDevolver(MouseEvent event) {
        if (comprobarDatos()) {
            try {
                Prestamo prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
                if (prestamo == null) {
                    alertaDeError("Ese número de referencia no existe");
                } else {
                    prestamoDAO.updatePrestamo(prestamo);
                    actualizartvPrestamos();
                }
            } catch (SQLException e) {
                alertaDeError("No se ha podido devolver ese libro");
            }
        } else {
            alertaDeError("Debe ser un número de referencia válido");
        }
    }

    /**
     * Maneja el evento de buscar un préstamo por estado.
     *
     * @param event el evento del clic del ratón.
     */
    @FXML
    void onClickBuscar(MouseEvent event) {
        try {
            ObservableList<Prestamo> prestamos = FXCollections.observableArrayList(prestamoDAO.getLibroByEstado(String.valueOf(cbBuscar.getValue())));
            tvPrestamos.setItems(prestamos);
            tvPrestamos.refresh();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Maneja el evento de crear un nuevo préstamo.
     *
     * @param event el evento del clic del ratón.
     */
    @FXML
    void onClickNuevoPrest(MouseEvent event) {
        if (dpFechaInicio.getValue() == null || dpFechaFin.getValue() == null) {
            alertaDeError("Debe elegir la fecha de inicio y fin del préstamo");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("nuevoPrestamoSeleccionarLibro-view.fxml"));
                Parent root = loader.load();
                libroPrest=null;
                NuevoPrestamoSeleccionarLibroController nuevoPrestamoSeleccionarLibroController = loader.getController();
                nuevoPrestamoSeleccionarLibroController.setOnGetLibro(libro -> {
                    Libro libroPrest = new Libro(libro.getIsbn(), libro.getTitulo(), libro.getAnio(), libro.getAutor(), libro.getGenero(), "Prestado");
                    this.libroPrest = libroPrest;
                });

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Elegir libro para prestar");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.showAndWait();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

            try {
                FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("nuevoPrestamoSeleccionarSocio-view.fxml"));
                Parent root = loader.load();
                socioPrest=null;
                NuevoPrestamoSeleccionarSocioController nuevoPrestamoSeleccionarSocioController = loader.getController();
                nuevoPrestamoSeleccionarSocioController.setOnGetSocio(socio -> {
                    Socio socioPrest = new Socio(socio.getNumeroSocio(), socio.getNombreSocio(), socio.getDireccionSocio(), socio.getTelefonoSocio(), socio.getEmailSocio());
                    this.socioPrest = socioPrest;
                });

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Elegir socio para prestar");
                stage.setScene(scene);
                stage.setResizable(false);
                stage.showAndWait();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }

            if (libroPrest != null && socioPrest != null) {
                try {
                    cont = prestamoDAO.getNumeroReserva() + 1;
                    Prestamo prestamo = new Prestamo(cont, Date.valueOf(String.valueOf(dpFechaInicio.getValue())), Date.valueOf(String.valueOf(dpFechaFin.getValue())), "Pendiente", libroPrest.getTitulo(), socioPrest.getNombreSocio());

                    if (listaPrestamos.contains(prestamo)) {
                        alertaDeError("Ya existe un préstamo con ese libro y ese socio");
                    } else {
                        try {
                            prestamoDAO.insertPrestamo(prestamo);
                            actualizartvPrestamos();
                        } catch (SQLException e) {
                            alertaDeError("No se ha podido hacer el préstamo");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * Maneja el evento de refrescar la tabla de préstamos.
     *
     * @param event el evento del clic del ratón.
     */
    @FXML
    void onClickRefrescar(MouseEvent event) {
        actualizartvPrestamos();
    }

    /**
     * Maneja el evento de seleccionar un préstamo en la tabla,
     * llenando el campo de texto con el número de referencia del préstamo seleccionado.
     *
     * @param event el evento del clic del ratón.
     */
    @FXML
    void onClicktvPrestamos(MouseEvent event) {
        Prestamo prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
        if (prestamo != null) {
            tfNumRefBor.setText(String.valueOf(prestamo.getNumReserva()));
        }
    }

    /**
     * Maneja el evento de abrir la ventana para añadir un nuevo libro.
     *
     * @param event el evento del clic del ratón.
     */
    @FXML
    void onClickNuevoLibro(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("libros-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Añadir Libros");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Maneja el evento de abrir la ventana para añadir un nuevo socio.
     *
     * @param event el evento del clic del ratón.
     */
    @FXML
    void onClickNuevoSocio(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("socios-view.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Añadir Socios");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Actualiza la tabla de préstamos, obteniendo los datos más recientes desde la base de datos.
     */
    public void actualizartvPrestamos() {
        try {
            listaPrestamos = FXCollections.observableArrayList(prestamoDAO.getAllPrestamos());
            libroDAO.updateLibroEstado();
            libroDAO.updateLibroEstadoDisponible();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        tcNumRef.setCellValueFactory(new PropertyValueFactory<>("numReserva"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tcNomSocio.setCellValueFactory(new PropertyValueFactory<>("nombreSocio"));
        tcFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        tcFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tvPrestamos.setItems(listaPrestamos);
        tvPrestamos.refresh();
    }

    /**
     * Actualiza el ComboBox de estado con valores predefinidos.
     */
    public void actualizarCbEstado() {
        try {
            List<Prestamo> listaPrestamo = prestamoDAO.getAllPrestamos();
            cbBuscar.getItems().add("DEVUELTO");
            cbBuscar.getItems().add("PENDIENTE");
            cbBuscar.getItems().add("VENCIDO");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Verifica si los datos introducidos son válidos.
     *
     * @return true si los datos son válidos, false en caso contrario.
     */
    public boolean comprobarDatos() {
        boolean bool = true;
        if (!tfNumRefBor.getText().matches("^[0-9]+$")) {
            tfNumRefBor.requestFocus();
            bool = false;
        }
        return bool;
    }

    /**
     * Muestra una alerta de error con un mensaje especificado.
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
}
