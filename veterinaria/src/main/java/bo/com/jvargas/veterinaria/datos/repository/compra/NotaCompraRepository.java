package bo.com.jvargas.veterinaria.datos.repository.compra;

import bo.com.jvargas.veterinaria.datos.model.NotaCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */
public interface NotaCompraRepository extends JpaRepository<NotaCompra, Long> {
    Optional<NotaCompra> findByIdAndDeletedFalse(Long id);

    List<NotaCompra> findAllByDeletedFalse();
}
