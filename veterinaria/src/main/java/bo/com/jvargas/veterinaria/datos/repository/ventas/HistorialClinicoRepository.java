package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.HistorialClinico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface HistorialClinicoRepository extends
        JpaRepository<HistorialClinico, Long> {
    List<HistorialClinico> findAllByDeletedFalse();

    Optional<HistorialClinico> findByIdAndDeletedFalse(Long id);
}
