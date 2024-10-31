package bo.com.jvargas.veterinaria.datos.model.sistema.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TipoProceso {
    INICIAR_SESION,
    GESTIONAR_CLIENTE,
    GESTIONAR_MASCOTA,
    GESTIONAR_CITA_MEDICA,
    GESTIONAR_PRODUCTO,
}
