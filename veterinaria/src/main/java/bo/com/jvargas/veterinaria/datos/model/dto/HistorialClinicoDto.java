package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.HistorialClinico;
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
public class HistorialClinicoDto {
    private Long id;
    private BigDecimal peso;
    private Integer fc;
    private String estadoFc;
    private Integer fr;
    private String estadoFr;
    private String mucosa;
    private String apetito;
    private String hidratacion;
    private String estadoGeneral;

    public static HistorialClinicoDto toDto(HistorialClinico historial) {
        return HistorialClinicoDto.builder()
                .id(historial.getId())
                .peso(historial.getPeso())
                .fc(historial.getFc())
                .estadoFc(historial.getEstadoFc())
                .fr(historial.getFr())
                .estadoFr(historial.getEstadoFr())
                .mucosa(historial.getMucosa())
                .apetito(historial.getApetito())
                .hidratacion(historial.getHidratacion())
                .estadoGeneral(historial.getEstadoGeneral())
                .build();
    }
}
