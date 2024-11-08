package bo.com.jvargas.veterinaria.negocio.inventario;

import bo.com.jvargas.veterinaria.datos.model.dto.EstanteDto;

import java.util.List;

/**
 * @author GERSON
 */

public interface EstanteService {
    List<EstanteDto> listarEstantes();

    void guardarEstante(EstanteDto estanteNuevo);

    void actualizarEstante(Long id, EstanteDto estanteNuevo);

    void eliminarEstante(Long id);
}
