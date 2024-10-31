package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDto implements Serializable {
    private String username;
    private String password;
}
