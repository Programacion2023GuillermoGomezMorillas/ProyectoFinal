package org.example.proyectofinaljava.model;

public class Socio {
    private int numeroSocio;
    private String nombreSocio;
    private String direccionSocio;
    private String telefonoSocio;
    private String emailSocio;

    public Socio(int numeroSocio, String nombreSocio, String direccionSocio, String telefonoSocio, String emailSocio) {
        this.numeroSocio = numeroSocio;
        this.nombreSocio = nombreSocio;
        this.direccionSocio = direccionSocio;
        this.telefonoSocio = telefonoSocio;
        this.emailSocio = emailSocio;
    }

    public int getNumeroSocio() {
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
}
