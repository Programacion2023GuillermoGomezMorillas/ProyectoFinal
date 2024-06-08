package org.example.proyectofinaljava.db;

import org.example.proyectofinaljava.model.Libro;
import org.example.proyectofinaljava.model.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrestamoDAO {
    private Connection connection=DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Prestamo
    private static final String INSERT_QUERY = "INSERT INTO Prestamo (numReserva, fechaInicio, fechaFin, estado, isbnLibro, numeroSocio) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Prestamo";
    private static final String SELECT_BY_FECHAINICIO_QUERY = "SELECT * FROM Prestamo WHERE fechaInicio > ? ";
    private static final String SELECT_BY_ESTADO_QUERY = "SELECT * FROM Prestamo WHERE estado = ?";
    private static final String UPDATE_QUERY = "UPDATE Prestamo SET fechaInicio = ?, fechaFin = ?, estado = ?, isbnLibro = ?, numeroSocio = ?  WHERE numReferencia = ?";
    private static final String DELETE_QUERY = "DELETE FROM Prestamo WHERE numReferencia = ?";

    // Clase singleton
    public static PrestamoDAO instance;
    // Constructor privado para evitar instancias directas
    private PrestamoDAO() {}
    // Método estático para obtener la instancia única de la conexión
    public static PrestamoDAO getConnection() {
        if (instance == null) {
            // Bloqueo sincronizado para evitar concurrencia
            synchronized (PrestamoDAO.class) {
                if (instance == null) {
                    // Generamos la clase
                    instance = new PrestamoDAO();
                }
            }
        }
        return instance;
    }

    // Método para insertar un nuevo prestamo en la base de datos
    public void insertPrestamo(Prestamo prestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, prestamo.getNumReserva());
            statement.setDate(2, prestamo.getFechaInicio());
            statement.setDate(3, prestamo.getFechaFin());
            statement.setString(4, prestamo.getEstado());
            statement.setString(5, prestamo.getIsbnLibro());
            statement.setLong(5, prestamo.getNumeroSocio());

            statement.executeUpdate();
        }
    }

    // Método para obtener todos los prestamos de la base de datos
    public List<Prestamo> getAllPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Prestamo prestamo = resulSetToPrestamo(resultSet);
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    // Método para obtener un prestamo por su Estado
    public List<Prestamo> getLibroByEstado(String estado) throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ESTADO_QUERY)) {
            statement.setString(1, estado);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Prestamo prestamo = resulSetToPrestamo(resultSet);
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    // Método para actualizar los datos de un prestamo en la base de datos
    public void updatePrestamo(Prestamo prestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setDate(1, prestamo.getFechaInicio());
            statement.setDate(2, prestamo.getFechaFin());
            statement.setString(3, prestamo.getEstado());
            statement.setString(4, prestamo.getIsbnLibro());
            statement.setLong(5, prestamo.getNumeroSocio());
            statement.setString(6, prestamo.getNumReserva());

            statement.executeUpdate();
        }
    }
    // Método para eliminar un prestamo de la base de datos por su DNI
    public void deletePrestamoByReferencia(String numReferencia) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, numReferencia);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Prestamo
    private Prestamo resulSetToPrestamo(ResultSet resultSet) throws SQLException {
        Prestamo prestamo = new Prestamo(
                resultSet.getString("numReserva"),
                resultSet.getDate("fechaInicio"),
                resultSet.getDate("fechaFin"),
                resultSet.getString("estado"),
                resultSet.getString("isbnLibro"),
                resultSet.getLong("numeroSocio"));
        return prestamo;
    }

    public static void main(String[] args) throws SQLException {
        PrestamoDAO prestamo = new PrestamoDAO();
        //System.out.println(libro.getAllLibros());

        System.out.println("---------------------------------------------------------------------------------------------------------------");
        System.out.println(prestamo.getLibroByEstado("Devuelto"));
        //Libro libroNuevo = new Libro("8945156456456", "LibroNuevo", "Jose Luis", "2005", "Fantasia");
        //libro.insertLibro(libroNuevo);
        //System.out.println(prestamo.getPrestamoByFechaInicio("Misterio"));
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        //System.out.println(libro.getAllLibros());
        //System.out.println("---------------------------------------------------------------------------------------------------------------");

    }
}
