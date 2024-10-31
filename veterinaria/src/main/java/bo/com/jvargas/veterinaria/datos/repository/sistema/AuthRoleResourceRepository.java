package bo.com.jvargas.veterinaria.datos.repository.sistema;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthResource;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthRole;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthRoleResourceRepository extends JpaRepository<AuthRoleResource, Long> {

    @Query( "SELECT arr " +
            "FROM AuthRoleResource arr " +
            "WHERE arr.deleted = FALSE " +
            "AND arr.id = :id ")
    Optional<AuthRoleResource> findById(@Param("id") Long id);

    @Query( "SELECT arr " +
            "FROM AuthRoleResource arr " +
            "WHERE arr.deleted = FALSE " +
            "AND arr.idAuthRole = :roleId " +
            "AND arr.idAuthResource = :resourceId ")
    Optional<AuthRoleResource> findByRoleAndResource(@Param("roleId") AuthRole authRole,
                                                     @Param("resourceId") AuthResource authResource);
}
