package org.example.proyectofinaljava.model;

import java.util.Objects;

/**
 * Clase que representa un socio en el sistema.
 */
public class Socio {
    private long numeroSocio;
    private String nombreSocio;
    private String direccionSocio;
    private String telefonoSocio;
    private String emailSocio;

    /**
     * Constructor de la clase Socio.
     *
     * @param numeroSocio    Número de socio.
     * @param nombreSocio    Nombre del socio.
     * @param direccionSocio Dirección del socio.
     * @param telefonoSocio  Teléfono del socio.
     * @param emailSocio     Correo electrónico del socio.
     */
    public Socio(long numeroSocio, String nombreSocio, String direccionSocio, String telefonoSocio, String emailSocio) {
        this.numeroSocio = numeroSocio;
        this.nombreSocio = nombreSocio;
        this.direccionSocio = direccionSocio;
        this.telefonoSocio = telefonoSocio;
        this.emailSocio = emailSocio;
    }

    /**
     * Obtiene el número de socio.
     *
     * @return Número de socio.
     */
    public long getNumeroSocio() {
        return numeroSocio;
    }

    /**
     * Establece el número de socio.
     *
     * @param numeroSocio Número de socio.
     */
    public void setNumeroSocio(long numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    /**
     * Obtiene el nombre del socio.
     *
     * @return Nombre del socio.
     */
    public String getNombreSocio() {
        return nombreSocio;
    }

    /**
     * Establece el nombre del socio.
     *
     * @param nombreSocio Nombre del socio.
     */
    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    /**
     * Obtiene la dirección del socio.
     *
     * @return Dirección del socio.
     */
    public String getDireccionSocio() {
        return direccionSocio;
    }

    /**
     * Establece la dirección del socio.
     *
     * @param direccionSocio Dirección del socio.
     */
    public void setDireccionSocio(String direccionSocio) {
        this.direccionSocio = direccionSocio;
    }

    /**
     * Obtiene el teléfono del socio.
     *
     * @return Teléfono del socio.
     */
    public String getTelefonoSocio() {
        return telefonoSocio;
    }

    /**
     * Establece el teléfono del socio.
     *
     * @param telefonoSocio Teléfono del socio.
     */
    public void setTelefonoSocio(String telefonoSocio) {
        this.telefonoSocio = telefonoSocio;
    }

    /**
     * Obtiene el correo electrónico del socio.
     *
     * @return Correo electrónico del socio.
     */
    public String getEmailSocio() {
        return emailSocio;
    }

    /**
     * Establece el correo electrónico del socio.
     *
     * @param emailSocio Correo electrónico del socio.
     */
    public void setEmailSocio(String emailSocio) {
        this.emailSocio = emailSocio;
    }

    /**
     * Sobrescritura del método toString para obtener una representación textual del socio.
     *
     * @return Cadena con la representación textual del socio.
     */
    @Override
    public String toString() {
        return "\nSocio{" +
                "numeroSocio=" + numeroSocio +
                ", nombreSocio='" + nombreSocio + '\'' +
                ", direccionSocio='" + direccionSocio + '\'' +
                ", telefonoSocio='" + telefonoSocio + '\'' +
                ", emailSocio='" + emailSocio + '\'' +
                '}';
    }

    /**
     * Sobrescritura del método equals para comparar dos objetos Socio basados en el número de socio,
     * teléfono o correo electrónico.
     *
     * @param o Objeto a comparar con el socio actual.
     * @return true si los socios son iguales (mismo número de socio, teléfono o correo electrónico),
     * false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socio socio = (Socio) o;
        return numeroSocio == socio.numeroSocio ||
                Objects.equals(telefonoSocio, socio.telefonoSocio) ||
                Objects.equals(emailSocio, socio.emailSocio);
    }

}
