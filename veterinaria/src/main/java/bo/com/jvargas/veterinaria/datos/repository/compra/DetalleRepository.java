package bo.com.jvargas.veterinaria.datos.repository.compra;

import bo.com.jvargas.veterinaria.datos.model.Detalle;
import bo.com.jvargas.veterinaria.datos.model.DetalleId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author GERSON
 */

public interface DetalleRepository extends JpaRepository<Detalle, DetalleId> {

}
