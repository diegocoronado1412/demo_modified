package com.example.demo.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entidad.Tratamiento;
import com.example.demo.repositorio.TratamientoRepository;

@Service
public class TratamientoServiceImpl implements TratamientoService {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Override
    public Tratamiento obtenerTratamientoPorId(Long id) {
        return tratamientoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tratamiento> obtenerTodosLosTratamientos() {
        return tratamientoRepository.findAll();
    }

    @Override
    public Tratamiento agregarTratamiento(Tratamiento tratamiento) {
        return tratamientoRepository.save(tratamiento);
    }

    @Override
    public Tratamiento actualizarTratamiento(Tratamiento tratamiento) {
        return tratamientoRepository.save(tratamiento);
    }

    @Override
    public void eliminarTratamiento(Long id) {
        tratamientoRepository.deleteById(id);
    }
}