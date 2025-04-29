package com.example.demo.controlador;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entidad.Cliente;
import com.example.demo.servicio.ClienteService;


@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteRestController {

    @Autowired
    private ClienteService clienteService;

    // Obtener cliente por cédula
    @GetMapping("/{id}")
    public Cliente obtenerClientePorId(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        if (cliente == null) {
            throw new RuntimeException("El cliente con cédula " + id + " no existe.");
        }
        return cliente;
    }

    // Listar todos
    @GetMapping("/listar")
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteService.obtenerTodosLosClientes();
    }

    // Registrar un nuevo cliente (JSON)
    @PostMapping("/registrar")
    public void registrarCliente(@RequestBody Cliente cliente) {
        clienteService.agregarCliente(cliente);
    }

    // Actualizar
    @PutMapping("/actualizar/{id}")
    public void actualizarCliente(@RequestBody Cliente cliente) {
        clienteService.actualizarCliente(cliente);
    }

    // Eliminar por cédula
    @DeleteMapping("/eliminar/{id}")
    public void eliminarCliente(@PathVariable Long id) {
        Cliente cliente = clienteService.obtenerClientePorId(id);
        if (cliente == null) {
            throw new RuntimeException("El cliente con cédula " + id + " no existe.");
        }
        clienteService.eliminarCliente(id);
    }
}









































