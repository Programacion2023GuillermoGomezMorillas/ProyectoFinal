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
    private static final String INSERT_QUERY = "INSERT INTO Libro (ISBN, titulo, anioPublicacion, genero_nombre, autor_idAutor) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM Libro";
    private static final String SELECT_BY_ISBN_QUERY = "SELECT * FROM Libro WHERE ISBN = ?";
    private static final String UPDATE_QUERY = "UPDATE Libro SET ISBN = ?, titulo = ?, anioPublicacion = ? WHERE isbn = ?";
    private static final String DELETE_QUERY = "DELETE FROM Libro WHERE ISBN = ?";

    // Método para insertar una nueva persona en la base de datos
    public void insertLibro(Libro libro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, libro.getISBN());
            statement.setString(2, libro.getTitulo());
            statement.setInt(3, libro.getAnioPublicacion());
            statement.setString(4, libro.getGenero());
            statement.setInt(5, libro.getIdAutor());

            statement.executeUpdate();
        }
    }

    // Método para obtener todas las personas de la base de datos
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
    // Método para obtener un libro por su ISNB
    public Libro getLibroByISbn(String isbn) throws SQLException {
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

    // Método para actualizar los datos de un libro en la base de datos
    public void updateLibro(Libro libro) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, libro.getISBN());
            statement.setString(2, libro.getTitulo());
            statement.setInt(3, libro.getAnioPublicacion());
            statement.setString(4, libro.getGenero());
            statement.setString(4, libro.getGenero());

            statement.executeUpdate();
        }
    }
    // Método para eliminar una persona de la base de datos por su DNI
    public void deleteLibroByIsbn(String isbn) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, isbn);
            statement.executeUpdate();
        }
    }

    // Método auxiliar para mapear un ResultSet en la
    //posición actual a un objeto Libro
    private Libro resulSetToLibro(ResultSet resultSet) throws SQLException {
        Libro libro = new Libro(
                resultSet.getString("ISBN"),
                resultSet.getString("titulo"),
                resultSet.getInt("anioPublicacion"),
                resultSet.getString("genero_nombre"),
                resultSet.getInt("autor_idAutor"));
        return libro;
    }

    public static void main(String[] args) throws SQLException {
        LibroDAO libro = new LibroDAO();

        List<Libro> listaLibros = libro.getAllLibros();
        for (Libro i : listaLibros) {
            System.out.println(i.toString());
        }
        System.out.println(libro.getLibroByISbn("9780747532699"));
        Libro libroNuevo = new Libro("8945153256456", "LibroPrueba2", 2000, "Fantasia", 4);
        libro.insertLibro(libroNuevo);

        listaLibros = libro.getAllLibros();
        for (Libro i : listaLibros) {
            System.out.println(i.toString());
        }

    }

}