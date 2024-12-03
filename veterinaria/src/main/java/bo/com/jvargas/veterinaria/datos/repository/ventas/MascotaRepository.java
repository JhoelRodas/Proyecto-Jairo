package bo.com.jvargas.veterinaria.datos.repository.ventas;

import bo.com.jvargas.veterinaria.datos.model.Mascota;
import bo.com.jvargas.veterinaria.datos.model.dto.MascotaDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    @Query("select new bo.com.jvargas.veterinaria.datos.model.dto.MascotaDto(m) " +
            "from Mascota m  " +
            "where m.deleted = false ")
    List<MascotaDto> listar();

    @Query("select m " +
            "from Mascota m ")
    List<Mascota> listarMascotas();

    Optional<Mascota> findByIdAndDeletedFalse(Long id);
}

