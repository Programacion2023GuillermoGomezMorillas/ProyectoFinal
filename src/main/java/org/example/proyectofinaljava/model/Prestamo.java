package org.example.proyectofinaljava.model;

import java.sql.Date;

public class Prestamo {
    private long numReserva;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private String titulo;
    private String nombreSocio;

    public Prestamo(long numReserva, Date fechaInicio, Date fechaFin, String estado, String titulo, String nombreSocio) {
        this.numReserva=numReserva;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.titulo = titulo;
        this.nombreSocio = nombreSocio;
    }

    public long getNumReserva() {
        return numReserva;
    }

    public void setNumReserva(long numReserva) {
        this.numReserva = numReserva;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "numReserva='" + numReserva + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                ", titulo='" + titulo + '\'' +
                ", nombreSocio=" + nombreSocio +
                '}';
    }
}
