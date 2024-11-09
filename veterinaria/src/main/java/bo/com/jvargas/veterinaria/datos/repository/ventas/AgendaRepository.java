package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author GERSON
 */

public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    List<Agenda> findAllByDeletedFalse();

    Optional<Agenda> findByIdAndDeletedFalse(Long id);
}
