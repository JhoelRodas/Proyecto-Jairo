package bo.com.jvargas.veterinaria.datos.repository;

import bo.com.jvargas.veterinaria.datos.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u " +
            "from Usuario u " +
            "where u.nombre = :nombre ")
    Usuario findByNombre(@Param("nombre") String nombreAcl);

    @Query("select u " +
            "from Usuario u ")
    List<Usuario> listar();
}
