package bo.com.jvargas.veterinaria.datos.model.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BodyReporteDto {
    private FiltroReporteDto filtros;
    private List<CabeceraReporteDto> headers;
    private String tipoReporte;
}
