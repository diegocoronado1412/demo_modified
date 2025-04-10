package com.example.demo.servicio;

import java.util.List;
import com.example.demo.entidad.Droga;

public interface DrogaService {
    Droga obtenerDrogaPorId(Long id);
    List<Droga> obtenerTodasLasDrogas();
    Droga agregarDroga(Droga droga);
    Droga actualizarDroga(Droga droga);
    void eliminarDroga(Long id);
}