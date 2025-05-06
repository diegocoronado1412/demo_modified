package com.example.demo;

import com.example.demo.entidad.Droga;
import com.example.demo.entidad.Mascota;
import com.example.demo.entidad.Tratamiento;
import com.example.demo.entidad.Veterinario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.demo.repositorio.TratamientoRepository;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TratamientoRepositoryTest {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Test
    @DisplayName("Guardar tratamiento")
    void testGuardarTratamiento() {
        Tratamiento tratamiento = construirTratamiento();

        Tratamiento resultado = tratamientoRepository.save(tratamiento);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isNotNull();
        assertThat(resultado.getNombre()).isEqualTo("Vacunación");
    }

    @Test
    @DisplayName("Buscar todos los tratamientos")
    void testBuscarTodosTratamientos() {
        Tratamiento t1 = construirTratamiento();
        Tratamiento t2 = construirTratamiento();
        t2.setNombre("Desparasitación");

        tratamientoRepository.save(t1);
        tratamientoRepository.save(t2);

        List<Tratamiento> lista = tratamientoRepository.findAll();
        assertThat(lista).hasSize(2);
    }

    @Test
    @DisplayName("Buscar tratamiento por ID")
    void testBuscarTratamientoPorId() {
        Tratamiento tratamiento = tratamientoRepository.save(construirTratamiento());

        Tratamiento encontrado = tratamientoRepository.findById(tratamiento.getId()).orElse(null);

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNombre()).isEqualTo("Vacunación");
    }

    @Test
    @DisplayName("Eliminar tratamiento")
    void testEliminarTratamiento() {
        Tratamiento tratamiento = tratamientoRepository.save(construirTratamiento());

        tratamientoRepository.deleteById(tratamiento.getId());

        boolean existe = tratamientoRepository.findById(tratamiento.getId()).isPresent();
        assertThat(existe).isFalse();
    }

    @Test
    @DisplayName("Buscar tratamientos por ID de mascota")
    void testBuscarPorMascotaId() {
        Tratamiento tratamiento = construirTratamiento();
        tratamiento.getMascota().setId(100L); // simulamos ID

        tratamientoRepository.save(tratamiento);

        List<Tratamiento> resultado = tratamientoRepository.findByMascotaId(100L);
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNombre()).isEqualTo("Vacunación");
    }

    // 🔧 Método auxiliar para construir un tratamiento completo
    private Tratamiento construirTratamiento() {
        Droga droga = new Droga();
        droga.setId(1L);

        Mascota mascota = new Mascota();
        mascota.setId(100L);

        Veterinario vet = new Veterinario();
        vet.setId(200L);

        Tratamiento t = new Tratamiento();
        t.setNombre("Vacunación");
        t.setDescripcion("Vacuna contra rabia");
        t.setFecha(new Date());
        t.setDroga(droga);
        t.setMascota(mascota);
        t.setVeterinario(vet);
        return t;
    }
}
