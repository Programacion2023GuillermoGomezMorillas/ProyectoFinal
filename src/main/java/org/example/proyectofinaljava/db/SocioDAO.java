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

    private Connection connection=DBConnection.getConnection();
    private static final String INSERT_QUERY = "INSERT INTO Socio (numeroSocio, nombreSocio, direccionSocio, telefonoSocio, emailSocio) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Socio";
    private static final String SELECT_BY_NUMEROSOCIO_QUERY = "SELECT * FROM Socio WHERE numeroSocio = ?";
    private static final String SELECT_BY_NOMBRE_QUERY = "SELECT * FROM Socio WHERE nombreSocio = ?";
    private static final String UPDATE_QUERY = "UPDATE Socio SET nombreSocio = ?, direccionSocio = ?, telefonoSocio = ?, emailSocio = ? WHERE numeroSocio = ?";
    private static final String DELETE_QUERY = "DELETE FROM Socio WHERE numeroSocio = ?";

    // Método para insertar un socio en base de datos
    public void insertLibro(Socio socio) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setInt(1, socio.getNumeroSocio());
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
            statement.setInt(5, socio.getNumeroSocio());

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
                resultSet.getInt("numeroSocio"),
                resultSet.getString("nombreSocio"),
                resultSet.getString("direccionSocio"),
                resultSet.getString("telefonoSocio"),
                resultSet.getString("emailSocio"));
        return socio;
    }

    public static void main(String[] args) throws SQLException {
        SocioDAO socio= new SocioDAO();
        System.out.println(socio.getAllSocios());
    }
}
