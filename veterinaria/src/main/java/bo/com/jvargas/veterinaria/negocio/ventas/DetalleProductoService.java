package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.dto.DetalleProductoDto;

import java.util.List;

/**
 * @author GERSON
 */


public interface DetalleProductoService {
    List<DetalleProductoDto> listarDetalleProductos();

    List<DetalleProductoDto> listarDetalles(Long idRecibo);

    void insertarDetalleProducto(DetalleProductoDto nuevoDetalle);

    void insertarDetallesProductos(List<DetalleProductoDto> nuevosDetalles);

    void elimiarDetalle(Long idRecibo, Long idProducto);
}
