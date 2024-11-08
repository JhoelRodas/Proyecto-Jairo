package bo.com.jvargas.veterinaria.datos.repository.inventario;

import bo.com.jvargas.veterinaria.datos.model.Estante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface EstanteRepository extends JpaRepository<Estante, Long> {
    List<Estante> findAllByDeletedFalse();

    Optional<Estante> findByIdAndDeletedFalse(Long id);
}
