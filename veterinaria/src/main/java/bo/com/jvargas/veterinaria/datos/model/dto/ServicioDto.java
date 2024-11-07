package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Servicio;
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
public class ServicioDto {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private String descripcion;

    public static ServicioDto toDto(Servicio servicio) {
        return ServicioDto.builder()
                .id(servicio.getId())
                .nombre(servicio.getNombre())
                .precio(servicio.getPrecio())
                .descripcion(servicio.getDescripcion())
                .build();
    }

    public static Servicio toEntity(ServicioDto servicioDto) {
        return Servicio.builder()
                .id(servicioDto.getId())
                .nombre(servicioDto.getNombre())
                .precio(servicioDto.getPrecio())
                .descripcion(servicioDto.getDescripcion())
                .build();
    }
}
