package com.example.demo.controlador;

import com.example.demo.dto.MensajeContacto;
import com.example.demo.servicio.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/contacto")
@CrossOrigin(origins = "http://localhost:4200")
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    
    @PostMapping
    public Map<String, String> recibirMensaje(@RequestBody MensajeContacto mensaje) {
        System.out.println("📥 Nuevo mensaje recibido:");
        System.out.println("Nombre: " + mensaje.getNombre());
        System.out.println("Email: " + mensaje.getEmail());
        System.out.println("Teléfono: " + mensaje.getTelefono());
        System.out.println("Mensaje: " + mensaje.getMensaje());

        try {
            contactoService.enviarCorreo(mensaje);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Mensaje enviado por correo.");
            return response;
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Ocurrió un error al enviar el correo: " + e.getMessage());
            return errorResponse;
        }
    }
}
