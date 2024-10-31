package bo.com.jvargas.veterinaria.datos.repository.sistema;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAction;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthResource;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthResourceAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthResourceActionRepository extends JpaRepository<AuthResourceAction, Long> {

    @Query( "SELECT ara " +
            "FROM AuthResourceAction ara " +
            "WHERE ara.deleted = false " +
            "AND ara.id = :id ")
    Optional<AuthResourceAction> findById(@Param("id") Long id);

    @Query( "SELECT ara " +
            "FROM AuthResourceAction ara " +
            "WHERE ara.deleted = false " +
            "AND ara.idAuthResource = :resourceId " +
            "AND ara.idAuthAction = :actionId ")
    Optional<AuthResourceAction> findByResourceAndAction(@Param("resourceId") AuthResource authResource,
                                                         @Param("actionId") AuthAction authAction);
}
