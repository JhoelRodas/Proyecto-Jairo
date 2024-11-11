package bo.com.jvargas.veterinaria.negocio.compra;

import bo.com.jvargas.veterinaria.datos.model.dto.DetalleDto;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface DetalleService {
    List<DetalleDto> listarDetalles();

    List<DetalleDto> getDetalles(Long idNotaCompra);

    Optional<DetalleDto> insertarDetalle(DetalleDto detalleDto);

    List<DetalleDto> insertarDetalles(List<DetalleDto> detallesDto);

    void eliminarDetalle(Long idProducto, Long idNotaCompra);
}
