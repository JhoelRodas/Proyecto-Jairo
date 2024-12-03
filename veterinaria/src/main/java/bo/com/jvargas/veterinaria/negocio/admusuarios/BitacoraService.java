package bo.com.jvargas.veterinaria.negocio.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.sistema.Bitacora;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.TipoProceso;

import java.util.Date;
import java.util.List;

public interface BitacoraService {
    void info(TipoProceso processType, String mensaje, Object... var2);
    void error(TipoProceso processType, String mensaje, Object... var2);
    List<Bitacora> listar(Date inicio, Date fin, TipoProceso proceso, String coincidencia);

}
