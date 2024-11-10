package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.HistorialClinico;
import bo.com.jvargas.veterinaria.datos.model.dto.HistorialClinicoDto;

import java.util.List;

/**
 * @author GERSON
 */

public interface HistorialClinicoService {
    List<HistorialClinicoDto> listarHistoriales();

    HistorialClinicoDto obtenerHistorial(Long id);

    void registrarHistorial(HistorialClinico historialNuevo);

    void actualizarHistorial(Long id, HistorialClinico historialNuevo);

    void eliminarHistorial(Long id);
}
