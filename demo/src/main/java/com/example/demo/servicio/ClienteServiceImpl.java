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
    public void agregarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public void actualizarCliente(Cliente clienteActualizado) {
        if (clienteActualizado.getId() == null || !clienteRepository.existsById(clienteActualizado.getId())) {
            throw new RuntimeException("Cliente no encontrado");
        }
        clienteRepository.save(clienteActualizado);
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
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
