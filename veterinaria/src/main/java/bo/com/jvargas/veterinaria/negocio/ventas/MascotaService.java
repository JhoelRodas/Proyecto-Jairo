package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.Mascota;
import bo.com.jvargas.veterinaria.datos.model.dto.MascotaDto;

import java.util.List;

public interface MascotaService {
    List<MascotaDto> lista();

    void registrar(MascotaDto mascota);
    void actualizar(Long id, Mascota mascota);
    void eliminar(Long id);
}
