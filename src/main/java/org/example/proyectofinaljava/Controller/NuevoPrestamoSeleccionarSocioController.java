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
import org.example.proyectofinaljava.db.SocioDAO;
import org.example.proyectofinaljava.model.Libro;
import org.example.proyectofinaljava.model.Socio;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controlador para seleccionar un socio a la hora de elegir un nuevo préstamo.
 */
public class NuevoPrestamoSeleccionarSocioController implements Initializable {
    ObservableList<Socio> listaSocios;

    private SocioDAO socioDAO;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btNuevoLibro;

    @FXML
    private Button btRefrescar;

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
    private TableColumn<Socio, Long> tcNumeroSocio;

    @FXML
    private TableColumn<Socio, String> tcTelefono;

    @FXML
    private TextField tfBuscar;

    @FXML
    private TableView<Socio> tvSocio;

    /**
     * Método para buscar dentro de la tabla de socios.
     *
     * @param event Evento de ratón.
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
                    if (cbBuscar.getValue() == null) {
                        alertaDeError("Selecciona un método de búsqueda");
                        return;
                    } else if (cbBuscar.getValue().equals("NUMERO")) {
                        socios = FXCollections.observableArrayList(socioDAO.getSocioByNumeroSocio(tfBuscar.getText()));
                    } else if (cbBuscar.getValue().equals("NOMBRE")) {
                        socios = FXCollections.observableArrayList(socioDAO.getSocioByNombre(tfBuscar.getText()));
                    }
                }
                if (socios.isEmpty()) {
                    alertaDeError("No existe esa busqueda");
                } else {
                    tvSocio.setItems(socios);
                    tvSocio.refresh();
                }
            } catch (SQLException e) {
                alertaDeError("Busqueda no encontrada");
            }
        }
    }

    /**
     * Refresca la tabla de los socios.
     *
     * @param event Evento de ratón.
     */
    @FXML
    void onClickRefrescar(MouseEvent event) {
        actualizarTvSocios();
    }

    /**
     * Método para cuando se haga clic en la tabla de socios.
     *
     * @param event Evento de ratón.
     */
    @FXML
    void onClickTvSocios(MouseEvent event) {
        tvSocio.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                //cuando el usuario acepte, llamamos a la acción definida en la ventana principal y salimos
                if (onGetSocio != null) {
                    onGetSocio.obtenSocio(tvSocio.getSelectionModel().getSelectedItem());
                    //cerramos la ventana
                    Stage stage = (Stage) tvSocio.getScene().getWindow();
                    stage.close();
                } else {
                    alertaDeError("Seleccione un socio");
                }
            }
        });
    }

    /**
     * Método para volver a la ventana anterior.
     *
     * @param event Evento de ratón.
     */
    @FXML
    void OnClickVolver(MouseEvent event) {
        this.onGetSocio = null;
        Stage stage = (Stage) btVolver.getScene().getWindow();
        stage.close();
    }

    /**
     * Método que se ejecuta cuando se inicia la ventana.
     *
     * @param url            URL de la localización.
     * @param resourceBundle ResourceBundle de los recursos.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socioDAO = SocioDAO.getConnection();
        this.onGetSocio = null;
        actualizarTvSocios();
        //Metemos en los combobox
        actualizarCbBuscar();

        //Asociamos la lista a la tabla
        tvSocio.setItems(listaSocios);
        tvSocio.refresh();
    }

    /**
     * Método para añadir al ComboBox las opciones para buscar.
     */
    private void actualizarCbBuscar() {
        //Añadimos el valor al ComboBox
        cbBuscar.getItems().add("NOMBRE");
        cbBuscar.getItems().add("NUMERO DE SOCIO");
    }

    /**
     * Método para actualizar los campos de la tabla.
     */
    public void actualizarTvSocios() {
        try {
            listaSocios = FXCollections.observableArrayList(socioDAO.getAllSocios());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //Con esto se asocia la lista a la tabla
        tcNumeroSocio.setCellValueFactory(new PropertyValueFactory<>("numeroSocio"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombreSocio"));
        tcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccionSocio"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefonoSocio"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<>("emailSocio"));
        tvSocio.setItems(listaSocios);
        tvSocio.refresh();
    }

    /**
     * Método para mostrar un mensaje de error al usuario.
     *
     * @param mensaje El mensaje a mostrar.
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
     * Interfaz para asignar la acción cuando volvamos de llamar a la ventana secundaria.
     */
    public interface OnGetSocio {
        void obtenSocio(Socio socio);
    }

    //la instancia a la que llamaremos cuando el usuario pulse a aceptar
    private NuevoPrestamoSeleccionarSocioController.OnGetSocio onGetSocio;

    /**
     * Método para asignar la acción a realizar al obtener el socio.
     *
     * @param onGetSocio La acción a realizar.
     */
    public void setOnGetSocio(NuevoPrestamoSeleccionarSocioController.OnGetSocio onGetSocio) {
        this.onGetSocio = onGetSocio;
    }

    /**
     * Método vacío para seleccionar un socio.
     */
    public void seleccionarSocio() {
    }
}
