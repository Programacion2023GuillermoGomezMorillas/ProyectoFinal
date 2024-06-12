package org.example.proyectofinaljava.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    @FXML
    private Button btBorrar;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btInsertar;

    @FXML
    private Button btModificar;

    @FXML
    private Button btSelectLibro;

    @FXML
    private Button btSelectSocio;

    @FXML
    private ComboBox<String> cbBuscar;

    @FXML
    private Label lbIsbn;

    @FXML
    private Label lbNombre;

    @FXML
    private Label lbNumero;

    @FXML
    private Label lbTitulo;
    @FXML
    private TableColumn<Prestamo, String> tcEstado;

    @FXML
    private TableColumn<Prestamo, String> tcNumSocio;

    @FXML
    private TableColumn<Prestamo, String> tcFechaInicio;

    @FXML
    private TableColumn<Prestamo, String> tcFechaFin;

    @FXML
    private TableColumn<Prestamo, String> tcNumRef;

    @FXML
    private TableColumn<Prestamo, String> tcIsbn;

    @FXML
    private Label tf;

    @FXML
    private TextField tfFechaFin;

    @FXML
    private TextField tfFechaFinModif;

    @FXML
    private TextField tfFechaInicio;

    @FXML
    private TextField tfFechaInicioModif;

    @FXML
    private TextField tfIsbnModif;

    @FXML
    private TextField tfNumRef;

    @FXML
    private TextField tfNumRefBor;

    @FXML
    private TextField tfNumRefModif;

    @FXML
    private TextField tfNumSocioModif;

    @FXML
    private TableView<Prestamo> tvPrestamos;

    /**
     * @param event Cuando se seleccione un tupla, los campos de modificar se pondrán automaticamente
     */
    @FXML
    void onClicktvPrestamos(MouseEvent event) {
        Prestamo prestamo = tvPrestamos.getSelectionModel().getSelectedItem();
        //si hay un prestamo seleccionado mostramos los datos
        if (prestamo != null) {
            tfIsbnModif.setText(prestamo.getIsbnLibro());
            tfNumSocioModif.setText(Long.toString(prestamo.getNumeroSocio()));
            tfNumRefModif.setText(prestamo.getNumReserva());
            tfFechaInicioModif.setText(String.valueOf(prestamo.getFechaInicio()));
            tfFechaFinModif.setText(String.valueOf(prestamo.getFechaFin()));
        }
    }

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
        tcIsbn.setCellValueFactory(new PropertyValueFactory<>("isbnLibro"));
        tcFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        tcNumSocio.setCellValueFactory(new PropertyValueFactory<>("numeroSocio"));
        tcFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
        tcEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
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
                limpiarDatosModif();
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
     * @param event para insertar un prestamo
     */
    @FXML
    void onClickInsertar(MouseEvent event) {
        Prestamo prestamo = null;

        //if (comprobarDatos()) {
        prestamo = new Prestamo(
                tfNumRef.getText(),
                Date.valueOf(tfFechaInicio.getText()),
                Date.valueOf(tfFechaFin.getText()),
                "Pendiente",
                lbIsbn.getText(),
                Integer.parseInt(lbNumero.getText())
        );
        //}

        if (prestamo != null) {
            if (listaPrestamos.contains(prestamo)) {
                alertaDeError("Ya existe un prestamo con ese numero de referencia");
            } else {
                try {
                    prestamoDAO.insertPrestamo(prestamo);
                    actualizartvPrestamos();
                    limpiarDatosModif();
                } catch (SQLException e) {
                    System.out.println("Error al guardar: " + prestamo);
                }
            }
        }
    }

    /**
     * @param event para modificar un libro
     */
    @FXML
    void onClickModificar(MouseEvent event) {
        Prestamo prestamo = null;

        //if (comprobarDatos()) {
        prestamo = new Prestamo(
                tfNumRefModif.getText(),
                Date.valueOf(tfFechaInicioModif.getText()),
                Date.valueOf(tfFechaFinModif.getText()),
                "Pendiente",
                tfIsbnModif.getText(),
                Integer.parseInt(tfNumSocioModif.getText())

        );
        // }

        if (prestamo != null) {
            try {
                prestamoDAO.updatePrestamo(prestamo);
                actualizartvPrestamos();
                limpiarDatosModif();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    //TODO: Meter la ventana segundaria
    @FXML
    void onClickSelecLibro(MouseEvent event) {
        try {
            //Cargamos la ventana de la gestion de los Prestamos
            FXMLLoader loader = new FXMLLoader(Launcher.class.getResource("seleccionarLibro-view.fxml"));
            Parent root = loader.load();

            SeleccionarLibroController seleccionarLibroController= loader.getController();

            seleccionarLibroController.setOnGetLibro(libro -> {
                lbTitulo.setText(libro.getTitulo());
                lbIsbn.setText(libro.getIsbn());
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
    }



    //TODO: Meter la ventana segundaria
    @FXML
    void onClickSelecSocio(MouseEvent event) {
        alertaDeError("a");

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

    /**
     * Metodo para limpiar los campos
     */
    public void limpiarDatosModif() {
        tfNumRefModif.setText("");
        tfNumSocioModif.setText("");
        tfFechaInicio.setText("");
        tfFechaInicioModif.setText("");
        tfNumRef.setText("");
        tfNumRefModif.setText("");
        tfFechaFin.setText("");
        tfFechaFinModif.setText("");
        lbNumero.setText("");
        lbIsbn.setText("");
        lbTitulo.setText("");
        lbNombre.setText("");


    }


}






