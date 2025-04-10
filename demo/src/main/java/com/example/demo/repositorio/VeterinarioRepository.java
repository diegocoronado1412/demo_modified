package com.example.demo.repositorio;

import com.example.demo.entidad.Veterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarioRepository extends JpaRepository<Veterinario, Long> {
    Veterinario findByCedula(String cedula);
}
