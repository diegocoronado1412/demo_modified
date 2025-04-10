package com.example.demo.dto;

public class ConfiguracionDTO {
    private String nombreClinica;
    private String horarioAtencion;
    private String contacto;
    private String tituloInicio;
    private String subtituloInicio;
    private String nuevosServicios;
    private String mensajeContacto;

    // GETTERS y SETTERS (o usa Lombok si prefieres)
    public String getNombreClinica() {
        return nombreClinica;
    }

    public void setNombreClinica(String nombreClinica) {
        this.nombreClinica = nombreClinica;
    }

    public String getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(String horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTituloInicio() {
        return tituloInicio;
    }

    public void setTituloInicio(String tituloInicio) {
        this.tituloInicio = tituloInicio;
    }

    public String getSubtituloInicio() {
        return subtituloInicio;
    }

    public void setSubtituloInicio(String subtituloInicio) {
        this.subtituloInicio = subtituloInicio;
    }

    public String getNuevosServicios() {
        return nuevosServicios;
    }

    public void setNuevosServicios(String nuevosServicios) {
        this.nuevosServicios = nuevosServicios;
    }

    public String getMensajeContacto() {
        return mensajeContacto;
    }

    public void setMensajeContacto(String mensajeContacto) {
        this.mensajeContacto = mensajeContacto;
    }
}
