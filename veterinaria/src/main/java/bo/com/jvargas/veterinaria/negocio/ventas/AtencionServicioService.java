package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.AtencionServicio;
import bo.com.jvargas.veterinaria.datos.model.dto.AtencionServicioDto;

import java.util.List;

/**
 * @author GERSON
 */

public interface AtencionServicioService {
    List<AtencionServicio> listar();

    void insertarServicios(List<AtencionServicioDto> nuevosServicios);

    void eliminarServicio(Long idAtencion, Long idServicio);
}
