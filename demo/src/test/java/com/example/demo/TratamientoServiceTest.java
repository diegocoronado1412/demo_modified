package com.example.demo;

import com.example.demo.entidad.Droga;
import com.example.demo.entidad.Mascota;
import com.example.demo.entidad.Tratamiento;
import com.example.demo.entidad.Veterinario;
import com.example.demo.repositorio.DrogaRepository;
import com.example.demo.repositorio.MascotaRepository;
import com.example.demo.repositorio.TratamientoRepository;
import com.example.demo.repositorio.VeterinarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.servicio.TratamientoServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TratamientoServiceTest {

    @Mock
    private TratamientoRepository tratamientoRepository;

    @Mock
    private MascotaRepository mascotaRepository;

    @Mock
    private VeterinarioRepository veterinarioRepository;

    @Mock
    private DrogaRepository drogaRepository;

    @InjectMocks
    private TratamientoServiceImpl tratamientoService;

    private Tratamiento tratamiento;

    @BeforeEach
    void setUp() {
        tratamiento = new Tratamiento();
        tratamiento.setId(1L);
        tratamiento.setNombre("Vacunación");
        tratamiento.setDescripcion("Aplicación de vacuna");
        tratamiento.setFecha(new Date());

        Mascota mascota = new Mascota();
        mascota.setId(100L);
        tratamiento.setMascota(mascota);

        Veterinario veterinario = new Veterinario();
        veterinario.setId(200L);
        tratamiento.setVeterinario(veterinario);

        Droga droga = new Droga();
        droga.setId(300L);
        tratamiento.setDroga(droga);
    }

    @Test
    void testAgregarTratamiento() {
        when(mascotaRepository.findById(100L)).thenReturn(Optional.of(tratamiento.getMascota()));
        when(veterinarioRepository.findById(200L)).thenReturn(Optional.of(tratamiento.getVeterinario()));
        when(drogaRepository.findById(300L)).thenReturn(Optional.of(tratamiento.getDroga()));
        when(tratamientoRepository.save(any(Tratamiento.class))).thenReturn(tratamiento);

        Tratamiento resultado = tratamientoService.agregarTratamiento(tratamiento);

        assertThat(resultado).isNotNull();
        verify(tratamientoRepository).save(tratamiento);
    }

    @Test
    void testObtenerTodosLosTratamientos() {
        when(tratamientoRepository.findAll()).thenReturn(Arrays.asList(tratamiento));

        var lista = tratamientoService.obtenerTodosLosTratamientos();

        assertThat(lista).hasSize(1);
        assertThat(lista.get(0).getNombre()).isEqualTo("Vacunación");
    }

    @Test
    void testObtenerTratamientosPorMascota() {
        when(tratamientoRepository.findByMascotaId(100L)).thenReturn(Arrays.asList(tratamiento));

        var lista = tratamientoService.obtenerTratamientosPorMascota(100L);

        assertThat(lista).isNotEmpty();
        verify(tratamientoRepository).findByMascotaId(100L);
    }
    @Test
void testObtenerTratamientoPorId() {
    when(tratamientoRepository.findById(1L)).thenReturn(Optional.of(tratamiento));

    Tratamiento resultado = tratamientoService.obtenerTratamientoPorId(1L);

    assertThat(resultado).isNotNull();
    assertThat(resultado.getId()).isEqualTo(1L);
    verify(tratamientoRepository).findById(1L);
}
@Test
void testEliminarTratamiento() {
    when(tratamientoRepository.existsById(1L)).thenReturn(true);
    doNothing().when(tratamientoRepository).deleteById(1L);

    tratamientoService.eliminarTratamiento(1L);

    verify(tratamientoRepository).deleteById(1L);
}


}
