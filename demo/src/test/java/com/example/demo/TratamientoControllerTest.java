package com.example.demo;

import com.example.demo.controlador.TratamientoController;
import com.example.demo.entidad.Droga;
import com.example.demo.entidad.Mascota;
import com.example.demo.entidad.Tratamiento;
import com.example.demo.entidad.Veterinario;
import com.example.demo.servicio.TratamientoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
public class TratamientoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TratamientoService tratamientoService;

    @InjectMocks
    private TratamientoController tratamientoController;

    private Tratamiento tratamiento;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tratamientoController).build();

        Droga droga = new Droga();
        droga.setId(1L);
        droga.setNombre("Antibiótico");

        Mascota mascota = new Mascota();
        mascota.setId(2L);
        mascota.setNombre("Firulais");

        Veterinario vet = new Veterinario();
        vet.setId(3L);
        vet.setNombre("Dra. Ana");

        tratamiento = new Tratamiento();
        tratamiento.setId(1L);
        tratamiento.setNombre("Vacunación");
        tratamiento.setDescripcion("Vacuna contra rabia");
        tratamiento.setFecha(new Date());
        tratamiento.setDroga(droga);
        tratamiento.setMascota(mascota);
        tratamiento.setVeterinario(vet);
    }

    @Test
    void testCrearTratamiento() throws Exception {
        when(tratamientoService.agregarTratamiento(any(Tratamiento.class))).thenReturn(tratamiento);

        mockMvc.perform(post("/api/tratamientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tratamiento)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Vacunación"));
    }

    @Test
    void testObtenerTratamientoPorId() throws Exception {
        when(tratamientoService.obtenerTratamientoPorId(1L)).thenReturn(tratamiento);

        mockMvc.perform(get("/api/tratamientos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Vacunación"));
    }

    @Test
    void testObtenerTodosLosTratamientos() throws Exception {
        when(tratamientoService.obtenerTodosLosTratamientos()).thenReturn(Collections.singletonList(tratamiento));

        mockMvc.perform(get("/api/tratamientos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Vacunación"));
    }

    @Test
    void testEliminarTratamiento() throws Exception {
        doNothing().when(tratamientoService).eliminarTratamiento(1L);

        mockMvc.perform(delete("/api/tratamientos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testActualizarTratamiento() throws Exception {
        when(tratamientoService.actualizarTratamiento(any(Tratamiento.class))).thenReturn(tratamiento);

        mockMvc.perform(put("/api/tratamientos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tratamiento)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Vacunación"));
    }

    // Método auxiliar para convertir objetos a JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
