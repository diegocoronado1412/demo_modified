package com.example.demo.controlador;

import com.example.demo.servicio.ClienteService;
import com.example.demo.servicio.VeterinarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdministradorController {

    // Inyecta servicios solo si los necesitas en ciertas pantallas
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VeterinarioService veterinarioService;

    /**
     * Verificación genérica del rol "admin".
     * Llamar dentro de cada método GET para no repetir lógica.
     */
    private boolean esAdmin(HttpSession session) {
        Object usuario = session.getAttribute("usuario");
        String rol = (String) session.getAttribute("rol");
        return (usuario != null && "admin".equals(rol));
    }

    // ============================
    //         DASHBOARD
    // ============================
    @GetMapping("/dashboard")
    public String mostrarDashboard(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        // Aquí podrías cargar estadísticas, contadores, etc. 
        // model.addAttribute("contadorClientes", clienteService.obtenerTodosLosClientes().size());
        return "admin/dashboard";
    }

    // ============================
    //         VETERINARIOS
    // ============================
    @GetMapping("/veterinarios")
    public String mostrarVeterinarios(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        // Cargar lista de veterinarios, si procede
        model.addAttribute("veterinarios", veterinarioService.obtenerTodosLosVeterinarios());
        return "admin/admin-veterinarios";
    }

    // ============================
    //         MASCOTAS
    // ============================
    @GetMapping("/mascotas")
    public String mostrarMascotas(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        // Si necesitas cargar info de mascotas, inyecta MascotaService y usa:
        // model.addAttribute("mascotas", mascotaService.findAll());
        return "admin/admin-mascotas";
    }

    // ============================
    //        TRATAMIENTOS
    // ============================
    @GetMapping("/tratamientos")
    public String mostrarTratamientos(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        // Carga datos si es necesario
        return "admin/admin-tratamientos";
    }

    // ============================
    //         PRODUCTOS
    // ============================
    @GetMapping("/productos")
    public String mostrarProductos(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        // Carga datos si es necesario
        return "admin/admin-productos";
    }

    // ============================
    //         USUARIOS
    // ============================
    @GetMapping("/usuarios")
    public String mostrarUsuarios(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        // Ejemplo: Cargar listas de clientes y vets
        model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
        model.addAttribute("veterinarios", veterinarioService.obtenerTodosLosVeterinarios());
        return "admin/admin-usuarios";
    }

    // ============================
    //       CONFIGURACION
    // ============================
    @GetMapping("/configuracion")
    public String mostrarConfiguracion(HttpSession session, Model model) {
        if (!esAdmin(session)) {
            return "redirect:/login";
        }
        // Podrías cargar datos de configuración, si los guardas en BD
        return "admin/admin-configuracion";
    }

}
