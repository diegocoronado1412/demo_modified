package com.example.demo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;

@Entity
public class Administrador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            
    private String usuario;     // Se usará como identificador, por ejemplo "123456789"
    private String contraseña;  // Contraseña de administrador
    private String rol;         // Debe ser "admin" en este caso

    @OneToMany(mappedBy = "administrador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Veterinario> veterinarios;

    // Constructor vacío (requerido por JPA)
    public Administrador() {}

    // Constructor completo
    public Administrador(String usuario, String contraseña, String rol) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }

    // Métodos para gestionar veterinarios
    public void agregarVeterinario(Veterinario veterinario) {
        veterinarios.add(veterinario);
        veterinario.setAdministrador(this);
    }

    public void eliminarVeterinario(Veterinario veterinario) {
        veterinarios.remove(veterinario);
        veterinario.setAdministrador(null);
    }

    // Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public List<Veterinario> getVeterinarios() {
        return veterinarios;
    }

    public void setVeterinarios(List<Veterinario> veterinarios) {
        this.veterinarios = veterinarios;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
