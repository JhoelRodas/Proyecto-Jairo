package bo.com.jvargas.veterinaria.datos.repository.sistema;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAction;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthPrivilege;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthRoleResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthPrivilegeRepository extends JpaRepository<AuthPrivilege, Long> {
    @Query( "SELECT ap " +
            "FROM AuthPrivilege ap " +
            "WHERE ap.deleted = FALSE " +
            "AND ap.id = :privilegeId ")
    Optional<AuthPrivilege> findById(@Param("privilegeId") Long privilegeId);

    @Query( "SELECT COUNT(ap.id)" +
            "FROM AuthPrivilege ap " +
            "WHERE ap.deleted = FALSE " +
            "AND ap.idAuthRoleResource = :roleResId ")
    int countAllByRoleResource(@Param("roleResId") AuthRoleResource authRoleResource);

    @Query( "SELECT ap " +
            "FROM AuthPrivilege ap " +
            "WHERE ap.deleted = FALSE " +
            "AND ap.idAuthAction = :idAction " +
            "AND ap.idAuthRoleResource = :idRoleResource ")
    Optional<AuthPrivilege> findByActionAndRoleResource(@Param("idAction") AuthAction idAction,
                                                        @Param("idRoleResource") AuthRoleResource idRoleResource);
}
