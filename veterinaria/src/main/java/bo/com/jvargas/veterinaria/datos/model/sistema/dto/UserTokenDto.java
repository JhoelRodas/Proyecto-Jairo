package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenDto implements Serializable {
    AuthUser authUser;
    String fechaExpiracion;
}
