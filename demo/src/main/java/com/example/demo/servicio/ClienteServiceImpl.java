package com.example.demo.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entidad.Cliente;
import com.example.demo.repositorio.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente obtenerClientePorCedula(String cedula) {
        return clienteRepository.findByCedula(cedula);
    }

    @Override
    public Cliente obtenerClientePorId(Long id) {
        return clienteRepository.findById(id).orElse(null); // Método claramente añadido
    }

    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente agregarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizarCliente(Cliente clienteActualizado) {
        Cliente cliente = clienteRepository.findByCedula(clienteActualizado.getCedula());
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setCorreo(clienteActualizado.getCorreo());
        cliente.setCelular(clienteActualizado.getCelular());

        if (clienteActualizado.getContraseña() != null && !clienteActualizado.getContraseña().isEmpty()) {
            cliente.setContraseña(clienteActualizado.getContraseña());
        }
        
        return clienteRepository.save(cliente);  // Asegura guardar cambios
    }

    @Override
    public void eliminarCliente(String cedula) {
        Cliente cliente = clienteRepository.findByCedula(cedula);
        if (cliente != null) {
            clienteRepository.delete(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado");
        }
    }

    @Override
    public Cliente autenticar(String cedula, String contraseña) {
        Cliente cliente = clienteRepository.findByCedula(cedula);
        if (cliente != null && cliente.getContraseña().equals(contraseña)) {
            return cliente;
        }
        return null;
    }
}
