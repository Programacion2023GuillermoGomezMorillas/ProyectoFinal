package org.example.proyectofinaljava.model;

import java.sql.Date;

public class Prestamo {
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private String isbnLibro;
    private int numeroSocio;

    public Prestamo(Date fechaInicio, Date fechaFin, String estado, String isbnLibro, int numeroSocio) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.isbnLibro = isbnLibro;
        this.numeroSocio = numeroSocio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIsbnLibro() {
        return isbnLibro;
    }

    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    public int getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    @Override
    public String toString() {
        return "\nPrestamo{" +
                "fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                ", isbnLibro='" + isbnLibro + '\'' +
                ", numeroSocio=" + numeroSocio +
                '}';
    }
}
