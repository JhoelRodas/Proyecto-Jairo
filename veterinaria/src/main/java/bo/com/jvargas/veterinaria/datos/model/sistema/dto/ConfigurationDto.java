package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationDto {
    private Long id;
    private Long asignacionId;
    private Boolean nuevo;
    private String acciones;
}
