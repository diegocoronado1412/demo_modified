package com.example.demo.servicio;

import com.example.demo.entidad.Veterinario;
import java.util.List;

public interface VeterinarioService {
    Veterinario autenticar(String cedula, String contraseña);
    Veterinario obtenerVeterinarioPorId(Long id);
    Veterinario obtenerVeterinarioPorCedula(String cedula);
    List<Veterinario> obtenerTodosLosVeterinarios();
    Veterinario agregarVeterinario(Veterinario veterinario);
    Veterinario actualizarVeterinario(Veterinario veterinario);
    void eliminarVeterinario(String cedula);
}
