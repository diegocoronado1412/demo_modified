package com.example.demo.servicio;

import java.util.List;
import com.example.demo.entidad.Administrador;

public interface AdministradorService {
    
    Administrador obtenerAdministradorPorUsuario(String usuario);
    List<Administrador> obtenerTodosLosAdministradores();
    Administrador agregarAdministrador(Administrador administrador);
    Administrador actualizarAdministrador(Administrador administrador);
    void eliminarAdministrador(String usuario);
}
