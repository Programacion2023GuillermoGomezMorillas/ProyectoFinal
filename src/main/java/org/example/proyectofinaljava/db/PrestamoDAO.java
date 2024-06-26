package org.example.proyectofinaljava.db;

import org.example.proyectofinaljava.model.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase PrestamoDAO para realizar operaciones CRUD en la base de datos para la entidad Prestamo.
 */
public class PrestamoDAO {
    private Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Prestamo
    private static final String INSERT_QUERY = "INSERT INTO Prestamo (numReserva, fechaInicio, fechaFin, estado, tituloLibro, socio) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Prestamo";
    private static final String SELECT_BY_FECHAINICIO_QUERY = "SELECT * FROM Prestamo WHERE fechaInicio > ?";
    private static final String SELECT_BY_ESTADO_QUERY = "SELECT * FROM Prestamo WHERE estado = ?";
    private static final String UPDATE_QUERY = "UPDATE Prestamo SET fechaInicio = ?, fechaFin = ?, estado = ?, tituloLibro = ?, socio = ? WHERE numReserva = ?";
    private static final String DELETE_QUERY = "DELETE FROM Prestamo WHERE numReserva = ?";
    private static final String SELECT_MAX_NUMREF = "SELECT max(numReserva) from Prestamo";
    private static final String UPDATE_FECHAFIN = "UPDATE Prestamo SET estado = 'Vencido' WHERE fechaFin < curdate()";

    // Clase singleton
    public static PrestamoDAO instance;

    // Constructor privado para evitar instancias directas
    private PrestamoDAO() {
    }

    /**
     * Obtiene la instancia única de PrestamoDAO.
     *
     * @return la instancia única de PrestamoDAO.
     */
    public static PrestamoDAO getConnection() {
        if (instance == null) {
            synchronized (PrestamoDAO.class) {
                if (instance == null) {
                    instance = new PrestamoDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Inserta un nuevo prestamo en la base de datos.
     *
     * @param prestamo el objeto Prestamo a insertar.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    public void insertPrestamo(Prestamo prestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setLong(1, prestamo.getNumReserva());
            statement.setDate(2, prestamo.getFechaInicio());
            statement.setDate(3, prestamo.getFechaFin());
            statement.setString(4, prestamo.getEstado());
            statement.setString(5, prestamo.getTitulo());
            statement.setString(6, prestamo.getNombreSocio());

            statement.executeUpdate();
        }
    }

    /**
     * Obtiene todos los prestamos de la base de datos.
     *
     * @return una lista de todos los prestamos.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    public List<Prestamo> getAllPrestamos() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Prestamo prestamo = resulSetToPrestamo(resultSet);
                comprobarFechaFin(prestamo);
                prestamos.add(prestamo);
            }
        }
        return prestamos;
    }

    /**
     * Obtiene el número máximo de reserva de la base de datos.
     *
     * @return el número máximo de reserva.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    public long getNumeroReserva() throws SQLException {
        long numRef = 0;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_MAX_NUMREF)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numRef = resultSet.getLong(1);
            }
        }
        return numRef;
    }

    /**
     * Obtiene una lista de prestamos por su estado.
     *
     * @param estado el estado de los prestamos a obtener.
     * @return una lista de prestamos con el estado especificado.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
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

    /**
     * Actualiza los datos de un prestamo en la base de datos.
     *
     * @param prestamo el objeto Prestamo con los datos actualizados.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    public void updatePrestamo(Prestamo prestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setDate(1, prestamo.getFechaInicio());
            statement.setDate(2, prestamo.getFechaFin());
            statement.setString(3, "Devuelto");
            statement.setString(4, prestamo.getTitulo());
            statement.setString(5, prestamo.getNombreSocio());
            statement.setLong(6, prestamo.getNumReserva());

            statement.executeUpdate();
        }
    }

    /**
     * Elimina un prestamo de la base de datos por su número de referencia.
     *
     * @param numReferencia el número de referencia del prestamo a eliminar.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    public void deletePrestamoByReferencia(String numReferencia) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, numReferencia);
            statement.executeUpdate();
        }
    }

    /**
     * Convierte un ResultSet en la posición actual a un objeto Prestamo.
     *
     * @param resultSet el ResultSet a convertir.
     * @return el objeto Prestamo correspondiente al ResultSet.
     * @throws SQLException si ocurre un error al acceder al ResultSet.
     */
    private Prestamo resulSetToPrestamo(ResultSet resultSet) throws SQLException {
        Prestamo prestamo = new Prestamo(
                resultSet.getLong("numReserva"),
                resultSet.getDate("fechaInicio"),
                resultSet.getDate("fechaFin"),
                resultSet.getString("estado"),
                resultSet.getString("tituloLibro"),
                resultSet.getString("socio"));

        return prestamo;
    }

    /**
     * Comprueba si la fecha de fin de un prestamo ha pasado y actualiza su estado a 'Vencido' si es necesario.
     *
     * @param prestamo el objeto Prestamo a comprobar.
     * @throws SQLException si ocurre un error al ejecutar la consulta.
     */
    private void comprobarFechaFin(Prestamo prestamo) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_FECHAFIN)) {
            statement.executeUpdate();
        }
    }
}
