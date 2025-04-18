package com.example.demo.error;

public class NotFoundException extends RuntimeException {
    private String mensaje;

    public NotFoundException(String mensaje) {
        super(mensaje);
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
