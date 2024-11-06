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
}
