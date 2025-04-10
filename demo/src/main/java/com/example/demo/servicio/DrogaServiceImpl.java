package com.example.demo.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entidad.Droga;
import com.example.demo.repositorio.DrogaRepository;

@Service
public class DrogaServiceImpl implements DrogaService {

    @Autowired
    private DrogaRepository drogaRepository;

    @Override
    public Droga obtenerDrogaPorId(Long id) {
        return drogaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Droga> obtenerTodasLasDrogas() {
        return drogaRepository.findAll();
    }

    @Override
    public Droga agregarDroga(Droga droga) {
        return drogaRepository.save(droga);
    }

    @Override
    public Droga actualizarDroga(Droga droga) {
        return drogaRepository.save(droga);
    }

    @Override
    public void eliminarDroga(Long id) {
        drogaRepository.deleteById(id);
    }
}