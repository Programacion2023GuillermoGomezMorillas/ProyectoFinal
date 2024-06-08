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
    private TableColumn<Libro, String> tcAno;

    @FXML
    private TableColumn<Libro, String> tcAutor;

    @FXML
    private TableColumn<Libro, String> tcGenero;

    @FXML
    private TableColumn<Libro, String> tcIsbn;

    @FXML
    private TableColumn<Libro, String> tcTitulo;

    @FXML
    private TextField tfAno;

    @FXML
    private TextField tfAnoModif;

    @FXML
    private TextField tfAutor;

    @FXML
    private TextField tfAutorModif;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfEmailModif;

    @FXML
    private TextField tfBuscar;

    @FXML
    private TextField tfIsbn;

    @FXML
    private TextField tfIsbnBor;

    @FXML
    private TextField tfIsbnModif;

    @FXML
    private TextField tfTitulo;

    @FXML
    private TextField tfTituloModif;
    @FXML
    private TableView<Socio> tvLibros;

    @FXML
    void onClickTvLibros(MouseEvent event) {
        Socio socio = tvLibros.getSelectionModel().getSelectedItem();
        //si hay un libro seleccionado mostramos los datos
        if (socio != null) {
            tfIsbnModif.setText(String.valueOf(socio.getNumeroSocio()));
            tfTituloModif.setText(socio.getNombreSocio());
            tfAnoModif.setText(socio.getDireccionSocio());
            tfAutorModif.setText(socio.getTelefonoSocio());
            tfEmailModif.setText(socio.getEmailSocio());
            tfIsbnBor.setText(String.valueOf(socio.getNumeroSocio()));
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
                socioDAO.deleteLibroByNumeroSocio(tfIsbnBor.getText());
                tfIsbn.setText("");
                tfTitulo.setText("");
                tfAno.setText("");
                tfAutor.setText("");
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
                    libros = FXCollections.observableArrayList(socioDAO.getSocioByNombre(tfIsbn.getText()));
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

        if (comprobarDatos()) {
            socio = new Socio(
                    Integer.parseInt(tfIsbn.getText()),
                    tfTitulo.getText(),
                    tfAutor.getText(),
                    tfAno.getText(),
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
                    System.out.printf("Error al guardar: " + socio);
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
                    Integer.parseInt(tfIsbnModif.getText()),
                    tfTituloModif.getText(),
                    tfAutorModif.getText(),
                    tfAnoModif.getText(),
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

        if (!tfIsbnModif.getText().matches("^[0-9]+$")) {
            alertaDeError("El numero es incorrecto o esta vacio");
            tfIsbnModif.requestFocus();
            bool = false;
        } else if (tfTituloModif.getText().isEmpty()) {
            alertaDeError("El nombre no puede ser un campo vacio");
            tfTituloModif.requestFocus();
        } else if (!tfAnoModif.getText().matches("^[0-9]+$")) {
            alertaDeError("El Teléfono no es correcto o esta vacio ");
            tfAnoModif.requestFocus();
            bool = false;
        } else if (tfAutorModif.getText().isEmpty()) {
            alertaDeError("La dirección no puede estar vacia");
            tfAutorModif.requestFocus();
            bool = false;
        }
         else if (tfEmailModif.getText().matches("^(.+)@(\\S+)$")) {
            alertaDeError("El email no es correcto");
            tfAutorModif.requestFocus();
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
        tfIsbnModif.setText("");
        tfTituloModif.setText("");
        tfAnoModif.setText("");
        tfAutorModif.setText("");
        cbGeneroModif.setValue("");
        tfIsbn.setText("");
        tfTitulo.setText("");
        tfAno.setText("");
        tfAutor.setText("");
        cbGenero.setValue("");
        tfIsbnBor.setText("");

    }
}
