package org.example.proyectofinaljava.db;

import org.example.proyectofinaljava.model.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneroDAO {


    private Connection connection = DBConnection.getConnection();

    private static final String SELECT_ALL_QUERY = "SELECT * FROM Genero";


    // Método para obtener todos los generos de la base de datos
    public List<String> getAllGeneros() throws SQLException {
        List<String> generos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                generos.add(resultSet.getString("nombre"));
            }
        }
        return generos;
    }
/*
    public static void main(String[] args) throws SQLException {
        GeneroDAO a = new GeneroDAO();
        System.out.println(a.getAllGeneros());
    }

 */
}
