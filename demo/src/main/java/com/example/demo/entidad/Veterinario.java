package com.example.demo.entidad;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Veterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String cedula;
    private String nombre;
    private String especialidad;
    private String fotoUrl;
    private int numeroAtenciones;
    private String contraseña;
    private String rol;  // Correctamente añadido

    @ManyToOne
    @JoinColumn(name = "administrador_usuario")
    private Administrador administrador;

    @OneToMany(mappedBy = "veterinario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tratamiento> tratamientos;

    public void administrarTratamiento(Tratamiento tratamiento) {
        tratamientos.add(tratamiento);
        tratamiento.setVeterinario(this);
    }

    public void incrementarAtenciones() {
        this.numeroAtenciones++;
    }

    // Getters y Setters completos claramente corregidos

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }
    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public int getNumeroAtenciones() {
        return numeroAtenciones;
    }
    public void setNumeroAtenciones(int numeroAtenciones) {
        this.numeroAtenciones = numeroAtenciones;
    }

    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }
    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public Administrador getAdministrador() {
        return administrador;
    }
    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}
