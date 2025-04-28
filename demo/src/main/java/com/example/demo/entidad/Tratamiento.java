package com.example.demo.entidad;

import java.util.Date;
import jakarta.persistence.*;

@Entity
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;

    private Long droga; // sigue siendo solo ID de la droga

    @ManyToOne
    @JoinColumn(name = "mascota_id")   // CORREGIDO
    private Mascota mascota;            // CORREGIDO

    @ManyToOne
    @JoinColumn(name = "veterinario_id")
    private Veterinario veterinario;

    public void registrarTratamiento() {
        this.fecha = new Date();
    }

    // getters y setters normales
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public Long getDroga() { return droga; }
    public void setDroga(Long droga) { this.droga = droga; }

    public Mascota getMascota() { return mascota; }
    public void setMascota(Mascota mascota) { this.mascota = mascota; }

    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }
}
