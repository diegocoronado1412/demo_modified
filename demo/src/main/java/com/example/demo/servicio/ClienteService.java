package com.example.demo.servicio;

import java.util.List;
import com.example.demo.entidad.Cliente;

public interface ClienteService {

    public Cliente obtenerClientePorCedula(String cedula);
    public Cliente obtenerClientePorId(Long id); 
    public List<Cliente> obtenerTodosLosClientes();
    public void agregarCliente(Cliente cliente);
    public void actualizarCliente(Cliente cliente);
    public void eliminarCliente(Long id);
    public Cliente autenticar(String cedula, String contraseña);
}
