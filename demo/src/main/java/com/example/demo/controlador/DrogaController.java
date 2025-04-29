package com.example.demo.controlador;

import com.example.demo.entidad.Droga;
import com.example.demo.servicio.DrogaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/droga")
@CrossOrigin(origins = "http://localhost:4200")
public class DrogaController {

    @Autowired
    private DrogaService drogaService;

    @GetMapping("/listar")
    public List<Droga> listarDrogas() {
        return drogaService.obtenerTodasLasDrogas();
    }

    @GetMapping("/{id}")
    public Droga obtenerDroga(@PathVariable Long id) {
        return drogaService.obtenerDrogaPorId(id);
    }

    @PostMapping("/crear")
    public Droga crearDroga(@RequestBody Droga droga) {
        return drogaService.agregarDroga(droga);
    }

    @PutMapping("/actualizar/{id}")
    public Droga actualizarDroga(@PathVariable Long id, @RequestBody Droga droga) {
        droga.setId(id);
        return drogaService.actualizarDroga(droga);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarDroga(@PathVariable Long id) {
        drogaService.eliminarDroga(id);
    }
}
