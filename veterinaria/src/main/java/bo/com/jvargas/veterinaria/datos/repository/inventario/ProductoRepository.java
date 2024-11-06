package bo.com.jvargas.veterinaria.datos.repository.inventario;

import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.dto.ProductoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @Query("select new bo.com.jvargas.veterinaria.datos.model.dto.ProductoDto(p) " +
            "from Producto p " +
            "where p.deleted = false ")
    List<ProductoDto> listar();

    Optional<Producto> findByIdAndDeletedFalse(Long id);
}
