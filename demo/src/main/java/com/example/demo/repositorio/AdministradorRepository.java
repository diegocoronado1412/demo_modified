package com.example.demo.repositorio;

import com.example.demo.entidad.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, String> {
    Administrador findByUsuario(String usuario);
}
