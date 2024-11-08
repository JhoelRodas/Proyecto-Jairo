package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Estante;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author GERSON
 */

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstanteDto {
    private Long id;
    private String nombre;
    private String descripcion;

    public static EstanteDto toDto(Estante estante) {
        return EstanteDto.builder()
                .id(estante.getId())
                .nombre(estante.getNombre())
                .descripcion(estante.getDescripcion())
                .build();
    }

    public static Estante toEntity(EstanteDto estanteDto) {
        return Estante.builder()
                .id(estanteDto.getId())
                .nombre(estanteDto.getNombre())
                .descripcion(estanteDto.getDescripcion())
                .build();
    }
}
