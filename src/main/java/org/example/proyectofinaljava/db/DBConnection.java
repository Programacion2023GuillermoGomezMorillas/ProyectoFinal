package org.example.proyectofinaljava.db;

import org.example.proyectofinaljava.model.Persona;

import java.sql.*;

/**
 * Clase para la conexion de la base de datos al proyecto
 */
public class DBConnection {

    // URL de conexión a la base de datos MySQL
    private static final String URL = "jdbc:mysql://127.0.1.1:3306/biblioteca";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "uP53y4yD";

    private static Connection connection;

    // Constructor privado para evitar instancias directas
    private DBConnection() {
    }

    // Método estático para obtener la instancia única de la conexión
    public static Connection getConnection() {
        if (connection == null) {
            // Bloqueo sincronizado para evitar concurrencia
            synchronized (DBConnection.class) {
                if (connection == null) {
                    try {
                        // Establecer la conexión
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    // Método para cerrar la conexión
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    }
