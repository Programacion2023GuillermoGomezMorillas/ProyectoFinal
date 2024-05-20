package org.example.proyectofinaljava.db;

import org.example.proyectofinaljava.model.Persona;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
/*
    // Objeto de conexión a la base de datos. Recuerda el patrón singleton de DBConnection
    private Connection connection=DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Persona
    private static final String INSERT_QUERY = "INSERT INTO Persona (dni, nombre, apellido, edad) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Libro";
    private static final String SELECT_BY_DNI_QUERY = "SELECT * FROM Persona WHERE dni = ?";
    private static final String UPDATE_QUERY = "UPDATE Persona SET nombre = ?, apellido = ?, edad = ? WHERE dni = ?";
    private static final String DELETE_QUERY = "DELETE FROM Persona WHERE dni = ?";

    // Método para insertar una nueva persona en la base de datos
    public void insertPersona(Persona persona) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, persona.getDni());
            statement.setString(2, persona.getNombre());
            statement.setString(3, persona.getApellido());
            statement.setInt(4, persona.getEdad());

            statement.executeUpdate();
        }
    }

    // Método para obtener todas las personas de la base de datos
    public List<Persona> getAllPersonas() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Persona persona = resulSetToPersona(resultSet);
                personas.add(persona);
            }
        }
        return personas;
    }

    // Método para obtener una persona por su DNI
    public Persona getPersonaByDni(String dni) throws SQLException {
        Persona persona = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_DNI_QUERY)) {
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                persona = resulSetToPersona(resultSet);
            }
        }
        return persona;
    }

    // Método para actualizar los datos de una persona en la base de datos
    public void updatePersona(Persona persona) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, persona.getNombre());
            statement.setString(2, persona.getApellido());
            statement.setInt(3, persona.getEdad());
            statement.setString(4, persona.getDni());

            statement.executeUpdate();
        }
    }

    // Método para eliminar una persona de la base de datos por su DNI
    public void deletePersonaByDni(String dni) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, dni);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la
    //posición actual a un objeto Persona
    /*private Persona resulSetToPersona(ResultSet resultSet) throws SQLException {
        Persona persona = new Persona(
                resultSet.getString("dni"),
                resultSet.getString("nombre"),
                resultSet.getString("apellido"),
                resultSet.getInt("edad"));
        return persona;
    }
    */

}