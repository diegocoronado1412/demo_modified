package com.example.demo.entidad;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private java.util.Date fecha;

    // Si finalmente pasas la entidad Droga en vez de sólo el id, cambia este campo:
    // @ManyToOne
    // @JoinColumn(name = "droga_id")
    // private Droga droga;
    //
    // Por ahora mantenemos el id numérico:
    private Long droga;

    @ManyToOne
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Veterinario veterinario;

    public Tratamiento() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public java.util.Date getFecha() { return fecha; }
    public void setFecha(java.util.Date fecha) { this.fecha = fecha; }

    public Long getDroga() { return droga; }
    public void setDroga(Long droga) { this.droga = droga; }

    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }

    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }
}
