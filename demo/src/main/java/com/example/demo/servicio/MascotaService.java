package com.example.demo.servicio;

import java.util.List;
import com.example.demo.entidad.Mascota;

public interface MascotaService {
    List<Mascota> obtenerMascotasPorCliente(String cedula);
    void actualizarMascota(Mascota mascota);
    Mascota agregarMascota(Mascota mascota);
    void eliminarMascota(Long id);
    Mascota obtenerMascotaPorId(Long id);
    List<Mascota> obtenerTodasLasMascotas();
    Mascota guardarMascota(Mascota mascota);
}
