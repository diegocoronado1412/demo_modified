package com.example.demo.controlador;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/veterinario")
public class VeterinarioViewController {

    @GetMapping("/panel")
    public String mostrarPanelVeterinario(HttpSession session, Model model) {
        Object usuario = session.getAttribute("usuario");
        String rol = (String) session.getAttribute("rol");

        // Verifica que el usuario esté autenticado y tenga el rol de veterinario
        if (usuario == null || !"veterinario".equals(rol)) {
            return "redirect:/login";
        }

        // Pasa información a la vista, por ejemplo:
        model.addAttribute("veterinario", usuario);
        // Agrega otras estadísticas o datos necesarios aquí

        // Retorna la vista ubicada en: templates/veterinario/panel.html
        return "veterinario/panel";
    }
}
