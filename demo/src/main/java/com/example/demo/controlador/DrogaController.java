package com.example.demo.controlador;

import com.example.demo.entidad.Droga;
import com.example.demo.servicio.DrogaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/droga")  // <-- importante para que case con Angular
@CrossOrigin(origins = "http://localhost:4200") // <-- para permitir conexión desde Angular
public class DrogaController {

    @Autowired
    private DrogaService drogaService;

    // LISTAR todas las drogas
    @GetMapping("/listar")
    public List<Droga> listarDrogas() {
        return drogaService.obtenerTodasLasDrogas();
    }

    // OBTENER una droga por ID
    @GetMapping("/{id}")
    public Droga obtenerDrogaPorId(@PathVariable Long id) {
        return drogaService.obtenerDrogaPorId(id);
    }

    // CREAR nueva droga
    @PostMapping("/crear")
    public Droga crearDroga(@RequestBody Droga droga) {
        return drogaService.agregarDroga(droga);
    }

    // ACTUALIZAR una droga
    @PutMapping("/actualizar/{id}")
    public Droga actualizarDroga(@PathVariable Long id, @RequestBody Droga droga) {
        droga.setId(id);
        return drogaService.actualizarDroga(droga);
    }

    // ELIMINAR una droga
    @DeleteMapping("/eliminar/{id}")
    public void eliminarDroga(@PathVariable Long id) {
        drogaService.eliminarDroga(id);
    }
}
