package org.example.proyectofinaljava.model;

import org.example.proyectofinaljava.db.LibroDAO;

import java.sql.SQLException;
import java.util.List;

import static org.example.proyectofinaljava.db.DBConnection.getConnection;

public class Libro {
    private String isbn;
    private String titulo;
    private int anioPublicacion;
    private int fotoLibro;
    private String genero;
    private String autor;


    public Libro(String isbn, String titulo, int anioPublicacion, String genero, String autor) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.anioPublicacion = anioPublicacion;
        //this.fotoLibro = fotoLibro;
        this.genero = genero;
        this.autor = autor;
    }

    //**********************Getters y Setter**********************
    public String getISBN() {
        return isbn;
    }

    public void setISBN(String ISBN) {
        this.isbn = ISBN;
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
                ", anioPublicacion=" + anioPublicacion +
                ", genero='" + genero + '\'' +
                ", idAutor=" + autor +
                '}';
    }


}
