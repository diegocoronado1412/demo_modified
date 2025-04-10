package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entidad.Tratamiento;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {
}