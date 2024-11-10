package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface AtencionRepository extends JpaRepository<Atencion, Long> {
    List<Atencion> findAllByDeletedFalse();

    Optional<Atencion> findByIdAndDeletedFalse(Long id);
}
