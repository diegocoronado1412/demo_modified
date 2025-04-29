package com.example.demo.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidad.Tratamiento;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
    // Este método será implementado automáticamente por Spring Data JPA
    List<Tratamiento> findByMascotaId(Long idMascota);
}
