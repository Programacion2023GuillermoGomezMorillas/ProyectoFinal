package org.example.proyectofinaljava.model;

public class Libro {
    private String ISBN;
    private String titulo;
    private int anioPublicacion;
    private int fotoLibro;
    private String genero;
    private int idAutor;


    public Libro(String ISBN, String titulo, int anioPublicacion, int fotoLibro, String genero, int idAutor) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        this.fotoLibro = fotoLibro;
        this.genero = genero;
        this.idAutor = idAutor;
    }

    //**********************Getters y Setter**********************
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public int getFotoLibro() {
        return fotoLibro;
    }

    public void setFotoLibro(int fotoLibro) {
        this.fotoLibro = fotoLibro;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }
}
