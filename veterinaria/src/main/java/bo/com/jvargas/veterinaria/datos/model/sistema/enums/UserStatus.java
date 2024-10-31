package bo.com.jvargas.veterinaria.datos.model.sistema.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum UserStatus {
    ACTIVO("ACTIVO"),
    INACTIVO("INACTIVO"),
    BLOQUEADO("BLOQUEADO"),
    ELIMINADO("ELIMINADO");
    private final String state;
    UserStatus(String state) {
        this.state = state;
    }
}
