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

    public Droga(String nombre, double preciocompra, double precioventa, int unidadesdisponibles, int unidadesvendidas) {
        this.nombre = nombre;
        this.precioCompra = preciocompra;
        this.precioVenta = precioventa;
        this.unidadesDisponibles = unidadesdisponibles;
        this.unidadesVendidas = unidadesvendidas;
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