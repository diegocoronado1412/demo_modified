package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.demo.entidad.Administrador;
import com.example.demo.repositorio.AdministradorRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private AdministradorRepository administradorRepository;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Si no existe el administrador con usuario "123456789", lo crea
        if (!administradorRepository.existsById("123456789")) {
            Administrador admin = new Administrador();
            admin.setUsuario("123456789");
            admin.setContraseña("admin");
            admin.setRol("admin");
            administradorRepository.save(admin);
            System.out.println("¡Administrador predeterminado creado!");
        } else {
            System.out.println("El administrador predeterminado ya existe.");
        }
    }
}
