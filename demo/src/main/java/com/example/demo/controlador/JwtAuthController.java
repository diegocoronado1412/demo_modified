package com.example.demo.controlador;

import com.example.demo.dto.LoginDTO;
import com.example.demo.entidad.Administrador;
import com.example.demo.servicio.AdministradorService;
import com.example.demo.seguridad.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/jwt")
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AdministradorService administradorService;

    // Endpoint de login que genera un JWT
    @PostMapping("/login")
    public Map<String, String> loginJwt(@RequestBody LoginDTO login) {
        Administrador admin = administradorService.obtenerAdministradorPorUsuario(login.getCedula());

        if (admin != null && admin.getContraseña().equals(login.getPassword())) {
            String token = jwtUtil.generarToken(admin.getUsuario(), "ADMIN");
            return Map.of(
                    "token", token,
                    "rol", "ADMIN"
            );
        }

        throw new RuntimeException("Credenciales inválidas");
    }

    // Endpoint protegido que requiere un JWT válido
    @GetMapping("/protegido")
    public Map<String, String> rutaProtegida(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");

        if (jwtUtil.validarToken(token)) {
            String rol = jwtUtil.extraerRol(token);
            return Map.of("mensaje", "Acceso autorizado", "rol", rol);
        }

        throw new RuntimeException("Token inválido");
    }
}
