package com.example.demo.servicio;

import java.util.List;
import com.example.demo.entidad.Cliente;

public interface ClienteService {

    Cliente obtenerClientePorCedula(String cedula);
    Cliente obtenerClientePorId(Long id); 
    List<Cliente> obtenerTodosLosClientes();
    Cliente agregarCliente(Cliente cliente);
    Cliente actualizarCliente(Cliente cliente);
    void eliminarCliente(String cedula);
    Cliente autenticar(String cedula, String contraseña);
}
