package com.example.demo.entidad;

import jakarta.persistence.*;

@Entity
public class Droga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double precioCompra;
    private double precioVenta;
    private int unidadesDisponibles;
    private int unidadesVendidas;

    // Constructor vacío requerido por JPA
    public Droga() {
    }

    // Constructor adicional SOLO con ID (necesario para deserialización desde JSON)
    public Droga(Long id) {
        this.id = id;
    }

    // Constructor con parámetros completos
    public Droga(String nombre,
                 double precioCompra,
                 double precioVenta,
                 int unidadesDisponibles,
                 int unidadesVendidas) {
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.unidadesDisponibles = unidadesDisponibles;
        this.unidadesVendidas = unidadesVendidas;
    }

    // Método para actualizar unidades
    public void actualizarUnidades(int cantidad) {
        this.unidadesDisponibles -= cantidad;
        this.unidadesVendidas += cantidad;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecioCompra() { return precioCompra; }
    public void setPrecioCompra(double precioCompra) { this.precioCompra = precioCompra; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    public int getUnidadesDisponibles() { return unidadesDisponibles; }
    public void setUnidadesDisponibles(int unidadesDisponibles) { this.unidadesDisponibles = unidadesDisponibles; }

    public int getUnidadesVendidas() { return unidadesVendidas; }
    public void setUnidadesVendidas(int unidadesVendidas) { this.unidadesVendidas = unidadesVendidas; }
}
