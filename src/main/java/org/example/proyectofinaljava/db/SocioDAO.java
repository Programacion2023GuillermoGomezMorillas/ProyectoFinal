package org.example.proyectofinaljava.db;

import org.example.proyectofinaljava.model.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase SocioDAO para realizar operaciones CRUD en la base de datos para la entidad Socios.
 */
public class SocioDAO {

    // Instancia única de SocioDAO (patrón singleton)
    public static SocioDAO instance;

    // Constructor privado para evitar instancias directas
    private SocioDAO() {}

    // Método estático para obtener la instancia única de SocioDAO
    /**
     * Obtiene la instancia única de SocioDAO utilizando el patrón Singleton.
     * @return instancia única de SocioDAO
     */
    public static SocioDAO getConnection() {
        if (instance == null) {
            // Bloqueo sincronizado para evitar concurrencia en la creación de la instancia
            synchronized (SocioDAO.class) {
                if (instance == null) {
                    instance = new SocioDAO();
                }
            }
        }
        return instance;
    }

    // Conexión a la base de datos utilizando el patrón singleton de DBConnection
    private Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Socio
    private static final String INSERT_QUERY = "INSERT INTO Socio (numeroSocio, nombreSocio, direccionSocio, telefonoSocio, emailSocio) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Socio";
    private static final String SELECT_BY_NUMEROSOCIO_QUERY = "SELECT * FROM Socio WHERE numeroSocio RLIKE ?";
    private static final String SELECT_BY_NOMBRE_QUERY = "SELECT * FROM Socio WHERE nombreSocio RLIKE ?";
    private static final String UPDATE_QUERY = "UPDATE Socio SET nombreSocio = ?, direccionSocio = ?, telefonoSocio = ?, emailSocio = ? WHERE numeroSocio = ?";
    private static final String DELETE_QUERY = "DELETE FROM Socio WHERE numeroSocio = ?";
    private static final String SELECT_MAX_NUMSOCIO = "SELECT max(numeroSocio) from Socio";

    // Método para insertar un socio en la base de datos
    /**
     * Inserta un nuevo socio en la base de datos.
     * @param socio objeto Socio que se va a insertar
     * @throws SQLException si ocurre algún error de SQL durante la ejecución
     */
    public void insertSocio(Socio socio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setLong(1, socio.getNumeroSocio());
            statement.setString(2, socio.getNombreSocio());
            statement.setString(3, socio.getDireccionSocio());
            statement.setString(4, socio.getTelefonoSocio());
            statement.setString(5, socio.getEmailSocio());

            statement.executeUpdate();
        }
    }

    // Método para obtener todos los socios de la base de datos
    /**
     * Obtiene todos los socios almacenados en la base de datos.
     * @return lista de objetos Socio que representa todos los socios en la base de datos
     * @throws SQLException si ocurre algún error de SQL durante la ejecución
     */
    public List<Socio> getAllSocios() throws SQLException {
        List<Socio> socios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Socio socio = resultSetToSocio(resultSet);
                socios.add(socio);
            }
        }
        return socios;
    }

    // Método para obtener un socio por su número de socio
    /**
     * Obtiene un socio de la base de datos según su número de socio.
     * @param numeroSocio número de socio del socio que se desea obtener
     * @return objeto Socio que corresponde al número de socio especificado, o null si no se encuentra
     * @throws SQLException si ocurre algún error de SQL durante la ejecución
     */
    public List<Socio> getSocioByNumeroSocio(String numeroSocio) throws SQLException {
        List<Socio> socios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NUMEROSOCIO_QUERY)) {
            statement.setString(1, numeroSocio);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Socio socio = resultSetToSocio(resultSet);
                socios.add(socio);
            }
        }
        return socios;
    }

    // Método para obtener un socio por su nombre
    /**
     * Obtiene un socio de la base de datos según su nombre.
     * @param nombre nombre del socio que se desea obtener o buscar parcialmente
     * @return objeto Socio que corresponde al nombre especificado, o null si no se encuentra
     * @throws SQLException si ocurre algún error de SQL durante la ejecución
     */
    public List<Socio> getSocioByNombre(String nombre) throws SQLException {
        List<Socio> socios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NOMBRE_QUERY)) {
            statement.setString(1, nombre);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Socio socio = resultSetToSocio(resultSet);
                socios.add(socio);
            }
        }
        return socios;
    }

    /**
     * Obtiene el número máximo de reserva de la base de datos.
     *
     * @return el número máximo de reserva.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    public long getNumeroReserva() throws SQLException {
        long numRef = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_MAX_NUMSOCIO)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numRef = resultSet.getLong(1);
            }
        }
        return numRef;
    }

    // Método para actualizar los datos de un socio en la base de datos
    /**
     * Actualiza los datos de un socio en la base de datos.
     * @param socio objeto Socio con los datos actualizados que se desea guardar
     * @throws SQLException si ocurre algún error de SQL durante la ejecución
     */
    public void updateSocio(Socio socio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, socio.getNombreSocio());
            statement.setString(2, socio.getDireccionSocio());
            statement.setString(3, socio.getTelefonoSocio());
            statement.setString(4, socio.getEmailSocio());
            statement.setLong(5, socio.getNumeroSocio());

            statement.executeUpdate();
        }
    }

    // Método para eliminar un socio de la base de datos por su número de socio
    /**
     * Elimina un socio de la base de datos según su número de socio.
     * @param numeroSocio número de socio del socio que se desea eliminar
     * @throws SQLException si ocurre algún error de SQL durante la ejecución
     */
    public void deleteSocioByNumeroSocio(String numeroSocio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, numeroSocio);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Socio
    /**
     * Convierte un ResultSet en un objeto Socio.
     * @param resultSet ResultSet que contiene los datos del socio en la posición actual
     * @return objeto Socio creado a partir de los datos en el ResultSet
     * @throws SQLException si ocurre algún error de SQL durante la ejecución
     */
    private Socio resultSetToSocio(ResultSet resultSet) throws SQLException {
        Socio socio = new Socio(
                resultSet.getLong("numeroSocio"),
                resultSet.getString("nombreSocio"),
                resultSet.getString("direccionSocio"),
                resultSet.getString("telefonoSocio"),
                resultSet.getString("emailSocio"));
        return socio;
    }


}
