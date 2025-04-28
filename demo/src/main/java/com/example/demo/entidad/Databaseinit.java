package com.example.demo.entidad;

import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.repositorio.AdministradorRepository;
import com.example.demo.repositorio.ClienteRepository;
import com.example.demo.repositorio.DrogaRepository;
import com.example.demo.repositorio.MascotaRepository;
import com.example.demo.repositorio.TratamientoRepository;
import com.example.demo.repositorio.VeterinarioRepository;

import jakarta.transaction.Transactional;

@Controller
@Transactional
public class Databaseinit implements ApplicationRunner {

    @Autowired
    AdministradorRepository administradorRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    DrogaRepository drogaRepository;

    @Autowired
    MascotaRepository mascotaRepository;

    @Autowired
    TratamientoRepository tratamientoRepository;

    @Autowired
    VeterinarioRepository veterinarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Nombres y apellidos comunes
        String[] nombres = { "Juan", "María", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Camila", "Jorge", "Sofía" };
        String[] apellidos = { "Gómez", "Rodríguez", "Pérez", "Martínez", "López", "Hernández", "Díaz", "Morales", "Torres", "Ramírez" };

        List<Cliente> clientes = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            String nombre = nombres[i % nombres.length];
            String apellido = apellidos[i % apellidos.length];
            String nombreCompleto = nombre + " " + apellido;
            String correo = (nombre + apellido).toLowerCase() + "@gmail.com";

            Cliente cliente = new Cliente(
                "100" + i,
                nombreCompleto,
                correo,
                "3001234" + String.format("%03d", i),
                "clave" + i,
                "cliente"
            );

            clientes.add(cliente);
        }

        clienteRepository.saveAll(clientes);

        // Veterinarios
        if (veterinarioRepository.count() == 0) {
            List<Veterinario> veterinarios = new ArrayList<>();

            Veterinario vet1 = new Veterinario();
            vet1.setCedula("2001");
            vet1.setNombre("Dr. Juan Pérez");
            vet1.setContraseña("clavevet1");
            vet1.setEspecialidad("Medicina general");
            vet1.setFotoUrl("foto_vet1.jpg");
            vet1.setRol("veterinario");
            vet1.setNumeroAtenciones(0);

            Veterinario vet2 = new Veterinario();
            vet2.setCedula("2002");
            vet2.setNombre("Dra. Ana Torres");
            vet2.setContraseña("clavevet2");
            vet2.setEspecialidad("Cirugía");
            vet2.setFotoUrl("foto_vet2.jpg");
            vet2.setRol("veterinario");
            vet2.setNumeroAtenciones(0);

            veterinarios.add(vet1);
            veterinarios.add(vet2);

            veterinarioRepository.saveAll(veterinarios);
            System.out.println("✅ Veterinarios insertados correctamente.");
        } else {
            System.out.println("⚠️ Veterinarios ya existen en la base de datos.");
        }

        // Mascotas
        List<Mascota> mascotas = new ArrayList<>();
        String[] especies = { "Perro", "Gato" };
        String[] razas = { "Labrador", "Siames", "Pastor Alemán", "Persa" };

        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            for (int j = 1; j <= 2; j++) {
                int index = (i * 2 + j) % razas.length;
                Mascota mascota = new Mascota();
                mascota.setNombre("Mascota_" + (i * 2 + j));
                mascota.setEspecie(especies[(i + j) % especies.length]);
                mascota.setRaza(razas[index]);
                mascota.setEdad((int) (Math.random() * 10 + 1));
                mascota.setFoto("foto_" + (i * 2 + j) + ".jpg");
                mascota.setCliente(cliente);
                mascota.setAntecedentes("Sin antecedentes");
                mascota.setVisitas("Ninguna");
                mascota.setCitas("Pendiente");
                mascota.setServicios("Vacunación");

                mascotas.add(mascota);
            }
        }

        mascotaRepository.saveAll(mascotas);

        // Drogas
        if (drogaRepository.count() == 0) {
            List<Droga> drogas = new ArrayList<>();
            try {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("static/excel/MEDICAMENTOS_VETERINARIA.xlsx");

                if (inputStream == null) {
                    System.err.println("❌ Error: No se encontró el archivo MEDICAMENTOS_VETERINARIA.xlsx en resources/static/excel/");
                } else {
                    Workbook workbook = new XSSFWorkbook(inputStream);
                    Sheet sheet = workbook.getSheetAt(0);
                    int rowCount = sheet.getPhysicalNumberOfRows();

                    for (int i = 1; i < rowCount; i++) { // Empezar en 1 para saltar encabezado
                        Row row = sheet.getRow(i);
                        if (row != null) {
                            Droga droga = new Droga(
                                row.getCell(1).getStringCellValue(),
                                row.getCell(3).getNumericCellValue(),
                                row.getCell(2).getNumericCellValue(),
                                (int) row.getCell(4).getNumericCellValue(),
                                (int) row.getCell(5).getNumericCellValue()
                            );
                            drogas.add(droga);
                        }
                    }
                    workbook.close();
                    inputStream.close();

                    drogaRepository.saveAll(drogas);
                    System.out.println("✅ " + drogas.size() + " medicamentos cargados desde Excel.");
                }
            } catch (IOException e) {
                System.err.println("❌ Error cargando medicamentos desde Excel:");
                e.printStackTrace();
            }
        } else {
            System.out.println("⚠️ Ya existen medicamentos en la base de datos.");
        }

        System.out.println("✅ Datos insertados: 50 clientes, 2 veterinarios, 100 mascotas y medicamentos cargados.");
    }
}
