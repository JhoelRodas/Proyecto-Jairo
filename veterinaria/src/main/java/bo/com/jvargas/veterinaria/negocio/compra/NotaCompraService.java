package bo.com.jvargas.veterinaria.negocio.compra;

import bo.com.jvargas.veterinaria.datos.model.dto.NotaCompraDetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.NotaCompraDto;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface NotaCompraService {
    List<NotaCompraDto> listar();

    byte[] generarPdfNotaCompra(Long id);

    NotaCompraDto verNotaDeCompra(Long id);

    NotaCompraDto guardar(NotaCompraDetalleDto nuevaNotaCompra);

    Optional<NotaCompraDto> actualizar(Long id,
                                       NotaCompraDto notaCompraAActualizar);

    void eliminar(Long id);
}
