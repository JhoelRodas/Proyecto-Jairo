package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReciboRepository extends JpaRepository<Recibo,Long> {
    Optional<Recibo> findByIdAndDeletedFalse(Long id);

    List<Recibo> findAllByDeletedFalse();
}
