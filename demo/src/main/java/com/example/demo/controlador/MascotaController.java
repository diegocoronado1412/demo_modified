package com.example.demo.controlador;

import com.example.demo.entidad.Cliente;
import com.example.demo.entidad.Mascota;
import com.example.demo.servicio.ClienteService;
import com.example.demo.servicio.MascotaService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mascota")
@CrossOrigin(origins = "http://localhost:4200")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/crear")
    public void guardarMascota(@RequestBody Mascota mascota, @RequestParam String clienteCedula) {
        Cliente cliente = clienteService.obtenerClientePorCedula(clienteCedula);
        if (cliente == null) {
            throw new RuntimeException("El cliente con cedula " + clienteCedula + " no existe.");
        }
        mascota.setCliente(cliente);
        mascotaService.guardarMascota(mascota);
    }

    @GetMapping("/listar")
    public List<Mascota> listarMascotas() {
        return mascotaService.obtenerTodasLasMascotas();
    }

    @PutMapping("/actualizar/{id}")
    public void actualizarMascota(@RequestBody Mascota mascota, @PathVariable Long id) {
        try {
            mascota.setId(id); // aseguramos el ID
            mascotaService.actualizarMascota(mascota);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar la mascota con ID: " + id);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarMascota(@PathVariable Long id) {
        mascotaService.eliminarMascota(id);
    }

    @GetMapping("/detalle/{id}")
    public Mascota verDetalleMascota(@PathVariable("id") Long id) {
        try {
            Mascota mascota = mascotaService.obtenerMascotaPorId(id);
            return mascota;
        } catch (RuntimeException e) {
            throw new RuntimeException("Mascota no encontrada con ID: " + id);
        }
    }

    @GetMapping("/obtenerMascotasPorCliente/{cedula}")
    public List<Mascota> obtenerMascotasPorCliente(@PathVariable String cedula) {
        Cliente cliente = clienteService.obtenerClientePorCedula(cedula);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con cédula: " + cedula);
        }
        return mascotaService.obtenerMascotasPorCliente(cedula);
    }

}
