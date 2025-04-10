package com.example.demo.servicio;

import com.example.demo.entidad.Veterinario;
import com.example.demo.repositorio.VeterinarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeterinarioServiceImpl implements VeterinarioService {

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    @Override
    public Veterinario autenticar(String cedula, String contraseña) {
        Veterinario veterinario = veterinarioRepository.findByCedula(cedula);
        if (veterinario != null && veterinario.getContraseña().equals(contraseña)) {
            return veterinario;
        }
        return null;
    }

    @Override
    public Veterinario obtenerVeterinarioPorId(Long id) {
        return veterinarioRepository.findById(id).orElse(null);
    }

    @Override
    public Veterinario obtenerVeterinarioPorCedula(String cedula) {
        return veterinarioRepository.findByCedula(cedula);
    }

    @Override
    public Veterinario agregarVeterinario(Veterinario veterinario) {
        return veterinarioRepository.save(veterinario);
    }

    @Override
    public List<Veterinario> obtenerTodosLosVeterinarios() {
        return veterinarioRepository.findAll();
    }

    @Override
    public Veterinario actualizarVeterinario(Veterinario veterinario) {
        Veterinario existente = veterinarioRepository.findById(veterinario.getId())
                .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));
        existente.setNombre(veterinario.getNombre());
        existente.setEspecialidad(veterinario.getEspecialidad());

        if(veterinario.getContraseña() != null && !veterinario.getContraseña().isEmpty()) {
            existente.setContraseña(veterinario.getContraseña());
        }

        return veterinarioRepository.save(existente);
    }

    @Override
    public void eliminarVeterinario(String cedula) {
        Veterinario veterinario = veterinarioRepository.findByCedula(cedula);
        if (veterinario != null) {
            veterinarioRepository.delete(veterinario);
        }
    }
}
