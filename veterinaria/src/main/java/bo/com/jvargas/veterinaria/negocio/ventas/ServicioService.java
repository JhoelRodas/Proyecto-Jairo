package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.Servicio;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface ServicioService {
    List<Servicio> listarServicios();

    Optional<Servicio> crearServicio(Servicio servicioNuevo);

    Optional<Servicio> actualizarServicio(Long id,
                                          Servicio servicioNuevo);

    void elimiarServicio(Long id);
}
