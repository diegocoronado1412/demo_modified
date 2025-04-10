package com.example.demo.controlador;

import com.example.demo.entidad.Veterinario;
import com.example.demo.servicio.VeterinarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/veterinario")
public class VeterinarioController {

    @Autowired
    private VeterinarioService veterinarioService;

    @GetMapping("/{cedula}")
    public Veterinario obtenerPorCedula(@PathVariable String cedula) {
        return veterinarioService.obtenerVeterinarioPorCedula(cedula);
    }

    @GetMapping
    public List<Veterinario> obtenerTodos() {
        return veterinarioService.obtenerTodosLosVeterinarios();
    }

    @PostMapping("/registrar")
    public Veterinario registrar(@RequestBody Veterinario veterinario) {
        // Si el rol viene nulo o vacío, se asigna "veterinario"
        if (veterinario.getRol() == null || veterinario.getRol().trim().isEmpty()) {
            veterinario.setRol("veterinario");
        }
        return veterinarioService.agregarVeterinario(veterinario);
    }

    @PutMapping("/actualizar")
    public Veterinario actualizar(@RequestBody Veterinario veterinario) {
        return veterinarioService.actualizarVeterinario(veterinario);
    }

    @DeleteMapping("/eliminar/{cedula}")
    public void eliminar(@PathVariable String cedula) {
        veterinarioService.eliminarVeterinario(cedula);
    }
}
