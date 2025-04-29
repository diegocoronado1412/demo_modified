package com.example.demo.servicio;

import java.util.List;
import com.example.demo.entidad.Tratamiento;

public interface TratamientoService {
    Tratamiento obtenerTratamientoPorId(Long id);
    List<Tratamiento> obtenerTodosLosTratamientos();
    Tratamiento agregarTratamiento(Tratamiento tratamiento);
    Tratamiento actualizarTratamiento(Tratamiento tratamiento);
    void eliminarTratamiento(Long id);
    List<Tratamiento> obtenerTratamientosPorMascota(Long idMascota);
}
