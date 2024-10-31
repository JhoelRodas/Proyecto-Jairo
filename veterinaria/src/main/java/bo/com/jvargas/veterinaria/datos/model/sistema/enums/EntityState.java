package bo.com.jvargas.veterinaria.datos.model.sistema.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum EntityState {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    ELIMINADO("ELIMINADO");
    private final String state;

    EntityState(String state) {
        this.state = state;
    }
}
