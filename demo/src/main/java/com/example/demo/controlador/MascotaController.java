package com.example.demo.controlador;

import com.example.demo.entidad.Cliente;
import com.example.demo.entidad.Mascota;
import com.example.demo.servicio.ClienteService;
import com.example.demo.servicio.MascotaService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private ClienteService clienteService;

    // Ruta donde se guardarán las imágenes (en desarrollo).
    private static final String UPLOADED_FOLDER = "src/main/resources/static/images/mascotas/";

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("mascota", new Mascota());
        model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
        return "mascota/crear";
    }

    @PostMapping("/crear")
    public String guardarMascota(@ModelAttribute Mascota mascota,
                                 @RequestParam("archivo") MultipartFile archivo,
                                 @RequestParam("clienteCedula") String clienteCedula) {
        // Procesar el archivo
        if (!archivo.isEmpty()) {
            try {
                File dir = new File(UPLOADED_FOLDER);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // Usa el nombre original en lugar de concatenar timestamp
                String filename = archivo.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + filename);
                Files.write(path, archivo.getBytes());
                mascota.setFoto(filename);
                System.out.println("Imagen guardada en: " + path.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/mascota/crear?error=imagen";
            }
        }
        // Asociar la mascota al cliente
        Cliente cliente = clienteService.obtenerClientePorCedula(clienteCedula);
        if (cliente == null) {
            return "redirect:/mascota/crear?error=cliente";
        }
        mascota.setCliente(cliente);
        mascotaService.guardarMascota(mascota);
        return "redirect:/mascota/listar";
    }

    @GetMapping("/listar")
    public String listarMascotas(Model model) {
        model.addAttribute("mascotas", mascotaService.obtenerTodasLasMascotas());
        return "mascota/listar";
    }

    @GetMapping("/editar/{id}")
    public String editarMascota(@PathVariable Long id, Model model) {
        Mascota mascota = mascotaService.obtenerMascotaPorId(id);
        model.addAttribute("mascota", mascota);
        model.addAttribute("clientes", clienteService.obtenerTodosLosClientes());
        return "mascota/editar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarMascota(@ModelAttribute Mascota mascota,
                                    @RequestParam("clienteCedula") String clienteCedula,
                                    @RequestParam(value = "archivo", required = false) MultipartFile archivo) {

        if (archivo != null && !archivo.isEmpty()) {
            try {
                File dir = new File(UPLOADED_FOLDER);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // Usa el nombre original en la edición también
                String filename = archivo.getOriginalFilename();
                Path path = Paths.get(UPLOADED_FOLDER + filename);
                Files.write(path, archivo.getBytes());
                mascota.setFoto(filename);
                System.out.println("Imagen actualizada en: " + path.toAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/mascota/editar/" + mascota.getId() + "?error=imagen";
            }
        }

        // Asociar la mascota al cliente
        Cliente cliente = clienteService.obtenerClientePorCedula(clienteCedula);
        if (cliente == null) {
            return "redirect:/mascota/editar/" + mascota.getId() + "?error=cliente";
        }
        mascota.setCliente(cliente);
        mascotaService.actualizarMascota(mascota);
        return "redirect:/mascota/listar";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarMascota(@PathVariable Long id) {
        mascotaService.eliminarMascota(id);
        return "redirect:/mascota/listar";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalleMascota(@PathVariable Long id, Model model) {
        try {
            Mascota mascota = mascotaService.obtenerMascotaPorId(id);
            model.addAttribute("mascota", mascota);
            return "mascota/detalle"; // Vista de detalle
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error"; // O una vista de error personalizada
        }
    }
}
