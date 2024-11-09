package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.dto.AgendaDto;

import java.util.List;

/**
 * @author GERSON
 */

public interface AgendaService {
    List<AgendaDto> listar();

    void registrar(AgendaDto agenda);

    void actualizar(Long id, AgendaDto agendaNueva);

    void eliminar(Long id);
}
