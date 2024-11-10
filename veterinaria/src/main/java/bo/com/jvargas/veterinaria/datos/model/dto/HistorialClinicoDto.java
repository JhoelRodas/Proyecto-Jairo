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
    private String mascota;
    private String sexo;
    private String especie;
    private String raza;
    private String duenio;

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
                .mascota(historial.getMascota().getNombre())
                .sexo(historial.getMascota().getSexo())
                .especie(historial.getMascota().getEspecie())
                .raza(historial.getMascota().getRaza())
                .duenio(historial.getMascota().getCiCliente().getNombre())
                .build();
    }
}
