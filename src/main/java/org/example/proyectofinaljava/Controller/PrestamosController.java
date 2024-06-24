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


public class PrestamosController implements Initializable {
    private ObservableList<Prestamo> listaPrestamos;
    private PrestamoDAO prestamoDAO;
    private LibroDAO libroDAO;
    private SocioDAO socioDAO;
    private Libro libroPrest;
    private Socio socioPrest;
    private long cont = 26;

    @FXML
    private Button BtNuevoPrest;

    @FXML
    private Button btBorrar;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btNuevoLibro;

    @FXML
    private Button btNuevoSocio;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socioDAO = SocioDAO.getConnection();
        libroDAO = LibroDAO.getConnection();
        prestamoDAO = PrestamoDAO.getConnection();
        actualizartvPrestamos();
        //Actualizar el comboBox del estado del prestamo
        actualizarCbEstado();

        //Asociamos la lista a la tabla
        tvPrestamos.setItems(listaPrestamos);
        tvPrestamos.refresh();

    }


    /**
     * @param event para borrar un Prestamo
     */
    @FXML
    void onClickBorrar(MouseEvent event) {
        if (tvPrestamos.getSelectionModel().isEmpty()) {
            alertaDeError("Selecciona un prestamo que quieras eliminar");
        } else {
            try {
                prestamoDAO.deletePrestamoByReferencia(tfNumRefBor.getText());
                actualizartvPrestamos();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }

    }


    /**
     * @param event Buscar un prestamo por estado
     */
    @FXML
    void onClickBuscar(MouseEvent event) {
        //buscamos el prestamo si esta devuelto o pendiente
        try {
            ObservableList<Prestamo> prestamos;

            if (cbBuscar.getValue().equals("DEVUELTO")) {
                prestamos = FXCollections.observableArrayList(prestamoDAO.getLibroByEstado((String) cbBuscar.getValue()));
            } else
                prestamos = FXCollections.observableArrayList(prestamoDAO.getLibroByEstado((String) cbBuscar.getValue()));

            //Asociamos la lista a la tabla
            tvPrestamos.setItems(prestamos);
            tvPrestamos.refresh();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Este metodo abre primero la ventana de libros para elegir alguno de los que hay en la tabla, seguidamente,
     * cuando se hace doble click a algun libro se cierra y se abre la ventana de socios para escoger el socio al que se le va a hacer el prestamo.
     *
     * @param event
     */
    @FXML
    void onClickNuevoPrest(MouseEvent event) {
        if (dpFechaInicio.getValue()==null || dpFechaFin.getValue()==null) {
            alertaDeError("Debe elegir la fecha de inicio y fin del prestamo");
        }
        else {

            try {
                //Cargamos la ventana de la gestion de los Prestamos
                FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("nuevoPrestamoSeleccionarLibro-view.fxml"));
                Parent root = loader.load();

                NuevoPrestamoSeleccionarLibroController nuevoPrestamoSeleccionarLibroController = loader.getController();

                nuevoPrestamoSeleccionarLibroController.setOnGetLibro(libro -> {
                    Libro libroPrest = new Libro(libro.getIsbn(), libro.getTitulo(), libro.getAnio(), libro.getAutor(), libro.getGenero());
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
                //Cargamos la ventana de la gestion de los Prestamos
                FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("nuevoPrestamoSeleccionarSocio-view.fxml"));
                Parent root = loader.load();

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
                Prestamo prestamo = new Prestamo(
                        cont,
                        Date.valueOf(dpFechaInicio.getValue()),
                        Date.valueOf(dpFechaFin.getValue()),
                        "Prestado",
                        libroPrest.getTitulo(),
                        socioPrest.getNombreSocio());
                cont++;
                if (listaPrestamos.contains(prestamo)) {
                    alertaDeError("Ya existe un prestamo con ese Libro y ese Socio");
                } else {
                    try {
                        prestamoDAO.insertPrestamo(prestamo);
                        actualizartvPrestamos();
                    } catch (SQLException e) {
                        System.err.println("Error al guardar el prestamo: " + prestamo.toString());
                    }
                }
/*
        try {
            Libro libro = null;

            if (comprobarDatos()) {
                libro = new Libro(
                        tfIsbn.getText(),
                        tfTitulo.getText(),
                        tfAutor.getText(),
                        tfAno.getText(),
                        cbGenero.getValue()
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
 */
                //----------------------------------------------------------------
/*
        Prestamo prestamo = null;

        if (comprobarDatos()) {
            prestamo = new Prestamo(
                    cont,
                    libroPrest.getIsbn(),
                    tfAutor.getText(),
                    tfAno.getText(),
                    cbGenero.getValue()
            );
            cont++;
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
    } catch(
    IOException e)

    {
        System.err.println(e.getMessage());
    }

 */

            }
        }

        }
    @FXML
    void onClicktvPrestamos(MouseEvent event) {
        tfNumRefBor.setText(tcNumRef.getText());
    }


    /**
     * Actualiza la tabla de prestamos por si se añade o se quita alguno
     */
    public void actualizartvPrestamos() {

        try {
            listaPrestamos = FXCollections.observableArrayList(prestamoDAO.getAllPrestamos());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //Con esto se asocia la lista a la tabla
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
     * Actualiza el comboBox de estado
     */
    public void actualizarCbEstado() {
        try {
            List<Prestamo> listaPrestamo = prestamoDAO.getAllPrestamos();
            //Añadimos el valor al comboBox
            cbBuscar.getItems().add("DEVUELTO");
            cbBuscar.getItems().add("PENDIENTE");
            cbBuscar.getItems().add("VENCIDO");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @return Devuelve true en el caso de que todas las comprobaciondes sean correctas
     */
       /*public boolean comprobarDatos() {
                boolean bool = true;

                if (!tfNumRefModif.getText().matches("^[0-9]{10,13}$")) {
                        alertaDeError("El ISBN es incorrecto o esta vacio");
                        tfNumRefModif.requestFocus();
                        bool = false;
                } else if (tfNumSocioModif.getText().isEmpty()) {
                        alertaDeError("El título no peude ser un campo vacio");
                        tfNumSocioModif.requestFocus();
                } else if (!tfFechaInicioModif.getText().matches("^[0-9]{1,4}$")) {
                        alertaDeError("El año no es correcto o esta vacio ");
                        tfFechaInicioModif.requestFocus();
                        bool = false;
                } else if (tfFechaDinModif.getText().isEmpty()) {
                        alertaDeError("El autor no puede ser un campo vacio");
                        tfFechaDinModif.requestFocus();
                        bool = false;
                }
                return bool;
        }
        */

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
}







