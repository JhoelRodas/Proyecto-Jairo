package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.enums.UserStatus;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest implements Serializable {
    private String usuario;
    private String nombre;
    private String apellido;
    private UserStatus estado;
    private String email;
    private String telefono;
    private String password;
    private Long rolId;
    private Short bancoId;
    private List<Long> colegioIds = new ArrayList<>();
}
