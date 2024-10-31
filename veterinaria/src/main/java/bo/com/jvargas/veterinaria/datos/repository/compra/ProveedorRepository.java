package bo.com.jvargas.veterinaria.datos.repository.compra;

import bo.com.jvargas.veterinaria.datos.model.Proveedor;
import bo.com.jvargas.veterinaria.datos.model.dto.ProveedorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    @Query("select new bo.com.jvargas.veterinaria.datos.model.dto.ProveedorDto(p) " +
            "from Proveedor p  " +
            "where p.deleted = false ")
    List<ProveedorDto> lista();
}
