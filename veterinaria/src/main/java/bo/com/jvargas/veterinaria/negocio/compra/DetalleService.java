package bo.com.jvargas.veterinaria.negocio.compra;

import bo.com.jvargas.veterinaria.datos.model.dto.DetalleDto;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface DetalleService {
    List<DetalleDto> listarDetalles();

    Optional<DetalleDto> insertarDetalle(DetalleDto detalleDto);

    void eliminarDetalle(Long idProducto, Long idNotaCompra);
}
