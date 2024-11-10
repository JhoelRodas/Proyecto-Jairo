package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.AtencionServicio;
import lombok.*;

/**
 * @author GERSON
 */

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtencionServicioDto {
    private Long idServicio;
    private Long idAtencion;

    public static AtencionServicioDto toDto(AtencionServicio atencionServicio) {
        return AtencionServicioDto.builder()
                .idServicio(atencionServicio.getIdServicio().getId())
                .idAtencion(atencionServicio.getIdAtencion().getId())
                .build();
    }
}
