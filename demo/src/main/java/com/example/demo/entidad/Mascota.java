package com.example.demo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String especie;
    private String raza;
    private Integer edad;
    private String antecedentes;
    private String visitas;
    private String citas;        
    private String servicios; 
// etc.

    
    // Campo para almacenar el nombre (o ruta) de la foto
    private String foto;
    
    // Relación: cada mascota pertenece a un Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Mascota() {
    }

    public Mascota(Long id, String nombre, String especie, String raza, Integer edad, String foto, Cliente cliente) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.foto = foto;
        this.cliente = cliente;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEspecie() {
        return especie;
    }
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    public String getRaza() {
        return raza;
    }
    public void setRaza(String raza) {
        this.raza = raza;
    }
    public Integer getEdad() {
        return edad;
    }
    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    public String getAntecedentes() {
        return antecedentes;
    }
    
    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }
    
    public String getVisitas() {
        return visitas;
    }
    
    public void setVisitas(String visitas) {
        this.visitas = visitas;
    }
    
    public String getCitas() {
        return citas;
    }
    
    public void setCitas(String citas) {
        this.citas = citas;
    }
    
    public String getServicios() {
        return servicios;
    }
    
    public void setServicios(String servicios) {
        this.servicios = servicios;
    }
}
