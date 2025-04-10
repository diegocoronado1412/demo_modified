package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entidad.Droga;
import com.example.demo.servicio.DrogaService;

@Controller
@RequestMapping("/drogas")
public class DrogaController {

    @Autowired
    private DrogaService drogaService;

    @GetMapping("/{id}")
    public String obtenerDrogaPorId(@PathVariable Long id, Model model) {
        model.addAttribute("droga", drogaService.obtenerDrogaPorId(id));
        return "droga/detalle";
    }

    @GetMapping
    public String obtenerTodasLasDrogas(Model model) {
        model.addAttribute("drogas", drogaService.obtenerTodasLasDrogas());
        return "droga/lista";
    }

    @PostMapping
    public String agregarDroga(@ModelAttribute Droga droga) {
        drogaService.agregarDroga(droga);
        return "redirect:/drogas";
    }
}
