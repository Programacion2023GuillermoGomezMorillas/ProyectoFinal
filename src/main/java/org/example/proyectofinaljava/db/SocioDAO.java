package org.example.proyectofinaljava.db;

import org.example.proyectofinaljava.model.Libro;
import org.example.proyectofinaljava.model.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SocioDAO {

    public static SocioDAO instance;

    // Constructor privado para evitar instancias directas
    private SocioDAO() {}

    // Método estático para obtener la instancia única de la conexión
    public static SocioDAO getConnection() {
        if (instance == null) {
            // Bloqueo sincronizado para evitar concurrencia
            synchronized (GeneroDAO.class) {
                if (instance == null) {
                    // Generamos la clase
                    instance = new SocioDAO();
                }
            }
        }
        return instance;
    }
    private Connection connection=DBConnection.getConnection();
    private static final String INSERT_QUERY = "INSERT INTO Socio (numeroSocio, nombreSocio, direccionSocio, telefonoSocio, emailSocio) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Socio";
    private static final String SELECT_BY_NUMEROSOCIO_QUERY = "SELECT * FROM Socio WHERE numeroSocio = ?";
    private static final String SELECT_BY_NOMBRE_QUERY = "SELECT * FROM Socio WHERE nombreSocio RLIKE ?";
    private static final String UPDATE_QUERY = "UPDATE Socio SET nombreSocio = ?, direccionSocio = ?, telefonoSocio = ?, emailSocio = ? WHERE numeroSocio = ?";
    private static final String DELETE_QUERY = "DELETE FROM Socio WHERE numeroSocio = ?";

    // Método para insertar un socio en base de datos
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
    public List<Socio> getAllSocios() throws SQLException {
        List<Socio> socios = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Socio socio = resulSetToSocio(resultSet);
                socios.add(socio);
            }
        }
        return socios;
    }

    // Método para obtener un socio por su número de socio
    public Socio getSocioByNumeroSocio(String numeroSocio) throws SQLException {
        Socio socio = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NUMEROSOCIO_QUERY)) {
            statement.setString(1, numeroSocio);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                socio = resulSetToSocio(resultSet);
            }
        }
        return socio;
    }

    // Método para obtener un socio por su nombre
    public Socio getSocioByNombre(String nombre) throws SQLException {
        Socio socio = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_NOMBRE_QUERY)) {
            statement.setString(1, nombre);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                socio = resulSetToSocio(resultSet);
            }
        }
        return socio;
    }

    // Método para actualizar los datos de un socio en la base de datos
    public void updateLibro(Socio socio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, socio.getNombreSocio());
            statement.setString(2, socio.getDireccionSocio());
            statement.setString(3, socio.getTelefonoSocio());
            statement.setString(4, socio.getEmailSocio());
            statement.setLong(5, socio.getNumeroSocio());

            statement.executeUpdate();
        }
    }
    // Método para eliminar un socio de la base de datos por su numero de socio
    public void deleteLibroByNumeroSocio(String numeroSocio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, numeroSocio);
            statement.executeUpdate();
        }
    }
    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Socio
    private Socio resulSetToSocio(ResultSet resultSet) throws SQLException {
        Socio socio = new Socio(
                resultSet.getLong("numeroSocio"),
                resultSet.getString("nombreSocio"),
                resultSet.getString("direccionSocio"),
                resultSet.getString("telefonoSocio"),
                resultSet.getString("emailSocio"));
        return socio;
    }

    public static void main(String[] args) throws SQLException {
        SocioDAO sociodao= new SocioDAO();
        System.out.println(sociodao.getAllSocios());
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
        //Socio socio = new Socio(6,"Guillermo", "Calle ocho, Madrid", "123123123","guillermo@gmail.com");
        //sociodao.insertSocio(socio);
        //System.out.println(sociodao.getAllSocios());
        //System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
