package com.example.demo.repositorio;

import com.example.demo.entidad.Mascota;
import com.example.demo.entidad.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // Devuelve todas las mascotas asociadas a un cliente dado
    List<Mascota> findByCliente(Cliente cliente);
}
