package com.example.demo.servicio;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entidad.Cliente;
import com.example.demo.entidad.Mascota;
import com.example.demo.repositorio.ClienteRepository;
import com.example.demo.repositorio.MascotaRepository;

@Service
public class MascotaServiceImpl implements MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public List<Mascota> obtenerMascotasPorCliente(String cedula) {
        // Busca el cliente por cédula (asegúrate de que el ClienteService y repositorio estén configurados para esto)
        Cliente cliente = clienteRepository.findByCedula(cedula);
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con cédula: " + cedula);
        }
        return mascotaRepository.findByCliente(cliente);
    }

    @Override
    public void actualizarMascota(Mascota mascota) {
        Mascota mascotaExistente = mascotaRepository.findById(mascota.getId())
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + mascota.getId()));
        mascotaExistente.setNombre(mascota.getNombre());
        mascotaExistente.setEspecie(mascota.getEspecie());
        mascotaExistente.setRaza(mascota.getRaza());
        mascotaExistente.setEdad(mascota.getEdad());
        // Actualiza la foto solo si se proporciona un nuevo valor
        if (mascota.getFoto() != null && !mascota.getFoto().isEmpty()) {
            mascotaExistente.setFoto(mascota.getFoto());
        }
        mascotaRepository.save(mascotaExistente);
    }

    @Override
    public Mascota agregarMascota(Mascota mascota) {
        // Buscar el cliente usando la cédula del objeto Mascota (que debe venir en mascota.getCliente().getCedula())
        Cliente cliente = clienteRepository.findByCedula(mascota.getCliente().getCedula());
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado con cédula: " + mascota.getCliente().getCedula());
        }
        mascota.setCliente(cliente);
        return mascotaRepository.save(mascota);
    }

    @Override
    public void eliminarMascota(Long id) {
        mascotaRepository.deleteById(id);
    }

    @Override
    public Mascota obtenerMascotaPorId(Long id) {
        return mascotaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
    }

    @Override
    public List<Mascota> obtenerTodasLasMascotas() {
        return mascotaRepository.findAll();
    }

    @Override
    public Mascota guardarMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }
}
