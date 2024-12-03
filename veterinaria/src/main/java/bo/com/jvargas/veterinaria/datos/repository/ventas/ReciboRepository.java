package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor; //agregado

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReciboRepository extends JpaRepository<Recibo,Long>, JpaSpecificationExecutor<Recibo>  {
    Optional<Recibo> findByIdAndDeletedFalse(Long id);

    List<Recibo> findAllByDeletedFalse();

    @Query("select r " +
            "from Recibo r  " +
            "where r.fecha between :from and :to ")
    List<Recibo> listaFiltrada(@Param("from") LocalDate from, @Param("to") LocalDate to);
}
