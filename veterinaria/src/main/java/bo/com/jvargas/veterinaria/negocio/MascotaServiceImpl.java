package bo.com.jvargas.veterinaria.negocio;

import bo.com.jvargas.veterinaria.datos.model.Cliente;
import bo.com.jvargas.veterinaria.datos.model.Mascota;
import bo.com.jvargas.veterinaria.datos.model.dto.MascotaDto;
import bo.com.jvargas.veterinaria.datos.repository.ClienteRepository;
import bo.com.jvargas.veterinaria.datos.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service("MascotaService")

public class MascotaServiceImpl implements  MascotaService{
    private final MascotaRepository mascotaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public List<MascotaDto> lista() {
        return mascotaRepository.listar();
    }

    @Override
    @Transactional
    public void registrar(MascotaDto mascota) {
        Mascota mascota2 = new Mascota();
        mascota2.setId(mascota.getId());
        mascota2.setNombre(mascota.getNombre());
        mascota2.setSexo(mascota.getSexo());
        mascota2.setEdad(mascota.getEdad());
        mascota2.setColor(mascota.getColor());
        Cliente cliente = clienteRepository.findByCi(mascota.getCi()).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        mascota2.setCiCliente(cliente);
        mascotaRepository.save(mascota2);
    }

    @Override
    public void actualizar(Long id, Mascota mascota) {
        Mascota mascotaBuscada = mascotaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        mascotaBuscada.setNombre(mascota.getNombre());
        mascotaBuscada.setEdad(mascota.getEdad());
        mascotaBuscada.setSexo(mascota.getSexo());
        mascotaBuscada.setColor(mascota.getColor());
//        mascotaBuscada.setCiCliente(mascota.getCiCliente());
        mascotaRepository.save(mascotaBuscada);
    }

    @Override
    public void eliminar(Long id) {
        Mascota mascotaBuscada = mascotaRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        mascotaBuscada.setDeleted(true);
        mascotaRepository.save(mascotaBuscada);
    }
}
