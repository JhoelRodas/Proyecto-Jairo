package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuxDto implements Serializable {
    Long id;
    String username;
}
