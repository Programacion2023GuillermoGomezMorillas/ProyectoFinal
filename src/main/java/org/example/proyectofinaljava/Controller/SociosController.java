package org.example.proyectofinaljava.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.proyectofinaljava.db.SocioDAO;
import org.example.proyectofinaljava.model.Libro;
import org.example.proyectofinaljava.model.Socio;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SociosController implements Initializable {
    private ObservableList<Socio> listaSocios;
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
    private ComboBox<String> cbBuscar;

    @FXML
    private ComboBox<String> cbGenero;

    @FXML
    private ComboBox<String> cbGeneroModif;

    @FXML
    private TableColumn<Socio, String> tcAno;

    @FXML
    private TableColumn<Socio, String> tcAutor;

    @FXML
    private TableColumn<Socio, String> tcGenero;

    @FXML
    private TableColumn<Socio, String> tcIsbn;

    @FXML
    private TableColumn<Socio, String> tcTitulo;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TextField tfTelefonoModif;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfDireccionModif;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfEmailModif;

    @FXML
    private TextField tfBuscar;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfNumeroBor;

    @FXML
    private TextField tfNumeroModif;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfNombreModif;
    @FXML
    private TableView<Socio> tvLibros;

    @FXML
    void onClickTvLibros(MouseEvent event) {
        Socio socio = tvLibros.getSelectionModel().getSelectedItem();
        //si hay un socio seleccionado mostramos los datos
        if (socio != null) {
            tfNumeroModif.setText(String.valueOf(socio.getNumeroSocio()));
            tfNombreModif.setText(socio.getNombreSocio());
            tfTelefonoModif.setText(socio.getTelefonoSocio());
            tfDireccionModif.setText(socio.getDireccionSocio());
            tfEmailModif.setText(socio.getEmailSocio());
            tfNumeroBor.setText(String.valueOf(socio.getNumeroSocio()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socioDAO = SocioDAO.getConnection();
        actualizarTvLibros();
        //Metemos en los combobox
        actualizarCbBuscar();

        //Asociamos la lista a la tabla
        tvLibros.setItems(listaSocios);
        tvLibros.refresh();

    }

    public void actualizarTvLibros() {
        try {
            listaSocios = FXCollections.observableArrayList(socioDAO.getAllSocios());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //Con esto se asocia la lista a la tabla
        tcIsbn.setCellValueFactory(new PropertyValueFactory<>("numeroSocio"));
        tcTitulo.setCellValueFactory(new PropertyValueFactory<>("nombreSocio"));
        tcAutor.setCellValueFactory(new PropertyValueFactory<>("direccionSocio"));
        tcAno.setCellValueFactory(new PropertyValueFactory<>("telefonoSocio"));
        tcGenero.setCellValueFactory(new PropertyValueFactory<>("emailSocio"));
        tvLibros.setItems(listaSocios);
        tvLibros.refresh();

    }

    /**
     * @param event para borrar un libro
     */
    @FXML
    void onClickBorrar(MouseEvent event) {
        if (tvLibros.getSelectionModel().isEmpty()) {
            alertaDeError("Selecciona un libro que quieras eliminar");
        } else {
            try {
                socioDAO.deleteLibroByNumeroSocio(tfNumeroBor.getText());
                tfNumero.setText("");
                tfNombre.setText("");
                tfTelefono.setText("");
                tfDireccion.setText("");
                actualizarTvLibros();

            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }

        }

    }


    /**
     * @param event para buscar un libro
     */
    @FXML
    void onClickBuscar(MouseEvent event) {
        //buscamos el libro seleccionado

        if (tfBuscar.getText().isEmpty()) {
            alertaDeError("Introduce una busqueda");
        } else {
            try {
                ObservableList<Socio> libros;

                if (cbBuscar.getValue().equals("NUMERO")) {
                    libros = FXCollections.observableArrayList(socioDAO.getSocioByNumeroSocio(tfBuscar.getText()));
                } else {
                    libros = FXCollections.observableArrayList(socioDAO.getSocioByNombre(tfNumero.getText()));
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
     * @param event para insertar un libro
     */
    @FXML
    void onClickInsertar(MouseEvent event) {
        Socio socio = null;

        if (comprobarDatosInsert()) {
            socio = new Socio(
                    Integer.parseInt(tfNumero.getText()),
                    tfNombre.getText(),
                    tfDireccion.getText(),
                    tfTelefono.getText(),
                    tfEmail.getText()
            );
        }

        if (socio != null) {
            if (listaSocios.contains(socio)) {
                alertaDeError("Ya existe un socio con ese número");
            } else {
                try {
                    socioDAO.insertSocio(socio);
                    actualizarTvLibros();
                    limpiarDatosModif();
                } catch (SQLException e) {
                    System.out.println("Error al guardar: " + socio);
                }
            }
        }
    }

    private boolean comprobarDatosInsert() {
        boolean bool = true;

        if (!tfNumero.getText().matches("^[0-9]+$")) {
            alertaDeError("El numero es incorrecto o esta vacio");
            tfNumero.requestFocus();
            bool = false;
        } else if (tfNombre.getText().isEmpty()) {
            alertaDeError("El nombre no puede ser un campo vacio");
            tfNombre.requestFocus();
        } else if (!tfTelefono.getText().matches("^[0-9]+$")) {
            alertaDeError("El Teléfono no es correcto o esta vacio ");
            tfTelefono.requestFocus();
            bool = false;
        } else if (tfDireccion.getText().isEmpty()) {
            alertaDeError("La dirección no puede estar vacia");
            tfDireccion.requestFocus();
            bool = false;
        }
        else if (tfEmailModif.getText().matches("^(\\.+)@(\\S+)$")) {
            alertaDeError("El email no es correcto");
            tfEmailModif.requestFocus();
            bool = false;
        }


        return bool;
    }


    /**
     * @param event para modificar un libro
     */
    @FXML
    void onClickModificar(MouseEvent event) {
        Socio socio = null;

        if (comprobarDatos()) {
            socio = new Socio(
                    Integer.parseInt(tfNumeroModif.getText()),
                    tfNombreModif.getText(),
                    tfDireccionModif.getText(),
                    tfTelefonoModif.getText(),
                    tfEmailModif.getText()

            );
        }

        if (socio != null) {
            try {
                socioDAO.updateLibro(socio);
                actualizarTvLibros();
                limpiarDatosModif();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    /**
     * Metodo para actualizar el ComboBox que se encarga de listar los generos
     */

    public void actualizarCbBuscar() {
        try {
            List<Socio> listaSocio = socioDAO.getAllSocios();
            //Añadimos el valor al comboBox
            cbBuscar.getItems().add("NUMERO");
            cbBuscar.getItems().add("NOMBRE");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @return Devuelve true en el caso de que todas las comprobaciondes sean correctas
     */
    public boolean comprobarDatos() {
        boolean bool = true;

        if (!tfNumeroModif.getText().matches("^[0-9]+$")) {
            alertaDeError("El numero es incorrecto o esta vacio");
            tfNumeroModif.requestFocus();
            bool = false;
        } else if (tfNombreModif.getText().isEmpty()) {
            alertaDeError("El nombre no puede ser un campo vacio");
            tfNombreModif.requestFocus();
        } else if (!tfDireccionModif.getText().matches("^[0-9]+$")) {
            alertaDeError("El Teléfono no es correcto o esta vacio ");
            tfDireccionModif.requestFocus();
            bool = false;
        } else if (tfTelefonoModif.getText().isEmpty()) {
            alertaDeError("La dirección no puede estar vacia");
            tfTelefonoModif.requestFocus();
            bool = false;
        }
         else if (tfEmailModif.getText().matches("^(.+)@(\\S+)$")) {
            alertaDeError("El email no es correcto");
            tfDireccionModif.requestFocus();
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
        tfNumeroModif.setText("");
        tfNombreModif.setText("");
        tfTelefonoModif.setText("");
        tfDireccionModif.setText("");
        tfEmail.setText("");
        tfEmailModif.setText("");
        tfNumero.setText("");
        tfNombre.setText("");
        tfTelefono.setText("");
        tfDireccion.setText("");
        tfNumeroBor.setText("");

    }
}
