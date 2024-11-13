package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.DetalleProducto;
import bo.com.jvargas.veterinaria.datos.model.DetalleProductoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author GERSON
 */

public interface DetalleProductoRepository extends
        JpaRepository<DetalleProducto, DetalleProductoId> {
    List<DetalleProducto> findAllByIdRecibo_Id(Long id);
}
