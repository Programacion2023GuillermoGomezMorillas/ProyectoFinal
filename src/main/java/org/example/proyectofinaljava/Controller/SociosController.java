package org.example.proyectofinaljava.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.proyectofinaljava.db.SocioDAO;
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
    private Button btQuitarFiltro;

    @FXML
    private Button btVolver;

    @FXML
    private ComboBox<String> cbBuscar;

    @FXML
    private TableColumn<Socio, String> tcDireccion;

    @FXML
    private TableColumn<Socio, String> tcEmail;

    @FXML
    private TableColumn<Socio, String> tcNombre;

    @FXML
    private TableColumn<Socio, String> tcNumero;

    @FXML
    private TableColumn<Socio, String> tcTelefono;

    @FXML
    private TextField tfBuscar;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfNumero;

    @FXML
    private TextField tfTelefono;

    @FXML
    private TableView<Socio> tvSocios;

    /**
     * @param event para borrar un libro
     */
    @FXML
    void onClickBorrar(MouseEvent event) {
        if (tvSocios.getSelectionModel().isEmpty()) {
            alertaDeError("Selecciona un socio que quieras eliminar");
        } else {
            try {
                socioDAO.deleteSocioByNumeroSocio(tfNumero.getText());
                tfNumero.setText("");
                tfNombre.setText("");
                tfTelefono.setText("");
                tfDireccion.setText("");
                actualizarTvSocios();

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
                ObservableList<Socio> socios;

                if (cbBuscar.getValue().equals("NUMERO")) {
                    socios = FXCollections.observableArrayList(socioDAO.getSocioByNumeroSocio(tfBuscar.getText()));
                } else {
                    socios = FXCollections.observableArrayList(socioDAO.getSocioByNombre(tfBuscar.getText()));
                }

                //Asociamos la lista a la tabla
                tvSocios.setItems(socios);
                tvSocios.refresh();
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
                    actualizarTvSocios();
                } catch (SQLException e) {
                    System.out.println("Error al guardar: " + socio);
                }
            }
        }
    }

    /**
     * @param event para modificar un libro
     */
    @FXML
    void onClickModificar(MouseEvent event) {
        Socio socio = null;

        if (comprobarDatos()) {
            socio = new Socio(
                    Integer.parseInt(tfNumero.getText()),
                    tfNombre.getText(),
                    tfDireccion.getText(),
                    tfTelefono.getText(),
                    tfEmail.getText()
            );
        }

        if (socio != null) {
            try {
                socioDAO.updateSocio(socio);
                actualizarTvSocios();
                limpiarDatosModif();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    @FXML
    void onClickQuitarFiltro(MouseEvent event) {
    actualizarTvSocios();
    }

    @FXML
    void onClickTvSocios(MouseEvent event) {
        Socio socio = tvSocios.getSelectionModel().getSelectedItem();
        //si hay un socio seleccionado mostramos los datos
        if (socio != null) {
            tfNumero.setText(String.valueOf(socio.getNumeroSocio()));
            tfNombre.setText(socio.getNombreSocio());
            tfTelefono.setText(socio.getTelefonoSocio());
            tfDireccion.setText(socio.getDireccionSocio());
            tfEmail.setText(socio.getEmailSocio());
        }
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socioDAO = SocioDAO.getConnection();
        actualizarTvSocios();
        //Metemos en los combobox
        actualizarCbBuscar();

        //Asociamos la lista a la tabla
        tvSocios.setItems(listaSocios);
        tvSocios.refresh();

    }

    public void actualizarTvSocios() {
        try {
            listaSocios = FXCollections.observableArrayList(socioDAO.getAllSocios());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //Con esto se asocia la lista a la tabla
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numeroSocio"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombreSocio"));
        tcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionSocio"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoSocio"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("emailSocio"));
        tvSocios.setItems(listaSocios);
        tvSocios.refresh();

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
        else if (tfEmail.getText().matches("^(\\.+)@(\\S+)$")) {
            alertaDeError("El email no es correcto");
            tfEmail.requestFocus();
            bool = false;
        }


        return bool;
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
         else if (tfEmail.getText().matches("^(.+)@(\\S+)$")) {
            alertaDeError("El email no es correcto");
            tfEmail.requestFocus();
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
        tfEmail.setText("");
        tfNumero.setText("");
        tfNombre.setText("");
        tfTelefono.setText("");
        tfDireccion.setText("");
    }
}
