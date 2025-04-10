package com.example.demo.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entidad.Administrador;
import com.example.demo.repositorio.AdministradorRepository;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public Administrador obtenerAdministradorPorUsuario(String usuario) {
        return administradorRepository.findById(usuario).orElse(null);
    }

    @Override
    public List<Administrador> obtenerTodosLosAdministradores() {
        return administradorRepository.findAll();
    }

    @Override
    public Administrador agregarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public Administrador actualizarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public void eliminarAdministrador(String usuario) {
        administradorRepository.deleteById(usuario);
    }
}
