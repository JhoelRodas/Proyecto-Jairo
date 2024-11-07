package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findAllByDeletedFalse();

    Optional<Servicio> findByIdAndDeletedFalse(Long id);
}
