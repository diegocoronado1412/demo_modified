package com.example.demo.repositorio;

import com.example.demo.entidad.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    List<Tratamiento> findByMascotaId(Long idMascota);
}
