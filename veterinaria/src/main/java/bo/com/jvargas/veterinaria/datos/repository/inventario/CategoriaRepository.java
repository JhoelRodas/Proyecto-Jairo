package bo.com.jvargas.veterinaria.datos.repository.inventario;

import bo.com.jvargas.veterinaria.datos.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author GERSON
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
