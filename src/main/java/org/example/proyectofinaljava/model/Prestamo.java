package org.example.proyectofinaljava.model;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Clase Prestamo que representa un préstamo en la biblioteca.
 */
public class Prestamo {
    private long numReserva;
    private Date fechaInicio;
    private Date fechaFin;
    private String estado;
    private String titulo;
    private String nombreSocio;

    /**
     * Constructor de la clase Prestamo.
     *
     * @param numReserva  el número de reserva del préstamo.
     * @param fechaInicio la fecha de inicio del préstamo.
     * @param fechaFin    la fecha de fin del préstamo.
     * @param estado      el estado del préstamo.
     * @param titulo      el título del libro prestado.
     * @param nombreSocio el nombre del socio que realiza el préstamo.
     */
    public Prestamo(long numReserva, Date fechaInicio, Date fechaFin, String estado, String titulo, String nombreSocio) {
        this.numReserva = numReserva;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        setEstado(estado);
        this.titulo = titulo;
        this.nombreSocio = nombreSocio;
    }

    /**
     * Obtiene el número de reserva del préstamo.
     *
     * @return el número de reserva.
     */
    public long getNumReserva() {
        return numReserva;
    }

    /**
     * Establece el número de reserva del préstamo.
     *
     * @param numReserva el número de reserva.
     */
    public void setNumReserva(long numReserva) {
        this.numReserva = numReserva;
    }

    /**
     * Obtiene el nombre del socio que realiza el préstamo.
     *
     * @return el nombre del socio.
     */
    public String getNombreSocio() {
        return nombreSocio;
    }

    /**
     * Establece el nombre del socio que realiza el préstamo.
     *
     * @param nombreSocio el nombre del socio.
     */
    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    /**
     * Obtiene la fecha de inicio del préstamo.
     *
     * @return la fecha de inicio.
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha de inicio del préstamo.
     *
     * @param fechaInicio la fecha de inicio.
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de fin del préstamo.
     *
     * @return la fecha de fin.
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha de fin del préstamo.
     *
     * @param fechaFin la fecha de fin.
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Obtiene el estado del préstamo.
     *
     * @return el estado.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del préstamo. Si la fecha actual es posterior a la fecha de fin, el estado se
     * establece como "Vencido".
     *
     * @param estado el estado del préstamo.
     */
    public void setEstado(String estado) {
        if (LocalDate.now().isAfter(fechaFin.toLocalDate())) {
            this.estado = "Vencido";
        } else {
            this.estado = estado;
        }
    }

    /**
     * Obtiene el título del libro prestado.
     *
     * @return el título del libro.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro prestado.
     *
     * @param titulo el título del libro.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Devuelve una representación en cadena del objeto Prestamo.
     *
     * @return una cadena que representa el objeto Prestamo.
     */
    @Override
    public String toString() {
        return "Prestamo{" +
                "numReserva=" + numReserva +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", estado='" + estado + '\'' +
                ", titulo='" + titulo + '\'' +
                ", nombreSocio='" + nombreSocio + '\'' +
                '}';
    }
}
