package com.example.demo.servicio;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidad.Droga;
import com.example.demo.entidad.Mascota;
import com.example.demo.entidad.Tratamiento;
import com.example.demo.entidad.Veterinario;
import com.example.demo.repositorio.DrogaRepository;
import com.example.demo.repositorio.MascotaRepository;
import com.example.demo.repositorio.TratamientoRepository;
import com.example.demo.repositorio.VeterinarioRepository;

@Service
public class TratamientoServiceImpl implements TratamientoService {

    @Autowired
    private TratamientoRepository tratamientoRepo;

    @Autowired
    private MascotaRepository mascotaRepo;

    @Autowired
    private VeterinarioRepository veterinarioRepo;

    @Autowired
    private DrogaRepository drogaRepo;

    @Override
    public Tratamiento agregarTratamiento(Tratamiento tratamiento) {
        Mascota mascota = mascotaRepo.findById(tratamiento.getMascota().getId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Veterinario vet = veterinarioRepo.findById(tratamiento.getVeterinario().getId())
            .orElseThrow(() -> new RuntimeException("Veterinario no encontrado"));

        Droga droga = drogaRepo.findById(tratamiento.getDroga().getId())
            .orElseThrow(() -> new RuntimeException("Droga no encontrada"));

        tratamiento.setMascota(mascota);
        tratamiento.setVeterinario(vet);
        tratamiento.setDroga(droga);
        tratamiento.setFecha(new Date());

        return tratamientoRepo.save(tratamiento);
    }

    @Override
    public List<Tratamiento> obtenerTodosLosTratamientos() {
        return tratamientoRepo.findAll();
    }

    @Override
    public List<Tratamiento> obtenerTratamientosPorMascota(Long idMascota) {
        return tratamientoRepo.findByMascotaId(idMascota);
    }

    @Override
    public Tratamiento obtenerTratamientoPorId(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTratamientoPorId'");
    }

    @Override
    public Tratamiento actualizarTratamiento(Tratamiento tratamiento) {
        throw new UnsupportedOperationException("Unimplemented method 'actualizarTratamiento'");
    }

    @Override
    public void eliminarTratamiento(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'eliminarTratamiento'");
    }
}
