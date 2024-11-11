package bo.com.jvargas.veterinaria.negocio.ventas;

import bo.com.jvargas.veterinaria.datos.model.dto.BodyReporteDto;
import bo.com.jvargas.veterinaria.datos.model.dto.DocumentoDto;

import javax.management.OperationsException;

public interface ReporteService {
    DocumentoDto descargarReporte(BodyReporteDto bodyReporteDto) throws OperationsException;
}
