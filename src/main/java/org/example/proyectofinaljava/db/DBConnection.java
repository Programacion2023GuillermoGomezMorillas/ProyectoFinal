package org.example.proyectofinaljava.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // URL de conexión a la base de datos MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/mi_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root123456";

    private static Connection connection;

    // Constructor privado para evitar instancias directas
    private DBConnection() {}

    // Método estático para obtener la instancia única de la conexión
    public static Connection getConnection() {
        if (connection == null) {
            // Bloqueo sincronizado para evitar concurrencia
            synchronized (DBConnection.class) {
                if (connection == null) {
                    try {
                        // Establecer la conexión
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                    } catch ( SQLException e) {
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