package org.example.proyectofinaljava.model;

public class Libro {
    private String isbn;
    private String titulo;
    private String anio;
    private String genero;
    private String autor;


    public Libro(String isbn, String titulo, String autor, String anio, String genero) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.genero = genero;
    }

    //**********************Getters y Setter**********************
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }


    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return  "\nLibro{" +
                "ISBN='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", anio=" + anio +
                ", genero='" + genero + '\'' +
                '}';
    }


}
