package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.dto.AtencionDto;

import java.util.List;

/**
 * @author GERSON
 */

public interface AtencionService{
    List<AtencionDto> listar();

    AtencionDto obtenerAtencion(Long id);

    void guardarAtencion(AtencionDto atencionNueva);

    void borrarAtencion(Long id);
}
