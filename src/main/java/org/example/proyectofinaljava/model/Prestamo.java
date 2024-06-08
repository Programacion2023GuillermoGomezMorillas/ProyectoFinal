package org.example.proyectofinaljava.model;

import java.sql.Date;

public class Prestamo {
    private String numReserva;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private String isbnLibro;
    private long numeroSocio;

    public Prestamo(String numReserva, Date fechaInicio, Date fechaFin, String estado, String isbnLibro, long numeroSocio) {
        this.numReserva=numReserva;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.isbnLibro = isbnLibro;
        this.numeroSocio = numeroSocio;
    }

    public String getNumReserva() {
        return numReserva;
    }

    public void setNumReserva(String numReserva) {
        this.numReserva = numReserva;
    }

    public void setNumeroSocio(long numeroSocio) {
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

    public long getNumeroSocio() {
        return numeroSocio;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "numReserva='" + numReserva + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                ", isbnLibro='" + isbnLibro + '\'' +
                ", numeroSocio=" + numeroSocio +
                '}';
    }
}
