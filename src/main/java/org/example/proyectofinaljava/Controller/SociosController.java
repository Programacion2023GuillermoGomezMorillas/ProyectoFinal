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

/**
 * Controlador para la gestión de socios.
 */
public class SociosController implements Initializable {
    private ObservableList<Socio> listaSocios;
    private SocioDAO socioDAO;
    private long cont;
    private Socio socioComprobante;

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
     * Método para manejar el evento de clic en el botón "Borrar".
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickBorrar(MouseEvent event) {
        if (tvSocios.getSelectionModel().isEmpty()) {
            alertaDeError("Selecciona un socio que quieras eliminar");
        } else {
            try {
                socioDAO.deleteSocioByNumeroSocio(tfNumero.getText());
                actualizarTvSocios();
                limpiarDatosModif();
            } catch (SQLException e) {
                alertaDeError("No puedes eliminar un socio con un libro prestado");
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
        if (tfBuscar.getText().isEmpty()) {
            alertaDeError("Introduce una búsqueda");
        } else {
            try {
                ObservableList<Socio> socios = null;
                if (cbBuscar.getValue() == null) {
                    alertaDeError("Tiene que seleccionar una busqueda");
                } else {
                    if (cbBuscar.getValue().equals("NUMERO")) {
                        socios = FXCollections.observableArrayList(socioDAO.getSocioByNumeroSocio(tfBuscar.getText()));
                    } else if (cbBuscar.getValue().equals("NOMBRE")) {
                        socios = FXCollections.observableArrayList(socioDAO.getSocioByNombre(tfBuscar.getText()));
                    }
                }
                if (socios.isEmpty()) {
                    alertaDeError("No existe esa busqueda");
                } else {
                    tvSocios.setItems(socios);
                    tvSocios.refresh();
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
        Socio socio = null;
        if (comprobarDatosTextField()) {
            try {
                cont = socioDAO.getNumeroReserva() + 1;
                socio = new Socio(
                        cont,
                        tfNombre.getText(),
                        tfDireccion.getText(),
                        tfTelefono.getText(),
                        tfEmail.getText()
                );
            } catch (SQLException e) {
                alertaDeError("Error al crear el socio");
            }
        }
        if (socio != null && comprobarDatosIguales(socio)) {
            if (listaSocios.contains(socio)) {
                alertaDeError("Ya existe un socio con esos datos");
            } else {
                try {
                    socioDAO.insertSocio(socio);
                    actualizarTvSocios();
                } catch (SQLException e) {
                    alertaDeError("Error al guardar:");
                }
            }
        } else {
            alertaDeError("Ya existe un Socio con esos datos");
        }
    }

    /**
     * Método para manejar el evento de clic en el botón "Modificar".
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickModificar(MouseEvent event) {
        Socio socio = null;
        if (comprobarDatosTextField()) {
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
                alertaDeError("No se puedo modificar");
            }
        }
    }

    /**
     * Comprueba si los datos del socio son iguales a los de la lista de socios.
     *
     * @param socio El socio a comprobar.
     * @return true si los datos son iguales, false en caso contrario.
     */
    private boolean comprobarDatosIguales(Socio socio) {
        boolean correcto = true;
        for (Socio socioLista : listaSocios) {
            if (socioLista.equals(socio)) {
                correcto = false;
            }
        }
        return correcto;
    }

    /**
     * Método para manejar el evento de clic en el botón "Quitar Filtro".
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickQuitarFiltro(MouseEvent event) {
        actualizarTvSocios();
    }

    /**
     * Método para manejar el evento de clic en la tabla de socios.
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickTvSocios(MouseEvent event) {
        this.socioComprobante = tvSocios.getSelectionModel().getSelectedItem();
        if (socioComprobante != null) {
            tfNumero.setText(String.valueOf(socioComprobante.getNumeroSocio()));
            tfNombre.setText(socioComprobante.getNombreSocio());
            tfTelefono.setText(socioComprobante.getTelefonoSocio());
            tfDireccion.setText(socioComprobante.getDireccionSocio());
            tfEmail.setText(socioComprobante.getEmailSocio());
        }
    }

    /**
     * Método para manejar el evento de clic en el botón "Volver".
     *
     * @param event el evento de clic del ratón.
     */
    @FXML
    void onClickVolver(MouseEvent event) {
        Stage stage = (Stage) btVolver.getScene().getWindow();
        stage.close();
    }

    /**
     * Método que se ejecuta al inicializar la ventana.
     *
     * @param url            URL de la localización.
     * @param resourceBundle ResourceBundle de los recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socioDAO = SocioDAO.getConnection();
        actualizarTvSocios();
        actualizarCbBuscar();
        tfNumero.setDisable(true);
        tvSocios.setItems(listaSocios);
        tvSocios.refresh();
    }

    /**
     * Método para actualizar los datos de la tabla de socios.
     */
    public void actualizarTvSocios() {
        try {
            listaSocios = FXCollections.observableArrayList(socioDAO.getAllSocios());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numeroSocio"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombreSocio"));
        tcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionSocio"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoSocio"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("emailSocio"));
        tvSocios.setItems(listaSocios);
        tvSocios.refresh();
    }

    /**
     * Método para actualizar el ComboBox con las opciones de búsqueda.
     */
    public void actualizarCbBuscar() {
        try {
            List<Socio> listaSocio = socioDAO.getAllSocios();
            cbBuscar.getItems().add("NUMERO");
            cbBuscar.getItems().add("NOMBRE");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Comprueba si los datos de los campos de texto son válidos.
     *
     * @return true si todos los datos son válidos, false en caso contrario.
     */
    public boolean comprobarDatosTextField() {
        boolean bool = true;
        if (tfNombre.getText().isEmpty()) {
            alertaDeError("El nombre no puede ser un campo vacío");
            tfNombre.requestFocus();
        } else if (!tfTelefono.getText().matches("^[0-9]{9}$")) {
            alertaDeError("El Teléfono no es correcto o está vacío");
            tfTelefono.requestFocus();
            bool = false;
        } else if (tfDireccion.getText().isEmpty()) {
            alertaDeError("La dirección no puede estar vacía");
            tfDireccion.requestFocus();
            bool = false;
        } else if (!tfEmail.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$") || tfEmail.getText().isEmpty()) {
            alertaDeError("El email no es correcto");
            tfEmail.requestFocus();
            bool = false;
        }
        return bool;
    }

    /**
     * Muestra una alerta de error con el mensaje especificado.
     *
     * @param mensaje El mensaje a mostrar en la alerta.
     */
    private void alertaDeError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Limpia los datos de los campos de texto.
     */
    public void limpiarDatosModif() {
        tfEmail.setText("");
        tfNumero.setText("");
        tfNombre.setText("");
        tfTelefono.setText("");
        tfDireccion.setText("");
        tfEmail.setText("");
    }
}
