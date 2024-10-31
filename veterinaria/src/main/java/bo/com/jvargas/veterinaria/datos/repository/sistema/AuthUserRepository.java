package bo.com.jvargas.veterinaria.datos.repository.sistema;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    @Query( "SELECT au " +
            "FROM AuthUser au " +
            "WHERE au.deleted = FALSE " +
            "AND au.userStatus <> bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus.ELIMINADO " +
            "AND au.id = :userId ")
    Optional<AuthUser> findById(@Param("userId") Long userId);

    @Query("select u from AuthUser u where lower(u.username) = :usuario and u.deleted = false")
    Optional<AuthUser> obtenerUsuarioParaAutentificacion(@Param("usuario") String usuario);

    Optional<AuthUser> findByEmailIgnoreCaseAndDeletedFalse(String email);

    @Query( "SELECT au " +
            "FROM AuthUser au " +
            "WHERE au.deleted = FALSE " +
            "AND au.userStatus <> bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus.ELIMINADO " +
            "AND upper(au.username) = upper(:username) ")
    Optional<AuthUser> findByUsername(@Param("username") String username);

    @Query( "SELECT new bo.com.jvargas.veterinaria.datos.model.sistema.dto.UserDto( " +
            "au.id, " +
            "au.username, " +
            "au.name, " +
            "au.lastname, " +
            "concat(au.name, ' ', au.lastname) , " +
            "au.userStatus, " +
            "au.email, " +
            "au.generatedPassword, " +
            "ar.id, " +
            "ar.name, " +
            "'', " +
            "au.tipoAuth) " +
            "FROM AuthUser au " +
            "INNER JOIN AuthRole ar ON ar = au.idAuthRole " +
            "WHERE au.deleted = FALSE " +
            "AND au.userStatus <> bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus.ELIMINADO " +
            "AND (  UPPER(au.name) LIKE UPPER(:filter) OR " +
            "       UPPER(au.lastname) LIKE UPPER(:filter) OR " +
            "       UPPER(au.email) LIKE UPPER(:filter) OR " +
            "       UPPER(au.username) LIKE UPPER(:filter) " +
            ")"
    )
    List<UserDto> pageWithFilter(@Param("filter") String filter);

    @Modifying
    @Query( "update AuthUser " +
            "set name = :nombre, " +
            "email = :correo," +
            "userStatus = bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus.ACTIVO " +
            "where deleted = false " +
            "and username = :username ")
    void updateInfoUser(@Param("username") String username,
                        @Param("nombre") String nombre,
                        @Param("correo") String correo);

    @Modifying
    @Query( "update AuthUser " +
            "set userStatus = bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus.ELIMINADO " +
            "where id in :idList ")
    void inhabilitarUsuarios(@Param("idList") List<Long> userToDeleteList);
}

