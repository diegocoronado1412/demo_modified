package com.example.demo.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entidad.Tratamiento;
import com.example.demo.servicio.TratamientoService;

@Controller
@RequestMapping("/tratamientos")
public class TratamientoController {

    @Autowired
    private TratamientoService tratamientoService;

    @GetMapping("/{id}")
    public String obtenerTratamientoPorId(@PathVariable Long id, Model model) {
        model.addAttribute("tratamiento", tratamientoService.obtenerTratamientoPorId(id));
        return "tratamiento/detalle";
    }

    @GetMapping
    public String obtenerTodosLosTratamientos(Model model) {
        model.addAttribute("tratamientos", tratamientoService.obtenerTodosLosTratamientos());
        return "tratamiento/lista";
    }

    @PostMapping
    public String agregarTratamiento(@ModelAttribute Tratamiento tratamiento) {
        tratamientoService.agregarTratamiento(tratamiento);
        return "redirect:/tratamientos";
    }
}
