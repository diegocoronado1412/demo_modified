package com.example.demo.controlador;

import com.example.demo.entidad.Tratamiento;
import com.example.demo.servicio.TratamientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
@CrossOrigin(origins = "*") // 🔥 Permite acceso desde Angular
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    @PostMapping
    public Tratamiento crearTratamiento(@RequestBody Tratamiento tratamiento) {
        return tratamientoService.agregarTratamiento(tratamiento);
    }
    

    @GetMapping
    public List<Tratamiento> obtenerTodosLosTratamientos() {
        return tratamientoService.obtenerTodosLosTratamientos();
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/mascota/{idMascota}")
    public List<Tratamiento> obtenerTratamientosPorMascota(@PathVariable Long idMascota) {
        return (List<Tratamiento>) tratamientoService.obtenerTratamientoPorId(idMascota);
    }
}
