package bo.com.jvargas.veterinaria.datos.repository.inventario;

import bo.com.jvargas.veterinaria.datos.model.Producto;
import bo.com.jvargas.veterinaria.datos.model.dto.MascotaDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ProductoDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepositoy extends JpaRepository<Producto, Long> {

    @Query("select new bo.com.jvargas.veterinaria.datos.model.dto.ProductoDto(p) " +
            "from Producto p " +
            "where p.deleted = false ")
    List<ProductoDto> listar();
}
