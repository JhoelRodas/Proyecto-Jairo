package bo.com.jvargas.veterinaria.datos.repository.inventario;

import bo.com.jvargas.veterinaria.datos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findAllByDeletedFalse();

    Optional<Categoria> findByIdAndDeletedFalse(Long id);
}
