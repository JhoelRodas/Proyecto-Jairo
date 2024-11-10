package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.AtencionServicio;
import bo.com.jvargas.veterinaria.datos.model.AtencionServicioId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author GERSON
 */

public interface AtencionServicioRespository extends
        JpaRepository<AtencionServicio, AtencionServicioId> {
}
