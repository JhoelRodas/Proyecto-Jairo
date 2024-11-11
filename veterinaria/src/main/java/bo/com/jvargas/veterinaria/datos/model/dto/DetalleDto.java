package bo.com.jvargas.veterinaria.datos.model.dto;

import bo.com.jvargas.veterinaria.datos.model.Detalle;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author GERSON
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleDto {
    private Long idProducto;
    private String nombreProducto;
    private Long idNotaCompra;
    private Short cantidad;
    private BigDecimal monto;

    public static DetalleDto toDto(Detalle detalle) {
        return DetalleDto.builder()
                .idProducto(detalle.getId().getIdProducto())
                .idNotaCompra(detalle.getId().getIdNotaCompra())
                .cantidad(detalle.getCantidad())
                .monto(detalle.getMonto())
                .build();
    }
    
    public static DetalleDto toDto2(Detalle detalle) {
        return DetalleDto.builder()
                .nombreProducto(detalle.getIdProducto().getNombre())
                .cantidad(detalle.getCantidad())
                .monto(detalle.getMonto())
                .build();
    }
}
