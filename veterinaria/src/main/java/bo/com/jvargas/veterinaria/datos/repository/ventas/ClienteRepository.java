package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.Cliente;
import bo.com.jvargas.veterinaria.datos.model.dto.ClienteDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c " +
            "from Cliente c " +
            "where c.nombre = :nombre ")
    Cliente findByNombre(@Param("nombre") String nombreAcl);

    @Query("select new bo.com.jvargas.veterinaria.datos.model.dto.ClienteDto(c) " +
            "from Cliente c " +
            "where c.deleted = false")
    List<ClienteDto> listar();

    @Query("select c " +
            "from Cliente c ")
    List<Cliente> listarCliente();

    @Query("select c " +
            "from Cliente c " +
            "where c.ci = :ci ")
    Optional<Cliente> findByCi(@Param("ci") String ci);

    Optional<Cliente> findByCiAndDeletedFalse(String ci);

    Optional<Cliente> findByIdAndDeletedFalse(Long id);
}
