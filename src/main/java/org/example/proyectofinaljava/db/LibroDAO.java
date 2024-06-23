package org.example.proyectofinaljava.db;

import org.example.proyectofinaljava.model.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    // Objeto de conexión a la base de datos. Recuerda el patrón singleton de DBConnection
    private Connection connection=DBConnection.getConnection();

    // Consultas SQL para manipular la tabla Libro
    private static final String INSERT_QUERY = "INSERT INTO Libro (ISBN, titulo, autor, anio, genero) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Libro";
    private static final String SELECT_BY_ISBN_QUERY = "SELECT * FROM Libro WHERE ISBN RLIKE ?";
    private static final String SELECT_BY_GENERO_QUERY = "SELECT * FROM Libro WHERE genero RLIKE ?";
    private static final String SELECT_BY_TITULO_QUERY = "SELECT * FROM Libro WHERE titulo RLIKE ?";
    private static final String UPDATE_QUERY = "UPDATE Libro SET titulo = ?, autor = ?, anio = ?, genero = ? WHERE isbn = ?";
    private static final String DELETE_QUERY = "DELETE FROM Libro WHERE ISBN = ?";

    // Clase singleton
    public static LibroDAO instance;
    // Constructor privado para evitar instancias directas
    private LibroDAO() {}
    // Método estático para obtener la instancia única de la conexión
    public static LibroDAO getConnection() {
        if (instance == null) {
            // Bloqueo sincronizado para evitar concurrencia
            synchronized (LibroDAO.class) {
                if (instance == null) {
                    // Generamos la clase
                    instance = new LibroDAO();
                }
            }
        }
        return instance;
    }

    // Método para insertar un nuevo libro en la base de datos
    public void insertLibro(Libro libro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, libro.getIsbn());
            statement.setString(2, libro.getTitulo());
            statement.setString(3, libro.getAutor());
            statement.setString(4, libro.getAnio());
            statement.setString(5, libro.getGenero());

            statement.executeUpdate();
        }
    }

    // Método para obtener todos los libros de la base de datos
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
    // Método para obtener un libro por su ISBN
    public Libro getLibroByIsbn(String isbn) throws SQLException {
        Libro libro = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_ISBN_QUERY)) {
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                libro = resulSetToLibro(resultSet);
            }
        }
        return libro;
    }
    // Método para obtener un libro por su Genero
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
    // Método para obtener un libro por su Nombre
    public Libro getLibroByTitulo(String titulo) throws SQLException {
        Libro libro = null;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_BY_TITULO_QUERY)) {
            statement.setString(1, titulo);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                libro = resulSetToLibro(resultSet);
            }
        }
        return libro;
    }

    // Método para actualizar los datos de un libro en la base de datos
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
    // Método para eliminar un libro de la base de datos por su DNI
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
                resultSet.getString("genero"));
        return libro;
    }

    public static void main(String[] args) throws SQLException {
        LibroDAO libro = new LibroDAO();
       //System.out.println(libro.getAllLibros());

        System.out.println("---------------------------------------------------------------------------------------------------------------");
        //Libro libroNuevo = new Libro("8945156456456", "LibroNuevo", "Jose Luis", "2005", "Fantasia");
        //libro.insertLibro(libroNuevo);
        System.out.println(libro.getLibroByGenero("Misterio"));
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        //System.out.println(libro.getAllLibros());
        //System.out.println("---------------------------------------------------------------------------------------------------------------");

    }






}