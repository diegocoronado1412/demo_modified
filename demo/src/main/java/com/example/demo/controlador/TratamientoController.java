package com.example.demo.controlador;

import com.example.demo.entidad.Tratamiento;
import com.example.demo.servicio.TratamientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
@CrossOrigin(origins = "*") // Permite acceso desde cualquier origen (útil en desarrollo)
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    /**
     * Crea un nuevo Tratamiento.
     * Se asigna la fecha actual antes de persistir.
     */
    @PostMapping
    public Tratamiento crearTratamiento(@RequestBody Tratamiento tratamiento) {
        tratamiento.setFecha(new Date());
        return tratamientoService.agregarTratamiento(tratamiento);
    }

    /**
     * Devuelve todos los tratamientos.
     */
    @GetMapping
    public List<Tratamiento> obtenerTodosLosTratamientos() {
        return tratamientoService.obtenerTodosLosTratamientos();
    }

    /**
     * Devuelve los tratamientos de una mascota en particular.
     */
    @GetMapping("/mascota/{idMascota}")
    public List<Tratamiento> obtenerTratamientosPorMascota(@PathVariable Long idMascota) {
        return tratamientoService.obtenerTratamientosPorMascota(idMascota);
    }
}
