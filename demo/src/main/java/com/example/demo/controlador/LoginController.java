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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoginController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VeterinarioService veterinarioService;

    // LOGIN API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales, HttpSession session) {
        String cedula = credenciales.get("cedula");
        String contraseña = credenciales.get("password");

        // Autenticar como Administrador
        Administrador admin = administradorService.obtenerAdministradorPorUsuario(cedula);
        if (admin != null && admin.getContraseña().equals(contraseña)) {
            session.setAttribute("usuario", admin);
            session.setAttribute("rol", "ADMIN");
            return ResponseEntity.ok(Map.of(
                    "userId", admin.getId(),
                    "role", "ADMIN",
                    "token", "mock-token-admin" // Opcional: puedes añadir un JWT real en el futuro
            ));
        }

        // Autenticar como Cliente
        Cliente cliente = clienteService.autenticar(cedula, contraseña);
        if (cliente != null) {
            session.setAttribute("usuario", cliente);
            session.setAttribute("rol", "CLIENTE");
            return ResponseEntity.ok(Map.of(
                    "userId", cliente.getId(),
                    "role", "CLIENTE",
                    "token", "mock-token-cliente"
            ));
        }

        // Autenticar como Veterinario
        Veterinario veterinario = veterinarioService.autenticar(cedula, contraseña);
        if (veterinario != null) {
            session.setAttribute("usuario", veterinario);
            session.setAttribute("rol", "VETERINARIO");
            return ResponseEntity.ok(Map.of(
                    "userId", veterinario.getId(),
                    "role", "VETERINARIO",
                    "token", "mock-token-veterinario"
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cédula o contraseña incorrecta");
    }

    // Obtener sesión actual
    @GetMapping("/usuario-actual")
    public ResponseEntity<?> obtenerUsuarioActual(HttpSession session) {
        Object usuario = session.getAttribute("usuario");
        String rol = (String) session.getAttribute("rol");

        if (usuario != null) {
            return ResponseEntity.ok(Map.of("usuario", usuario, "rol", rol));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No hay usuario autenticado.");
    }

    // Logout API
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada exitosamente.");
    }
}
