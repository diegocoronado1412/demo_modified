package com.example.demo;

import com.example.demo.entidad.Droga;
import com.example.demo.entidad.Mascota;
import com.example.demo.entidad.Tratamiento;
import com.example.demo.entidad.Veterinario;
import com.example.demo.repositorio.DrogaRepository;
import com.example.demo.repositorio.MascotaRepository;
import com.example.demo.repositorio.TratamientoRepository;
import com.example.demo.repositorio.VeterinarioRepository;
import com.example.demo.servicio.TratamientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TratamientoServiceIntegrationTest {

    @Autowired
    private TratamientoService tratamientoService;

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private DrogaRepository drogaRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    private Droga droga;
    private Mascota mascota;
    private Veterinario veterinario;

    @BeforeEach
    void setUp() {
        tratamientoRepository.deleteAll();
        veterinarioRepository.deleteAll();
        mascotaRepository.deleteAll();
        drogaRepository.deleteAll();

        droga = new Droga("Antibiótico", 100, 150, 50, 10);
        droga = drogaRepository.save(droga);

        mascota = new Mascota();
        mascota.setNombre("Max");
        mascota.setEspecie("Perro");
        mascota.setRaza("Beagle");
        mascota.setEdad(4);
        mascota.setAntecedentes("");
        mascota.setCitas("");
        mascota.setFoto("");
        mascota.setServicios("");
        mascota.setVisitas("");
        mascota = mascotaRepository.save(mascota);

        veterinario = new Veterinario();
        veterinario.setNombre("Dr. Juan");
        veterinario.setCedula("INT-" + System.currentTimeMillis());
        veterinario.setEspecialidad("Vacunas");
        veterinario.setContraseña("123");
        veterinario.setRol("VETERINARIO");
        veterinario.setNumeroAtenciones(0);
        veterinario.setFotoUrl("");
        veterinario = veterinarioRepository.save(veterinario);
    }

    @Test
    @DisplayName("Agregar y obtener tratamiento correctamente (integración)")
    void testAgregarYObtenerTratamiento() {
        Tratamiento t = new Tratamiento();
        t.setNombre("Desparasitación");
        t.setDescripcion("Desparasitación mensual");
        t.setFecha(new Date());
        t.setDroga(droga);
        t.setMascota(mascota);
        t.setVeterinario(veterinario);

        Tratamiento creado = tratamientoService.agregarTratamiento(t);

        assertThat(creado).isNotNull();
        assertThat(creado.getId()).isNotNull();

        Tratamiento recuperado = tratamientoService.obtenerTratamientoPorId(creado.getId());

        assertThat(recuperado).isNotNull();
        assertThat(recuperado.getNombre()).isEqualTo("Desparasitación");
    }

    @Test
    @DisplayName("Obtener tratamientos por mascota (integración)")
    void testObtenerTratamientosPorMascota() {
        Tratamiento t = new Tratamiento();
        t.setNombre("Chequeo");
        t.setDescripcion("Chequeo general");
        t.setFecha(new Date());
        t.setDroga(droga);
        t.setMascota(mascota);
        t.setVeterinario(veterinario);

        tratamientoService.agregarTratamiento(t);

        List<Tratamiento> lista = tratamientoService.obtenerTratamientosPorMascota(mascota.getId());

        assertThat(lista).isNotEmpty();
        assertThat(lista.get(0).getNombre()).isEqualTo("Chequeo");
    }

    @Test
    @DisplayName("Obtener todos los tratamientos (integración)")
    void testObtenerTodosLosTratamientos() {
        Tratamiento t1 = new Tratamiento();
        t1.setNombre("Vacuna");
        t1.setDescripcion("Vacuna anual");
        t1.setFecha(new Date());
        t1.setDroga(droga);
        t1.setMascota(mascota);
        t1.setVeterinario(veterinario);

        tratamientoService.agregarTratamiento(t1);

        List<Tratamiento> lista = tratamientoService.obtenerTodosLosTratamientos();
        assertThat(lista).isNotEmpty();
    }

    @Test
    @DisplayName("Actualizar tratamiento correctamente (integración)")
    void testActualizarTratamiento() {
        Tratamiento t = new Tratamiento();
        t.setNombre("Chequeo");
        t.setDescripcion("Chequeo básico");
        t.setFecha(new Date());
        t.setDroga(droga);
        t.setMascota(mascota);
        t.setVeterinario(veterinario);

        Tratamiento creado = tratamientoService.agregarTratamiento(t);
        creado.setNombre("Chequeo avanzado");
        creado.setDescripcion("Chequeo avanzado y completo");

        Tratamiento actualizado = tratamientoService.actualizarTratamiento(creado);

        assertThat(actualizado.getNombre()).isEqualTo("Chequeo avanzado");
        assertThat(actualizado.getDescripcion()).isEqualTo("Chequeo avanzado y completo");
    }

    @Test
    @DisplayName("Eliminar tratamiento correctamente (integración)")
    void testEliminarTratamiento() {
        Tratamiento t = new Tratamiento();
        t.setNombre("Control");
        t.setDescripcion("Control de rutina");
        t.setFecha(new Date());
        t.setDroga(droga);
        t.setMascota(mascota);
        t.setVeterinario(veterinario);

        Tratamiento creado = tratamientoService.agregarTratamiento(t);
        tratamientoService.eliminarTratamiento(creado.getId());

        assertThat(tratamientoRepository.existsById(creado.getId())).isFalse();
    }

    @Test
    @DisplayName("Eliminar tratamiento inexistente lanza excepción (integración)")
    void testEliminarTratamientoInexistente() {
        assertThrows(RuntimeException.class, () -> {
            tratamientoService.eliminarTratamiento(999L);
        });
    }

    @Test
    @DisplayName("Obtener tratamiento inexistente lanza excepción (integración)")
    void testObtenerTratamientoInexistente() {
        assertThrows(RuntimeException.class, () -> {
            tratamientoService.obtenerTratamientoPorId(999L);
        });
    }
}
