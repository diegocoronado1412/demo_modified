package com.example.demo.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entidad.Cliente;
import com.example.demo.servicio.ClienteService;

@RestController
@RequestMapping("/api/cliente")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    // Obtener cliente por cédula
    @GetMapping("/{cedula}")
    public Cliente obtenerClientePorCedula(@PathVariable String cedula) {
        Cliente cliente = clienteService.obtenerClientePorCedula(cedula);
        if (cliente == null) {
            throw new RuntimeException("El cliente con cédula " + cedula + " no existe.");
        }
        return cliente;
    }

    // Listar todos
    @GetMapping
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteService.obtenerTodosLosClientes();
    }

    // Registrar un nuevo cliente (JSON)
    @PostMapping("/registrar")
    public Cliente registrarCliente(@RequestBody Cliente cliente) {
        if (cliente.getRol() == null || cliente.getRol().isEmpty()) {
            cliente.setRol("cliente");
        }
        return clienteService.agregarCliente(cliente);
    }

    // Actualizar
    @PutMapping("/actualizar")
    public Cliente actualizarCliente(@RequestBody Cliente cliente) {
        return clienteService.actualizarCliente(cliente);
    }

    // Eliminar por cédula
    @DeleteMapping("/eliminar/{cedula}")
    public void eliminarCliente(@PathVariable String cedula) {
        clienteService.eliminarCliente(cedula);
    }
}
