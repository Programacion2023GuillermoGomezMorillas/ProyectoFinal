package org.example.proyectofinaljava.db;

import org.example.proyectofinaljava.model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase LibroDAO para realizar operaciones CRUD en la base de datos para la entidad Libros.
 */
public class LibroDAO {
    // Objeto de conexión a la base de datos utilizando el patrón singleton de DBConnection
    private Connection connection = DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Libro
    private static final String INSERT_QUERY = "INSERT INTO Libro (ISBN, titulo, autor, anio, genero, estado) VALUES (?, ?, ?, ?, ?, ))";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Libro";
    private static final String SELECT_ALL_QUERY_DISPONIBLE = "SELECT * FROM Libro where estado = 'Disponible'";
    private static final String SELECT_BY_ISBN_QUERY = "SELECT * FROM Libro WHERE ISBN RLIKE ?";
    private static final String SELECT_BY_GENERO_QUERY = "SELECT * FROM Libro WHERE genero RLIKE ?";
    private static final String SELECT_BY_TITULO_QUERY = "SELECT * FROM Libro WHERE titulo RLIKE ?";
    private static final String SELECT_BY_ESTADO_QUERY = "SELECT * FROM Libro WHERE estado = 'Disponible'";
    private static final String UPDATE_QUERY = "UPDATE Libro SET titulo = ?, autor = ?, anio = ?, genero = ? WHERE isbn = ?";
    private static final String DELETE_QUERY = "DELETE FROM Libro WHERE ISBN = ?";
    private static final String UPDATE_PRESTADO = "update Libro set estado = 'Prestado' where titulo in (select tituloLibro from Prestamo);";
    private static final String UPDATE_DISPONIBLE = "update Libro set estado = 'Disponible' where titulo not in (select tituloLibro from Prestamo) or 'Devuelto' in (select estado from Prestamo);";
    private static final String SQL_SAFE_UPDATES = "SET SQL_SAFE_UPDATES = 0;";


    // Clase singleton
    public static LibroDAO instance;

    // Constructor privado para evitar instancias directas
    private LibroDAO() {
    }

    // Método estático para obtener la instancia única de la conexión
    public static LibroDAO getConnection() {
        if (instance == null) {
            // Bloqueo sincronizado para evitar concurrencia
            synchronized (LibroDAO.class) {
                if (instance == null) {
                    instance = new LibroDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Método para insertar un nuevo libro en la base de datos
     */
    public void insertLibro(Libro libro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, libro.getIsbn());
            statement.setString(2, libro.getTitulo());
            statement.setString(3, libro.getAutor());
            statement.setString(4, libro.getAnio());
            statement.setString(5, libro.getGenero());
            statement.setString(6, "No Prestado");

            statement.executeUpdate();
        }
    }

    /**
     * Método para obtener todos los libros de la base de datos
     */
    public List<Libro> getAllLibros() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Libro libro = resulSetToLibro(resultSet);
                libros.add(libro);
            }
        }
        return libros;
    }

    /**
     * Método para obtener todos los libros de la base de datos Disponibles para reservar
     */
    public List<Libro> getAllLibrosDisponibles() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY_DISPONIBLE)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Libro libro = resulSetToLibro(resultSet);
                libros.add(libro);
            }
        }
        return libros;
    }

    /**
     * Método para obtener un libro por su ISBN
     */
    public List<Libro> getLibroByIsbn(String isbn) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN_QUERY)) {
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Libro libro = resulSetToLibro(resultSet);
                libros.add(libro);
            }
        }
        return libros;
    }

    /**
     * Método para obtener un libro por su Estado
     */
    public List<Libro> getLibroByEstado(String isbn) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ESTADO_QUERY)) {
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Libro libro = resulSetToLibro(resultSet);
                libros.add(libro);
            }
        }
        return libros;
    }

    /**
     * Método para obtener un libro por su Genero
     */
    public List<Libro> getLibroByGenero(String genero) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_GENERO_QUERY)) {
            statement.setString(1, genero);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Libro libro = resulSetToLibro(resultSet);
                libros.add(libro);
            }
        }
        return libros;
    }

    /**
     * Método para obtener un libro por su Nombre
     */
    public List<Libro> getLibroByTitulo(String titulo) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_TITULO_QUERY)) {
            statement.setString(1, titulo);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Libro libro = resulSetToLibro(resultSet);
                libros.add(libro);
            }
        }
        return libros;
    }

    /**
     * Método para actualizar los datos de un libro en la base de datos
     */
    public void updateLibro(Libro libro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, libro.getTitulo());
            statement.setString(2, libro.getAutor());
            statement.setString(3, libro.getAnio());
            statement.setString(4, libro.getGenero());
            statement.setString(5, libro.getIsbn());

            statement.executeUpdate();
        }
    }

    public void updateLibroEstado() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PRESTADO)) {
            statement.executeUpdate();
        }
    }

    public void updateLibroEstadoDisponible() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_SAFE_UPDATES)) {
            statement.executeUpdate();
        }
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_DISPONIBLE)) {
            statement.executeUpdate();
        }
    }

    /**
     * Método para eliminar un libro de la base de datos por su ISBN
     */
    public void deleteLibroByIsbn(String isbn) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, isbn);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la posición actual a un objeto Libro
    private Libro resulSetToLibro(ResultSet resultSet) throws SQLException {
        Libro libro = new Libro(
                resultSet.getString("ISBN"),
                resultSet.getString("titulo"),
                resultSet.getString("autor"),
                resultSet.getString("anio"),
                resultSet.getString("genero"),
                resultSet.getString("estado"));
        return libro;
    }

    public static void main(String[] args) throws SQLException {
        LibroDAO libro = new LibroDAO();
        // Ejemplo de uso para probar los métodos implementados
        System.out.println(libro.getLibroByIsbn("978006"));
    }
}
