package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.dto.ReciboDetalleDto;
import bo.com.jvargas.veterinaria.datos.model.dto.ReciboDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface ReciboService {
    List<ReciboDto> listarRecibos();

    ReciboDetalleDto verRecibo(Long id);

    @Transactional(readOnly = true)
    List<ReciboDto> listarRecibosReporte(Date from, Date to);

    void guardarRecibo(ReciboDetalleDto nuevoRecibo);

    void anularRecibo(Long id);
}
