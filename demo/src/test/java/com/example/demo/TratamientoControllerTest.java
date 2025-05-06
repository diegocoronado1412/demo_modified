package com.example.demo;

import com.example.demo.controlador.TratamientoController;
import com.example.demo.entidad.Tratamiento;
import com.example.demo.servicio.TratamientoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

        tratamiento = new Tratamiento();
        tratamiento.setId(1L);
        tratamiento.setNombre("Vacunación");
        tratamiento.setDescripcion("Vacuna contra rabia");
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

    // Método de utilidad para convertir objetos Java a JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
