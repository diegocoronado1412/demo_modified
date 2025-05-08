package com.example.demo;

import com.example.demo.entidad.Droga;
import com.example.demo.entidad.Mascota;
import com.example.demo.entidad.Tratamiento;
import com.example.demo.entidad.Veterinario;
import com.example.demo.repositorio.DrogaRepository;
import com.example.demo.repositorio.MascotaRepository;
import com.example.demo.repositorio.TratamientoRepository;
import com.example.demo.repositorio.VeterinarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class TratamientoRepositoryTest {

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private DrogaRepository drogaRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private VeterinarioRepository veterinarioRepository;

    private static int contador = 1; // Para cédulas únicas

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
        Long mascotaId = tratamiento.getMascota().getId();

        tratamientoRepository.save(tratamiento);

        List<Tratamiento> resultado = tratamientoRepository.findByMascotaId(mascotaId);
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNombre()).isEqualTo("Vacunación");
    }

    @Test
    @DisplayName("Actualizar tratamiento")
    void testActualizarTratamiento() {
        Tratamiento tratamiento = tratamientoRepository.save(construirTratamiento());

        tratamiento.setNombre("Actualizado");
        tratamiento.setDescripcion("Tratamiento modificado");

        Tratamiento actualizado = tratamientoRepository.save(tratamiento);

        assertThat(actualizado.getNombre()).isEqualTo("Actualizado");
        assertThat(actualizado.getDescripcion()).isEqualTo("Tratamiento modificado");
    }

    // ✅ Método auxiliar para crear el tratamiento con relaciones persistidas
    private Tratamiento construirTratamiento() {
        Droga droga = new Droga();
        droga.setNombre("Antibiótico");
        droga.setPrecioCompra(100);
        droga.setPrecioVenta(150);
        droga.setUnidadesDisponibles(20);
        droga.setUnidadesVendidas(5);
        droga = drogaRepository.save(droga);

        Mascota mascota = new Mascota();
        mascota.setNombre("Firulais");
        mascota.setEspecie("Perro");
        mascota.setRaza("Labrador");
        mascota.setEdad(3);
        mascota.setAntecedentes("Ninguno");
        mascota.setVisitas("");
        mascota.setCitas("");
        mascota.setServicios("");
        mascota.setFoto("");
        mascota = mascotaRepository.save(mascota);

        Veterinario vet = new Veterinario();
        vet.setNombre("Dra. Ana");
        vet.setCedula("123456789" + contador++); // evita violar el índice único
        vet.setEspecialidad("General");
        vet.setFotoUrl("");
        vet.setNumeroAtenciones(0);
        vet.setContraseña("test123");
        vet.setRol("VETERINARIO");
        vet = veterinarioRepository.save(vet);

        Tratamiento t = new Tratamiento();
        t.setNombre("Vacunación");
        t.setDescripcion("Vacuna contra la rabia");
        t.setFecha(new Date());
        t.setDroga(droga);
        t.setMascota(mascota);
        t.setVeterinario(vet);
        return t;
    }
}
