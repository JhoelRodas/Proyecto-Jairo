package bo.com.jvargas.veterinaria.datos.model.dto;


import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroReporteDto {

    private List<Integer> cityList;
    private List<Long> departamentosList;
    private Date from;
    private Date to;
    private String q;
    private Long idCanalOnboarding;
    private Integer idMunicipio;
    private String nombre;
    private Long gerency;
    private Long directoraId;
    private Integer departamento;
    private Long onboardingChannel;
    private Integer city;
    private String filter;
    private Integer idMunicipios;
    private Integer gerenciaNacionalId;
    private Integer gerenciaZonalId;
    private Integer departamentoId;
    private Integer municipio;
    private String code;
    private String description;
    private String stringValue;
    private Long group;

}
