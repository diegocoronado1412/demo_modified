package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entidad.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Busca un cliente por su cédula
    Cliente findByCedula(String cedula);

    // Busca un cliente por cédula y contraseña
    Cliente findByCedulaAndContraseña(String cedula, String contraseña);
}
