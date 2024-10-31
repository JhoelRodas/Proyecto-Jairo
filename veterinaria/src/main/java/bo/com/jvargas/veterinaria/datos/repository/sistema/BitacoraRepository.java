package bo.com.jvargas.veterinaria.datos.repository.sistema;

import bo.com.jvargas.veterinaria.datos.model.sistema.Bitacora;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoProceso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BitacoraRepository extends PagingAndSortingRepository<Bitacora, Long> {

    @Query("    select b " +
            "   from Bitacora b " +
            "   where b.deleted = false " +
            "   and (cast(:dateInicio as date) is null or cast(:dateFin as date) is null or b.createdDate between :dateInicio and :dateFin) " +
            "   and (:proceso is null or b.tipoProceso = :proceso) " +
            "   and ( lower(b.log) like lower(:coincidencia) " +
            "   ) "
    )
    List<Bitacora> listByFilters(@Param("dateInicio") Date inicio,
                                 @Param("dateFin") Date fin,
                                 @Param("proceso") TipoProceso proceso,
                                 @Param("coincidencia") String coincidencia);

    @Query( "SELECT l " +
            "FROM Bitacora l " +
            "where l.deleted = false " +
            "AND l.tipoProceso = :processType ")
    Page<Bitacora> listarPorProcesoPorUsuario(@Param("processType") TipoProceso processType, Pageable pageable);
}
