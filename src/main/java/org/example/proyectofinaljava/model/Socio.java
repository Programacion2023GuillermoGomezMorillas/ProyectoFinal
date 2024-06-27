package org.example.proyectofinaljava.model;

import java.util.Objects;

public class Socio {
    private long numeroSocio;
    private String nombreSocio;
    private String direccionSocio;
    private String telefonoSocio;
    private String emailSocio;

    public Socio(long numeroSocio, String nombreSocio, String direccionSocio, String telefonoSocio, String emailSocio) {
        this.numeroSocio = numeroSocio;
        this.nombreSocio = nombreSocio;
        this.direccionSocio = direccionSocio;
        this.telefonoSocio = telefonoSocio;
        this.emailSocio = emailSocio;
    }

    public long getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(int numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public String getNombreSocio() {
        return nombreSocio;
    }

    public void setNombreSocio(String nombreSocio) {
        this.nombreSocio = nombreSocio;
    }

    public String getDireccionSocio() {
        return direccionSocio;
    }

    public void setDireccionSocio(String direccionSocio) {
        this.direccionSocio = direccionSocio;
    }

    public String getTelefonoSocio() {
        return telefonoSocio;
    }

    public void setTelefonoSocio(String telefonoSocio) {
        this.telefonoSocio = telefonoSocio;
    }

    public String getEmailSocio() {
        return emailSocio;
    }

    public void setEmailSocio(String emailSocio) {
        this.emailSocio = emailSocio;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socio socio = (Socio) o;
        return numeroSocio == socio.numeroSocio || Objects.equals(telefonoSocio, socio.telefonoSocio) || Objects.equals(emailSocio, socio.emailSocio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroSocio, telefonoSocio, emailSocio);
    }
}
