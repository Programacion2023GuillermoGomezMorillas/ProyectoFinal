package org.example.proyectofinaljava.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.proyectofinaljava.db.GeneroDAO;
import org.example.proyectofinaljava.db.LibroDAO;
import org.example.proyectofinaljava.model.Libro;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class NuevoPrestamoController implements Initializable {

    private ObservableList<Libro> listaLibros;
    private LibroDAO libroDAO;
    private GeneroDAO generoDAO;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btNuevoLibro;

    @FXML
    private Button btVolver;

    @FXML
    private ComboBox<String> cbBuscar;

    @FXML
    private ComboBox<String> cbGeneroModif;

    @FXML
    private TableColumn<?, ?> tcAno;

    @FXML
    private TableColumn<?, ?> tcAutor;

    @FXML
    private TableColumn<?, ?> tcGenero;

    @FXML
    private TableColumn<?, ?> tcIsbn;

    @FXML
    private TableColumn<?, ?> tcTitulo;

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

    @FXML
    void onClickBuscar(MouseEvent event) {

    }

    @FXML
    void onClickTvLibros(MouseEvent event) {

    }
    /**
     * Al iniciar la ventana se ejecutará el siguiente método
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generoDAO = GeneroDAO.getConnection();
        libroDAO = LibroDAO.getConnection();
        actualizarTvLibros();
        //Metemos en los combobox
        actualizarCbBuscar();

        //Asociamos la lista a la tabla
        tvLibros.setItems(listaLibros);
        tvLibros.refresh();
    }

    /**
     * Añaidos al comboBox los tres valores para buscar sobre ellos
     */
    public void actualizarCbBuscar() {
        try {
            List<String> listaGeneros = generoDAO.getAllGeneros();
            //Añadimos el valor al comboBox
            cbBuscar.getItems().add("GENERO");
            cbBuscar.getItems().add("TITULO");
            cbBuscar.getItems().add("IBSN");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
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
    //esta interface nos permite asignar la acción cuando volvamos de llamar a la ventana secundaria
    public interface OnGetLibro {
        void obtenLibro(Libro libro);
    }

    //la instancia  a la que llamaremos cuando el usuario pulse a aceptar
    private SeleccionarLibroController.OnGetLibro onGetLibro;

    //nos permitirá asignar la lambda en la principal con la acción que realizaremos
    public void setOnGetLibro(SeleccionarLibroController.OnGetLibro onGetLibro) {
        this.onGetLibro = onGetLibro;
    }
}


