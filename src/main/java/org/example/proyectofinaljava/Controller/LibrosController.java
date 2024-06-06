package org.example.proyectofinaljava.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class LibrosController {

    @FXML
    private Button btBorrar;

    @FXML
    private Button btBuscar;

    @FXML
    private Button btInsertar;

    @FXML
    private Button btModificar;

    @FXML
    private ChoiceBox<?> cbBuscar;

    @FXML
    private ChoiceBox<?> cbGenero;

    @FXML
    private ChoiceBox<?> cbGeneroModif;

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
    private TextField tfAno;

    @FXML
    private TextField tfAnoModif;

    @FXML
    private TextField tfAutor;

    @FXML
    private TextField tfAutorModif;

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

}
