package bo.com.jvargas.veterinaria.datos.repository.sistema;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Long>{

    @Query("select r from AuthRole r order by r.id ASC ")
    List<AuthRole> getFindAllOrOrderById();

    @Query("select r from AuthRole r where r.name =:nombre")
    List<AuthRole> searchByName(@Param("nombre") String nombre);

    @Query("select r from AuthRole r where r.name =:nombre")
    AuthRole searchOneByName(@Param("nombre") String nombre);

    AuthRole findRolByName(String nombre);
    Optional<AuthRole> findByName(String nombre);

    @Query("select r from AuthUser u inner join u.idAuthRole r where u.id =:idUser")
    AuthRole findByIdUsuario(@Param("idUser") Long idUser);

    @Query("select r from AuthRole r where r.deleted=false AND r.roleStatus = 'ACTIVO'  order by r.id ASC ")
    List<AuthRole> getFindAllPrivilegioOrOrderById();

    @Query("select r from AuthRole r where r.deleted=false AND r.roleStatus = 'ACTIVO' AND r.name <> 'ROLE_INTEGRACION' order by r.id ASC ")
    List<AuthRole> getFindAllPrivilegioIntegrationOrOrderById();

    @Query( "SELECT ar " +
            "FROM AuthRole ar " +
            "WHERE ar.deleted = FALSE " +
            "AND ar.roleStatus <> bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState.ELIMINADO " +
            "AND ar.id = :roleId ")
    Optional<AuthRole> findById(@Param("roleId") Long roleId);

    @Query( "SELECT ar " +
            "FROM AuthRole ar " +
            "WHERE ar.deleted = FALSE " +
            "AND ar.roleStatus <> bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState.ELIMINADO " +
            "AND UPPER(ar.name) = UPPER(:name) ")
    Optional<AuthRole> finByName(@Param("name") String name);

    @Query( "SELECT ar " +
            "FROM AuthRole ar " +
            "WHERE ar.deleted = FALSE " +
            "AND ar.roleStatus <> bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState.ELIMINADO " +
            "AND (" +
            "   UPPER(ar.name) LIKE UPPER(:filter) OR " +
            "   UPPER(ar.description) LIKE UPPER(:filter) )")
    List<AuthRole> pageWithFilter(@Param("filter") String filter);

    @Modifying
    @Query( "UPDATE AuthRole ar " +
            "SET ar.deleted = TRUE, ar.roleStatus = bo.com.jvargas.veterinaria.datos.model.sistema.enums.EntityState.ELIMINADO " +
            "WHERE ar = :roleId")
    void deleteAuthRole(@Param("roleId") AuthRole authRole);
}
