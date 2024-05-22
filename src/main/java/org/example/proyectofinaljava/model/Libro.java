package org.example.proyectofinaljava.model;

import org.example.proyectofinaljava.db.LibroDAO;

import java.sql.SQLException;
import java.util.List;

import static org.example.proyectofinaljava.db.DBConnection.getConnection;

public class Libro {
    private String ISBN;
    private String titulo;
    private int anioPublicacion;
    private String genero;
    private int idAutor;


    public Libro(String ISBN, String titulo, int anioPublicacion, String genero, int idAutor) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
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

    @Override
    public String toString() {
        return "Libro{" +
                "ISBN='" + ISBN + '\'' +
                ", titulo='" + titulo + '\'' +
                ", anioPublicacion=" + anioPublicacion +
                ", genero='" + genero + '\'' +
                ", idAutor=" + idAutor +
                '}';
    }


}
