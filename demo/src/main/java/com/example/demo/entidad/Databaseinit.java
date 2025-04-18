package com.example.demo.entidad;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

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
        // Nombres y apellidos comunes para generar nombres realistas
        String[] nombres = { "Juan", "María", "Carlos", "Ana", "Luis", "Laura", "Pedro", "Camila", "Jorge", "Sofía" };
        String[] apellidos = { "Gómez", "Rodríguez", "Pérez", "Martínez", "López", "Hernández", "Díaz", "Morales",
                "Torres", "Ramírez" };

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
                    "cliente");

            clientes.add(cliente);
        }

        // Guardar todos los clientes en lote
        clienteRepository.saveAll(clientes);

        // Crear 100 mascotas (2 por cliente)
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

        List<Droga> drogas = new ArrayList<>();
        try {
            String filePath = "C:\\Users\\aleja\\OneDrive\\Documentos\\WEBDIEGO\\demo_modified\\demo\\src\\main\\resources\\static\\excel\\MEDICAMENTOS_VETERINARIA.xlsx";
            FileInputStream file = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();
            for (int i = 0; i < rowCount; i++) {
                long id = 0;
                String nombre = "";
                double preciocompra = 0;
                double precioventa = 0;
                int stock = 0;
                int uvendidas = 0;
                Row row = sheet.getRow(i);
                Random random = new Random();
                int randomNumber = random.nextInt(10);
                if (row != null) {
                    if (row.getRowNum() == 0) {
                        continue;
                    } else {
                        Cell cell = row.getCell(0);
                        id = (long) cell.getNumericCellValue();
                        cell = row.getCell(1);
                        nombre = cell.getStringCellValue();
                        cell = row.getCell(2);
                        precioventa = cell.getNumericCellValue();
                        cell = row.getCell(3);
                        preciocompra = cell.getNumericCellValue();
                        cell = row.getCell(4);
                        stock = (int) cell.getNumericCellValue();
                        cell = row.getCell(5);
                        uvendidas = (int) cell.getNumericCellValue();
                        Droga droga = new Droga(nombre, preciocompra, precioventa, stock, uvendidas);
                        System.out.println(droga.getId());
                        System.out.println(droga.getNombre());
                        System.out.println(droga.getPrecioVenta());
                        System.out.println(droga.getPrecioCompra());
                        System.out.println(droga.getUnidadesDisponibles());
                        System.out.println(droga.getUnidadesVendidas());
                        drogas.add(droga);

                    }
                }
            }
            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        drogaRepository.saveAll(drogas);

        // Guardar todas las mascotas en lote
        mascotaRepository.saveAll(mascotas);

        System.out.println("✅ Datos insertados: 50 clientes con nombres reales y 100 mascotas (2 por cliente).");
    }
}
