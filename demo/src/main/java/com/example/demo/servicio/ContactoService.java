package com.example.demo.servicio;

import com.example.demo.dto.MensajeContacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactoService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCorreo(MensajeContacto mensaje) {
        try {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo("coronadodoriadiegoandres@gmail.com");  // Cambia por tu correo real
            email.setSubject("Nuevo mensaje de contacto");
            email.setText("Nombre: " + mensaje.getNombre() + "\n"
                        + "Email: " + mensaje.getEmail() + "\n"
                        + "Teléfono: " + mensaje.getTelefono() + "\n"
                        + "Mensaje: " + mensaje.getMensaje());
            mailSender.send(email);
            System.out.println("✅ Correo enviado exitosamente");
        } catch (Exception e) {
            System.err.println("🚨 Error al enviar correo: " + e.getMessage());
            e.printStackTrace();
            throw e;  // Esto permite que Spring devuelva el error 500 con detalles
        }
    }
}
