package com.example.demo.controlador;

import com.example.demo.entidad.Administrador;
import com.example.demo.entidad.Cliente;
import com.example.demo.entidad.Veterinario;
import com.example.demo.servicio.AdministradorService;
import com.example.demo.servicio.ClienteService;
import com.example.demo.servicio.VeterinarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VeterinarioService veterinarioService;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    // LOGIN por formulario HTML
    @PostMapping("/login")
    public String procesarLogin(@RequestParam String cedula,
                                @RequestParam String contraseña,
                                HttpSession session,
                                Model model) {
        // Primero, intentar autenticar como Administrador
        Administrador admin = administradorService.obtenerAdministradorPorUsuario(cedula);
        if (admin != null && admin.getContraseña().equals(contraseña)) {
            session.setAttribute("usuario", admin);
            session.setAttribute("rol", "admin");
            return "redirect:/admin/panel";
        }

        // Intentar autenticar como Cliente (el panel de cliente lo maneja ClienteController)
        Cliente cliente = clienteService.autenticar(cedula, contraseña);
        if (cliente != null) {
            cliente.setContraseña(null);
            session.setAttribute("usuario", cliente);
            session.setAttribute("rol", "cliente");
            return "redirect:/cliente/panel";
        }

        // Intentar autenticar como Veterinario
        Veterinario veterinario = veterinarioService.autenticar(cedula, contraseña);
        if (veterinario != null) {
            veterinario.setContraseña(null);
            session.setAttribute("usuario", veterinario);
            session.setAttribute("rol", "veterinario");
            return "redirect:/veterinario/panel";
        }

        model.addAttribute("error", "Cédula o contraseña incorrecta.");
        return "login";
    }

    // LOGIN vía API para peticiones JavaScript
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales, HttpSession session) {
        String cedula = credenciales.get("cedula");
        String contraseña = credenciales.get("contraseña");

        // Autenticar como Administrador
        Administrador admin = administradorService.obtenerAdministradorPorUsuario(cedula);
        if (admin != null && admin.getContraseña().equals(contraseña)) {
            session.setAttribute("usuario", admin);
            session.setAttribute("rol", "admin");
            return ResponseEntity.ok(Map.of("usuario", admin, "rol", "admin"));
        }

        // Autenticar como Cliente
        Cliente cliente = clienteService.autenticar(cedula, contraseña);
        if (cliente != null) {
            cliente.setContraseña(null);
            session.setAttribute("usuario", cliente);
            session.setAttribute("rol", "cliente");
            return ResponseEntity.ok(Map.of("usuario", cliente, "rol", "cliente"));
        }

        // Autenticar como Veterinario
        Veterinario veterinario = veterinarioService.autenticar(cedula, contraseña);
        if (veterinario != null) {
            veterinario.setContraseña(null);
            session.setAttribute("usuario", veterinario);
            session.setAttribute("rol", "veterinario");
            return ResponseEntity.ok(Map.of("usuario", veterinario, "rol", "veterinario"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cédula o contraseña incorrecta");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // Endpoint para obtener el usuario actual vía API
    @GetMapping("/api/usuario-actual")
    @ResponseBody
    public ResponseEntity<?> obtenerUsuarioActual(HttpSession session) {
        Object usuario = session.getAttribute("usuario");
        String rol = (String) session.getAttribute("rol");

        if (usuario != null) {
            return ResponseEntity.ok(Map.of("usuario", usuario, "rol", rol));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay usuario autenticado.");
    }
}
