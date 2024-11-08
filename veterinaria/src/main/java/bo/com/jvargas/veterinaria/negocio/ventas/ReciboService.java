package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.dto.ReciboDetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReciboDto;

import java.util.List;

public interface ReciboService {
    List<ReciboDto> listarRecibos();

    void guardarRecibo(ReciboDetalleDto nuevoRecibo);

    void anularRecibo(Long id);
}
