package org.example.proyectofinaljava.model;

import java.util.Objects;

/**
 * Clase que representa un libro en el sistema.
 */
public class Libro {
    private String isbn;
    private String titulo;
    private String anio;
    private String genero;
    private String autor;
    private String estado;

    /**
     * Constructor de la clase Libro.
     *
     * @param isbn   ISBN del libro.
     * @param titulo Título del libro.
     * @param autor  Autor del libro.
     * @param anio   Año de publicación del libro.
     * @param genero Género del libro.
     * @param estado Estado del libro (Disponible, Prestado, etc.).
     */
    public Libro(String isbn, String titulo, String autor, String anio, String genero, String estado) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.genero = genero;
        this.estado = estado;
    }

    //**********************Getters y Setters**********************

    /**
     * Obtiene el ISBN del libro.
     *
     * @return ISBN del libro.
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del libro.
     *
     * @param isbn ISBN del libro.
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return Título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     *
     * @param titulo Título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el año de publicación del libro.
     *
     * @return Año de publicación del libro.
     */
    public String getAnio() {
        return anio;
    }

    /**
     * Establece el año de publicación del libro.
     *
     * @param anio Año de publicación del libro.
     */
    public void setAnio(String anio) {
        this.anio = anio;
    }

    /**
     * Obtiene el género del libro.
     *
     * @return Género del libro.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Establece el género del libro.
     *
     * @param genero Género del libro.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * Obtiene el autor del libro.
     *
     * @return Autor del libro.
     */
    public String getAutor() {
        return autor;
    }

    /**
     * Establece el autor del libro.
     *
     * @param autor Autor del libro.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Obtiene el estado actual del libro.
     *
     * @return Estado del libro.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del libro.
     *
     * @param estado Estado del libro.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Sobrescritura del método toString para obtener una representación textual del libro.
     *
     * @return Cadena con la representación textual del libro.
     */
    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", anio='" + anio + '\'' +
                ", genero='" + genero + '\'' +
                ", autor='" + autor + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    /**
     * Sobrescritura del método equals para comparar dos objetos Libro basados en el ISBN.
     *
     * @param o Objeto a comparar con el libro actual.
     * @return true si los libros son iguales (mismo ISBN), false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(isbn, libro.isbn);
    }
}
