package bo.com.jvargas.veterinaria.datos.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private String usuario;
    private String password;
    private String token;
}
