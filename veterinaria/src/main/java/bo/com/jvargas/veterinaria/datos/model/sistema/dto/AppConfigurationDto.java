package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.enums.ApplicationType;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppConfigurationDto implements Serializable {
    private Long id;
    private ApplicationType applicationType;
    private String jsonConfig;
    private Long idAuthUser;
}
