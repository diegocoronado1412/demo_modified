package com.example.demo.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entidad.Cliente;
import com.example.demo.entidad.Mascota;
import com.example.demo.servicio.ClienteService;
import com.example.demo.servicio.MascotaService;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MascotaService mascotaService;

    // Métodos existentes para CRUD de Cliente

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/crear";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        if (cliente.getRol() == null || cliente.getRol().isEmpty()) {
            cliente.setRol("cliente");
        }
        clienteService.agregarCliente(cliente);
        return "redirect:/cliente/listar";
    }

    @GetMapping("/listar")
    public String listarClientes(Model model) {
        List<Cliente> clientes = clienteService.obtenerTodosLosClientes();
        model.addAttribute("clientes", clientes);
        return "cliente/listar";
    }

    @GetMapping("/editar")
    public String editarCliente(@RequestParam("cedula") String cedula, Model model) {
        Cliente cliente = clienteService.obtenerClientePorCedula(cedula);
        model.addAttribute("cliente", cliente);
        return "cliente/editar";
    }

    @PostMapping("/actualizar")
    public String actualizarCliente(@ModelAttribute Cliente cliente) {
        clienteService.actualizarCliente(cliente);
        return "redirect:/cliente/listar";
    }

    @GetMapping("/eliminar/{cedula}")
    public String eliminarCliente(@PathVariable String cedula) {
        clienteService.eliminarCliente(cedula);
        return "redirect:/cliente/listar";
    }

    @GetMapping("/panel")
    public String mostrarPanelCliente() {
        return "cliente/panel";
    }

    // -----------------------------------------------------
    // MÉTODOS ADICIONALES PARA LA INTEGRACIÓN CON MASCOTAS
    // -----------------------------------------------------

    /**
     * Muestra la lista de mascotas asociadas a un cliente.
     * Se usa la cédula del cliente para buscarlo y obtener sus mascotas.
     * Se envía al modelo el objeto cliente, la lista de mascotas y un objeto vacío para el formulario.
     */
    @GetMapping("/{cedula}/mascotas")
    public String listarMascotasPorCliente(@PathVariable("cedula") String cedula, Model model) {
        Cliente cliente = clienteService.obtenerClientePorCedula(cedula);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con cédula: " + cedula);
        }
        model.addAttribute("cliente", cliente);
        // Se asume que MascotaService tiene un método obtenerMascotasPorCliente(String cedula)
        model.addAttribute("mascotas", mascotaService.obtenerMascotasPorCliente(cedula));
        model.addAttribute("mascota", new Mascota()); // Para el formulario de nueva mascota
        return "cliente/mascotas";  // Asegúrate de tener esta vista en templates/cliente/mascotas.html
    }

    /**
     * Procesa el formulario para agregar una mascota a un cliente.
     * Recibe el objeto Mascota y la cédula del cliente mediante un parámetro.
     */
    @PostMapping("/agregar-mascota")
    public String agregarMascotaCliente(@ModelAttribute Mascota mascota,
                                          @RequestParam("clienteCedula") String cedula) {
        Cliente cliente = clienteService.obtenerClientePorCedula(cedula);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con cédula: " + cedula);
        }
        mascota.setCliente(cliente);
        mascotaService.agregarMascota(mascota);
        return "redirect:/cliente/" + cedula + "/mascotas";
    }

    /**
     * Endpoint REST opcional para obtener las mascotas de un cliente en formato JSON.
     */
    @GetMapping("/obtener-mascotas/{cedula}")
    @ResponseBody
    public List<Mascota> obtenerMascotasPorCliente(@PathVariable String cedula) {
        return mascotaService.obtenerMascotasPorCliente(cedula);
    }

    /**
     * Endpoint REST opcional para obtener los datos de un cliente en formato JSON.
     */
    @GetMapping("/obtener-datos")
    @ResponseBody
    public ResponseEntity<Cliente> obtenerDatosCliente(@RequestParam("cedula") String cedula) {
        Cliente cliente = clienteService.obtenerClientePorCedula(cedula);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }
}
