package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.Servicio;
import bo.com.jvargas.veterinaria.datos.model.dto.ServicioDto;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface ServicioService {
    List<ServicioDto> listarServicios();

    Optional<ServicioDto> crearServicio(ServicioDto servicioNuevo);

    Optional<ServicioDto> actualizarServicio(Long id,
                                          ServicioDto servicioNuevo);

    void elimiarServicio(Long id);
}
