package com.example.demo.controlador;

import com.example.demo.entidad.Tratamiento;
import com.example.demo.servicio.TratamientoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
@CrossOrigin(origins = "*")
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tratamiento crearTratamiento(@RequestBody Tratamiento tratamiento) {
        tratamiento.setFecha(new Date());
        return tratamientoService.agregarTratamiento(tratamiento);
    }

    @GetMapping
    public List<Tratamiento> obtenerTodosLosTratamientos() {
        return tratamientoService.obtenerTodosLosTratamientos();
    }

    @GetMapping("/{id}")
    public Tratamiento obtenerTratamientoPorId(@PathVariable Long id) {
        return tratamientoService.obtenerTratamientoPorId(id);
    }

    @GetMapping("/mascota/{idMascota}")
    public List<Tratamiento> obtenerTratamientosPorMascota(@PathVariable Long idMascota) {
        return tratamientoService.obtenerTratamientosPorMascota(idMascota);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarTratamiento(@PathVariable Long id) {
        tratamientoService.eliminarTratamiento(id);
    }
    @PutMapping("/{id}")
public Tratamiento actualizarTratamiento(@PathVariable Long id, @RequestBody Tratamiento tratamiento) {
    tratamiento.setId(id); // Aseguramos que el ID se mantenga
    return tratamientoService.actualizarTratamiento(tratamiento);
}

}
